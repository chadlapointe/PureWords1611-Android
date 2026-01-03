# PureWords1611-Android: App Structure Overview

**Project**: PureWords1611-Android  
**Purpose**: Technical architecture and structure documentation  
**Date**: January 3, 2026  
**Status**: ✅ Complete

---

## 📐 Architecture Overview

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                         PureWords1611 Application                    │
│                              (Android)                               │
└─────────────────────────────────────────────────────────────────────┘
                                  │
                    ┌─────────────┴─────────────┐
                    │                           │
        ┌───────────▼──────────┐    ┌──────────▼───────────┐
        │  Presentation Layer   │    │  System Services     │
        │  (Jetpack Compose)    │    │  (Android + Hilt)    │
        └───────────┬───────────┘    └──────────┬───────────┘
                    │                           │
        ┌───────────▼──────────┐    ┌──────────▼───────────┐
        │   ViewModel Layer     │    │  Analytics Service   │
        │   (Business Logic)    │    │  (Firebase)          │
        └───────────┬───────────┘    └──────────────────────┘
                    │
        ┌───────────▼──────────┐
        │     Data Layer        │
        │  (Models + Engines)   │
        └───────────────────────┘
```

### MVVM Pattern Implementation

```
┌──────────────────┐         ┌──────────────────┐         ┌──────────────────┐
│                  │         │                  │         │                  │
│      VIEW        │◀────────│    VIEWMODEL     │◀────────│      MODEL       │
│  (Compose UI)    │         │   (State + Logic)│         │  (Data + Engine) │
│                  │         │                  │         │                  │
│  • Composable    │ observe │  • StateFlow     │  uses   │  • Data Classes  │
│  • UI Events     │────────▶│  • User Actions  │────────▶│  • Game Engines  │
│  • Rendering     │         │  • Business Logic│         │  • Repositories  │
│                  │         │  • @HiltViewModel│         │  • Dictionary    │
└──────────────────┘         └──────────────────┘         └──────────────────┘
```

---

## 🗂️ Package Structure

### Complete Package Hierarchy

```
com.purewords1611.android
│
├── 📱 MainActivity.kt                    [Entry Point]
│   • @AndroidEntryPoint
│   • Hosts Compose UI
│   • Game mode routing
│   • Analytics integration
│
├── 🎯 PureWordsApplication.kt           [Application Class]
│   • @HiltAndroidApp
│   • Firebase initialization
│   • App-wide setup
│
├── 📦 di/                               [Dependency Injection]
│   ├── AppModule.kt                     • App-level dependencies
│   └── DataModule.kt                    • Data layer dependencies
│
├── 🎨 ui/                               [UI Layer - Jetpack Compose]
│   │
│   ├── GameModeSelectionScreen.kt       [Main Menu]
│   │   • Three game mode cards
│   │   • Material Design 3 layout
│   │   • Navigation callbacks
│   │
│   ├── gameplay/                        [Verse Challenge UI]
│   │   └── GameplayLoop.kt
│   │       • Verse display with blanks
│   │       • Input fields
│   │       • Validation feedback
│   │       • Lives and score display
│   │
│   ├── wordgrid/                        [Word Grid UI]
│   │   └── WordGridGameScreen.kt
│   │       • 4×4 letter grid
│   │       • Path selection
│   │       • Timer and score
│   │       • Found words list
│   │
│   ├── wordmatching/                    [Word Matching UI]
│   │   └── WordMatchingGameScreen.kt
│   │       • Two-column word layout
│   │       • Match connections
│   │       • Level progression
│   │       • Score display
│   │
│   └── theme/                           [Design System]
│       ├── Color.kt                     • Color definitions
│       ├── Theme.kt                     • Material 3 theme
│       └── Type.kt                      • Typography scale
│
├── 🧠 viewmodel/                        [ViewModel Layer]
│   │
│   ├── GameViewModel.kt                 [Verse Challenge Logic]
│   │   • Verse loading
│   │   • Answer validation
│   │   • Lives management
│   │   • Score calculation
│   │   • @HiltViewModel
│   │
│   ├── WordGridViewModel.kt             [Word Grid Logic]
│   │   • Grid generation
│   │   • Path tracking
│   │   • Word validation
│   │   • Timer management
│   │   • @HiltViewModel
│   │
│   ├── WordMatchingViewModel.kt         [Word Matching Logic]
│   │   • Word pair generation
│   │   • Match validation
│   │   • Level progression
│   │   • Score tracking
│   │   • @HiltViewModel
│   │
│   └── GameViewModelFactory.kt          [Factory]
│       • ViewModel creation
│       • Dependency injection
│
├── 📊 data/                             [Data Layer]
│   │
│   ├── Verse.kt                         [Data Model]
│   │   • KJV verse data class
│   │   • Blank word positions
│   │   • Reference information
│   │
│   ├── WordGrid.kt                      [Data Model]
│   │   • 4×4 letter grid
│   │   • Valid word list
│   │   • Grid generation logic
│   │
│   ├── WordMatchingGame.kt              [Data Model]
│   │   • Word pairs
│   │   • Level data
│   │   • Match state
│   │
│   ├── WordGameEngine.kt                [Business Logic]
│   │   • Verse selection algorithm
│   │   • Blank word generation
│   │   • Answer validation
│   │   • Score calculation
│   │
│   ├── VerseRepository.kt               [Data Access]
│   │   • Verse database access
│   │   • Caching logic
│   │   • Data transformation
│   │
│   └── WordDictionary.kt                [Utility]
│       • KJV word validation
│       • Dictionary lookup
│       • Word list management
│
└── 📈 analytics/                        [Analytics Layer]
    └── AnalyticsManager.kt              [Firebase Wrapper]
        • Event tracking
        • Screen view tracking
        • Custom events
        • @Singleton
```

---

## 🎯 Component Responsibilities

### MainActivity

**Role**: Main entry point and navigation controller

**Responsibilities**:
- Initialize Compose UI
- Manage game mode state
- Route between screens
- Inject analytics
- Track screen views

**Key Code**:
```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var analyticsManager: AnalyticsManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analyticsManager.trackAppLaunch()
        setContent {
            PureWords1611Theme {
                GameScreen(analyticsManager)
            }
        }
    }
}
```

---

### ViewModels

#### GameViewModel (Verse Challenge)

**State**:
```kotlin
data class GameUiState(
    val verse: Verse?,
    val userInputs: List<String>,
    val isValidated: Boolean,
    val isCorrect: Boolean,
    val lives: Int,
    val score: Int,
    val gameOver: Boolean
)
```

**Actions**:
- `updateInput(index: Int, text: String)` - Update answer field
- `validateAnswer()` - Check if answers are correct
- `continueGame()` - Load next verse
- `resetGame()` - Restart with 3 lives

---

#### WordGridViewModel

**State**:
```kotlin
data class WordGridUiState(
    val grid: WordGrid,
    val currentPath: List<Position>,
    val foundWords: List<String>,
    val score: Int,
    val timeRemaining: Int,
    val gameOver: Boolean,
    val isVictory: Boolean
)
```

**Actions**:
- `addToPath(position: Position)` - Add letter to current word
- `submitWord()` - Validate and score current word
- `clearPath()` - Reset current selection
- `resetGame()` - Generate new grid

---

#### WordMatchingViewModel

**State**:
```kotlin
data class WordMatchingUiState(
    val leftWords: List<Word>,
    val rightWords: List<Word>,
    val selectedLeft: String?,
    val selectedRight: String?,
    val matches: List<Pair<String, String>>,
    val score: Int,
    val level: Int,
    val levelComplete: Boolean,
    val gameComplete: Boolean
)
```

**Actions**:
- `selectLeftWord(id: String)` - Select word from left column
- `selectRightWord(id: String)` - Select word from right column and check match
- `nextLevel()` - Progress to next level
- `retryLevel()` - Retry current level
- `resetGame()` - Return to level 1

---

## 🔄 Data Flow

### Verse Challenge Flow

```
User Action: Enter answer and tap "Validate"
     │
     ▼
[GameViewModel.validateAnswer()]
     │
     ├─▶ Get current verse
     ├─▶ Compare user inputs with correct words
     ├─▶ Calculate score
     │   ├─▶ Correct: +10 points
     │   └─▶ Incorrect: −1 life
     └─▶ Update UiState
         │
         ▼
[Compose UI re-renders]
     │
     ├─▶ Show feedback (✓ or ✗)
     ├─▶ Update lives display
     ├─▶ Update score
     └─▶ Reveal complete verse
```

---

### Word Grid Flow

```
User Action: Tap letter cells to form word, tap "Submit"
     │
     ▼
[WordGridViewModel.submitWord()]
     │
     ├─▶ Build word from current path
     ├─▶ Validate against dictionary
     ├─▶ Check if already found
     ├─▶ Calculate points
     │   └─▶ Base 10 + (length - 3) × 5
     └─▶ Update UiState
         │
         ▼
[Compose UI re-renders]
     │
     ├─▶ Add word to found list
     ├─▶ Update score
     ├─▶ Clear path selection
     └─▶ Check win condition (10+ words)
```

---

### Word Matching Flow

```
User Action: Tap word in left column, then tap word in right column
     │
     ▼
[WordMatchingViewModel.selectRightWord()]
     │
     ├─▶ Get selected left word
     ├─▶ Get tapped right word
     ├─▶ Check if pair is correct
     │   ├─▶ Correct: Mark matched, +10 points
     │   └─▶ Incorrect: Clear selection
     ├─▶ Check if level complete (all matched)
     │   ├─▶ All matched: +50 bonus
     │   └─▶ Continue: Clear selection
     └─▶ Update UiState
         │
         ▼
[Compose UI re-renders]
     │
     ├─▶ Show match result (✓ or ✗)
     ├─▶ Update score
     ├─▶ Disable matched words
     └─▶ Show level complete if all matched
```

---

## 🔌 Dependency Injection (Hilt)

### Module Configuration

#### AppModule

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAnalyticsManager(
        @ApplicationContext context: Context
    ): AnalyticsManager = AnalyticsManager(context)
}
```

**Provides**:
- Analytics Manager (Singleton)
- Application-level dependencies

---

#### DataModule

```kotlin
@Module
@InstallIn(ViewModelComponent::class)
object DataModule {
    @Provides
    fun provideVerseRepository(): VerseRepository
    
    @Provides
    fun provideWordDictionary(): WordDictionary
    
    @Provides
    fun provideWordGameEngine(
        repository: VerseRepository
    ): WordGameEngine
}
```

**Provides**:
- Verse Repository
- Word Dictionary
- Word Game Engine
- ViewModel-scoped dependencies

---

### Injection Points

```
PureWordsApplication (@HiltAndroidApp)
     │
     ├─▶ MainActivity (@AndroidEntryPoint)
     │      └─▶ AnalyticsManager (@Inject)
     │
     └─▶ ViewModels (@HiltViewModel)
            ├─▶ GameViewModel
            │      ├─▶ WordGameEngine
            │      ├─▶ VerseRepository
            │      └─▶ AnalyticsManager
            │
            ├─▶ WordGridViewModel
            │      ├─▶ WordDictionary
            │      └─▶ AnalyticsManager
            │
            └─▶ WordMatchingViewModel
                   └─▶ AnalyticsManager
```

---

## 📊 State Management

### StateFlow Pattern

All ViewModels use Kotlin StateFlow for reactive state management:

```kotlin
class GameViewModel @Inject constructor(...) : ViewModel() {
    
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    
    fun validateAnswer() {
        viewModelScope.launch {
            // Business logic
            _uiState.update { currentState ->
                currentState.copy(
                    isValidated = true,
                    isCorrect = isCorrect,
                    score = newScore
                )
            }
        }
    }
}
```

**Benefits**:
- Reactive updates
- Lifecycle-aware
- Thread-safe
- Survives configuration changes

---

## 🎨 UI Architecture

### Composable Hierarchy

```
MainActivity
└── PureWords1611Theme
    └── Surface
        └── GameScreen
            │
            ├── When(MENU)
            │   └── GameModeSelectionScreen
            │       ├── Column (layout)
            │       ├── Text (title)
            │       └── 3× Card (game modes)
            │
            ├── When(VERSE_GAME)
            │   └── VerseGameScreen
            │       └── Scaffold
            │           ├── TopAppBar
            │           └── GameplayScreen
            │               ├── Verse Display Card
            │               ├── Input Fields
            │               └── Action Buttons
            │
            ├── When(WORD_GRID)
            │   └── WordGridScreen
            │       └── Scaffold
            │           ├── TopAppBar
            │           └── WordGridGameScreen
            │               ├── Grid (4×4 cells)
            │               ├── Current Path Display
            │               ├── Action Buttons
            │               └── Found Words List
            │
            └── When(WORD_MATCHING)
                └── WordMatchingScreen
                    └── Scaffold
                        ├── TopAppBar
                        └── WordMatchingGameScreen
                            ├── Two Column Layout
                            ├── Word Cards
                            └── Match Indicators
```

---

## 📈 Analytics Integration

### Event Tracking

```kotlin
class AnalyticsManager @Inject constructor(
    private val context: Context
) {
    private val analytics = Firebase.analytics
    
    fun trackAppLaunch()
    fun trackScreenView(screenName: String)
    fun trackGameModeSelected(gameMode: String)
    fun trackGameCompleted(gameMode: String, score: Int)
    fun trackReturnToMenu(from: String)
}
```

**Tracked Events**:
- App launch
- Screen views
- Game mode selection
- Game completion with scores
- Return to menu
- Level progression

---

## 🧪 Testing Structure

### Unit Tests

```
test/kotlin/com/purewords1611/android/
│
├── data/
│   ├── WordGridTest.kt              # Grid generation, word validation
│   ├── VerseTest.kt                 # Verse model logic
│   ├── WordGameEngineTest.kt        # Verse game engine
│   └── WordMatchingEngineTest.kt    # Word matching logic
│
├── viewmodel/
│   ├── GameViewModelTest.kt         # Verse game ViewModel
│   ├── WordGridViewModelTest.kt     # Word grid ViewModel
│   └── WordMatchingViewModelTest.kt # Word matching ViewModel
│
└── analytics/
    └── AnalyticsManagerTest.kt      # Analytics wrapper
```

**Coverage**:
- All game engines
- All ViewModels
- Data models
- Business logic

---

## 🔒 Security & Privacy

### Data Privacy

- **No Personal Data**: App collects no personal information
- **Local Storage**: All game data stored on device
- **Analytics**: Anonymous usage data only via Firebase
- **No Network**: 100% offline gameplay
- **No Permissions**: Minimal permissions (notifications only)

### ProGuard Configuration

Release builds use ProGuard for:
- Code obfuscation
- Size reduction
- Security hardening

---

## 📦 Build Configuration

### Gradle Setup

```kotlin
android {
    namespace = "com.purewords1611.android"
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.purewords1611.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
    }
    
    buildFeatures {
        compose = true
    }
}
```

### Dependencies

**Core**:
- AndroidX Core KTX
- Lifecycle Runtime
- Activity Compose

**UI**:
- Compose BOM 2024.12.01
- Material Design 3
- Navigation Compose

**Architecture**:
- Hilt 2.51.1
- Room 2.6.1
- Coroutines 1.7.3

**Analytics**:
- Firebase BOM 32.7.0
- Firebase Analytics

---

## 🚀 Performance Considerations

### Optimizations

1. **Lazy Loading**: Compose lazy column for word lists
2. **State Hoisting**: Minimal recomposition
3. **Coroutines**: Non-blocking operations
4. **Room Database**: Efficient data caching
5. **ProGuard**: Release optimization

### Memory Management

- ViewModel scoped to lifecycle
- Proper coroutine cancellation
- StateFlow instead of LiveData
- Efficient Compose recomposition

---

## 📝 Code Quality

### Standards

- **Language**: Kotlin
- **Style**: Android Kotlin Style Guide
- **Architecture**: Clean Architecture principles
- **Patterns**: MVVM, Repository, Dependency Injection
- **Testing**: Unit tests for all business logic

### Documentation

- KDoc comments on public APIs
- Inline comments for complex logic
- README files for setup
- Wireframes for UI specifications

---

## ✅ Verification Checklist

- [x] MVVM architecture implemented
- [x] Dependency injection with Hilt
- [x] Jetpack Compose UI
- [x] Material Design 3 theme
- [x] Three game modes complete
- [x] Navigation working
- [x] State management with StateFlow
- [x] Analytics integration
- [x] Unit tests written
- [x] Build configuration ready
- [x] Documentation complete

---

**Status**: ✅ Architecture Complete  
**Code Status**: ✅ Implementation Complete  
**Build Status**: ⏳ Pending Maven repository access  
**Ready For**: Testing, Play Store Preparation

---

*Last Updated: January 3, 2026*
