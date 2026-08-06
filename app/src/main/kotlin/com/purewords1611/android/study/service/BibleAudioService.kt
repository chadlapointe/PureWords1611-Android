package com.purewords1611.android.study.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import com.purewords1611.android.MainActivity
import com.purewords1611.android.R
import com.purewords1611.android.study.data.OrthographyMode
import com.purewords1611.android.study.data.TranslationMode
import com.purewords1611.android.study.data.VerseText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class BibleAudioService : Service(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var mediaSession: MediaSessionCompat? = null
    private val queue = mutableListOf<VerseText>()
    private var currentIndex = -1
    
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()
    
    private val _currentVerseId = MutableStateFlow<Long?>(null)
    val currentVerseId: StateFlow<Long?> = _currentVerseId.asStateFlow()

    private var currentOrthographyMode = OrthographyMode.ORIGINAL_1611
    private var currentTranslationMode = TranslationMode.KJV_1611

    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    inner class AudioBinder : Binder() {
        fun getService(): BibleAudioService = this@BibleAudioService
    }

    private val binder = AudioBinder()

    override fun onCreate() {
        super.onCreate()
        tts = TextToSpeech(this, this)
        
        mediaSession = MediaSessionCompat(this, "BibleAudioService").apply {
            setCallback(object : MediaSessionCompat.Callback() {
                override fun onPlay() {
                    resume()
                }
                override fun onPause() {
                    pause()
                }
                override fun onSkipToNext() {
                    skipToNext()
                }
                override fun onSkipToPrevious() {
                    skipToPrevious()
                }
                override fun onStop() {
                    stopSelf()
                }
            })
            isActive = true
        }
        
        createNotificationChannel()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.US
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    if (utteranceId?.startsWith("verse_") == true) {
                        val id = utteranceId.removePrefix("verse_").toLongOrNull()
                        _currentVerseId.value = id
                    }
                    updatePlaybackState(PlaybackStateCompat.STATE_PLAYING)
                }

                override fun onDone(utteranceId: String?) {
                    if (utteranceId?.startsWith("verse_") == true) {
                        skipToNext()
                    }
                }

                override fun onError(utteranceId: String?) {
                    updatePlaybackState(PlaybackStateCompat.STATE_ERROR)
                    _isPlaying.value = false
                }
            })
        }
    }

    fun playQueue(
        verses: List<VerseText>, 
        startIndex: Int = 0,
        orthographyMode: OrthographyMode = OrthographyMode.ORIGINAL_1611,
        translationMode: TranslationMode = TranslationMode.KJV_1611
    ) {
        queue.clear()
        queue.addAll(verses)
        currentIndex = startIndex
        currentOrthographyMode = orthographyMode
        currentTranslationMode = translationMode
        playCurrent()
    }

    private fun playCurrent() {
        if (currentIndex !in queue.indices) {
            _isPlaying.value = false
            _currentVerseId.value = null
            stopForeground(STOP_FOREGROUND_REMOVE)
            return
        }
        
        val verse = queue[currentIndex]
        val rawText = if (currentTranslationMode == TranslationMode.ESV) {
            verse.comparativeText ?: verse.modernizedText
        } else {
            if (currentOrthographyMode == OrthographyMode.ORIGINAL_1611) verse.originalText else verse.modernizedText
        }
        
        val textToSpeak = rawText?.replace("_", "") ?: ""
        
        _isPlaying.value = true
        updateMetadata(verse)
        updatePlaybackState(PlaybackStateCompat.STATE_PLAYING)
        
        startForeground(NOTIFICATION_ID, createNotification())
        
        tts?.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, "verse_${verse.id}")
    }

    private fun resume() {
        if (currentIndex in queue.indices) {
            playCurrent()
        }
    }

    private fun pause() {
        tts?.stop()
        _isPlaying.value = false
        updatePlaybackState(PlaybackStateCompat.STATE_PAUSED)
        notificationManager.notify(NOTIFICATION_ID, createNotification())
    }

    private fun skipToNext() {
        if (currentIndex < queue.size - 1) {
            currentIndex++
            playCurrent()
        } else {
            _isPlaying.value = false
            _currentVerseId.value = null
            updatePlaybackState(PlaybackStateCompat.STATE_STOPPED)
            stopForeground(STOP_FOREGROUND_REMOVE)
        }
    }

    private fun skipToPrevious() {
        if (currentIndex > 0) {
            currentIndex--
            playCurrent()
        }
    }

    private fun updateMetadata(verse: VerseText) {
        val metadata = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, "${verse.book} ${verse.chapter}:${verse.verse}")
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, "Pure Words 1611")
            .build()
        mediaSession?.setMetadata(metadata)
    }

    private fun updatePlaybackState(state: Int) {
        val playbackState = PlaybackStateCompat.Builder()
            .setActions(
                PlaybackStateCompat.ACTION_PLAY or
                PlaybackStateCompat.ACTION_PAUSE or
                PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or
                PlaybackStateCompat.ACTION_STOP or
                PlaybackStateCompat.ACTION_PLAY_PAUSE
            )
            .setState(state, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 1.0f)
            .build()
        mediaSession?.setPlaybackState(playbackState)
    }

    private fun createNotification(): Notification {
        val controller = mediaSession?.controller
        val mediaMetadata = controller?.metadata
        val description = mediaMetadata?.description

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(description?.title)
            .setContentText(description?.subtitle)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession?.sessionToken)
                    .setShowActionsInCompactView(0, 1, 2)
            )

        val skipPrevIntent = PendingIntent.getService(this, 1, Intent(this, BibleAudioService::class.java).apply { action = ACTION_PREV }, PendingIntent.FLAG_IMMUTABLE)
        val playPauseIntent = if (_isPlaying.value) {
            PendingIntent.getService(this, 2, Intent(this, BibleAudioService::class.java).apply { action = ACTION_PAUSE }, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getService(this, 2, Intent(this, BibleAudioService::class.java).apply { action = ACTION_PLAY }, PendingIntent.FLAG_IMMUTABLE)
        }
        val skipNextIntent = PendingIntent.getService(this, 3, Intent(this, BibleAudioService::class.java).apply { action = ACTION_NEXT }, PendingIntent.FLAG_IMMUTABLE)

        builder.addAction(android.R.drawable.ic_media_previous, "Previous", skipPrevIntent)
        if (_isPlaying.value) {
            builder.addAction(android.R.drawable.ic_media_pause, "Pause", playPauseIntent)
        } else {
            builder.addAction(android.R.drawable.ic_media_play, "Play", playPauseIntent)
        }
        builder.addAction(android.R.drawable.ic_media_next, "Next", skipNextIntent)

        return builder.build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Bible Audio", NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> resume()
            ACTION_PAUSE -> pause()
            ACTION_NEXT -> skipToNext()
            ACTION_PREV -> skipToPrevious()
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onDestroy() {
        super.onDestroy()
        tts?.stop()
        tts?.shutdown()
        mediaSession?.release()
    }

    companion object {
        private const val CHANNEL_ID = "bible_audio_channel"
        private const val NOTIFICATION_ID = 1
        
        const val ACTION_PLAY = "com.purewords1611.android.ACTION_PLAY"
        const val ACTION_PAUSE = "com.purewords1611.android.ACTION_PAUSE"
        const val ACTION_NEXT = "com.purewords1611.android.ACTION_NEXT"
        const val ACTION_PREV = "com.purewords1611.android.ACTION_PREV"
    }
}
