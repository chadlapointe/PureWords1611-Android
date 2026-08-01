# Pure Words 1611 — Release Readiness (v1)

## Purpose
Define the explicit v1 release gate for the study-first app and track what is ready versus what must be completed before Play submission.

## Release Gates

1. **Canonical Data Integrity**
   - Canonical schema defined and documented.
   - Importer/validator rejects malformed or untrusted payloads.
   - Source metadata fields are populated for canonical records.

2. **Core Study Experience**
   - Reader supports chapter navigation and chapter jump.
   - Orthography toggle is active in reading flow.
   - Marginal notes and explanation-depth rendering are functional.
   - Seeker path tracks and step rendering are available.

3. **Study Tools**
   - Bookmarks, highlights, and personal notes can be persisted locally.
   - Notes surface is available and usable from selected verses.

4. **Audio & Accessibility Baseline**
   - TTS playback for selected verse is implemented.
   - UI surfaces use clear labels and navigable controls.

5. **Build and Validation Lane**
   - x86_64 CI workflow exists for Android build + tests:
     - `.github/workflows/android-ci.yml`
   - CI is the authoritative build lane while local ARM AAPT2 mismatch remains.

## Current Status Snapshot

- **Done**
  - Canonical schema and importer/validator foundation
  - Reader chapter navigation + chapter jump
  - Explanation-depth preference persistence + rendering
  - Seeker path baseline UI
  - Bookmarks/highlights/personal notes baseline UI + persistence
  - Selected-verse TTS playback
  - x86_64 CI workflow

- **Remaining before production submission**
  - Expand starter datasets into full verified 1611 production corpus
  - Complete deep QA on content fidelity and source traceability
  - Run CI validation on final release commit set
  - Execute final Play submission checklist and policy declarations

## Exit Criteria for v1 Submission

Version 1.0 is submission-ready only when:
1. CI is green on the release candidate commit.
2. Canonical datasets are production-grade (not starter placeholders).
3. Product specification requirements are verified against app behavior.
4. Final documentation and store materials are complete.
