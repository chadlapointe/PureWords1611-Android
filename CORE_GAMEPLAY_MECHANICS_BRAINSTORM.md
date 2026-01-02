# PureWords1611 - Core Gameplay Mechanics Brainstorm

## 🎯 Project Overview
**Project**: PureWords1611-Android  
**Goal**: Bible-inspired word game app to revitalize Google Play developer status  
**Target**: Android users who appreciate interactive word games and Bible content  
**Bible Version**: 1611 King James Version (KJV)  

---

## 🎮 Core Gameplay Mechanics (5 Mechanics Defined)

### 1. Daily Verse Challenge (Fill-in-the-Blank)
**Description**: Players complete Bible verses by filling in missing words from the KJV 1611 text.

**Gameplay**:
- Players are shown a Bible verse with one or more words blanked out (e.g., "In the _____ God created...")
- Players type in the missing word(s)
- Validation is case-insensitive with whitespace trimming
- Lives system: Players start with 3 lives, lose 1 per incorrect answer
- Score system: +10 points per correct answer
- Game continues until all lives are lost

**Player Actions**:
- Read the verse with blanks
- Type missing word(s) into text input field(s)
- Submit answer for validation
- Continue to next verse after feedback

**Win Conditions**:
- Continue playing as long as lives remain
- Goal: Achieve highest score possible before losing all lives
- Game over when all 3 lives are lost

**Android Implementation**:
```kotlin
// Data Layer
data class Verse(
    val reference: String,        // "Genesis 1:1"
    val text: String,             // Complete verse
    val blankedText: String,      // Verse with blanks
    val missingWords: List<String> // Correct answers
)

class VerseRepository(context: Context) {
    fun loadVerses(): List<Verse>
    fun getRandomVerse(): Verse
}

// ViewModel Layer (MVVM Architecture)
class GameViewModel(repository: VerseRepository) : ViewModel() {
    val uiState: StateFlow<GameUiState>
    fun validateAnswer()
    fun continueGame()
    fun resetGame()
}

data class GameUiState(
    val currentVerse: Verse?,
    val userInputs: List<String>,
    val score: Int,
    val lives: Int,
    val gameState: GameState,
    val feedback: String
)

// UI Layer (Jetpack Compose)
@Composable
fun GameplayScreen(
    uiState: GameUiState,
    onInputChange: (Int, String) -> Unit,
    onValidate: () -> Unit,
    onContinue: () -> Unit,
    onReset: () -> Unit
)

// Storage
// - verses.json in assets/ folder with KJV verses
// - Room database (future) for statistics
```

**Technical Details**:
- **Database**: JSON asset file (`assets/verses.json`) containing pre-formatted verses
- **UI Framework**: Jetpack Compose with Material Design 3
- **Architecture**: MVVM pattern with StateFlow for reactive state
- **Validation**: Case-insensitive string matching with trim()
- **Asset Management**: Load verses from assets at startup

---

### 2. Word Grid Search (Boggle-Style)
**Description**: Players find words by connecting adjacent letters in a 4x4 grid, competing against a timer.

**Gameplay**:
- 4x4 grid of random letters (weighted by frequency)
- Players tap/swipe adjacent cells to form words
- Only valid KJV words are accepted (3+ letters)
- 2-minute countdown timer
- Each word can only be found once
- Win by finding 10+ unique words before time expires

**Player Actions**:
- Tap/swipe to select adjacent cells in any direction (horizontal, vertical, diagonal)
- Build word by selecting path of connected cells
- Submit word for validation
- Clear current selection to start over
- View found words and current score

**Win Conditions**:
- **Victory**: Find 10 or more unique valid words within 2 minutes
- **Time's Up**: Game ends when timer reaches 0:00
- **Scoring**: Base 10 points + 5 points per letter beyond 3
  - 3-letter word: 10 points
  - 4-letter word: 15 points
  - 5-letter word: 20 points, etc.

**Android Implementation**:
```kotlin
// Data Layer
data class WordGrid(
    val size: Int,
    val letters: List<List<Char>>
) {
    fun isValidPosition(position: GridPosition): Boolean
    fun areAdjacent(pos1: GridPosition, pos2: GridPosition): Boolean
    fun getWordFromPath(path: List<GridPosition>): String
}

data class GridPosition(val row: Int, val col: Int)

class WordDictionary(context: Context) {
    fun isValidWord(word: String): Boolean
    fun loadWords(): Set<String>
}

class WordGameEngine(
    wordDictionary: WordDictionary,
    gridSize: Int = 4
) {
    fun generateGrid(): WordGrid
    fun validateWord(grid: WordGrid, path: List<GridPosition>): ValidationResult
    fun calculateScore(word: String): Int
}

// ViewModel Layer
class WordGridViewModel(wordDictionary: WordDictionary) : ViewModel() {
    val uiState: StateFlow<WordGridUiState>
    fun addToPath(position: GridPosition)
    fun clearPath()
    fun submitWord()
    fun startNewGame()
}

data class WordGridUiState(
    val grid: WordGrid?,
    val currentPath: List<GridPosition>,
    val foundWords: List<String>,
    val score: Int,
    val timeRemaining: Int, // seconds
    val gameState: WordGridGameState,
    val feedback: String
)

// UI Layer (Jetpack Compose)
@Composable
fun WordGridGameScreen(
    viewModel: WordGridViewModel,
    onBackToMenu: () -> Unit
)

@Composable
fun GridCell(
    letter: Char,
    isSelected: Boolean,
    selectionOrder: Int?,
    onClick: () -> Unit
)
```

**Technical Details**:
- **Letter Distribution**: Weighted random (vowels and common consonants more frequent)
- **Path Validation**: Ensure cells are adjacent and not reused in same word
- **Dictionary**: Extract KJV words from verse data (3+ characters)
- **Timer**: Coroutine-based countdown (viewModelScope.launch)
- **UI**: Material Design 3 with dynamic selection highlighting

---

### 3. Word Matching Pairs
**Description**: Match Bible-related words to their definitions, synonyms, or related concepts.

**Gameplay**:
- Grid of word cards and definition cards (face up)
- Tap two cards to create a pair
- Correct matches stay highlighted and locked
- Incorrect matches show feedback, cards remain available
- Complete all pairs to win

**Player Actions**:
- Tap first card (word or definition)
- Tap second card to attempt match
- View feedback (correct/incorrect)
- Continue until all pairs matched

**Win Conditions**:
- **Victory**: Successfully match all word-definition pairs
- **Scoring**: +15 points per correct match
- **Time Bonus**: Extra points for completing quickly (optional)

**Android Implementation**:
```kotlin
// Data Layer
data class WordPair(
    val word: String,           // "Grace"
    val definition: String,     // "Unmerited favor from God"
    val category: String        // "Theological Terms"
)

class WordMatchingGame(val pairs: List<WordPair>) {
    fun createShuffledCards(): List<MatchCard>
    fun checkMatch(card1: MatchCard, card2: MatchCard): Boolean
}

sealed class MatchCard {
    data class WordCard(val id: Int, val text: String) : MatchCard()
    data class DefinitionCard(val id: Int, val text: String) : MatchCard()
}

// ViewModel Layer
class WordMatchingViewModel : ViewModel() {
    val uiState: StateFlow<WordMatchingUiState>
    fun selectCard(card: MatchCard)
    fun resetGame()
}

data class WordMatchingUiState(
    val cards: List<MatchCard>,
    val selectedCards: List<MatchCard>,
    val matchedPairs: Set<Int>, // IDs of matched pairs
    val score: Int,
    val gameState: MatchGameState,
    val feedback: String
)

// UI Layer
@Composable
fun WordMatchingScreen(
    viewModel: WordMatchingViewModel,
    onBackToMenu: () -> Unit
)

@Composable
fun MatchCardItem(
    card: MatchCard,
    isSelected: Boolean,
    isMatched: Boolean,
    onClick: () -> Unit
)
```

**Technical Details**:
- **Card Layout**: LazyVerticalGrid with 2 columns
- **Match Detection**: Pair IDs link word and definition cards
- **State Management**: Track selected cards (max 2) and matched pairs
- **Bible Data**: Pre-curated word-definition pairs from biblical terms
- **Animations**: Card selection animations with Material Design

---

### 4. Scripture Speed Typing
**Description**: Type Bible verses as quickly and accurately as possible against a timer.

**Gameplay**:
- Display a complete Bible verse
- Player types the verse word-for-word
- Real-time feedback on accuracy (character-by-character or word-by-word)
- Timer tracks completion speed
- Mistakes highlighted in red
- Calculate WPM (words per minute) and accuracy percentage

**Player Actions**:
- Read displayed verse
- Type verse in text field
- Monitor real-time accuracy feedback
- Complete verse before time runs out

**Win Conditions**:
- **Victory**: Complete verse with 90%+ accuracy
- **Scoring**: Based on WPM and accuracy
  - Base score: WPM × Accuracy%
  - Example: 40 WPM × 95% = 38 points
- **Time Limit**: 60-90 seconds per verse (based on verse length)

**Android Implementation**:
```kotlin
// Data Layer
data class TypingChallenge(
    val verse: Verse,
    val targetText: String,
    val timeLimit: Int // seconds
)

class TypingEngine {
    fun calculateAccuracy(typed: String, target: String): Float
    fun calculateWPM(wordsTyped: Int, secondsElapsed: Int): Int
    fun getRealtimeFeedback(typed: String, target: String): List<CharFeedback>
}

data class CharFeedback(
    val char: Char,
    val isCorrect: Boolean,
    val position: Int
)

// ViewModel Layer
class TypingViewModel(repository: VerseRepository) : ViewModel() {
    val uiState: StateFlow<TypingUiState>
    fun updateTypedText(text: String)
    fun completeChallenge()
    fun startNewChallenge()
}

data class TypingUiState(
    val targetVerse: Verse?,
    val typedText: String,
    val characterFeedback: List<CharFeedback>,
    val timeRemaining: Int,
    val wpm: Int,
    val accuracy: Float,
    val gameState: TypingGameState
)

// UI Layer
@Composable
fun TypingChallengeScreen(
    viewModel: TypingViewModel,
    onBackToMenu: () -> Unit
)

@Composable
fun TypedTextDisplay(
    targetText: String,
    typedText: String,
    feedback: List<CharFeedback>
)
```

**Technical Details**:
- **Real-time Feedback**: Compare typed text character-by-character
- **Text Styling**: SpannableString or AnnotatedString for colored feedback
- **Timer**: Countdown based on verse length
- **Input Method**: Standard TextField with focus management
- **Accuracy Calculation**: Levenshtein distance or simple character matching

---

### 5. Daily Verse with Notifications
**Description**: Deliver a new Bible verse every day with optional notification reminders.

**Gameplay**:
- One verse delivered per day at user-selected time
- Verse displayed in clean, readable format with reference
- Option to bookmark favorite verses
- Share verse via Android share sheet
- View verse history (previous daily verses)

**Player Actions**:
- Read daily verse
- Bookmark verse for later reference
- Share verse with others (text, social media)
- Browse verse history
- Set notification time preference

**Win Conditions**:
- **Engagement Goal**: Read daily verse to maintain streak
- **Streak System**: Track consecutive days of reading
- **No competitive scoring**: Focus on spiritual engagement

**Android Implementation**:
```kotlin
// Data Layer
data class DailyVerse(
    val verse: Verse,
    val date: LocalDate,
    val isBookmarked: Boolean = false,
    val isRead: Boolean = false
)

@Entity(tableName = "daily_verses")
data class DailyVerseEntity(
    @PrimaryKey val date: String,
    val reference: String,
    val text: String,
    val isBookmarked: Boolean,
    val isRead: Boolean
)

@Dao
interface DailyVerseDao {
    @Query("SELECT * FROM daily_verses ORDER BY date DESC")
    fun getAllDailyVerses(): Flow<List<DailyVerseEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyVerse(verse: DailyVerseEntity)
}

class DailyVerseRepository(
    private val dao: DailyVerseDao,
    private val verseRepository: VerseRepository
) {
    fun getTodaysVerse(): Flow<DailyVerse?>
    suspend fun generateDailyVerse()
    suspend fun bookmarkVerse(date: LocalDate)
    suspend fun markVerseAsRead(date: LocalDate)
}

// Notification System
class DailyVerseNotificationManager(context: Context) {
    fun scheduleDailyNotification(hour: Int, minute: Int)
    fun cancelNotification()
    fun showNotification(verse: DailyVerse)
}

class DailyVerseWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        // Generate and notify daily verse
    }
}

// ViewModel Layer
class DailyVerseViewModel(
    private val repository: DailyVerseRepository,
    private val notificationManager: DailyVerseNotificationManager
) : ViewModel() {
    val todaysVerse: StateFlow<DailyVerse?>
    val verseHistory: StateFlow<List<DailyVerse>>
    
    fun bookmarkVerse()
    fun shareVerse(verse: DailyVerse)
    fun markAsRead()
    fun setNotificationTime(hour: Int, minute: Int)
}

// UI Layer
@Composable
fun DailyVerseScreen(
    viewModel: DailyVerseViewModel,
    onNavigateToHistory: () -> Unit
)

@Composable
fun VerseCard(
    verse: DailyVerse,
    onBookmark: () -> Unit,
    onShare: () -> Unit
)
```

**Technical Details**:
- **Persistence**: Room database for verse history and bookmarks
- **Notifications**: WorkManager for reliable daily scheduling
- **Notification Channel**: High-priority for devotional content
- **Share Intent**: Android share sheet (Intent.ACTION_SEND)
- **UI**: Material Design 3 card with typography emphasis
- **Date Management**: LocalDate for day-based logic

---

## 🏗️ Architecture Overview

### Technology Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material Design 3)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Async Operations**: Kotlin Coroutines + Flow
- **Database**: Room (SQLite) for persistence
- **Dependency Injection**: Hilt or Koin (recommended)
- **Testing**: JUnit, Mockito, Compose Testing

### Project Structure
```
app/src/main/kotlin/com/purewords1611/android/
├── data/
│   ├── Verse.kt
│   ├── WordGrid.kt
│   ├── WordDictionary.kt
│   ├── WordGameEngine.kt
│   ├── WordMatchingGame.kt
│   ├── VerseRepository.kt
│   └── DailyVerseRepository.kt
├── database/
│   ├── AppDatabase.kt
│   ├── DailyVerseDao.kt
│   └── DailyVerseEntity.kt
├── viewmodel/
│   ├── GameViewModel.kt
│   ├── WordGridViewModel.kt
│   ├── WordMatchingViewModel.kt
│   ├── TypingViewModel.kt
│   └── DailyVerseViewModel.kt
├── ui/
│   ├── theme/ (Color, Type, Theme)
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
├── workers/
│   └── DailyVerseWorker.kt
├── notification/
│   └── DailyVerseNotificationManager.kt
└── MainActivity.kt
```

### Navigation Structure
```
Main Menu (Game Mode Selection)
├── Daily Verse Challenge (Fill-in-the-Blank)
├── Word Grid Search (Boggle-Style)
├── Word Matching Pairs
├── Scripture Speed Typing
└── Daily Verse & History
```

---

## 📊 Comparison Matrix

| Feature | Daily Verse Challenge | Word Grid | Word Matching | Speed Typing | Daily Verse |
|---------|----------------------|-----------|---------------|--------------|-------------|
| **Type** | Fill-in-blank | Word search | Matching | Typing test | Devotional |
| **Timer** | No | Yes (2 min) | Optional | Yes (60-90s) | No |
| **Lives** | Yes (3) | No | No | No | No |
| **Scoring** | +10/correct | +10 base + bonus | +15/match | WPM × Accuracy | Streak-based |
| **Difficulty** | Easy-Medium | Medium-Hard | Easy | Medium-Hard | Easy |
| **Replayability** | High | Very High | Medium | High | Daily |
| **Bible Knowledge** | Required | Optional | Required | Required | None |
| **Competition** | High scores | High scores | High scores | WPM leaderboard | Streaks |

---

## 🎨 UI/UX Considerations

### Design Principles
1. **Clean & Readable**: Emphasis on typography for Bible text readability
2. **Spiritual Aesthetic**: Calming colors, appropriate imagery
3. **Accessibility**: Large text options, high contrast mode, screen reader support
4. **Material Design 3**: Modern Android design language
5. **Performance**: Smooth animations, instant feedback

### Color Palette (Suggested)
```kotlin
// Primary: Deep blue (reverence, wisdom)
val PrimaryColor = Color(0xFF1A4D8F)

// Secondary: Gold (sacred, divine)
val SecondaryColor = Color(0xFFD4AF37)

// Background: Off-white (parchment feel)
val BackgroundColor = Color(0xFFFAF8F3)

// Text: Dark charcoal
val TextColor = Color(0xFF2C2C2C)

// Accent: Burgundy (scripture red)
val AccentColor = Color(0xFF8B0000)
```

### Typography
- **Verse Text**: Serif font (Georgia, Merriweather) for traditional feel
- **UI Elements**: Sans-serif font (Roboto, Inter) for clarity
- **Sizes**: Minimum 16sp for body text, 20sp+ for verses

---

## 📱 Minimum Viable Product (MVP)

### Phase 1: Core Features (Launch)
1. ✅ Daily Verse Challenge (Fill-in-blank)
2. ✅ Word Grid Search (Boggle-style)
3. ✅ Game Mode Selection Menu
4. ✅ Basic scoring and feedback
5. ✅ Material Design 3 theming

### Phase 2: Enhanced Features (Post-Launch)
1. Word Matching Pairs game
2. Scripture Speed Typing game
3. Daily Verse with notifications
4. Room database for persistence
5. Statistics and leaderboards

### Phase 3: Premium Features (Future)
1. Achievements and badges
2. Cloud sync (Firebase)
3. Multiplayer challenges
4. In-app verse library expansion
5. Dark mode with multiple themes

---

## 🔒 Privacy & Data

### Data Collection (Minimal)
- **Anonymous Analytics**: Firebase Analytics (optional, opt-in)
  - Game mode usage
  - Session duration
  - App crashes
- **Local Storage Only**: User progress, bookmarks, preferences
- **No Personal Data**: No accounts, emails, or personal information required

### Permissions Required
- **Notifications** (Optional): For daily verse reminders
- **Internet** (Optional): For analytics only, app works offline

---

## 🚀 Implementation Priority

### Priority 1: Core Gameplay (Weeks 1-2)
1. Verse Repository and data loading
2. Daily Verse Challenge ViewModel and UI
3. Word Grid generation and validation
4. Word Grid ViewModel and UI
5. Game Mode Selection Screen

### Priority 2: Polish & Testing (Week 3)
1. Comprehensive unit tests
2. UI/UX refinements
3. Sound effects and animations
4. Error handling and edge cases
5. Performance optimization

### Priority 3: Additional Features (Week 4+)
1. Word Matching game mode
2. Speed Typing game mode
3. Daily Verse with notifications
4. Statistics tracking
5. Achievements system

---

## ✅ Acceptance Criteria Met

- ✅ **3-5 Core Mechanics Defined**: 5 mechanics described in detail
- ✅ **Clear Descriptions**: Each mechanic has goal, actions, and win conditions
- ✅ **Android Implementation**: Kotlin code structure for each mechanic
- ✅ **PR-Ready Format**: Comprehensive Markdown documentation
- ✅ **Actionable**: Clear architecture and implementation guidance

---

## 📝 Next Steps

1. **Review & Approve**: Stakeholder review of mechanics
2. **Prioritize Features**: Confirm MVP features for launch
3. **Create Issues**: Break down into implementable tasks
4. **Design Assets**: Create app icon, screenshots, promotional graphics
5. **Begin Development**: Start with Priority 1 features

---

**Document Created**: 2026-01-02  
**Author**: GitHub Copilot Coding Agent  
**Status**: ✅ Ready for Review  
**Branch**: copilot/define-core-gameplay-mechanics-again

---

*"Thy word is a lamp unto my feet, and a light unto my path." - Psalm 119:105 (KJV)*
