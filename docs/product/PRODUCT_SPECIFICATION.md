# Pure Words 1611 - Product Specification

## Product Statement

Pure Words 1611 is an Android-first study app centered on the pure text of the 1611 King James Bible, including the full Apocrypha and original translators' marginal notes, with serious tools for believers, teachers, and seekers.

## Strategic Direction

1. Main product focus is a high-excellence Bible study app.
2. Text fidelity is non-negotiable.
3. Usability must remain clear, structured, and approachable for both advanced study and guided seeker use.
4. Legacy word games remain preserved and can return later as optional practice tools.

## Audience

### Primary
- Serious Bible students
- Pastors and teachers
- Believers seeking historically grounded study

### Secondary
- Seekers who need a structured entry path
- Users transitioning away from modern Bible versions

## Core Product Principles

1. Pure 1611 text with optional original orthography presentation.
2. Full Apocrypha as printed in the 1611 publication set.
3. Original 1611 translators' marginal notes presented as first-class study data.
4. Deep but flexible explanations:
   - Historical context
   - Linguistic context
   - Optional light doctrinal framing
5. Structured seeker path with two modes:
   - Full-evidence path
   - Gentler introductory path
6. Completely free product with optional donation support.
7. Offline-first operation and privacy-respecting behavior.
8. Visual direction: classic + clean modern.

## Platform and Delivery

- Primary platform: Android (highest quality first).
- Secondary platforms: deferred until Android experience reaches quality target.

## Product Scope for Main Branch

The `main` branch now serves the study app foundation:

- Study app architecture and data contracts
- Canon text and supporting datasets
- Notes, glossary, and seeker-path content systems
- Study-focused UX and documentation

Game-focused implementation history is retained on `legacy-games`.

## Information Architecture

### Core Data Domains
- Bible text (`data/bible/`)
- Study notes (`data/notes/`)
- Glossary entries (`data/glossary/`)
- Seeker path content (`data/seeker-path/`)

### Core Documentation Domains
- Product decisions (`docs/product/`)
- Study app implementation planning (`docs/study-app/`)
- Architecture records (`docs/architecture/`)

## Quality Bar

1. Text integrity and source traceability are mandatory.
2. Explanations must distinguish fact, interpretation, and optional guidance.
3. UX must support deep study without visual clutter.
4. Offline reading and study must be reliable.
5. Privacy posture must be explicit and conservative.

## Legacy Games Positioning

Verse Challenge, Word Grid, and Word Matching remain valuable but are explicitly secondary in sequencing. They may be reintroduced as optional "Practice the Pure Words" modules after study foundations are complete.

## Current Status

- Product direction locked.
- `legacy-games` branch created and pushed to preserve existing game work.
- `main` branch now positioned for study app-first development.
