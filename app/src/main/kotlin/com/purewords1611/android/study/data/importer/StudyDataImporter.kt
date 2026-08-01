package com.purewords1611.android.study.data.importer

import com.purewords1611.android.study.data.local.MarginalNoteDao
import com.purewords1611.android.study.data.local.MarginalNoteEntity
import com.purewords1611.android.study.data.local.ReadingPreferenceDao
import com.purewords1611.android.study.data.local.ReadingPreferenceEntity
import com.purewords1611.android.study.data.local.VerseDao
import com.purewords1611.android.study.data.local.VerseEntity
import com.purewords1611.android.study.data.local.ExplanationDao
import com.purewords1611.android.study.data.local.ExplanationEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudyDataImporter @Inject constructor(
    private val loader: CanonicalDataLoader,
    private val validator: CanonicalDataValidator,
    private val verseDao: VerseDao,
    private val marginalNoteDao: MarginalNoteDao,
    private val explanationDao: ExplanationDao,
    private val readingPreferenceDao: ReadingPreferenceDao
) {
    suspend fun importIfEmpty() {
        if (verseDao.count() > 0) {
            return
        }

        val sources = loader.loadSources()
        val verses = loader.loadVerses()
        val notes = loader.loadMarginalNotes()
        val glossary = loader.loadGlossary()
        val explanations = loader.loadExplanations()
        val seekerTracks = loader.loadSeekerTracks()
        val seekerSteps = loader.loadSeekerSteps()
        val frontMatter = loader.loadFrontMatter()

        validator.validate(
            sources = sources,
            verses = verses,
            notes = notes,
            glossary = glossary,
            explanations = explanations,
            seekerTracks = seekerTracks,
            seekerSteps = seekerSteps,
            frontMatter = frontMatter
        )

        verseDao.upsertAll(
            verses.map {
                VerseEntity(
                    id = it.id,
                    book = it.book,
                    chapter = it.chapter,
                    verse = it.verse,
                    section = it.section,
                    canonicalOrder = it.canonicalOrder,
                    originalText = it.originalText,
                    modernizedText = it.modernizedText,
                    hasItalicWords = it.hasItalicWords,
                    sourceId = it.sourceId,
                    sourceLocator = it.sourceLocator,
                    checksumSha256 = it.checksumSha256
                )
            }
        )

        marginalNoteDao.upsertAll(
            notes.map {
                MarginalNoteEntity(
                    id = it.id,
                    verseId = it.verseId,
                    noteType = it.noteType,
                    note = it.note,
                    anchorToken = it.anchorToken,
                    sourceId = it.sourceId,
                    sourceLocator = it.sourceLocator,
                    checksumSha256 = it.checksumSha256
                )
            }
        )

        explanationDao.upsertAll(
            explanations.map {
                ExplanationEntity(
                    id = it.id,
                    verseId = it.verseId,
                    level = it.level,
                    contentMarkdown = it.contentMarkdown,
                    sourceId = it.sourceId,
                    checksumSha256 = it.checksumSha256
                )
            }
        )

        readingPreferenceDao.upsert(
            ReadingPreferenceEntity(
                id = 1,
                explanationLevel = "HISTORICAL_LINGUISTIC"
            )
        )
    }
}
