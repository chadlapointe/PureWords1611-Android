package com.purewords1611.android.study.data

import com.purewords1611.android.study.data.importer.CanonicalDataLoader
import com.purewords1611.android.study.data.importer.StudyDataImporter
import com.purewords1611.android.study.data.local.BookmarkDao
import com.purewords1611.android.study.data.local.BookmarkEntity
import com.purewords1611.android.study.data.local.ChapterIndexRow
import com.purewords1611.android.study.data.local.ExplanationDao
import com.purewords1611.android.study.data.local.HighlightDao
import com.purewords1611.android.study.data.local.HighlightEntity
import com.purewords1611.android.study.data.local.MarginalNoteDao
import com.purewords1611.android.study.data.local.PersonalNoteDao
import com.purewords1611.android.study.data.local.PersonalNoteEntity
import com.purewords1611.android.study.data.local.ReadingPreferenceDao
import com.purewords1611.android.study.data.local.VerseDao
import com.purewords1611.android.study.data.local.VerseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface StudyRepository {
    fun observeVerses(query: String): Flow<List<VerseText>>
    fun observeChapterVerses(book: String, chapter: Int): Flow<List<VerseText>>
    fun observeChapterIndex(): Flow<List<ChapterIndexEntry>>
    suspend fun getChapterVerses(book: String, chapter: Int): List<VerseText>
    fun observeMarginalNotes(verseId: Long): Flow<List<MarginalNote>>
    fun observeExplanations(verseId: Long, level: ExplanationDepth): Flow<List<ExplanationEntry>>
    fun observeExplanationDepth(): Flow<ExplanationDepth>
    fun observeSeekerTracks(): Flow<List<SeekerTrackEntry>>
    fun observeSeekerSteps(trackId: String): Flow<List<SeekerStepEntry>>
    fun observeBookmarks(): Flow<List<BookmarkItem>>
    fun observeHighlights(): Flow<List<HighlightItem>>
    fun observePersonalNotes(): Flow<List<PersonalNoteItem>>
    fun observeImportProgress(): Flow<Float>
    suspend fun addBookmark(verseId: Long)
    suspend fun addHighlight(verseId: Long, colorName: String)
    suspend fun savePersonalNote(verseId: Long, note: String)
    suspend fun setExplanationDepth(level: ExplanationDepth)
    suspend fun seedIfEmpty()
}

@Singleton
class OfflineStudyRepository @Inject constructor(
    private val verseDao: VerseDao,
    private val marginalNoteDao: MarginalNoteDao,
    private val explanationDao: ExplanationDao,
    private val readingPreferenceDao: ReadingPreferenceDao,
    private val bookmarkDao: BookmarkDao,
    private val highlightDao: HighlightDao,
    private val personalNoteDao: PersonalNoteDao,
    private val canonicalDataLoader: CanonicalDataLoader,
    private val dataImporter: StudyDataImporter,
) : StudyRepository {

    override fun observeVerses(query: String): Flow<List<VerseText>> {
        val source = if (query.isBlank()) {
            verseDao.observeAllVerses()
        } else {
            val ftsQuery = query.trim().split("\\s+".toRegex()).asSequence()
                .filter { it.isNotBlank() }
                .joinToString(" ") { "$it*" }
            
            if (ftsQuery.isBlank()) {
                verseDao.observeAllVerses()
            } else {
                verseDao.searchVerses(ftsQuery)
            }
        }

        return source.map { entities -> entities.map { it.toModel() } }
    }

    override fun observeChapterVerses(book: String, chapter: Int): Flow<List<VerseText>> {
        return verseDao.observeVersesByChapter(book = book, chapter = chapter)
            .map { entities -> entities.map { it.toModel() } }
    }

    override fun observeChapterIndex(): Flow<List<ChapterIndexEntry>> {
        return verseDao.observeChapterIndex().map { rows ->
            rows.map { it.toModel() }
        }
    }

    override suspend fun getChapterVerses(book: String, chapter: Int): List<VerseText> {
        return verseDao.getVersesByChapter(book, chapter).map { it.toModel() }
    }

    override fun observeMarginalNotes(verseId: Long): Flow<List<MarginalNote>> {
        return marginalNoteDao.observeByVerse(verseId).map { entities ->
            entities.map { MarginalNote(id = it.id, verseId = it.verseId, note = it.note) }
        }
    }

    override fun observeExplanations(
        verseId: Long,
        level: ExplanationDepth
    ): Flow<List<ExplanationEntry>> {
        return explanationDao.observeByVerseAndLevel(verseId = verseId, level = level.name)
            .map { entities ->
                entities.map {
                    ExplanationEntry(
                        id = it.id,
                        verseId = it.verseId,
                        level = ExplanationDepth.valueOf(it.level),
                        contentMarkdown = it.contentMarkdown
                    )
                }
            }
    }

    override fun observeExplanationDepth(): Flow<ExplanationDepth> {
        return readingPreferenceDao.observePreferences().map { preferences ->
            if (preferences == null) {
                ExplanationDepth.HISTORICAL_LINGUISTIC
            } else {
                ExplanationDepth.valueOf(preferences.explanationLevel)
            }
        }
    }

    override fun observeSeekerTracks(): Flow<List<SeekerTrackEntry>> = flow {
        emit(
            canonicalDataLoader.loadSeekerTracks().map {
                SeekerTrackEntry(
                    trackId = it.trackId,
                    title = it.title,
                    description = it.description
                )
            }
        )
    }

    override fun observeSeekerSteps(trackId: String): Flow<List<SeekerStepEntry>> = flow {
        emit(
            canonicalDataLoader.loadSeekerSteps()
                .asSequence()
                .filter { it.trackId == trackId }
                .sortedBy { it.sequence }
                .map {
                    SeekerStepEntry(
                        stepId = it.stepId,
                        trackId = it.trackId,
                        sequence = it.sequence,
                        title = it.title,
                        bodyMarkdown = it.bodyMarkdown,
                    )
                }
                .toList()
        )
    }

    override fun observeBookmarks(): Flow<List<BookmarkItem>> {
        return bookmarkDao.observeAll().map { entities ->
            entities.map {
                BookmarkItem(
                    id = it.id,
                    verseId = it.verseId,
                    createdAtEpochMillis = it.createdAtEpochMillis
                )
            }
        }
    }

    override fun observePersonalNotes(): Flow<List<PersonalNoteItem>> {
        return personalNoteDao.observeAll().map { entities ->
            entities.map {
                PersonalNoteItem(
                    id = it.id,
                    verseId = it.verseId,
                    note = it.note,
                    updatedAtEpochMillis = it.updatedAtEpochMillis
                )
            }
        }
    }

    override fun observeImportProgress(): Flow<Float> = dataImporter.importProgress

    override fun observeHighlights(): Flow<List<HighlightItem>> {
        return highlightDao.observeAll().map { entities ->
            entities.map {
                HighlightItem(
                    id = it.id,
                    verseId = it.verseId,
                    colorName = it.colorName,
                    createdAtEpochMillis = it.createdAtEpochMillis
                )
            }
        }
    }

    override suspend fun addBookmark(verseId: Long) {
        bookmarkDao.insert(
            BookmarkEntity(
                verseId = verseId,
                createdAtEpochMillis = System.currentTimeMillis()
            )
        )
    }

    override suspend fun savePersonalNote(verseId: Long, note: String) {
        personalNoteDao.upsert(
            PersonalNoteEntity(
                verseId = verseId,
                note = note,
                updatedAtEpochMillis = System.currentTimeMillis(),
            )
        )
    }

    override suspend fun addHighlight(verseId: Long, colorName: String) {
        highlightDao.insert(
            HighlightEntity(
                verseId = verseId,
                colorName = colorName,
                createdAtEpochMillis = System.currentTimeMillis()
            )
        )
    }

    override suspend fun setExplanationDepth(level: ExplanationDepth) {
        readingPreferenceDao.upsert(
            com.purewords1611.android.study.data.local.ReadingPreferenceEntity(
                id = 1,
                explanationLevel = level.name
            )
        )
    }

    override suspend fun seedIfEmpty() {
        dataImporter.importIfEmpty()
    }

    private fun VerseEntity.toModel(): VerseText {
        return VerseText(
            id = id,
            book = book,
            bookOriginal = bookOriginal,
            chapter = chapter,
            verse = verse,
            section = section,
            originalText = originalText,
            modernizedText = modernizedText,
            comparativeText = comparativeText,
            hasItalicWords = hasItalicWords
        )
    }

    private fun ChapterIndexRow.toModel(): ChapterIndexEntry {
        return ChapterIndexEntry(
            book = book,
            bookOriginal = bookOriginal,
            chapter = chapter,
            section = section,
            firstCanonicalOrder = firstCanonicalOrder
        )
    }
}
