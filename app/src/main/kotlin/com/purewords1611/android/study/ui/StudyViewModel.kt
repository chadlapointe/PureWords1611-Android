package com.purewords1611.android.study.ui

import com.purewords1611.android.study.data.ChapterIndexEntry
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purewords1611.android.study.data.BookmarkItem
import com.purewords1611.android.study.data.ExplanationDepth
import com.purewords1611.android.study.data.ExplanationEntry
import com.purewords1611.android.study.data.OrthographyMode
import com.purewords1611.android.study.data.PersonalNoteItem
import com.purewords1611.android.study.data.HighlightItem
import com.purewords1611.android.study.data.SeekerStepEntry
import com.purewords1611.android.study.data.SeekerTrackEntry
import com.purewords1611.android.study.data.StudyRepository
import com.purewords1611.android.study.data.TestamentSection
import com.purewords1611.android.study.data.VerseText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class StudyUiState(
    val orthographyMode: OrthographyMode = OrthographyMode.ORIGINAL_1611,
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
    val personalNotes: List<PersonalNoteItem> = emptyList()
)

@HiltViewModel
class StudyViewModel @Inject constructor(
    private val repository: StudyRepository
) : ViewModel() {
    private val orthographyMode = MutableStateFlow(OrthographyMode.ORIGINAL_1611)
    private val query = MutableStateFlow("")
    private val selectedVerseId = MutableStateFlow<Long?>(null)
    private val activeChapter = MutableStateFlow<ChapterIndexEntry?>(null)
    private val activeSeekerTrackId = MutableStateFlow<String?>(null)

    private val chapterIndexFlow = repository.observeChapterIndex()
    private val seekerTracksFlow = repository.observeSeekerTracks()

    val uiState: StateFlow<StudyUiState> = combine(
        orthographyMode,
        query,
        selectedVerseId,
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
        combine(query, activeChapter) { queryText, chapter -> queryText.trim() to chapter }
            .flatMapLatest { (queryText, chapter) ->
                when {
                    queryText.isNotBlank() -> repository.observeVerses(queryText)
                    chapter != null -> repository.observeChapterVerses(chapter.book, chapter.chapter)
                    else -> repository.observeVerses("")
                }
            },
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
    ) { mode, currentQuery, activeVerseId, chapterIndex, explanationDepth, seekerTracks, seekerSteps, bookmarks, highlights, personalNotes, verses, notes, explanations ->
        val resolvedActiveChapter = activeChapter.value
            ?: chapterIndex.firstOrNull()
        val resolvedTrackId = activeSeekerTrackId.value
            ?: seekerTracks.firstOrNull()?.trackId
        StudyUiState(
            orthographyMode = mode,
            query = currentQuery,
            chapterIndex = chapterIndex,
            activeChapter = resolvedActiveChapter,
            explanationDepth = explanationDepth,
            verses = verses,
            apocryphaVerseCount = verses.count { it.section == TestamentSection.APOCRYPHA },
            selectedVerseId = activeVerseId,
            selectedVerseDisplayText = verses.firstOrNull { it.id == activeVerseId }?.let {
                if (mode == OrthographyMode.ORIGINAL_1611) it.originalText else it.modernizedText
            },
            selectedVerseNotes = notes.map { it.note },
            selectedVerseExplanations = explanations,
            seekerTracks = seekerTracks,
            activeSeekerTrackId = resolvedTrackId,
            seekerSteps = seekerSteps,
            bookmarks = bookmarks,
            highlights = highlights,
            personalNotes = personalNotes
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = StudyUiState()
    )

    init {
        viewModelScope.launch {
            repository.seedIfEmpty()
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
    }

    fun updateQuery(value: String) {
        query.value = value
    }

    fun setOrthographyMode(mode: OrthographyMode) {
        orthographyMode.value = mode
    }

    fun selectVerse(verseId: Long) {
        selectedVerseId.value = verseId
    }

    fun clearSelectedVerse() {
        selectedVerseId.value = null
    }

    fun selectChapter(book: String, chapter: Int) {
        val next = uiState.value.chapterIndex.firstOrNull {
            it.book == book && it.chapter == chapter
        }
        if (next != null) {
            activeChapter.value = next
            selectedVerseId.value = null
        }
    }

    fun goToPreviousChapter() {
        val chapters = uiState.value.chapterIndex
        val current = activeChapter.value ?: return
        val index = chapters.indexOfFirst {
            it.book == current.book && it.chapter == current.chapter
        }
        if (index > 0) {
            activeChapter.value = chapters[index - 1]
            selectedVerseId.value = null
        }
    }

    fun goToNextChapter() {
        val chapters = uiState.value.chapterIndex
        val current = activeChapter.value ?: return
        val index = chapters.indexOfFirst {
            it.book == current.book && it.chapter == current.chapter
        }
        if (index >= 0 && index < chapters.lastIndex) {
            activeChapter.value = chapters[index + 1]
            selectedVerseId.value = null
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
}
