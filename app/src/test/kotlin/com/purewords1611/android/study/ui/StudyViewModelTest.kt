package com.purewords1611.android.study.ui

import com.purewords1611.android.study.data.ChapterIndexEntry
import com.purewords1611.android.study.data.BookmarkItem
import com.purewords1611.android.study.data.ExplanationDepth
import com.purewords1611.android.study.data.ExplanationEntry
import com.purewords1611.android.study.data.HighlightItem
import com.purewords1611.android.study.data.MarginalNote
import com.purewords1611.android.study.data.OrthographyMode
import com.purewords1611.android.study.data.PersonalNoteItem
import com.purewords1611.android.study.data.SeekerStepEntry
import com.purewords1611.android.study.data.SeekerTrackEntry
import com.purewords1611.android.study.data.StudyRepository
import com.purewords1611.android.study.data.TestamentSection
import com.purewords1611.android.study.data.VerseText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class StudyViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updates orthography mode`() = runTest {
        val viewModel = StudyViewModel(FakeStudyRepository())

        viewModel.setOrthographyMode(OrthographyMode.MODERNIZED)
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(OrthographyMode.MODERNIZED, viewModel.uiState.value.orthographyMode)
    }

    @Test
    fun `loads marginal notes for selected verse`() = runTest {
        val viewModel = StudyViewModel(FakeStudyRepository())

        viewModel.selectVerse(1L)
        dispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value.selectedVerseNotes.isNotEmpty())
        assertEquals("Anchor note", viewModel.uiState.value.selectedVerseNotes.first())
    }

    @Test
    fun `updates explanation depth preference`() = runTest {
        val repository = FakeStudyRepository()
        val viewModel = StudyViewModel(repository)

        viewModel.setExplanationDepth(ExplanationDepth.MINIMAL)
        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(ExplanationDepth.MINIMAL, viewModel.uiState.value.explanationDepth)
    }
}

private class FakeStudyRepository : StudyRepository {
    private val explanationDepth = MutableStateFlow(ExplanationDepth.HISTORICAL_LINGUISTIC)

    private val verses = listOf(
        VerseText(
            id = 1L,
            book = "Psalmes",
            chapter = 12,
            verse = 6,
            section = TestamentSection.OLD_TESTAMENT,
            originalText = "Original text",
            modernizedText = "Modern text",
            hasItalicWords = false
        )
    )

    override fun observeVerses(query: String): Flow<List<VerseText>> {
        return flowOf(
            if (query.isBlank()) verses else verses.filter { it.originalText.contains(query, ignoreCase = true) }
        )
    }

    override fun observeChapterVerses(book: String, chapter: Int): Flow<List<VerseText>> {
        return flowOf(verses.filter { it.book == book && it.chapter == chapter })
    }

    override fun observeChapterIndex(): Flow<List<ChapterIndexEntry>> {
        return flowOf(
            listOf(
                ChapterIndexEntry(
                    book = "Psalmes",
                    chapter = 12,
                    section = TestamentSection.OLD_TESTAMENT,
                    firstCanonicalOrder = 1
                )
            )
        )
    }

    override fun observeMarginalNotes(verseId: Long): Flow<List<MarginalNote>> {
        return if (verseId == 1L) {
            flowOf(listOf(MarginalNote(id = 1L, verseId = 1L, note = "Anchor note")))
        } else {
            flowOf(emptyList())
        }
    }

    override fun observeExplanations(
        verseId: Long,
        level: ExplanationDepth
    ): Flow<List<ExplanationEntry>> {
        return if (verseId == 1L) {
            flowOf(
                listOf(
                    ExplanationEntry(
                        id = "e1",
                        verseId = 1L,
                        level = level,
                        contentMarkdown = "Explanation at ${level.name}"
                    )
                )
            )
        } else {
            flowOf(emptyList())
        }
    }

    override fun observeExplanationDepth(): Flow<ExplanationDepth> = explanationDepth

    override suspend fun setExplanationDepth(level: ExplanationDepth) {
        explanationDepth.value = level
    }

    override fun observeSeekerTracks(): Flow<List<SeekerTrackEntry>> {
        return flowOf(
            listOf(
                SeekerTrackEntry(
                    trackId = "FULL_EVIDENCE",
                    title = "Full",
                    description = "full path"
                )
            )
        )
    }

    override fun observeSeekerSteps(trackId: String): Flow<List<SeekerStepEntry>> {
        return flowOf(
            listOf(
                SeekerStepEntry(
                    stepId = "s1",
                    trackId = trackId,
                    sequence = 1,
                    title = "Start",
                    bodyMarkdown = "Read"
                )
            )
        )
    }

    override fun observeBookmarks(): Flow<List<BookmarkItem>> = flowOf(emptyList())

    override fun observeHighlights(): Flow<List<HighlightItem>> = flowOf(emptyList())

    override fun observePersonalNotes(): Flow<List<PersonalNoteItem>> = flowOf(emptyList())

    override suspend fun addBookmark(verseId: Long) = Unit

    override suspend fun addHighlight(verseId: Long, colorName: String) = Unit

    override suspend fun savePersonalNote(verseId: Long, note: String) = Unit

    override suspend fun seedIfEmpty() = Unit
}
