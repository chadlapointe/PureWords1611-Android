# Pure Words 1611 — Product Specification

**Version:** 1.0  
**Last Updated:** 31 July 2026  
**Status:** Approved for development  

---

## 1. Vision & Mission

Pure Words 1611 is a high-excellence Android application that presents the pure, unaltered words of the 1611 King James Version of the Bible.

The app exists to give serious believers, pastors, teachers, and honest seekers direct access to the original 1611 text together with the tools needed to understand and trust it — without modern revision, dilution, or compromise.

> “The words of the LORD are pure words: as silver tried in a furnace of earth, purified seven times.” — Psalm 12:6

---

## 2. Core Principles

- Faithfulness to the pure 1611 text is non-negotiable.
- Highest possible level of excellence in accuracy, usability, and design.
- Completely free (optional donations only).
- Offline-first and privacy-respecting.
- Clear path for both mature believers and genuine seekers.
- Classic + clean modern aesthetic that honors the text.

---

## 3. Target Audience

**Primary**
- Serious believers who prefer or are moving toward the 1611 text
- Pastors and teachers
- True Bible believers who want the pure words

**Secondary**
- Honest seekers and non-Christians
- Christians who have been influenced by modern versions and are open to examining the pure text

The app must serve the primary audience with depth while remaining accessible and non-hostile to the secondary audience.

---

## 4. Platform Strategy

- **Primary launch platform:** Android
- Other platforms (iOS, web) may be considered later
- Highest quality on Android is the priority

---

## 5. Monetization

- Completely free
- Optional donations only
- No ads, no forced in-app purchases, no paywalls on core content

---

## 6. Core Features (Version 1.0)

### 6.1 Bible Text
- Full text of the 1611 King James Version
- Complete Apocrypha exactly as published in the 1611 edition (positioned between Old and New Testaments)
- Toggle between:
  - Original 1611 orthography
  - Modernized spelling (same pure words)
- Clear marking of italicized words (words supplied by the translators)
- High-quality continuous reading mode + traditional chapter/verse navigation

### 6.2 Original 1611 Marginal Notes
- High priority
- Include as many of the original translators’ marginal notes as possible in the first version
- Notes should be easily viewable in context with the verse

### 6.3 Explanations System
- Maximum practicable depth with user control
- Three levels available:
  1. Minimal (just enough to clarify difficult words and the place of the Apocrypha)
  2. Historical & Linguistic (primary recommended level)
  3. Historical + light theological/doctrinal notes
- Users can choose their preferred depth

### 6.4 Seeker Path
- A very clear, structured path for seekers and those who may be misled
- Inspired by careful, systematic teaching (e.g. the style of strong KJV-defense messages)
- Users are offered a choice at the start of the path:
  - Full clear-evidence version
  - Gentler starting path
- The path must present the pure words and guide people toward the gospel and the truth with both clarity and respect

### 6.5 Audio
- High-quality text-to-speech of the pure 1611 text included in version 1.0

### 6.6 Study Tools (Basic)
- Bookmarks
- Highlights
- Personal notes
- Basic search across the pure text

### 6.7 Design Direction
- Classic and reverent foundation (traditional Bible aesthetic, elegant typography, calm and dignified colors)
- Combined with clean modern usability and excellent readability
- The overall feel must communicate weight, seriousness, and excellence

---

## 7. Secondary Features (Post-Version 1.0)

The following may be added later as complementary tools under a clearly secondary section (e.g. “Practice the Pure Words”):

- Verse Challenge
- Word Grid
- Word Matching

These games must always remain secondary to the study experience and should reinforce the pure text rather than define the app’s identity.

All original game design work is preserved on the `legacy-games` branch.

---

## 8. Content & Data Requirements

### Required Data Sets
- Complete 1611 text (original orthography)
- Modernized spelling version of the same pure words
- Full Apocrypha as printed in 1611
- Original 1611 translators’ marginal notes (as complete as possible)
- Glossary of archaic and significant words
- Structured content for the Seeker Path (both versions)
- “Translators to the Reader” and Epistle Dedicatory

### Data Quality Standard
- Textual fidelity is the highest priority
- All text must be verified against reliable 1611 sources
- No modern critical-text influence

---

## 9. Technical Preferences

- Offline-first architecture
- Modern Android stack (Kotlin + Jetpack Compose recommended)
- Local database (Room or equivalent)
- Clean, maintainable architecture (MVVM or similar)
- Strong accessibility support
- Privacy by design (no personal data collection)

---

## 10. Explicitly Out of Scope for Version 1.0

- iOS or web versions
- Paid features or subscriptions
- Heavy social features
- Modern Bible version comparison as a primary tool
- Turning the app into a game-first experience

---

## 11. Success Definition

Version 1.0 will be considered successful when:

- The pure 1611 text (with orthography toggle and Apocrypha) is accurately and beautifully presented
- Original marginal notes are available in meaningful quantity
- The seeker path is clear, respectful, and effective
- The app feels excellent, stable, and worthy of the pure words it contains
- Serious users and honest seekers both find genuine value

---

**Document Owner:** Chad Lapointe  
**Project:** Pure Words 1611
