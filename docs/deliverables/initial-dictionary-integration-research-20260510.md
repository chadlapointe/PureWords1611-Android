---
title: "Initial Dictionary Integration Research"
task_id: "2cb19f13-0a9c-81f3-9ac1-eafabdad59ff"
created: "2026-05-10T10:50:34.451869"
type: "deliverable"
---

📋 **Task: Initial Dictionary Integration Research**

🔍 **Research Complete** (via Perplexity Api)

### Comprehensive Summary: Initial Dictionary Integration Research for PureWords1611-Android

#### Key Findings
The search results provide limited but targeted insights into dictionary handling on Android, primarily focused on **Gboard's personal dictionary export/import** (real-world user workflow) and **Bible-related apps with dictionary features** (relevant to the project's apparent 1611 KJV theme, e.g., King James Pure Bible Search). No direct hits on advanced "integration" like embedding custom dictionaries into custom Android apps, but actionable patterns emerge for vocabulary/word-based apps like PureWords1611-Android (targeting educational word games/vocabulary tools).

- **Gboard Personal Dictionary Sync/Export (Most Relevant for User Dictionary Features)**:
  - Android's Gboard keyboard supports exporting personal dictionaries as a standard `PersonalDictionary.zip` file via Settings > Languages & Input > Gboard > Dictionary > Personal Dictionary > Export.
  - Import is seamless: Select the .zip from Google Drive/email/etc.; Gboard auto-extracts and loads entries without manual decompression.
  - Not automatic sync, but "few clicks" for cross-device transfer—ideal for apps encouraging user-added words (e.g., vocabulary builders).
  - **Relevance**: PureWords1611 could integrate Gboard APIs or mimic this for custom user dictionaries, enhancing retention in word-game apps.

- **Bible Search Apps with Dictionary Support (Thematic Alignment)**:
  - **King James Pure Bible Search** (open-source, C++/Qt, cross-platform including Android): Features scripture search, browsing, and **dictionaries** (e.g., multilingual support, Spanish KJV). Users can search for "divine patterns" via word-based queries.
    - GitHub/SourceForge available for forking; tested on Android/mobile browsers.
    - **Relevance**: Mirrors PureWords1611's 1611 KJV focus—could inspire dictionary embedding for word studies/games.
  - e-Sword ecosystem: Includes KJV margin notes, Greek-Hebrew dictionaries—popular downloads suggest demand for integrated lexical tools in Bible/word apps.

- **Irrelevant/Off-Topic**:
  - [3] Academic paper on Android app security/malware detection via code analysis/user comments—useful for dev best practices but not dictionary-specific.

#### Relevant Links
| Link | Description | Relevance Score (1-5) |
|------|-------------|-----------------------|
| [1] https://pjordan.substack.com/p/how-to-sync-your-personal-dictionary | Step-by-step Gboard dictionary export/import guide (.zip workflow). | 5/5 (Direct Android dictionary handling) |
| [2] https://www.purebiblesearch.com | King James Pure Bible Search (Android-compatible, dictionary features). | 4/5 (Open-source Bible/word search model) |
| [4] https://www.scribd.com/document/835428722/Kingjames-Pure-Bible-Search | PDF overview of King James Pure Bible Search (dictionaries, multilingual). | 4/5 (Project inspiration) |
| [5] http://www.biblesupport.com/most-popular-e-sword-downloads.html | e-Sword downloads (KJV dictionaries, word studies). | 3/5 (Thematic resources) |
| GitHub: https://github.com/chadlapointe/PureWords1611-Android | Project repo (for integration testing). | N/A (Contextual) |

#### Actionable Insights & Recommendations
1. **Implement Gboard-Like Dictionary Export/Import** (High Priority, Quick Win):
   - Use Android's `UserDictionary` APIs (or store as JSON/.zip) for user-added words. Export to Drive/share for "sync."
   - Code Snippet Starter (Kotlin):
     ```kotlin
     // Example: Save dictionary as ZIP (adapt from Gboard pattern)
     val words = listOf("1611", "KJV", "pureword") // User entries
     val zipFile = File(context.filesDir, "PersonalDictionary.zip")
     ZipOutputStream(FileOutputStream(zipFile)).use { zip ->
         val entry = ZipEntry("dictionary.txt")
         zip.putNextEntry(entry)
         zip.write(words.joinToString("\n").toByteArray())
         zip.closeEntry()
     }
     // Share via Intent: Intent(Intent.ACTION_SEND).setType("application/zip").putExtra(Intent.EXTRA_STREAM, Uri.fromFile(zipFile))
     ```
   - Boosts Play Store appeal: "Seamless dictionary backup across devices."

2. **Embed Custom KJV Dictionaries** (Project Fit):
   - Fork/integrate King James Pure Bible Search's Qt/C++ dictionary logic (convert to native Android via NDK or Kotlin).
   - Source free KJV dictionaries from e-Sword (e.g., 1611 margin notes) as SQLite assets for offline word lookup/games.
   - Feature Idea: Word game mode where users learn/match 1611 KJV terms, with personal dictionary integration.

3. **Next Research/Dev Steps** (Autonomy: ACT):
   - **Prototype**: Add dictionary export in PureWords1611 repo by EOD (test on Android 14+).
   - **Deeper Dive**: Query "Android Room SQLite dictionary app tutorial" or "KJV 1611 dictionary JSON dataset."
   - **Play Store Angle**: Emphasize "educational vocabulary builder with KJV word studies" for approval (hits project goal).
   - **Timeline**: Implement MVP dictionary by 2026-05-20; full publish prep by deadline (2026-07-26).

This sets a strong foundation—prioritize Gboard integration for immediate user value. Need code prototypes or further searches?

✅ Task marked for review. Please verify findings and mark complete if satisfied.