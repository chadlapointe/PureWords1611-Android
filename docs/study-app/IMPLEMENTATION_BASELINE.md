# Study App Implementation Baseline

This baseline establishes `main` as a study-first Android foundation aligned with the approved product specification.

## Implemented in this baseline

1. Study-first app entry flow in `MainActivity` using Compose.
2. Room data model scaffold for:
   - 1611 verse text (original and modernized spelling forms)
   - Apocrypha section tagging
   - Marginal notes
   - Bookmarks, highlights, and personal notes
3. Repository layer (`StudyRepository`) and offline implementation (`OfflineStudyRepository`).
4. Hilt module wiring for database, DAOs, and repository binding.
5. Read-focused Compose shell with:
   - Orthography mode toggle
   - Search field
   - Verse list
   - Section visibility including Apocrypha counts
6. Placeholder sections for Notes and Seeker Path to support phased implementation.
7. Canonical data ingestion scaffold:
   - Asset-backed canonical dataset files under `app/src/main/assets/study/`
   - Importer + validator pipeline for verses, marginal notes, and source metadata
   - Repository seeding now routes through validation before local persistence
8. Reader navigation + study tools scaffold:
   - Chapter index, chapter jump, and previous/next chapter navigation
   - Explanation depth preference persistence and filtered explanation rendering
   - Seeker Path track/step presentation baseline
   - Bookmarks, highlights, and personal notes capture/listing baseline
9. Audio baseline:
   - Text-to-speech playback for selected verse content in Read flow

## Next execution targets

1. Expand canonical datasets from starter records to full verified 1611 corpus.
2. Import full Apocrypha and translators' marginal notes with traceable source metadata.
3. Implement full Notes/Bookmarks/Highlights interaction flows.
4. Implement structured two-track Seeker Path content models and UI.
5. Add chapter/verse navigation, continuous reading mode, and verse detail context view.
