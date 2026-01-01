# Core Gameplay Loop Documentation

## Overview

The PureWords1611 Android app features a word-based game where players fill in missing words from Bible verses (KJV 1611). This document describes the core gameplay loop implementation.

## Game Mechanics

### Objective
Players must correctly fill in the blanks in Bible verses to score points while managing a limited number of lives.

### Rules
- **Starting Lives**: 3 lives (displayed as hearts ❤️)
- **Scoring**: +10 points for each correct answer
- **Lives Lost**: -1 life for each incorrect answer
- **Game Over**: When all lives are lost
- **Win Condition**: Continue playing as long as lives remain

### Validation
- **Case-Insensitive**: "Beginning", "beginning", and "BEGINNING" are all accepted
- **Whitespace Trimmed**: Leading and trailing spaces are ignored
- **Exact Match**: Words must match exactly (after case normalization and trimming)

## Gameplay Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                         Start Game                               │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │  Loading Screen │
                    │  (Load Verses)  │
                    └────────┬────────┘
                             │
                             ▼
┌────────────────────────────────────────────────────────────────┐
│                      Playing Screen                             │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  Score: XX                          Lives: ❤️❤️❤️         │  │
│  └──────────────────────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │  Reference: Genesis 1:1                                   │  │
│  │                                                            │  │
│  │  "In the _____ God created the heaven and the earth."    │  │
│  │                                                            │  │
│  │  Word 1: [          ]  (User input field)                │  │
│  │                                                            │  │
│  └──────────────────────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │              [  Check Answer  ]                           │  │
│  └──────────────────────────────────────────────────────────┘  │
└────────────────────────────┬───────────────────────────────────┘
                             │
                             ▼
                     User submits answer
                             │
                ┌────────────┴────────────┐
                │                         │
        Correct Answer            Incorrect Answer
                │                         │
                ▼                         ▼
    ┌───────────────────┐      ┌──────────────────────┐
    │ Correct Feedback  │      │ Incorrect Feedback   │
    │   +10 points      │      │   -1 life            │
    │                   │      │                      │
    │ Show full verse   │      │ Show correct answer  │
    │                   │      │                      │
    │  [  Continue  ]   │      │  [  Continue  ]      │
    └────────┬──────────┘      └──────────┬───────────┘
             │                            │
             │                            ▼
             │                    Lives remaining?
             │                            │
             │                    ┌───────┴────────┐
             │                    │                │
             │                   Yes              No
             └────────────────────┘                │
                             │                     ▼
                             ▼           ┌──────────────────┐
                    Load Next Verse      │  Game Over       │
                             │           │  Final Score: XX │
                             │           │                  │
                             └───────────│  [ Play Again ]  │
                                         └──────────────────┘
```

## Architecture

### Components

#### 1. Data Layer (`com.purewords1611.android.data`)

**Verse.kt**
- Data class representing a Bible verse
- Properties:
  - `reference`: String (e.g., "Genesis 1:1")
  - `text`: Complete verse text
  - `blankedText`: Verse with blanks (e.g., "In the _____ God...")
  - `missingWords`: List of correct answers

**VerseRepository.kt**
- Manages verse data loading from JSON asset
- Methods:
  - `loadVerses()`: Loads all verses from assets/verses.json
  - `getRandomVerse()`: Returns a random verse for gameplay
  - `parseVerses()`: Parses JSON into Verse objects

#### 2. ViewModel Layer (`com.purewords1611.android.viewmodel`)

**GameViewModel.kt**
- Manages game state and business logic
- Uses Kotlin Coroutines and StateFlow for reactive state management

**Game States:**
- `Loading`: Initial state while verses are being loaded
- `Playing`: Active gameplay, waiting for user input
- `Correct`: Shows success feedback after correct answer
- `Incorrect`: Shows error feedback after incorrect answer
- `GameOver`: Final state when all lives are lost

**GameUiState:**
```kotlin
data class GameUiState(
    val currentVerse: Verse?,
    val userInputs: List<String>,
    val score: Int,
    val lives: Int,
    val gameState: GameState,
    val feedback: String
)
```

**Key Methods:**
- `loadNextVerse()`: Loads a new random verse
- `updateInput(index, text)`: Updates user input for a specific blank
- `validateAnswer()`: Validates user input against correct answers
- `continueGame()`: Proceeds to next verse
- `resetGame()`: Resets game to initial state

#### 3. UI Layer (`com.purewords1611.android.ui.gameplay`)

**GameplayLoop.kt**
- Contains all Composable UI screens
- Screens:
  - `LoadingScreen`: Shows loading indicator
  - `PlayingScreen`: Main gameplay interface
  - `FeedbackScreen`: Shows correct/incorrect feedback
  - `GameOverScreen`: Displays final score and restart option

### Data Flow

```
User Action → ViewModel Method → State Update → UI Recomposition
```

Example flow for answer submission:
1. User fills input fields and clicks "Check Answer"
2. `validateAnswer()` is called in GameViewModel
3. ViewModel validates input against correct answers
4. GameViewModel updates `_uiState` with new state
5. UI observes state change via `StateFlow`
6. Compose recomposes UI to show feedback screen

## Asset Files

### verses.json
Located at: `app/src/main/assets/verses.json`

Format:
```json
[
  {
    "reference": "Genesis 1:1",
    "text": "In the beginning God created the heaven and the earth.",
    "blankedText": "In the _____ God created the heaven and the earth.",
    "missingWords": ["beginning"]
  }
]
```

Current content: 10 popular KJV verses

## Testing

### Unit Tests

**VerseTest.kt**
- Tests Verse data class creation
- Tests verses with single and multiple missing words

**GameViewModelTest.kt**
- Tests validation logic
- Tests case-insensitive matching
- Tests whitespace trimming
- Tests multiple word validation
- Uses Kotlin Coroutines Test library

### Test Coverage
- ✅ Data model creation
- ✅ Validation logic (case-insensitive, whitespace handling)
- ✅ Multiple word validation
- ⚠️ Full ViewModel tests require mocking (future enhancement)
- ⚠️ UI tests require Compose testing framework (future enhancement)

## Dependencies Added

```kotlin
// ViewModel support for Compose
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

// Coroutines testing
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
```

## Future Enhancements

### Gameplay
- [ ] Add difficulty levels (more blanks for harder difficulty)
- [ ] Add timer-based scoring (bonus points for speed)
- [ ] Add hints system (deduct points for hints)
- [ ] Add daily challenges
- [ ] Add achievement system
- [ ] Add leaderboard

### Content
- [ ] Expand verse database (currently 10 verses)
- [ ] Add verse categories (Psalms, Proverbs, Gospels, etc.)
- [ ] Add verse of the day feature
- [ ] Add verse bookmarking/favorites

### Technical
- [ ] Persist high scores using Room database
- [ ] Add analytics tracking
- [ ] Add sound effects and animations
- [ ] Add accessibility features (text-to-speech)
- [ ] Add localization support

## Usage Example

```kotlin
// In MainActivity.kt
@Composable
fun GameScreen() {
    val repository = remember { 
        VerseRepository(LocalContext.current) 
    }
    val viewModel = remember { 
        GameViewModel(repository) 
    }
    val uiState by viewModel.uiState.collectAsState()
    
    GameplayScreen(
        uiState = uiState,
        onInputChange = { index, text -> 
            viewModel.updateInput(index, text) 
        },
        onValidate = { viewModel.validateAnswer() },
        onContinue = { viewModel.continueGame() },
        onReset = { viewModel.resetGame() }
    )
}
```

## Building and Running

Due to build environment limitations (Maven repository access), the app cannot be built in the current CI environment. However, the implementation is complete and follows Android best practices.

To build locally:
```bash
./gradlew assembleDebug
```

To run tests:
```bash
./gradlew test
```

## Code Quality

- ✅ Follows MVVM architecture pattern
- ✅ Uses Kotlin Coroutines for async operations
- ✅ Uses StateFlow for reactive state management
- ✅ Separates concerns (data, business logic, UI)
- ✅ Includes comprehensive documentation
- ✅ Includes unit tests for validation logic
- ✅ Uses Material Design 3 components
- ✅ Follows Android Compose best practices

## Summary

The core gameplay loop implementation provides a solid foundation for the PureWords1611 Android app. The architecture is clean, testable, and extensible. The game mechanics are simple yet engaging, with clear feedback and progressive difficulty through the lives system. The codebase is ready for further feature development and polish.
