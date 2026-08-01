# Pure Words 1611 — Canonical Data Schema (v1.0)

## Purpose
Define the source-of-truth data contracts for Version 1.0 so ingestion, storage, and UI behavior remain text-faithful and predictable.

## Design Rules
1. Text fidelity first: original 1611 text is authoritative.
2. Modernized spelling is an alternate rendering of the same words, not an alternate translation.
3. Every imported record must carry source metadata for traceability.
4. IDs must be deterministic and stable across builds.
5. Offline-first: all v1 data is bundled or importable into local storage.

## Canonical Domain Objects

### 1. BibleVerse
- `verse_id` (string, deterministic): `book_key:chapter:verse`
- `book_key` (string): canonical key (e.g., `genesis`, `psalmes`, `1_esdras`)
- `book_display_name` (string)
- `testament_section` (enum): `OLD_TESTAMENT | APOCRYPHA | NEW_TESTAMENT`
- `canonical_order` (int): global ordering index across full canon
- `chapter` (int)
- `verse` (int)
- `text_original_1611` (string)
- `text_modernized_spelling` (string)
- `has_italicized_words` (boolean)
- `italic_ranges` (array of `{start:int,end:int}` on displayed text)
- `source_id` (string)
- `source_locator` (string): edition/page or reference pointer
- `checksum_sha256` (string)

### 2. MarginalNote
- `note_id` (string, deterministic)
- `verse_id` (string, fk -> BibleVerse.verse_id)
- `note_type` (enum): `ALT_READING | CROSS_REFERENCE | TRANSLATION_NOTE | OTHER`
- `note_text` (string)
- `anchor_token` (string, optional): token or phrase in verse
- `source_id` (string)
- `source_locator` (string)
- `checksum_sha256` (string)

### 3. GlossaryEntry
- `entry_id` (string)
- `headword` (string)
- `normalized_headword` (string)
- `definition_short` (string)
- `definition_extended` (string)
- `historical_notes` (string, optional)
- `linguistic_notes` (string, optional)
- `related_verse_ids` (array<string>)
- `source_id` (string)
- `checksum_sha256` (string)

### 4. ExplanationEntry
- `explanation_id` (string)
- `verse_id` (string)
- `level` (enum): `MINIMAL | HISTORICAL_LINGUISTIC | HISTORICAL_LIGHT_DOCTRINAL`
- `content_markdown` (string)
- `tags` (array<string>)
- `source_id` (string)
- `checksum_sha256` (string)

### 5. SeekerPathTrack
- `track_id` (enum): `FULL_EVIDENCE | GENTLER_START`
- `title` (string)
- `description` (string)
- `step_count` (int)

### 6. SeekerPathStep
- `step_id` (string)
- `track_id` (fk -> SeekerPathTrack.track_id)
- `sequence` (int)
- `title` (string)
- `body_markdown` (string)
- `primary_verse_ids` (array<string>)
- `reflection_prompts` (array<string>)
- `cta_type` (enum): `READ | REFLECT | PRAY | CONTINUE`

### 7. FrontMatterDocument
- `doc_id` (enum): `TRANSLATORS_TO_THE_READER | EPISTLE_DEDICATORY`
- `title` (string)
- `text_original` (string)
- `text_modernized_spelling` (string)
- `source_id` (string)
- `checksum_sha256` (string)

### 8. SourceRecord
- `source_id` (string, unique)
- `label` (string)
- `publisher_or_archive` (string)
- `edition_year` (string)
- `url_or_reference` (string)
- `license_or_rights` (string)
- `notes` (string, optional)

## Local Storage Mapping (Room)

### Core tables
- `verses`
- `marginal_notes`
- `glossary_entries`
- `explanations`
- `seeker_tracks`
- `seeker_steps`
- `front_matter_docs`
- `sources`

### User tables
- `bookmarks`
- `highlights`
- `personal_notes`
- `reading_preferences` (orthography mode, explanation level, font settings)
- `seeker_progress` (track, step, timestamps)

## JSON Packaging Contracts (for `/data`)

### `/data/bible/verses_1611.json`
- array of `BibleVerse`

### `/data/notes/marginal_notes_1611.json`
- array of `MarginalNote`

### `/data/glossary/glossary_v1.json`
- array of `GlossaryEntry`

### `/data/notes/explanations_v1.json`
- array of `ExplanationEntry`

### `/data/seeker-path/seeker_tracks_v1.json`
- array of `SeekerPathTrack`

### `/data/seeker-path/seeker_steps_v1.json`
- array of `SeekerPathStep`

### `/data/bible/front_matter_v1.json`
- array of `FrontMatterDocument`

### `/data/bible/sources_v1.json`
- array of `SourceRecord`

## Validation Requirements
1. `verse_id` uniqueness and canonical order monotonicity.
2. Apocrypha placement between Old and New Testament by `canonical_order`.
3. Every `source_id` resolves to a `SourceRecord`.
4. Checksums must be computed for all canonical records.
5. No empty text for required text fields.
6. Explanation entries must only use allowed `level` values.
7. Seeker path steps must be contiguous by sequence within each track.

## Implementation Notes
- Existing `study` module entities are a functional scaffold and should be migrated to this canonical schema incrementally.
- Import pipeline should fail fast on validation errors to protect textual integrity.
