package com.purewords1611.android.study.data.importer

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CanonicalDataValidator @Inject constructor() {
    fun validate(
        sources: List<CanonicalSourceRecord>,
        verses: List<CanonicalVerseRecord>,
        notes: List<CanonicalMarginalNoteRecord>,
        glossary: List<CanonicalGlossaryRecord>,
        explanations: List<CanonicalExplanationRecord>,
        seekerTracks: List<CanonicalSeekerTrackRecord>,
        seekerSteps: List<CanonicalSeekerStepRecord>,
        frontMatter: List<CanonicalFrontMatterRecord>
    ) {
        require(sources.isNotEmpty()) { "Sources dataset cannot be empty." }
        require(verses.isNotEmpty()) { "Verses dataset cannot be empty." }

        val sourceIds = sources.map { it.sourceId }.toSet()
        require(sourceIds.size == sources.size) { "Duplicate source_id values detected." }

        val verseIds = verses.map { it.id }
        require(verseIds.distinct().size == verseIds.size) { "Duplicate verse id values detected." }

        verses.forEach { verse ->
            require(verse.book.isNotBlank()) { "Verse ${verse.id} has blank book." }
            require(verse.chapter > 0) { "Verse ${verse.id} has invalid chapter." }
            require(verse.verse > 0) { "Verse ${verse.id} has invalid verse number." }
            require(verse.originalText.isNotBlank()) { "Verse ${verse.id} has blank original text." }
            require(verse.modernizedText.isNotBlank()) { "Verse ${verse.id} has blank modernized text." }
            require(sourceIds.contains(verse.sourceId)) { "Verse ${verse.id} references unknown source_id ${verse.sourceId}." }
        }

        val canonicalOrders = verses.map { it.canonicalOrder }
        require(canonicalOrders.distinct().size == canonicalOrders.size) { "Duplicate canonical_order values detected." }
        require(canonicalOrders == canonicalOrders.sorted()) { "canonical_order must be monotonically increasing." }

        val verseIdSet = verseIds.toSet()
        notes.forEach { note ->
            require(note.note.isNotBlank()) { "Marginal note ${note.id} has blank note text." }
            require(verseIdSet.contains(note.verseId)) {
                "Marginal note ${note.id} references missing verse_id ${note.verseId}."
            }
            require(sourceIds.contains(note.sourceId)) {
                "Marginal note ${note.id} references unknown source_id ${note.sourceId}."
            }
        }

        glossary.forEach { entry ->
            require(entry.id.isNotBlank()) { "Glossary entry has blank id." }
            require(entry.headword.isNotBlank()) { "Glossary ${entry.id} has blank headword." }
            require(entry.definitionShort.isNotBlank()) { "Glossary ${entry.id} has blank definition." }
            require(sourceIds.contains(entry.sourceId)) {
                "Glossary ${entry.id} references unknown source_id ${entry.sourceId}."
            }
        }

        val allowedExplanationLevels = setOf(
            "MINIMAL",
            "HISTORICAL_LINGUISTIC",
            "HISTORICAL_LIGHT_DOCTRINAL"
        )
        explanations.forEach { explanation ->
            require(explanation.id.isNotBlank()) { "Explanation has blank id." }
            require(verseIdSet.contains(explanation.verseId)) {
                "Explanation ${explanation.id} references missing verse_id ${explanation.verseId}."
            }
            require(allowedExplanationLevels.contains(explanation.level)) {
                "Explanation ${explanation.id} uses invalid level ${explanation.level}."
            }
            require(explanation.contentMarkdown.isNotBlank()) {
                "Explanation ${explanation.id} has blank content."
            }
            require(sourceIds.contains(explanation.sourceId)) {
                "Explanation ${explanation.id} references unknown source_id ${explanation.sourceId}."
            }
        }

        val trackIds = seekerTracks.map { it.trackId }.toSet()
        require(trackIds.size == seekerTracks.size) { "Duplicate seeker track ids detected." }
        seekerTracks.forEach { track ->
            require(track.trackId.isNotBlank()) { "Seeker track id cannot be blank." }
            require(track.title.isNotBlank()) { "Seeker track ${track.trackId} has blank title." }
        }

        val stepsByTrack = seekerSteps.groupBy { it.trackId }
        stepsByTrack.forEach { (trackId, steps) ->
            require(trackIds.contains(trackId)) { "Seeker steps reference missing track_id $trackId." }
            val sorted = steps.map { it.sequence }.sorted()
            val expected = (1..steps.size).toList()
            require(sorted == expected) {
                "Seeker track $trackId has non-contiguous sequence values."
            }
            steps.forEach { step ->
                require(step.stepId.isNotBlank()) { "Seeker step has blank id." }
                require(step.title.isNotBlank()) { "Seeker step ${step.stepId} has blank title." }
                require(step.bodyMarkdown.isNotBlank()) { "Seeker step ${step.stepId} has blank content." }
            }
        }

        val allowedFrontMatterIds = setOf(
            "TRANSLATORS_TO_THE_READER",
            "EPISTLE_DEDICATORY"
        )
        frontMatter.forEach { doc ->
            require(allowedFrontMatterIds.contains(doc.docId)) {
                "Front matter has unsupported doc_id ${doc.docId}."
            }
            require(doc.title.isNotBlank()) { "Front matter ${doc.docId} has blank title." }
            require(doc.textOriginal.isNotBlank()) { "Front matter ${doc.docId} has blank original text." }
            require(doc.textModernized.isNotBlank()) { "Front matter ${doc.docId} has blank modernized text." }
            require(sourceIds.contains(doc.sourceId)) {
                "Front matter ${doc.docId} references unknown source_id ${doc.sourceId}."
            }
        }
    }
}
