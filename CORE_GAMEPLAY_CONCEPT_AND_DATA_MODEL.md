# PureWords1611 - Core Gameplay Concept & Data Model

**Document Version**: 1.0  
**Created**: January 2, 2026  
**Status**: Initial Definition Complete  
**Author**: GitHub Copilot Coding Agent

---

## 📋 Executive Summary

PureWords1611-Android is a word-based Android game application that combines biblical content from the 1611 King James Version with engaging gameplay mechanics. The app features multiple game modes that challenge players' vocabulary, pattern recognition, and biblical knowledge while providing an educational and spiritually enriching experience.

### Project Goals
- **Primary**: Establish Google Play Store presence with a high-quality, engaging word game
- **Secondary**: Provide educational biblical content in an interactive format
- **Tertiary**: Demonstrate expertise in Android development and game design

---

## 🎮 Core Gameplay Concept

### Overview

PureWords1611 offers **five distinct game modes**, each providing unique challenges based on biblical vocabulary and verses from the 1611 KJV:

1. **Daily Verse Challenge** - Fill-in-the-blank verse completion
2. **Word Grid Search** - Boggle-style word finding game
3. **Word Matching Pairs** - Biblical term definition matching
4. **Scripture Speed Typing** - Timed verse transcription
5. **Daily Verse Devotional** - Non-competitive daily scripture reading

---

## 🎯 Game Mode #1: Daily Verse Challenge

### Objective
Complete Bible verses by filling in missing words to score points while managing limited lives.

### Game Rules
- **Starting Resources**: 3 lives
- **Scoring**: +10 points per correct word
- **Lives System**: -1 life per incorrect answer
- **Game Over**: When all 3 lives are lost
- **Win Condition**: Continue playing to achieve highest possible score

### Gameplay Mechanics
1. Player is shown a Bible verse with 1-3 words blanked out
2. Player types the missing word(s) into input field(s)
3. System validates answer (case-insensitive, whitespace-trimmed)
4. Correct answer: +10 points, show full verse, load next verse
5. Incorrect answer: -1 life, show correct answer, load next verse if lives remain
6. Game continues until all lives are exhausted

### Visual Flow Diagram
```
┌─────────────┐
│   Start     │
│  Game (3♥)  │
└──────┬──────┘
       │
       ▼
┌──────────────────────────────────┐
│  "In the _____ God created..."   │
│  Input: [____________]            │
│  Score: 0    Lives: ♥♥♥          │
└──────┬───────────────────────────┘
       │
       ▼ (Submit Answer)
       │
   ┌───┴────┐
   │ Valid? │
   └───┬────┘
       │
    ┌──┴──┐
    │     │
   YES    NO
    │     │
    │     └──► -1 Life, Show Answer
    │
    └──► +10 Points, Show Verse
         │
         ▼
    ┌────────────┐
    │ Lives > 0? │
    └────┬───────┘
         │
      ┌──┴──┐
      │     │
     YES    NO
      │     │
      │     └──► Game Over Screen
      │
      └──► Load Next Verse (loop)
```

---

## 🔲 Game Mode #2: Word Grid Search

### Objective
Find as many valid words as possible in a 4x4 letter grid within 2 minutes.

### Game Rules
- **Grid**: 4x4 random letters (weighted distribution)
- **Time Limit**: 2 minutes (120 seconds)
- **Minimum Word Length**: 3 letters
- **Word Source**: KJV biblical vocabulary
- **Adjacency**: Words formed by connecting adjacent cells (horizontal, vertical, diagonal)
- **No Reuse**: Each cell can only be used once per word
- **Scoring**: Base 10 points + 5 points per letter beyond 3
  - 3-letter: 10 points
  - 4-letter: 15 points
  - 5-letter: 20 points
- **Win Condition**: Find 10+ unique words before time expires

### Gameplay Mechanics
1. System generates 4x4 grid with weighted random letters
2. Timer starts at 2:00
3. Player taps adjacent cells to build word path
4. System highlights selected cells and shows current word
5. Player submits word or clears selection
6. Valid word: Add to found words list, increase score
7. Invalid word: Show error feedback, clear selection
8. Game ends when timer reaches 0:00
9. Victory if 10+ words found, otherwise Time's Up

### Visual Grid Representation
```
┌─────────────────────────────────────┐
│  Score: 45    Timer: 1:23    7/10   │
├─────────────────────────────────────┤
│                                      │
│    ┌───┬───┬───┬───┐               │
│    │ T │ H │ E │ R │               │
│    ├───┼───┼───┼───┤               │
│    │ A │ N │ D │ E │               │
│    ├───┼───┼───┼───┤               │
│    │ L │ O │ R │ S │               │
│    ├───┼───┼───┼───┤               │
│    │ W │ I │ T │ H │               │
│    └───┴───┴───┴───┘               │
│                                      │
│  Current: "LORD" ✓                  │
│  [Clear] [Submit]                   │
│                                      │
│  Found Words:                        │
│  THE, AND, LORD, WITH, HAND...      │
└─────────────────────────────────────┘
```

---

## 🃏 Game Mode #3: Word Matching Pairs

### Objective
Match biblical terms to their correct definitions or synonyms.

### Game Rules
- **Card Layout**: Grid of 8-12 cards (4-6 pairs)
- **Card Types**: Word cards and Definition cards
- **Matching**: Tap two cards to attempt a match
- **Scoring**: +15 points per correct match
- **No Time Limit**: Play at your own pace
- **Win Condition**: Successfully match all pairs

### Gameplay Mechanics
1. System shuffles word-definition pairs into card grid
2. All cards are visible (not memory game)
3. Player taps first card (word or definition)
4. Player taps second card to attempt match
5. Correct match: Cards lock in highlighted state, +15 points
6. Incorrect match: Show feedback, cards remain available
7. Continue until all pairs matched
8. Victory screen shows final score

### Example Matches
```
Word              Definition
─────────────     ──────────────────────────────────
Grace             Unmerited favor from God
Salvation         Deliverance from sin and its consequences
Righteousness     State of moral perfection and justice
Covenant          Sacred agreement between God and humanity
Redemption        Act of buying back or ransoming
```

---

## ⌨️ Game Mode #4: Scripture Speed Typing

### Objective
Type Bible verses accurately and quickly to achieve high WPM (words per minute) score.

### Game Rules
- **Display**: Complete verse shown at top
- **Input**: Real-time typing with character-by-character feedback
- **Time Limit**: 60-90 seconds (based on verse length)
- **Validation**: Character accuracy tracking
- **Scoring**: WPM × Accuracy percentage
- **Win Condition**: 90%+ accuracy

### Gameplay Mechanics
1. System displays complete Bible verse
2. Timer starts when player begins typing
3. Real-time feedback: Green for correct, Red for incorrect
4. Calculate WPM: (Characters typed / 5) / (seconds / 60)
5. Calculate Accuracy: (Correct chars / Total chars) × 100
6. Final Score: WPM × (Accuracy / 100)
7. Victory if accuracy ≥ 90%

### Typing Interface
```
┌──────────────────────────────────────────┐
│ Reference: John 3:16                      │
├──────────────────────────────────────────┤
│ Target:                                   │
│ For God so loved the world, that he      │
│ gave his only begotten Son, that         │
│ whosoever believeth in him should not    │
│ perish, but have everlasting life.       │
├──────────────────────────────────────────┤
│ Your typing:                              │
│ For God so loved the world, that he      │
│ gave his on█                              │
│ ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓      │
│                                           │
│ WPM: 42    Accuracy: 95%    Time: 0:45   │
└──────────────────────────────────────────┘
```

---

## 📖 Game Mode #5: Daily Verse Devotional

### Objective
Read and engage with daily Bible verses for spiritual growth.

### Features (Non-Competitive)
- **Daily Verse**: One new verse delivered each day
- **Notifications**: Optional reminder at user-selected time
- **Bookmarking**: Save favorite verses
- **Sharing**: Share verses via Android share sheet
- **History**: Browse previous daily verses
- **Streak Tracking**: Track consecutive reading days

### User Actions
1. Open app to view today's verse
2. Read verse in clean, readable format
3. Bookmark verse (optional)
4. Share verse with others (optional)
5. Browse verse history
6. Set notification preferences

---

## 🏗️ Data Model

### Entity Relationship Diagram

```
┌─────────────────┐
│     Verse       │
├─────────────────┤
│ id (PK)         │
│ reference       │
│ text            │
│ blankedText     │
│ missingWords    │
│ category        │
└────────┬────────┘
         │
         │ 1:N
         │
         ▼
┌─────────────────────┐
│   DailyVerse        │
├─────────────────────┤
│ date (PK)           │
│ verseId (FK)        │
│ isRead              │
│ isBookmarked        │
│ timestamp           │
└──────────────────── ┘

┌─────────────────┐
│   WordPair      │
├─────────────────┤
│ id (PK)         │
│ word            │
│ definition      │
│ category        │
└─────────────────┘

┌─────────────────┐
│  GameSession    │
├─────────────────┤
│ id (PK)         │
│ gameMode        │
│ score           │
│ startTime       │
│ endTime         │
│ completed       │
└─────────────────┘

┌─────────────────┐
│  UserStats      │
├─────────────────┤
│ id (PK)         │
│ gameMode        │
│ highScore       │
│ gamesPlayed     │
│ totalScore      │
│ lastPlayed      │
└─────────────────┘
```

### Core Data Entities

#### 1. Verse
Primary entity for bible verse content.

**Table**: `verses` (Asset JSON, not database)

| Field | Type | Description | Example |
|-------|------|-------------|---------|
| reference | String | Bible reference | "Genesis 1:1" |
| text | String | Complete verse text | "In the beginning God created..." |
| blankedText | String | Verse with blanks | "In the _____ God created..." |
| missingWords | List<String> | Correct answers | ["beginning"] |

**Kotlin Data Class**:
```kotlin
data class Verse(
    val reference: String,
    val text: String,
    val blankedText: String,
    val missingWords: List<String>
)
```

**Example Data**:
```json
{
  "reference": "Genesis 1:1",
  "text": "In the beginning God created the heaven and the earth.",
  "blankedText": "In the _____ God created the heaven and the earth.",
  "missingWords": ["beginning"]
}
```

---

#### 2. DailyVerse
Tracks daily verse delivery and user interaction.

**Table**: `daily_verses` (Room Database)

| Field | Type | Description | Constraints |
|-------|------|-------------|-------------|
| date | String | Date (YYYY-MM-DD) | PRIMARY KEY |
| reference | String | Bible reference | NOT NULL |
| text | String | Verse text | NOT NULL |
| isBookmarked | Boolean | Bookmark status | DEFAULT false |
| isRead | Boolean | Read status | DEFAULT false |

**Kotlin Entity**:
```kotlin
@Entity(tableName = "daily_verses")
data class DailyVerseEntity(
    @PrimaryKey val date: String,
    val reference: String,
    val text: String,
    val isBookmarked: Boolean = false,
    val isRead: Boolean = false
)
```

**Example Records**:
```
date       | reference  | text                          | isBookmarked | isRead
-----------|------------|-------------------------------|--------------|-------
2026-01-02 | John 3:16  | For God so loved the world... | true         | true
2026-01-01 | Psalm 23:1 | The LORD is my shepherd...    | false        | true
```

---

#### 3. WordGrid
Represents letter grid for word search game.

**Data Class** (Runtime only, not persisted):
```kotlin
data class WordGrid(
    val size: Int = 4,
    val letters: List<List<Char>>
) {
    fun isValidPosition(position: GridPosition): Boolean
    fun areAdjacent(pos1: GridPosition, pos2: GridPosition): Boolean
    fun getWordFromPath(path: List<GridPosition>): String
}

data class GridPosition(
    val row: Int, 
    val col: Int
)
```

**Example Grid**:
```kotlin
WordGrid(
    size = 4,
    letters = listOf(
        listOf('T', 'H', 'E', 'R'),
        listOf('A', 'N', 'D', 'E'),
        listOf('L', 'O', 'R', 'S'),
        listOf('W', 'I', 'T', 'H')
    )
)
```

---

#### 4. WordPair
Biblical term and definition pairs for matching game.

**Data Class** (Asset JSON, not database):
```kotlin
data class WordPair(
    val word: String,
    val definition: String,
    val category: String
)
```

**Example Data**:
```json
[
  {
    "word": "Grace",
    "definition": "Unmerited favor from God",
    "category": "Theological Terms"
  },
  {
    "word": "Salvation",
    "definition": "Deliverance from sin and its consequences",
    "category": "Theological Terms"
  }
]
```

---

#### 5. GameSession
Tracks individual game play sessions.

**Table**: `game_sessions` (Room Database - Future)

| Field | Type | Description |
|-------|------|-------------|
| id | Int | Auto-increment PK |
| gameMode | String | Game mode identifier |
| score | Int | Final score |
| startTime | Long | Timestamp (ms) |
| endTime | Long | Timestamp (ms) |
| completed | Boolean | Completion status |

**Kotlin Entity**:
```kotlin
@Entity(tableName = "game_sessions")
data class GameSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val gameMode: String,
    val score: Int,
    val startTime: Long,
    val endTime: Long,
    val completed: Boolean
)
```

---

#### 6. UserStats
Aggregate statistics per game mode.

**Table**: `user_stats` (Room Database - Future)

| Field | Type | Description |
|-------|------|-------------|
| gameMode | String | PRIMARY KEY |
| highScore | Int | Highest score achieved |
| gamesPlayed | Int | Total games played |
| totalScore | Int | Sum of all scores |
| lastPlayed | Long | Timestamp of last play |

**Kotlin Entity**:
```kotlin
@Entity(tableName = "user_stats")
data class UserStatsEntity(
    @PrimaryKey val gameMode: String,
    val highScore: Int,
    val gamesPlayed: Int,
    val totalScore: Int,
    val lastPlayed: Long
)
```

---

### Data Access Layer (DAO)

#### DailyVerseDao
```kotlin
@Dao
interface DailyVerseDao {
    @Query("SELECT * FROM daily_verses ORDER BY date DESC")
    fun getAllDailyVerses(): Flow<List<DailyVerseEntity>>
    
    @Query("SELECT * FROM daily_verses WHERE date = :date")
    suspend fun getDailyVerseByDate(date: String): DailyVerseEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyVerse(verse: DailyVerseEntity)
    
    @Update
    suspend fun updateDailyVerse(verse: DailyVerseEntity)
    
    @Query("UPDATE daily_verses SET isBookmarked = :bookmarked WHERE date = :date")
    suspend fun updateBookmarkStatus(date: String, bookmarked: Boolean)
    
    @Query("SELECT * FROM daily_verses WHERE isBookmarked = 1 ORDER BY date DESC")
    fun getBookmarkedVerses(): Flow<List<DailyVerseEntity>>
}
```

#### GameSessionDao (Future Implementation)
```kotlin
@Dao
interface GameSessionDao {
    @Query("SELECT * FROM game_sessions WHERE gameMode = :mode ORDER BY startTime DESC")
    fun getSessionsByMode(mode: String): Flow<List<GameSessionEntity>>
    
    @Insert
    suspend fun insertSession(session: GameSessionEntity): Long
    
    @Query("SELECT * FROM game_sessions ORDER BY score DESC LIMIT :limit")
    fun getTopSessions(limit: Int): Flow<List<GameSessionEntity>>
}
```

#### UserStatsDao (Future Implementation)
```kotlin
@Dao
interface UserStatsDao {
    @Query("SELECT * FROM user_stats WHERE gameMode = :mode")
    suspend fun getStatsByMode(mode: String): UserStatsEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertStats(stats: UserStatsEntity)
    
    @Query("SELECT * FROM user_stats ORDER BY highScore DESC")
    fun getAllStats(): Flow<List<UserStatsEntity>>
}
```

---

### Repository Pattern

#### VerseRepository
Manages Bible verse data loading and access.

```kotlin
class VerseRepository(private val context: Context) {
    
    private var verses: List<Verse> = emptyList()
    
    /**
     * Load all verses from assets/verses.json
     */
    fun loadVerses(): List<Verse> {
        if (verses.isNotEmpty()) return verses
        
        return try {
            val json = context.assets
                .open("verses.json")
                .bufferedReader()
                .use { it.readText() }
            
            val jsonArray = JSONArray(json)
            verses = parseVerses(jsonArray)
            verses
        } catch (e: Exception) {
            Log.e("VerseRepository", "Error loading verses", e)
            emptyList()
        }
    }
    
    /**
     * Get a random verse for gameplay
     */
    fun getRandomVerse(): Verse? {
        if (verses.isEmpty()) loadVerses()
        return verses.randomOrNull()
    }
    
    private fun parseVerses(jsonArray: JSONArray): List<Verse> {
        // Parse JSON into Verse objects
    }
}
```

#### DailyVerseRepository
Manages daily verse delivery and persistence.

```kotlin
class DailyVerseRepository(
    private val dao: DailyVerseDao,
    private val verseRepository: VerseRepository
) {
    
    /**
     * Get today's verse (Flow for reactive UI)
     */
    fun getTodaysVerse(): Flow<DailyVerse?> {
        val today = LocalDate.now().toString()
        return flow {
            var verse = dao.getDailyVerseByDate(today)
            if (verse == null) {
                generateDailyVerse()
                verse = dao.getDailyVerseByDate(today)
            }
            emit(verse?.toDomainModel())
        }
    }
    
    /**
     * Generate and save today's verse
     */
    suspend fun generateDailyVerse() {
        val today = LocalDate.now().toString()
        val verse = verseRepository.getRandomVerse() ?: return
        
        val dailyVerse = DailyVerseEntity(
            date = today,
            reference = verse.reference,
            text = verse.text,
            isBookmarked = false,
            isRead = false
        )
        
        dao.insertDailyVerse(dailyVerse)
    }
    
    /**
     * Mark verse as read
     */
    suspend fun markVerseAsRead(date: String) {
        val verse = dao.getDailyVerseByDate(date) ?: return
        dao.updateDailyVerse(verse.copy(isRead = true))
    }
    
    /**
     * Toggle bookmark status
     */
    suspend fun toggleBookmark(date: String) {
        val verse = dao.getDailyVerseByDate(date) ?: return
        dao.updateBookmarkStatus(date, !verse.isBookmarked)
    }
    
    /**
     * Get verse history
     */
    fun getVerseHistory(): Flow<List<DailyVerse>> {
        return dao.getAllDailyVerses()
            .map { entities -> entities.map { it.toDomainModel() } }
    }
    
    /**
     * Get bookmarked verses
     */
    fun getBookmarkedVerses(): Flow<List<DailyVerse>> {
        return dao.getBookmarkedVerses()
            .map { entities -> entities.map { it.toDomainModel() } }
    }
}
```

#### WordDictionary
Manages KJV word validation.

```kotlin
class WordDictionary(context: Context) {
    
    private val words: Set<String> by lazy {
        loadWords(context)
    }
    
    /**
     * Check if word exists in KJV dictionary
     */
    fun isValidWord(word: String): Boolean {
        return words.contains(word.lowercase())
    }
    
    /**
     * Load words from verse data
     */
    private fun loadWords(context: Context): Set<String> {
        // Extract unique words from all verses
        // Filter to 3+ character words
        // Return as lowercase set
    }
}
```

---

## 🎨 UI State Management

### MVVM Architecture

All game modes follow the MVVM (Model-View-ViewModel) pattern:

```
┌──────────────┐
│   View (UI)  │ ← Jetpack Compose
│  @Composable │
└───────┬──────┘
        │ Observes StateFlow
        │ Triggers Actions
        ▼
┌──────────────┐
│  ViewModel   │ ← Business Logic
│  StateFlow   │
└───────┬──────┘
        │ Uses Repository
        │ Manages State
        ▼
┌──────────────┐
│  Repository  │ ← Data Access
│  DAO/Assets  │
└───────┬──────┘
        │
        ▼
┌──────────────┐
│  Data Layer  │ ← Room DB / JSON
│ Database/File│
└──────────────┘
```

### Example: GameViewModel State

```kotlin
data class GameUiState(
    val currentVerse: Verse?,
    val userInputs: List<String>,
    val score: Int,
    val lives: Int,
    val gameState: GameState,
    val feedback: String
)

sealed class GameState {
    object Loading : GameState()
    object Playing : GameState()
    object Correct : GameState()
    object Incorrect : GameState()
    object GameOver : GameState()
}

class GameViewModel(
    private val repository: VerseRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(GameUiState(/*...*/))
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    
    fun validateAnswer() { /* Logic */ }
    fun continueGame() { /* Logic */ }
    fun resetGame() { /* Logic */ }
}
```

---

## 📊 Data Flow Examples

### Example 1: Daily Verse Challenge Flow

```
User Action: Start Game
    │
    ▼
ViewModel: Initialize game state
    │
    ▼
Repository: loadVerses()
    │
    ▼
Asset: Read verses.json
    │
    ▼
Repository: getRandomVerse()
    │
    ▼
ViewModel: Update UI state with verse
    │
    ▼
UI: Display verse with blanks
    │
    ▼
User Action: Enter answer and submit
    │
    ▼
ViewModel: validateAnswer()
    │
    ├─► Correct: score + 10, load next verse
    │
    └─► Incorrect: lives - 1, check game over
        │
        ├─► Lives > 0: load next verse
        │
        └─► Lives = 0: show game over screen
```

### Example 2: Word Grid Game Flow

```
User Action: Start Word Grid Game
    │
    ▼
ViewModel: Initialize game
    │
    ▼
WordGameEngine: generateGrid()
    │
    ├─► Create 4x4 grid
    ├─► Weight letter distribution
    └─► Return WordGrid
    │
    ▼
ViewModel: Start 2-minute timer
    │
    ▼
UI: Display grid and timer
    │
    ▼
User Action: Select adjacent cells
    │
    ▼
ViewModel: addToPath(position)
    │
    ├─► Validate position in bounds
    ├─► Check adjacency to last cell
    ├─► Add to current path
    └─► Update UI with selection
    │
    ▼
User Action: Submit word
    │
    ▼
WordGameEngine: validateWord()
    │
    ├─► Check path connectivity
    ├─► Extract word from path
    ├─► Check against dictionary
    ├─► Check if already found
    └─► Calculate score
    │
    ├─► Valid: Add to found words, increase score
    │
    └─► Invalid: Show error feedback
    │
    ▼
Timer: Countdown to 0:00
    │
    ├─► Time remaining: Continue play
    │
    └─► Time expired: End game
        │
        ├─► 10+ words: Victory
        │
        └─► < 10 words: Time's Up
```

### Example 3: Daily Verse Devotional Flow

```
System: Daily at configured time
    │
    ▼
WorkManager: Trigger DailyVerseWorker
    │
    ▼
DailyVerseRepository: generateDailyVerse()
    │
    ├─► Check if today's verse exists
    ├─► If not, get random verse
    └─► Insert into daily_verses table
    │
    ▼
NotificationManager: Show notification
    │
    ▼
User Action: Tap notification / Open app
    │
    ▼
DailyVerseViewModel: getTodaysVerse()
    │
    ▼
Repository: Query daily_verses WHERE date = today
    │
    ▼
UI: Display verse in card format
    │
    ▼
User Action: Bookmark verse
    │
    ▼
Repository: updateBookmarkStatus()
    │
    ▼
DAO: UPDATE daily_verses SET isBookmarked = true
    │
    ▼
UI: Update bookmark icon (filled)
```

---

## 🔧 Technical Implementation Details

### Technology Stack

| Layer | Technology | Purpose |
|-------|------------|---------|
| Language | Kotlin | Primary development language |
| UI Framework | Jetpack Compose | Modern declarative UI |
| Architecture | MVVM | Separation of concerns |
| Database | Room | Local data persistence |
| Async | Coroutines + Flow | Non-blocking operations |
| DI | Hilt (Future) | Dependency injection |
| Testing | JUnit + Mockito | Unit testing |
| Build | Gradle KTS | Build automation |

### Project Structure

```
app/src/main/kotlin/com/purewords1611/android/
│
├── data/                          # Data models and game engines
│   ├── Verse.kt
│   ├── WordGrid.kt
│   ├── WordPair.kt
│   ├── WordDictionary.kt
│   ├── WordGameEngine.kt
│   ├── WordMatchingGame.kt
│   ├── VerseRepository.kt
│   └── DailyVerseRepository.kt
│
├── database/                      # Room database
│   ├── AppDatabase.kt
│   ├── DailyVerseDao.kt
│   ├── GameSessionDao.kt
│   └── UserStatsDao.kt
│
├── viewmodel/                     # ViewModels for each game mode
│   ├── GameViewModel.kt
│   ├── WordGridViewModel.kt
│   ├── WordMatchingViewModel.kt
│   ├── TypingViewModel.kt
│   └── DailyVerseViewModel.kt
│
├── ui/                            # Composable UI screens
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Type.kt
│   │   └── Theme.kt
│   ├── gameplay/
│   │   └── GameplayLoop.kt
│   ├── wordgrid/
│   │   └── WordGridGameScreen.kt
│   ├── wordmatching/
│   │   └── WordMatchingGameScreen.kt
│   ├── typing/
│   │   └── TypingChallengeScreen.kt
│   ├── daily/
│   │   └── DailyVerseScreen.kt
│   └── GameModeSelectionScreen.kt
│
├── workers/                       # Background workers
│   └── DailyVerseWorker.kt
│
├── notification/                  # Notification management
│   └── DailyVerseNotificationManager.kt
│
├── analytics/                     # Analytics tracking
│   └── AnalyticsManager.kt
│
└── MainActivity.kt                # Main entry point
```

### Asset Files

```
app/src/main/assets/
├── verses.json           # Bible verses for gameplay
└── word_pairs.json       # Word-definition pairs (future)
```

---

## 📈 Scoring System Summary

| Game Mode | Scoring Formula | Max Points |
|-----------|----------------|------------|
| Daily Verse Challenge | +10 per correct word | Unlimited |
| Word Grid Search | 10 + (5 × extra letters) | Varies |
| Word Matching Pairs | +15 per match | 60-90 (4-6 pairs) |
| Scripture Speed Typing | WPM × (Accuracy / 100) | Varies |
| Daily Verse Devotional | N/A (Streak based) | N/A |

---

## 🎯 Game States Summary

### Common Game States

All competitive game modes implement these states:

```kotlin
sealed class GameState {
    object Loading : GameState()     // Initial data load
    object Playing : GameState()     // Active gameplay
    object Paused : GameState()      // Game paused (if applicable)
    object Victory : GameState()     // Win condition met
    object GameOver : GameState()    // Loss condition or completion
    object TimeUp : GameState()      // Timer expired (if applicable)
}
```

---

## 🚀 Future Enhancements

### Phase 2: Enhanced Features
- [ ] Room database for persistent statistics
- [ ] User profiles and preferences
- [ ] Achievement system
- [ ] Daily challenges with leaderboards
- [ ] Sound effects and haptic feedback
- [ ] Animation polish

### Phase 3: Advanced Features
- [ ] Cloud sync (Firebase)
- [ ] Multiplayer challenges
- [ ] Expanded verse library
- [ ] Multiple difficulty levels
- [ ] In-app content updates
- [ ] Social sharing enhancements

---

## ✅ Acceptance Criteria Validation

### Requirements Met

✅ **Brief description of game objectives, rules, and mechanics**
- All 5 game modes documented with complete rules
- Clear objectives and win conditions defined
- Detailed gameplay mechanics explained

✅ **Visuals or diagrams to illustrate key concepts**
- ASCII flow diagrams for gameplay loops
- Entity relationship diagrams for data model
- UI mockups for each game mode
- Architecture diagrams for technical implementation

✅ **Clear, well-structured data model**
- All entities documented with fields and types
- Relationships between entities defined
- DAO interfaces specified
- Repository pattern documented

✅ **Tables, relationships, and example use cases**
- Entity tables with field specifications
- Foreign key relationships shown in ERD
- Multiple data flow examples provided
- Real-world usage scenarios documented

---

## 📝 Conclusion

This document provides a comprehensive definition of the PureWords1611-Android core gameplay concept and data model. The application features five distinct game modes built on a solid MVVM architecture with a well-defined data layer. The modular design allows for independent development of each game mode while maintaining consistency across the application.

The data model supports both immediate gameplay needs (asset-based verse data) and future enhancements (Room database for statistics and persistence). The clear separation of concerns through the repository pattern ensures testability and maintainability.

All game modes share common architectural patterns while allowing for mode-specific customizations, ensuring a cohesive user experience across the application.

---

**Document Status**: ✅ Complete  
**Ready for**: Implementation Planning  
**Next Steps**: 
1. Review and approve gameplay concepts
2. Prioritize game modes for MVP
3. Create implementation tasks
4. Begin development sprints

---

*"Thy word is a lamp unto my feet, and a light unto my path." - Psalm 119:105 (KJV)*
