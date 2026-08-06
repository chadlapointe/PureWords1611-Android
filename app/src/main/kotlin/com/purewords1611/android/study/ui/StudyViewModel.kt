package com.purewords1611.android.study.ui

import com.purewords1611.android.study.data.ChapterIndexEntry
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purewords1611.android.study.data.BookmarkItem
import com.purewords1611.android.study.data.ExplanationDepth
import com.purewords1611.android.study.data.ExplanationEntry
import com.purewords1611.android.study.data.MarginalNote
import com.purewords1611.android.study.data.OrthographyMode
import com.purewords1611.android.study.data.PersonalNoteItem
import com.purewords1611.android.study.data.HighlightItem
import com.purewords1611.android.study.data.ReaderItem
import com.purewords1611.android.study.data.SeekerStepEntry
import com.purewords1611.android.study.data.SeekerTrackEntry
import com.purewords1611.android.study.data.StudyRepository
import com.purewords1611.android.study.data.TestamentSection
import com.purewords1611.android.study.data.TranslationMode
import com.purewords1611.android.study.data.VerseText
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.purewords1611.android.study.service.BibleAudioService
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

data class StudyUiState(
    val orthographyMode: OrthographyMode = OrthographyMode.ORIGINAL_1611,
    val translationMode: TranslationMode = TranslationMode.KJV_1611,
    val query: String = "",
    val verses: List<VerseText> = emptyList(),
    val chapterIndex: List<ChapterIndexEntry> = emptyList(),
    val activeChapter: ChapterIndexEntry? = null,
    val explanationDepth: ExplanationDepth = ExplanationDepth.HISTORICAL_LINGUISTIC,
    val apocryphaVerseCount: Int = 0,
    val selectedVerseId: Long? = null,
    val selectedVerseDisplayText: String? = null,
    val selectedVerseNotes: List<String> = emptyList(),
    val selectedVerseExplanations: List<ExplanationEntry> = emptyList(),
    val seekerTracks: List<SeekerTrackEntry> = emptyList(),
    val activeSeekerTrackId: String? = null,
    val seekerSteps: List<SeekerStepEntry> = emptyList(),
    val bookmarks: List<BookmarkItem> = emptyList(),
    val highlights: List<HighlightItem> = emptyList(),
    val personalNotes: List<PersonalNoteItem> = emptyList(),
    val readerItems: List<ReaderItem> = emptyList(),
    val highlightedVerseId: Long? = null,
    val isReadingChapter: Boolean = false,
    val scrollToVerseId: Long? = null,
    val importProgress: Float = 0f,
)

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class StudyViewModel @Inject constructor(
    private val repository: StudyRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    private val orthographyMode = MutableStateFlow(OrthographyMode.ORIGINAL_1611)
    private val translationMode = MutableStateFlow(TranslationMode.KJV_1611)
    private val query = MutableStateFlow("")
    private val selectedVerseId = MutableStateFlow<Long?>(null)
    private val activeChapter = MutableStateFlow<ChapterIndexEntry?>(null)
    private val activeSeekerTrackId = MutableStateFlow<String?>(null)
    private val highlightedVerseId = MutableStateFlow<Long?>(null)
    private val isReadingChapter = MutableStateFlow(value = false)
    private val scrollToVerseId = MutableStateFlow<Long?>(null)

    private var audioService: BibleAudioService? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as BibleAudioService.AudioBinder
            val s = binder.getService()
            audioService = s
            isBound = true
            
            viewModelScope.launch {
                s.isPlaying.collect { isReadingChapter.value = it }
            }
            viewModelScope.launch {
                s.currentVerseId.collect { highlightedVerseId.value = it }
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            audioService = null
            isBound = false
        }
    }

    private val chapterIndexFlow = repository.observeChapterIndex()
    private val seekerTracksFlow = repository.observeSeekerTracks()

    private val versesFlow = combine(query, activeChapter) { queryText, chapter -> 
        queryText.trim() to chapter 
    }.flatMapLatest { (queryText, _) ->
        if (queryText.isNotBlank()) {
            repository.observeVerses(queryText)
        } else {
            repository.observeVerses("")
        }
    }.distinctUntilChanged()

    private val readerItemsFlow = versesFlow.map { verses ->
        val items = mutableListOf<ReaderItem>()
        if (query.value.isNotBlank()) {
            verses.forEach { items.add(ReaderItem.VerseLine(it)) }
        } else {
            var lastSection: TestamentSection? = null
            var lastBook: String? = null
            var lastChapter: Int? = null
            
            verses.forEach { verse ->
                if (verse.section != lastSection) {
                    items.add(ReaderItem.SectionHeader(verse.section))
                    lastSection = verse.section
                }
                if (verse.book != lastBook) {
                    items.add(ReaderItem.BookHeader(verse.book, verse.bookOriginal))
                    lastBook = verse.book
                    lastChapter = null 
                }
                if (verse.chapter != lastChapter) {
                    items.add(ReaderItem.ChapterHeader(verse.book, verse.chapter))
                    lastChapter = verse.chapter
                }
                items.add(ReaderItem.VerseLine(verse))
            }
        }
        items
    }.flowOn(Dispatchers.Default)

    @Suppress("UNCHECKED_CAST")
    val uiState: StateFlow<StudyUiState> = combine(
        listOf(
            orthographyMode,
            translationMode,
            query,
            selectedVerseId,
            highlightedVerseId,
            isReadingChapter,
            scrollToVerseId,
            chapterIndexFlow,
            repository.observeExplanationDepth(),
            seekerTracksFlow,
            activeSeekerTrackId.flatMapLatest { trackId ->
                if (trackId == null) {
                    flowOf(emptyList())
                } else {
                    repository.observeSeekerSteps(trackId)
                }
            },
            repository.observeBookmarks(),
            repository.observeHighlights(),
            repository.observePersonalNotes(),
            versesFlow,
            readerItemsFlow,
            repository.observeImportProgress(),
            selectedVerseId.flatMapLatest { verseId ->
                if (verseId == null) {
                    flowOf(emptyList())
                } else {
                    repository.observeMarginalNotes(verseId)
                }
            },
            combine(selectedVerseId, repository.observeExplanationDepth()) { verseId, depth ->
                verseId to depth
            }.flatMapLatest { (verseId, depth) ->
                if (verseId == null) {
                    flowOf(emptyList())
                } else {
                    repository.observeExplanations(verseId = verseId, level = depth)
                }
            }
        )
    ) { args: Array<Any?> ->
        val mode = args[0] as OrthographyMode
        val tMode = args[1] as TranslationMode
        val currentQuery = args[2] as String
        val activeVerseId = args[3] as Long?
        val hVerseId = args[4] as Long?
        val reading = args[5] as Boolean
        val sToVerseId = args[6] as Long?
        val chapterIndex = args[7] as List<ChapterIndexEntry>
        val explanationDepth = args[8] as ExplanationDepth
        val seekerTracks = args[9] as List<SeekerTrackEntry>
        val seekerSteps = args[10] as List<SeekerStepEntry>
        val bookmarks = args[11] as List<BookmarkItem>
        val highlights = args[12] as List<HighlightItem>
        val personalNotes = args[13] as List<PersonalNoteItem>
        val verses = args[14] as List<VerseText>
        val items = args[15] as List<ReaderItem>
        val progress = args[16] as Float
        val notes = args[17] as List<MarginalNote>
        val explanations = args[18] as List<ExplanationEntry>

        val apocryphaCount = chapterIndex.count { it.section == TestamentSection.APOCRYPHA }

        android.util.Log.i("StudyViewModel", "uiState update: ${verses.size} verses. ActiveChapter: ${activeChapter.value?.book} ${activeChapter.value?.chapter}")

        val resolvedActiveChapter = activeChapter.value
            ?: chapterIndex.firstOrNull()
        
        android.util.Log.i("StudyViewModel", "UI State lambda. Verses: ${verses.size}, Items: ${items.size}")

        val resolvedTrackId = activeSeekerTrackId.value
            ?: seekerTracks.firstOrNull()?.trackId
        StudyUiState(
            orthographyMode = mode,
            translationMode = tMode,
            query = currentQuery,
            chapterIndex = chapterIndex,
            activeChapter = resolvedActiveChapter,
            explanationDepth = explanationDepth,
            verses = verses,
            readerItems = items,
            apocryphaVerseCount = apocryphaCount,
            selectedVerseId = activeVerseId,
            selectedVerseDisplayText = verses.firstOrNull { it.id == activeVerseId }?.let {
                if (tMode == TranslationMode.ESV) {
                    it.comparativeText ?: it.modernizedText
                } else {
                    if (mode == OrthographyMode.ORIGINAL_1611) it.originalText else it.modernizedText
                }
            },
            selectedVerseNotes = notes.map { it.note },
            selectedVerseExplanations = explanations,
            seekerTracks = seekerTracks,
            activeSeekerTrackId = resolvedTrackId,
            seekerSteps = seekerSteps,
            bookmarks = bookmarks,
            highlights = highlights,
            personalNotes = personalNotes,
            highlightedVerseId = hVerseId,
            isReadingChapter = reading,
            scrollToVerseId = sToVerseId,
            importProgress = progress,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = StudyUiState()
    )

    init {
        viewModelScope.launch {
            try {
                android.util.Log.i("StudyViewModel", "Seeding database if empty...")
                repository.seedIfEmpty()
                android.util.Log.i("StudyViewModel", "Seeding check completed.")
            } catch (e: Exception) {
                android.util.Log.e("StudyViewModel", "Seeding failed", e)
            }
        }
        viewModelScope.launch {
            chapterIndexFlow.collect { chapters ->
                if (activeChapter.value == null) {
                    activeChapter.value = chapters.firstOrNull()
                }
            }
        }
        viewModelScope.launch {
            seekerTracksFlow.collect { tracks ->
                if (activeSeekerTrackId.value == null) {
                    activeSeekerTrackId.value = tracks.firstOrNull()?.trackId
                }
            }
        }
        Intent(context, BibleAudioService::class.java).also { intent ->
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (isBound) {
            context.unbindService(connection)
            isBound = false
        }
    }

    fun updateQuery(value: String) {
        query.value = value
    }

    fun setOrthographyMode(mode: OrthographyMode) {
        orthographyMode.value = mode
    }

    fun toggleTranslationMode() {
        translationMode.value = if (translationMode.value == TranslationMode.KJV_1611) {
            TranslationMode.ESV
        } else {
            TranslationMode.KJV_1611
        }
    }

    fun selectVerse(verseId: Long) {
        selectedVerseId.value = verseId
    }

    fun clearSelectedVerse() {
        selectedVerseId.value = null
    }

    fun selectChapter(book: String, chapter: Int) {
        viewModelScope.launch {
            val verses = repository.getChapterVerses(book, chapter)
            val firstVerseId = verses.firstOrNull()?.id
            if (firstVerseId != null) {
                scrollToVerseId.value = firstVerseId
                activeChapter.value = uiState.value.chapterIndex.firstOrNull {
                    (it.book == book) && (it.chapter == chapter)
                }
            }
        }
    }

    fun onScrollToVerseHandled() {
        scrollToVerseId.value = null
    }

    fun updateActiveChapterFromScroll(book: String, chapter: Int) {
        val current = activeChapter.value
        if (current?.book != book || current.chapter != chapter) {
            activeChapter.value = uiState.value.chapterIndex.firstOrNull {
                (it.book == book) && (it.chapter == chapter)
            }
        }
    }

    fun setExplanationDepth(depth: ExplanationDepth) {
        viewModelScope.launch {
            repository.setExplanationDepth(depth)
        }
    }

    fun selectSeekerTrack(trackId: String) {
        activeSeekerTrackId.value = trackId
    }

    fun addBookmarkForSelectedVerse() {
        val verseId = selectedVerseId.value ?: return
        viewModelScope.launch {
            repository.addBookmark(verseId)
        }
    }

    fun savePersonalNoteForSelectedVerse(note: String) {
        val verseId = selectedVerseId.value ?: return
        val trimmed = note.trim()
        if (trimmed.isBlank()) return
        viewModelScope.launch {
            repository.savePersonalNote(verseId = verseId, note = trimmed)
        }
    }

    fun addHighlightForSelectedVerse(colorName: String = "yellow") {
        val verseId = selectedVerseId.value ?: return
        viewModelScope.launch {
            repository.addHighlight(verseId = verseId, colorName = colorName)
        }
    }

    fun readFullChapter(startVerseId: Long? = null) {
        val allVerses = uiState.value.verses
        if (allVerses.isEmpty()) return
        
        val startIndex = if (startVerseId != null) {
            allVerses.indexOfFirst { it.id == startVerseId }.coerceAtLeast(0)
        } else 0
        
        audioService?.playQueue(
            verses = allVerses,
            startIndex = startIndex,
            orthographyMode = orthographyMode.value,
            translationMode = translationMode.value
        )
    }

    fun speakSelectedVerse() {
        val verseId = selectedVerseId.value ?: return
        val verse = uiState.value.verses.firstOrNull { it.id == verseId } ?: return
        audioService?.playQueue(
            verses = listOf(verse),
            startIndex = 0,
            orthographyMode = orthographyMode.value,
            translationMode = translationMode.value
        )
    }

    fun stopReading() {
        context.startService(Intent(context, BibleAudioService::class.java).apply {
            action = BibleAudioService.ACTION_PAUSE
        })
    }
}
