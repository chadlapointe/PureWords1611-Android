# PureWords1611 - Core Game Loop Architecture

**Document Version**: 1.0  
**Date**: January 2, 2026  
**Purpose**: Technical specification of core game loops and architecture

---

## Overview

This document provides detailed technical specifications for the core game loops in PureWords1611, including state machines, data flow diagrams, and implementation details for each of the three game modes.

---

## Application-Level Game Loop

### Main Navigation Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                        App Launch                                │
│                     (MainActivity)                               │
└──────────────────────────┬──────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────────┐
│                   Initialize Analytics                           │
│               AnalyticsManager.getInstance()                     │
└──────────────────────────┬──────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────────┐
│                  Main Menu Screen                                │
│            (GameModeSelectionScreen)                             │
│                                                                  │
│         [Verse Challenge]  [Word Grid]  [Word Matching]         │
└───┬──────────────────┬─────────────────┬────────────────────────┘
    │                  │                 │
    │                  │                 └─────────────┐
    │                  │                               │
    ▼                  ▼                               ▼
┌────────────┐  ┌──────────────┐              ┌──────────────────┐
│   Verse    │  │  Word Grid   │              │  Word Matching   │
│  Challenge │  │     Game     │              │      Game        │
│    Mode    │  │     Mode     │              │      Mode        │
└─────┬──────┘  └──────┬───────┘              └────────┬─────────┘
      │                │                                │
      └────────────────┴────────────┬───────────────────┘
                                    │
                                    ▼
                         ┌──────────────────┐
                         │  Back to Menu    │
                         │  (User action)   │
                         └──────────────────┘
```

### State Management Architecture

```kotlin
// MainActivity.kt
enum class GameMode {
    MENU,           // Main menu selection screen
    VERSE_GAME,     // Verse Challenge mode
    WORD_GRID,      // Word Grid mode
    WORD_MATCHING   // Word Matching mode
}

@Composable
fun GameScreen() {
    var currentMode by remember { mutableStateOf(GameMode.MENU) }
    val analyticsManager = remember { AnalyticsManager.getInstance(context) }
    
    // Track screen views when mode changes
    LaunchedEffect(currentMode) {
        analyticsManager.trackScreenView(currentMode.name)
    }
    
    when (currentMode) {
        GameMode.MENU -> GameModeSelectionScreen(...)
        GameMode.VERSE_GAME -> VerseGameScreen(...)
        GameMode.WORD_GRID -> WordGridScreen(...)
        GameMode.WORD_MATCHING -> WordMatchingScreen(...)
    }
}
```

---

## Game Mode 1: Verse Challenge Loop

### State Machine

```
┌──────────────┐
│   Loading    │  Initial state while verses load
└──────┬───────┘
       │ loadNextVerse()
       ▼
┌──────────────┐
│   Playing    │  Waiting for user input
└──┬─────┬─────┘
   │     │
   │     └─────────── updateInput() ─────┐
   │                                     │
   │ validateAnswer()                    │
   ▼                                     │
┌──────────────┐                         │
│  Validate    │  Check user input       │
└──┬─────┬─────┘                         │
   │     │                               │
   │     └─ Incorrect ──┐                │
   │                    │                │
   │ Correct            ▼                │
   ▼              ┌──────────────┐       │
┌──────────────┐  │  Incorrect   │       │
│   Correct    │  │  - lives--   │       │
│  + score     │  │  Show verse  │       │
│  Show verse  │  └──────┬───────┘       │
└──────┬───────┘         │               │
       │                 │               │
       │ continueGame()  │               │
       ▼                 ▼               │
   ┌─────────────────────┐               │
   │  Check Lives > 0?   │               │
   └──┬──────────────┬───┘               │
      │              │                   │
    Yes             No                   │
      │              │                   │
      │              ▼                   │
      │      ┌──────────────┐            │
      │      │  Game Over   │            │
      │      │ Show score   │            │
      │      └──────┬───────┘            │
      │             │                    │
      │       resetGame()                │
      │             │                    │
      └─────────────┴────────────────────┘
          Load next verse (loop)
```

### Data Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                      User Interaction                            │
│  (Compose UI - GameplayLoop.kt)                                  │
│   - Input text fields                                            │
│   - Check Answer button                                          │
│   - Continue button                                              │
└────────────────────┬────────────────────────────────────────────┘
                     │ User actions
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│                    GameViewModel                                 │
│  Methods:                                                        │
│   • updateInput(index, text)    - Update specific input field   │
│   • validateAnswer()            - Check all inputs               │
│   • continueGame()              - Load next verse                │
│   • resetGame()                 - Start over                     │
│                                                                  │
│  State (_uiState: MutableStateFlow<GameUiState>):                │
│   - currentVerse: Verse?                                         │
│   - userInputs: List<String>                                     │
│   - score: Int                                                   │
│   - lives: Int                                                   │
│   - gameState: GameState                                         │
│   - feedback: String                                             │
└────────────────────┬────────────────────────────────────────────┘
                     │ Repository calls
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│                    VerseRepository                               │
│  Methods:                                                        │
│   • loadVerses()        - Load from verses.json                  │
│   • getRandomVerse()    - Select random verse                    │
│                                                                  │
│  Data Source: app/src/main/assets/verses.json                   │
└─────────────────────────────────────────────────────────────────┘
```

### State Definitions

```kotlin
sealed class GameState {
    object Loading : GameState()
    object Playing : GameState()
    object Correct : GameState()
    object Incorrect : GameState()
    object GameOver : GameState()
}

data class GameUiState(
    val currentVerse: Verse?,
    val userInputs: List<String>,
    val score: Int,
    val lives: Int,
    val gameState: GameState,
    val feedback: String
)

data class Verse(
    val reference: String,      // "Genesis 1:1"
    val text: String,           // Full verse text
    val blankedText: String,    // Text with blanks
    val missingWords: List<String>  // Correct answers
)
```

### Key Algorithms

#### Answer Validation
```kotlin
fun validateAnswer() {
    val isCorrect = userInputs.zip(currentVerse.missingWords)
        .all { (input, answer) ->
            input.trim().equals(answer, ignoreCase = true)
        }
    
    if (isCorrect) {
        _score += 10
        _gameState = GameState.Correct
    } else {
        _lives -= 1
        _gameState = if (_lives > 0) {
            GameState.Incorrect
        } else {
            GameState.GameOver
        }
    }
}
```

---

## Game Mode 2: Word Grid Loop

### State Machine

```
┌──────────────┐
│   Loading    │  Initialize game
└──────┬───────┘
       │ startNewGame()
       ▼
┌─────────────────────────────────────────────────────────────────┐
│                        Playing                                   │
│                                                                  │
│  ┌────────────────────┐                                         │
│  │ Timer Running (2m) │                                         │
│  └────────┬───────────┘                                         │
│           │                                                      │
│           ▼                                                      │
│  ┌────────────────────┐     addToPath()   ┌──────────────┐     │
│  │  Build Word Path   │ ◄───────────────  │ User taps    │     │
│  │  (select cells)    │                   │ grid cells   │     │
│  └────────┬───────────┘                   └──────────────┘     │
│           │                                                      │
│           │ submitWord()                                         │
│           ▼                                                      │
│  ┌────────────────────┐                                         │
│  │  Validate Word     │                                         │
│  │  & Path            │                                         │
│  └────┬──────┬────────┘                                         │
│       │      │                                                   │
│   Valid   Invalid                                                │
│       │      │                                                   │
│       │      └─── clearPath() ───┐                              │
│       │                           │                              │
│       ▼                           │                              │
│  ┌────────────────────┐          │                              │
│  │  + score           │          │                              │
│  │  Add to found list │          │                              │
│  │  Clear path        │          │                              │
│  └────────┬───────────┘          │                              │
│           │                      │                              │
│           └──────────────────────┘                              │
│                 │                                                │
│           Check Conditions                                       │
│                 │                                                │
└─────────────────┼────────────────────────────────────────────────┘
                  │
      ┌───────────┴───────────┐
      │                       │
  Time = 0            Found >= 10 words
      │                       │
      ▼                       ▼
┌──────────────┐      ┌──────────────┐
│  Time's Up   │      │   Victory    │
│  Show score  │      │  Show score  │
│  Play again? │      │  Play again? │
└──────┬───────┘      └──────┬───────┘
       │                     │
       │ resetGame()         │
       └─────────────────────┘
               │
               ▼
         Back to Loading
```

### Data Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                      User Interaction                            │
│  (WordGridGameScreen.kt)                                         │
│   - Tap grid cells                                               │
│   - Submit button                                                │
│   - Clear button                                                 │
└────────────────────┬────────────────────────────────────────────┘
                     │ UI events
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│                  WordGridViewModel                               │
│  Methods:                                                        │
│   • startNewGame()      - Generate new grid, start timer         │
│   • addToPath(position) - Add cell to current selection          │
│   • clearPath()         - Reset current selection                │
│   • submitWord()        - Validate and score word                │
│   • pauseGame()         - Stop timer                             │
│   • resetGame()         - Restart game                           │
│                                                                  │
│  State (_uiState: MutableStateFlow<WordGridUiState>):            │
│   - grid: WordGrid?                                              │
│   - currentPath: List<GridPosition>                              │
│   - foundWords: List<String>                                     │
│   - score: Int                                                   │
│   - timeRemaining: Int (seconds)                                 │
│   - gameState: WordGridGameState                                 │
│   - feedback: String                                             │
│                                                                  │
│  Timer (Coroutine):                                              │
│   viewModelScope.launch {                                        │
│     while (timeRemaining > 0 && gameState == Playing) {          │
│       delay(1000)                                                │
│       timeRemaining -= 1                                         │
│     }                                                            │
│   }                                                              │
└────────────────────┬────────────────────────────────────────────┘
                     │ Engine & Dictionary calls
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│              WordGameEngine + WordDictionary                     │
│                                                                  │
│  WordGameEngine:                                                 │
│   • generateGrid()         - Create 4x4 weighted random grid     │
│   • validateWord()         - Check path validity and dictionary  │
│   • calculateScore(word)   - Base + length bonus                 │
│                                                                  │
│  WordDictionary:                                                 │
│   • loadWords()            - Extract from verses.json            │
│   • isValidWord(word)      - Check KJV dictionary                │
└─────────────────────────────────────────────────────────────────┘
```

### Grid Data Structure

```kotlin
data class WordGrid(
    val size: Int = 4,
    val letters: List<List<Char>>
) {
    fun getLetterAt(position: GridPosition): Char
    fun isValidPosition(position: GridPosition): Boolean
    fun areAdjacent(pos1: GridPosition, pos2: GridPosition): Boolean
    fun getWordFromPath(path: List<GridPosition>): String
    fun isValidPath(path: List<GridPosition>): Boolean
}

data class GridPosition(
    val row: Int,
    val col: Int
)
```

### Adjacency Algorithm

```kotlin
fun areAdjacent(pos1: GridPosition, pos2: GridPosition): Boolean {
    val rowDiff = abs(pos1.row - pos2.row)
    val colDiff = abs(pos1.col - pos2.col)
    
    // Adjacent if within 1 step (horizontal, vertical, or diagonal)
    // But not the same cell
    return (rowDiff <= 1 && colDiff <= 1) && 
           !(rowDiff == 0 && colDiff == 0)
}
```

### Path Validation Algorithm

```kotlin
fun isValidPath(path: List<GridPosition>): Boolean {
    // Minimum length check
    if (path.size < 2) return false
    
    // No duplicate positions
    if (path.toSet().size != path.size) return false
    
    // All positions valid
    if (!path.all { isValidPosition(it) }) return false
    
    // Consecutive positions are adjacent
    for (i in 0 until path.size - 1) {
        if (!areAdjacent(path[i], path[i + 1])) {
            return false
        }
    }
    
    return true
}
```

### Score Calculation

```kotlin
fun calculateScore(word: String): Int {
    val baseScore = 10
    val lengthBonus = maxOf(0, word.length - 3) * 5
    return baseScore + lengthBonus
}

// Examples:
// 3-letter word: 10 + 0 = 10 points
// 4-letter word: 10 + 5 = 15 points
// 5-letter word: 10 + 10 = 20 points
// 6-letter word: 10 + 15 = 25 points
```

---

## Game Mode 3: Word Matching Loop

### State Machine

```
┌──────────────┐
│   Loading    │  Initialize level 1
└──────┬───────┘
       │ loadLevel(1)
       ▼
┌─────────────────────────────────────────────────────────────────┐
│                        Playing                                   │
│                                                                  │
│  Current Level: X/5                                              │
│  Matches: Y/5                                                    │
│                                                                  │
│  ┌──────────────────┐                                           │
│  │  Display Pairs   │                                           │
│  │  (2 columns)     │                                           │
│  └────────┬─────────┘                                           │
│           │                                                      │
│           │ User taps word                                       │
│           ▼                                                      │
│  ┌──────────────────┐                                           │
│  │  Select Word     │                                           │
│  │  (left or right) │                                           │
│  └────────┬─────────┘                                           │
│           │                                                      │
│           │ Both words selected?                                │
│           ▼                                                      │
│  ┌──────────────────┐                                           │
│  │  Auto-Validate   │                                           │
│  │  Match           │                                           │
│  └────┬──────┬──────┘                                           │
│       │      │                                                   │
│   Valid   Invalid                                                │
│       │      │                                                   │
│       │      └─── Deselect, -2 points                           │
│       │                                                          │
│       ▼                                                          │
│  ┌──────────────────┐                                           │
│  │  Mark matched    │                                           │
│  │  + 10 points     │                                           │
│  │  Turn green      │                                           │
│  └────────┬─────────┘                                           │
│           │                                                      │
│           │ All 5 pairs matched?                                │
│           ▼                                                      │
└───────────┼──────────────────────────────────────────────────────┘
            │
         ┌──┴──┐
        Yes   No
         │     └─── Continue playing
         ▼
┌──────────────────┐
│  Level Complete  │
│  Show score      │
│  Perfect bonus?  │
└────────┬─────────┘
         │ nextLevel()
         ▼
    More levels?
         │
    ┌────┴────┐
   Yes       No
    │         │
    │         ▼
    │    ┌──────────────┐
    │    │ Game Complete│
    │    │ Total score  │
    │    └──────┬───────┘
    │           │
    │     resetGame()
    └───────────┘
         │
         ▼
    Back to Level 1
```

### Data Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                      User Interaction                            │
│  (WordMatchingGameScreen.kt)                                     │
│   - Tap left column word                                         │
│   - Tap right column word                                        │
│   - Next Level button                                            │
│   - Retry Level button                                           │
└────────────────────┬────────────────────────────────────────────┘
                     │ UI events
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│               WordMatchingViewModel                              │
│  Methods:                                                        │
│   • selectLeftWord(id)   - Select word from left column          │
│   • selectRightWord(id)  - Select word from right column         │
│   • checkMatch()         - Auto-validate when both selected      │
│   • nextLevel()          - Advance to next level                 │
│   • retryLevel()         - Restart current level                 │
│   • resetGame()          - Start from level 1                    │
│                                                                  │
│  State (_uiState: MutableStateFlow<WordMatchingUiState>):        │
│   - currentLevel: Int (0-4 for levels 1-5)                       │
│   - leftWords: List<MatchableWord>                               │
│   - rightWords: List<MatchableWord>                              │
│   - selectedLeftId: Int?                                         │
│   - selectedRightId: Int?                                        │
│   - completedMatches: Set<Pair<Int, Int>>                        │
│   - score: Int                                                   │
│   - mistakes: Int                                                │
│   - gameState: MatchingGameState                                 │
│   - levelComplete: Boolean                                       │
└────────────────────┬────────────────────────────────────────────┘
                     │ Engine calls
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│                 WordMatchingEngine                               │
│  Methods:                                                        │
│   • getLevelPairs(level)      - Get pairs for level              │
│   • getTotalLevels()          - Return 5                         │
│   • isValidMatch(left, right) - Check if words match             │
│   • calculateScore()          - Base + perfect bonus - mistakes  │
│                                                                  │
│  Data:                                                           │
│   LEVELS: List<List<WordPair>>  (5 levels, 5 pairs each)        │
│                                                                  │
│  Scoring:                                                        │
│   - +10 per match                                                │
│   - +50 if level completed with 0 mistakes                       │
│   - -2 per mistake                                               │
└─────────────────────────────────────────────────────────────────┘
```

### Match Validation

```kotlin
fun checkMatch() {
    val leftWord = leftWords.find { it.id == selectedLeftId }?.text
    val rightWord = rightWords.find { it.id == selectedRightId }?.text
    
    if (leftWord != null && rightWord != null) {
        if (engine.isValidMatch(leftWord, rightWord, currentLevelPairs)) {
            // Valid match
            _score += 10
            _completedMatches.add(Pair(selectedLeftId!!, selectedRightId!!))
            
            // Check if level complete
            if (_completedMatches.size == currentLevelPairs.size) {
                // Perfect bonus if no mistakes
                if (_mistakes == 0) {
                    _score += 50
                }
                _gameState = MatchingGameState.LevelComplete
            }
        } else {
            // Invalid match
            _mistakes += 1
            _score = maxOf(0, _score - 2)
        }
        
        // Deselect both words
        _selectedLeftId = null
        _selectedRightId = null
    }
}
```

### Level Progression

```kotlin
fun nextLevel() {
    _currentLevel += 1
    
    if (_currentLevel >= engine.getTotalLevels()) {
        _gameState = MatchingGameState.GameComplete
    } else {
        loadLevel(_currentLevel)
        _gameState = MatchingGameState.Playing
    }
}

fun loadLevel(level: Int) {
    val pairs = engine.getLevelPairs(level)
    
    // Shuffle words for each column
    _leftWords = pairs.mapIndexed { index, pair ->
        MatchableWord(pair.leftWord, index)
    }.shuffled()
    
    _rightWords = pairs.mapIndexed { index, pair ->
        MatchableWord(pair.rightWord, index + 100) // Offset IDs
    }.shuffled()
    
    // Reset level state
    _completedMatches = emptySet()
    _mistakes = 0
    _selectedLeftId = null
    _selectedRightId = null
}
```

---

## Common Patterns Across All Game Modes

### MVVM Architecture

All game modes follow the same architectural pattern:

```
┌─────────────────────────────────────────────────────────────────┐
│                         UI Layer                                 │
│  (Jetpack Compose Composables)                                   │
│  - Stateless functions                                           │
│  - Observe ViewModel state via collectAsState()                  │
│  - Emit user events to ViewModel                                 │
│  - No business logic                                             │
└────────────────────┬────────────────────────────────────────────┘
                     │ StateFlow observation
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│                      ViewModel Layer                             │
│  (AndroidX ViewModel)                                            │
│  - Holds UI state in StateFlow                                   │
│  - Contains business logic                                       │
│  - Coordinates between UI and Data layer                         │
│  - Survives configuration changes                                │
│  - Lifecycle-aware                                               │
└────────────────────┬────────────────────────────────────────────┘
                     │ Repository/Engine calls
                     ▼
┌─────────────────────────────────────────────────────────────────┐
│                       Data Layer                                 │
│  (Repositories, Engines, Models)                                 │
│  - Data models (Verse, WordGrid, WordPair)                       │
│  - Business logic engines                                        │
│  - Data sources (JSON assets)                                    │
│  - No Android dependencies                                       │
└─────────────────────────────────────────────────────────────────┘
```

### State Management Pattern

```kotlin
// All ViewModels follow this pattern:

class SomeViewModel(dependencies) : ViewModel() {
    // Private mutable state
    private val _uiState = MutableStateFlow(InitialState)
    
    // Public immutable state for UI
    val uiState: StateFlow<SomeUiState> = _uiState.asStateFlow()
    
    // Public methods for UI interactions
    fun onUserAction() {
        // Business logic
        _uiState.value = _uiState.value.copy(
            field = newValue
        )
    }
    
    // Private helper methods
    private fun helperMethod() { }
}
```

### ViewModel Factory Pattern

```kotlin
// All ViewModels use factories for dependency injection:

class SomeViewModelFactory(
    private val dependency: SomeDependency
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SomeViewModel(dependency) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// Usage in Composable:
@Composable
fun SomeScreen() {
    val dependency = remember { SomeDependency(context) }
    val viewModel: SomeViewModel = viewModel(
        factory = SomeViewModelFactory(dependency)
    )
    val uiState by viewModel.uiState.collectAsState()
    // ...
}
```

### Coroutine Usage

```kotlin
// ViewModels use viewModelScope for coroutines:

class SomeViewModel : ViewModel() {
    init {
        // Load data on initialization
        viewModelScope.launch {
            loadData()
        }
    }
    
    fun startTimer() {
        // Timer coroutine (Word Grid)
        viewModelScope.launch {
            while (condition) {
                delay(1000)
                updateTimer()
            }
        }
    }
    
    // Coroutines automatically cancelled when ViewModel cleared
}
```

---

## Analytics Integration

### Event Tracking Points

```
Application Start
     │
     ├─→ Track: App Launch
     │
     ▼
Main Menu Displayed
     │
     ├─→ Track: Screen View ("Menu")
     │
User Selects Game Mode
     │
     ├─→ Track: Game Mode Selected ("verse_game" | "word_grid" | "word_matching")
     ├─→ Track: Screen View (game mode name)
     │
     ▼
Game Play Session
     │
     ├─→ [Game-specific events]
     │    - Score updates
     │    - Game completion
     │    - Level progression
     │
User Returns to Menu
     │
     └─→ Track: Return to Menu (from which game)
```

### Analytics Implementation

```kotlin
class AnalyticsManager private constructor(context: Context) {
    fun trackScreenView(screenName: String)
    fun trackGameModeSelected(mode: String)
    fun trackReturnToMenu(fromScreen: String)
    fun trackGameCompleted(mode: String, score: Int, duration: Long)
    fun trackLevelCompleted(mode: String, level: Int, score: Int)
    
    companion object {
        @Volatile
        private var instance: AnalyticsManager? = null
        
        fun getInstance(context: Context): AnalyticsManager {
            return instance ?: synchronized(this) {
                instance ?: AnalyticsManager(context).also { instance = it }
            }
        }
    }
}
```

---

## Performance Optimization Strategies

### UI Layer Optimizations

```kotlin
// Use remember for expensive operations
@Composable
fun GameScreen() {
    val repository = remember { VerseRepository(context) }
    val viewModel = remember { GameViewModel(repository) }
}

// Use immutable state for efficient recomposition
data class GameUiState(
    val score: Int,
    val lives: Int
    // All properties are val (immutable)
)

// Use derivedStateOf for computed values
@Composable
fun Timer(timeRemaining: Int) {
    val timeString by remember {
        derivedStateOf {
            String.format("%d:%02d", 
                timeRemaining / 60, 
                timeRemaining % 60
            )
        }
    }
}
```

### ViewModel Optimizations

```kotlin
class GameViewModel : ViewModel() {
    // Use StateFlow instead of LiveData for better performance
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    
    // Batch state updates
    fun updateGameState(score: Int, lives: Int) {
        _uiState.value = _uiState.value.copy(
            score = score,
            lives = lives
        )
        // Single recomposition instead of two
    }
    
    // Use viewModelScope for automatic cancellation
    private var timerJob: Job? = null
    
    fun startTimer() {
        timerJob?.cancel()  // Cancel previous timer
        timerJob = viewModelScope.launch {
            // Timer logic
        }
    }
    
    override fun onCleared() {
        // Cleanup automatically handled by viewModelScope
        super.onCleared()
    }
}
```

### Data Layer Optimizations

```kotlin
// Load data once and cache
class VerseRepository(context: Context) {
    private var cachedVerses: List<Verse>? = null
    
    suspend fun loadVerses(): List<Verse> {
        return cachedVerses ?: run {
            val verses = loadFromAssets(context)
            cachedVerses = verses
            verses
        }
    }
}

// Use HashSet for O(1) lookup
class WordDictionary(context: Context) {
    private val words: Set<String> by lazy {
        loadWords().toSet()  // O(1) lookup instead of O(n)
    }
    
    fun isValidWord(word: String): Boolean {
        return words.contains(word.lowercase())
    }
}
```

---

## Error Handling Strategy

### Graceful Degradation

```kotlin
// Repository level
class VerseRepository(context: Context) {
    suspend fun loadVerses(): List<Verse> {
        return try {
            parseVerses(loadJsonFromAssets(context))
        } catch (e: IOException) {
            Log.e(TAG, "Failed to load verses", e)
            emptyList()  // Graceful degradation
        } catch (e: JSONException) {
            Log.e(TAG, "Failed to parse verses", e)
            getDefaultVerses()  // Fallback data
        }
    }
}

// ViewModel level
class GameViewModel(repository: VerseRepository) : ViewModel() {
    fun loadNextVerse() {
        viewModelScope.launch {
            try {
                val verse = repository.getRandomVerse()
                if (verse != null) {
                    _uiState.value = _uiState.value.copy(
                        currentVerse = verse,
                        gameState = GameState.Playing
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        gameState = GameState.Error,
                        feedback = "No verses available"
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading verse", e)
                _uiState.value = _uiState.value.copy(
                    gameState = GameState.Error,
                    feedback = "Failed to load verse"
                )
            }
        }
    }
}
```

---

## Testing Strategy

### Unit Test Structure

```kotlin
// Data layer tests
class WordGridTest {
    @Test
    fun `test grid creation`() { }
    
    @Test
    fun `test adjacency checking`() { }
    
    @Test
    fun `test path validation`() { }
}

// ViewModel tests
class GameViewModelTest {
    private lateinit var repository: VerseRepository
    private lateinit var viewModel: GameViewModel
    
    @Before
    fun setup() {
        repository = mock()
        viewModel = GameViewModel(repository)
    }
    
    @Test
    fun `test answer validation`() = runTest {
        // Test business logic
    }
}
```

---

## Summary

This architecture provides:

✅ **Separation of Concerns**: Clear boundaries between UI, business logic, and data  
✅ **Testability**: Each layer can be tested independently  
✅ **Scalability**: Easy to add new game modes following established patterns  
✅ **Maintainability**: Consistent patterns across all components  
✅ **Performance**: Optimized for smooth 60fps UI  
✅ **Reliability**: Comprehensive error handling and graceful degradation  

All three game modes follow these consistent patterns while implementing their unique gameplay mechanics, making the codebase maintainable and extensible.

---

**Document Status**: ✅ Complete  
**Last Updated**: January 2, 2026  
**Related Documents**: FEATURE_SET_DEFINITION.md, GAMEPLAY_DOCUMENTATION.md
