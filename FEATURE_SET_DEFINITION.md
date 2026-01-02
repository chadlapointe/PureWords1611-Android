# PureWords1611 - Initial Feature Set Definition

**Document Version**: 1.0  
**Date**: January 2, 2026  
**Status**: Implementation Complete  
**Purpose**: Master reference for initial feature set and core game loops

---

## Executive Summary

PureWords1611-Android is a word-based mobile game application featuring engaging, educational gameplay centered on King James Version (KJV 1611) Bible vocabulary. The app includes three distinct game modes, each designed to challenge players' vocabulary knowledge and provide an enjoyable learning experience.

### Project Objectives

1. **Establish Google Play Presence**: Successfully publish to Google Play Store
2. **Showcase Development Expertise**: Demonstrate high-quality Android development
3. **Engage Target Audience**: Provide interactive word-based games for Bible vocabulary
4. **Platform**: Android (min SDK 24, target SDK 34)
5. **Launch Target**: March 2026

### Success Metrics

- Successfully published on Google Play Store
- 1,000+ downloads within first 3 months
- 4.0+ star rating maintained
- 40%+ daily active user engagement rate
- Zero privacy/security violations

---

## Target Audience

### Primary User Personas

1. **Scripture Students** (35% of users)
   - Age: 18-45
   - Motivation: Learn and memorize Bible vocabulary
   - Usage: Daily study sessions

2. **Casual Word Game Enthusiasts** (30% of users)
   - Age: 25-55
   - Motivation: Enjoyable word puzzles with meaningful content
   - Usage: Entertainment during breaks

3. **Christian Educators** (20% of users)
   - Age: 30-65
   - Motivation: Educational tool for teaching biblical vocabulary
   - Usage: Classroom or homeschool activities

4. **Traditional KJV Readers** (15% of users)
   - Age: 45+
   - Motivation: Prefer authentic 1611 KJV text
   - Usage: Faith-based learning

---

## Core Features Overview

### Three Game Modes

PureWords1611 includes three distinct game modes, each with unique mechanics and challenges:

| Feature | Verse Challenge | Word Grid | Word Matching |
|---------|----------------|-----------|---------------|
| **Game Type** | Fill-in-the-blanks | Word search (Boggle-style) | Pair matching |
| **Difficulty** | Medium | Medium-High | Easy-Medium |
| **Time Limit** | None | 2 minutes | None |
| **Lives System** | 3 lives | None | Mistake tracking |
| **Base Score** | +10 per answer | +10 per word | +10 per match |
| **Win Condition** | Survive with lives | Find 10+ words | Complete all levels |
| **Replay Value** | High (many verses) | High (random grids) | Medium (fixed levels) |

---

## Game Mode 1: Verse Challenge

### Description
Fill in the missing words from King James Version Bible verses. Test your knowledge of scripture by completing verses with blanks.

### Core Mechanics

#### Gameplay Flow
```
Start → Load Random Verse → Display Verse with Blanks → User Input → Validate
                ↓                                                      ↓
          Lives Remaining?                                      Correct Answer?
                ↓                                                      ↓
         Yes → Next Verse                                    Yes → +10 points
         No → Game Over                                      No → -1 life
```

#### Features
- **Verse Display**: Bible verse with 1-3 missing words replaced by blanks
- **Dynamic Input Fields**: One text field per blank word
- **Validation**: Case-insensitive, whitespace-trimmed matching
- **Lives System**: Start with 3 lives, lose 1 per wrong answer
- **Scoring**: +10 points for each correct answer
- **Continuous Play**: Continue until all lives are lost
- **Feedback**: Display full verse after each attempt

#### User Interface Components
1. **Score Display**: Current score in top-left
2. **Lives Indicator**: "Lives: X/3" in top-right
3. **Verse Reference**: Book, chapter, and verse citation
4. **Blanked Verse**: Verse text with underscores for missing words
5. **Input Fields**: One text field per blank (numbered)
6. **Submit Button**: "Check Answer" button
7. **Feedback Screen**: Shows correct/incorrect with full verse
8. **Continue Button**: Proceeds to next verse
9. **Game Over Screen**: Final score and "Play Again" option

#### Data Sources
- **verses.json**: Asset file containing 10 popular KJV verses
- Each verse includes: reference, full text, blanked text, missing words array

#### Technical Specifications
- **Architecture**: MVVM pattern
- **State Management**: Kotlin StateFlow
- **Async Operations**: Kotlin Coroutines
- **UI Framework**: Jetpack Compose with Material Design 3

---

## Game Mode 2: Word Grid

### Description
Find words by connecting adjacent letters in a 4x4 grid. Race against a 2-minute timer to discover as many valid KJV words as possible.

### Core Mechanics

#### Gameplay Flow
```
Start → Generate 4x4 Grid → Timer Starts → Player Selects Path → Validate Word
                ↓                    ↓                              ↓
         Display Grid       Time Remaining?                   Valid Word?
                ↓                    ↓                              ↓
         Select Cells         Yes → Continue              Yes → +10 base points
                ↓             No → Time's Up                     + bonus
         Build Word                                        No → Clear path
                ↓
         Submit Word → Found 10+ words? → Yes → Victory!
```

#### Features
- **4x4 Letter Grid**: Randomly generated with weighted letter distribution
- **Adjacency Rules**: Connect horizontal, vertical, or diagonal adjacent cells
- **Path Building**: Tap cells to build word path (no cell reuse per word)
- **Visual Feedback**: 
  - Selected cells highlighted in primary color
  - Selection order shown with numbers (1, 2, 3...)
  - Current word displayed above controls
- **Timer**: 2-minute countdown (MM:SS format, red when <30s)
- **Scoring System**:
  - Base: +10 points per valid word
  - Length Bonus: +5 points per letter beyond 3
  - Example: 5-letter word = 10 + (2 × 5) = 20 points
- **Win Condition**: Find 10 or more unique valid words before time expires
- **Validation**: 
  - Minimum 3 letters
  - Must be in KJV dictionary
  - Cannot submit same word twice
  - Path must be valid (adjacent cells, no reuse)

#### User Interface Components
1. **Header**: Score and timer display
2. **Progress Counter**: "Words Found: X/10"
3. **4x4 Grid**: 16 clickable cells with letters
4. **Cell Display**: Large letter with selection number overlay
5. **Current Word**: Real-time word display as path is built
6. **Feedback Area**: Success/error messages with color coding
7. **Controls**: "Clear" and "Submit" buttons
8. **Found Words List**: Scrollable list of discovered words
9. **End Screen**: Final score, words found, "Play Again" option

#### Letter Distribution
Weighted random generation favoring common letters:
- **High Frequency**: E, A, I, O, U (vowels)
- **Medium Frequency**: T, N, S, R, H, L, D, C, U, M
- **Low Frequency**: P, F, G, W, Y, B, V, K
- **Rare**: J, X, Z, Q

#### Technical Specifications
- **Grid Engine**: `WordGameEngine` generates grids with weighted distribution
- **Dictionary**: `WordDictionary` loads KJV words from verses
- **Path Validation**: Adjacency checking with diagonal support
- **Timer**: Coroutine-based countdown (1-second intervals)
- **State Management**: `WordGridViewModel` with StateFlow

---

## Game Mode 3: Word Matching

### Description
Match related biblical words by tapping them in two columns. Progress through 5 levels of increasing difficulty with synonyms, antonyms, and related terms.

### Core Mechanics

#### Gameplay Flow
```
Start → Level 1 → Shuffle Words → Display Two Columns → Player Selects Words
          ↓                                                        ↓
     Display Pairs                                         Both Words Selected?
          ↓                                                        ↓
    5 pairs/level                                          Auto-Validate Match
          ↓                                                        ↓
  Player taps words                                          Valid Match?
          ↓                                                        ↓
All matched? → Yes → Level Complete → Next Level        Yes → +10 points, mark green
          ↓                              ↓               No → -2 points, deselect
         No                         More Levels?
          ↓                              ↓
    Continue                    Yes → Next Level
                                No → Game Complete!
```

#### Features
- **Two-Column Layout**: Words displayed in left and right columns
- **Tap Selection**: Tap one word in each column to attempt match
- **Auto-Validation**: Match checked immediately when both words selected
- **Visual Feedback**:
  - Selected words highlighted
  - Matched pairs turn green and become unselectable
  - Invalid matches shake and deselect
- **Scoring System**:
  - +10 points per correct match
  - +50 bonus for completing level without mistakes (perfect)
  - -2 points per incorrect match attempt
  - Score accumulates across all levels
- **5 Levels**: Progressive difficulty with different word relationships
- **Level Completion**: Match all 5 pairs to advance
- **Game Completion**: Complete all 5 levels

#### Level Progression

**Level 1: Basic Synonyms**
- joy → gladness
- love → charity
- faith → trust
- peace → rest
- grace → mercy

**Level 2: Biblical Opposites**
- light → darkness
- heaven → earth
- good → evil
- life → death
- strength → weakness

**Level 3: Related Concepts**
- prayer → supplication
- wisdom → understanding
- righteousness → holiness
- blessing → favor
- glory → honor

**Level 4: Advanced Synonyms**
- word → saying
- truth → verity
- hope → expectation
- power → might
- salvation → deliverance

**Level 5: Sophisticated Terms**
- covenant → testament
- repentance → contrition
- praise → worship
- kingdom → dominion
- eternal → everlasting

#### User Interface Components
1. **Header**: Level indicator, score, progress
2. **Category Label**: Shows relationship type (Synonyms, Opposites, Related)
3. **Left Column**: 5 shuffled words
4. **Right Column**: 5 shuffled matching words
5. **Word Cards**: Tappable cards with visual states
6. **Feedback Messages**: Success/error notifications
7. **Level Complete Screen**: Score summary, perfect bonus indicator
8. **Game Complete Screen**: Final score, congratulations, "Play Again"

#### Technical Specifications
- **Match Engine**: `WordMatchingEngine` manages levels and validation
- **Pre-defined Pairs**: 25 total pairs (5 per level)
- **Shuffle Algorithm**: Randomizes word order each game
- **State Tracking**: Records matched pairs and mistakes
- **Perfect Detection**: Tracks zero-mistake levels for bonus

---

## Cross-Game Features

### Main Menu
- **Game Mode Selection**: Card-based interface for choosing game type
- **Game Descriptions**: Brief explanation of each mode
- **Feature Highlights**: Key mechanics and scoring displayed
- **Clean Design**: Material Design 3 cards with elevation

### Navigation
- **Top Bar**: Consistent navigation with back-to-menu button
- **Mode Switching**: Seamless transitions between games
- **State Preservation**: Game states maintained when switching modes

### Analytics
- **Screen Tracking**: Track which screens users view
- **Game Mode Selection**: Track which games users play
- **Engagement**: Monitor return-to-menu patterns
- **Anonymous**: No personal data collected

### User Experience
- **Material Design 3**: Modern, consistent UI across all modes
- **Accessibility**: Screen reader friendly, clear text labels
- **Responsive**: Adapts to different screen sizes
- **Smooth Animations**: Polished transitions and interactions

---

## Technical Architecture

### Architecture Pattern
**MVVM (Model-View-ViewModel)** throughout the application:

```
┌─────────────────────────────────────────────────────────────┐
│                         UI Layer                             │
│  (Jetpack Compose Screens - GameplayLoop, WordGrid, etc.)   │
└───────────────────────────┬─────────────────────────────────┘
                            │ observes StateFlow
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                      ViewModel Layer                         │
│  (GameViewModel, WordGridViewModel, WordMatchingViewModel)  │
│  - State management with StateFlow                           │
│  - Business logic                                            │
│  - User interaction handling                                 │
└───────────────────────────┬─────────────────────────────────┘
                            │ calls
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                       Data Layer                             │
│  - Repositories: VerseRepository, WordDictionary             │
│  - Models: Verse, WordGrid, WordPair                         │
│  - Engines: WordGameEngine, WordMatchingEngine               │
└─────────────────────────────────────────────────────────────┘
```

### Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Kotlin | 1.9.20 |
| Min SDK | Android API 24 | Android 7.0 |
| Target SDK | Android API 34 | Android 14 |
| UI Framework | Jetpack Compose | BOM 2024.02.00 |
| Design System | Material Design | 3 |
| State Management | StateFlow | - |
| Async Operations | Kotlin Coroutines | 1.7.3 |
| ViewModel | AndroidX Lifecycle | 2.7.0 |
| Testing | JUnit + Mockito | 4.13.2 / 5.5.0 |
| Build System | Gradle | 8.7 |

### Key Components

#### Data Models
- **Verse**: Bible verse with reference, text, blanks, answers
- **WordGrid**: 4x4 grid with position and adjacency logic
- **GridPosition**: Row/column coordinates for grid cells
- **WordPair**: Left/right word pairs for matching
- **MatchableWord**: Word with unique ID for tracking

#### Repositories & Engines
- **VerseRepository**: Loads verses from JSON assets
- **WordDictionary**: KJV word dictionary from verses
- **WordGameEngine**: Grid generation and validation
- **WordMatchingEngine**: Level data and match validation

#### ViewModels
- **GameViewModel**: Verse Challenge state and logic
- **WordGridViewModel**: Word Grid state, timer, path tracking
- **WordMatchingViewModel**: Word Matching state and progression

#### UI Components
- **GameModeSelectionScreen**: Main menu
- **GameplayLoop**: Verse Challenge screens
- **WordGridGameScreen**: Word Grid interface
- **WordMatchingGameScreen**: Word Matching interface
- **MainActivity**: Navigation and mode switching

### State Management

All game states use **Kotlin StateFlow** for reactive UI updates:

```kotlin
// Example state structure
data class GameUiState(
    val currentItem: DataModel?,
    val userInputs: List<String>,
    val score: Int,
    val gameState: GameState,
    val feedback: String
)

// ViewModel exposes state
val uiState: StateFlow<GameUiState>

// UI observes state
val uiState by viewModel.uiState.collectAsState()
```

---

## Data Assets

### verses.json
**Location**: `app/src/main/assets/verses.json`

**Content**: 10 popular KJV Bible verses for Verse Challenge mode

**Format**:
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

**Verses Included**:
1. Genesis 1:1 - Creation
2. John 3:16 - For God so loved the world
3. Psalm 23:1 - The Lord is my shepherd
4. Proverbs 3:5 - Trust in the Lord
5. Romans 8:28 - All things work together for good
6. Jeremiah 29:11 - Plans to prosper you
7. Philippians 4:13 - I can do all things through Christ
8. Isaiah 40:31 - They that wait upon the Lord
9. Matthew 11:28 - Come unto me, all ye that labour
10. Psalm 46:1 - God is our refuge and strength

### Word Dictionary
**Source**: Extracted from verses.json
**Processing**: Filtered to 3+ letter words
**Usage**: Validation for Word Grid mode
**Size**: ~100-150 unique KJV words

### Word Matching Levels
**Storage**: Hard-coded in `WordMatchingEngine`
**Total Pairs**: 25 pairs across 5 levels
**Categories**: Synonyms, Opposites, Related Concepts

---

## Testing Strategy

### Unit Tests Implemented

#### Data Layer Tests
- **VerseTest**: Verse model creation and validation
- **WordGridTest**: Grid operations, adjacency, path validation
- **WordGameEngineTest**: Score calculation, word validation
- **WordMatchingEngineTest**: Match validation, level data

#### ViewModel Tests
- **GameViewModelTest**: Answer validation logic
- **WordGridViewModelTest**: Path building and validation
- **WordMatchingViewModelTest**: Match detection and scoring

#### Analytics Tests
- **AnalyticsManagerTest**: Event tracking validation

### Test Coverage
- ✅ Data models: Comprehensive
- ✅ Business logic: Comprehensive
- ✅ Validation rules: Comprehensive
- ⚠️ UI components: Basic (requires Compose testing framework)

### Testing Frameworks
- **JUnit 4**: Unit test runner
- **Mockito**: Mocking framework
- **Kotlin Coroutines Test**: Async testing
- **Truth**: Assertion library (optional)

---

## Performance Considerations

### Optimization Strategies

#### Memory Management
- **ViewModel Lifecycle**: Properly scoped to prevent leaks
- **StateFlow**: Efficient reactive updates
- **Lazy Loading**: Dictionary loaded on first use
- **Resource Cleanup**: Coroutines cancelled when not needed

#### UI Performance
- **Compose Best Practices**: Stateless composables for optimal recomposition
- **Immutable State**: Data classes with val properties
- **Efficient Layouts**: Minimal nesting, proper modifiers
- **Grid Rendering**: O(1) cell lookups

#### Data Operations
- **JSON Parsing**: One-time load on initialization
- **Dictionary Search**: HashSet for O(1) lookup
- **Path Validation**: Early exit on invalid conditions
- **Timer**: Low-overhead coroutine-based countdown

---

## Accessibility Features

### Screen Reader Support
- **Content Descriptions**: All interactive elements labeled
- **Semantic Markup**: Proper heading hierarchy
- **State Announcements**: Game state changes announced
- **Clear Text**: "Lives: X/3" instead of emoji

### Visual Design
- **High Contrast**: Material Design 3 color schemes
- **Text Size**: Readable font sizes throughout
- **Touch Targets**: Minimum 48dp touch areas
- **Color Independence**: Not relying solely on color for information

### User Experience
- **Clear Feedback**: Text-based messages for all actions
- **Consistent Navigation**: Standard back button behavior
- **Error Messages**: Helpful, descriptive error text
- **No Time Pressure**: Verse Challenge has no timer

---

## Security & Privacy

### Privacy-First Approach
- ✅ **No Personal Data**: No user accounts or personal information
- ✅ **No Permissions**: Standard app permissions only
- ✅ **No Network**: All data local (verses bundled in app)
- ✅ **Anonymous Analytics**: Basic usage statistics only
- ✅ **No Ads**: Completely ad-free experience
- ✅ **No In-App Purchases**: 100% free

### Data Storage
- **Local Only**: All game data stored on device
- **No Cloud Sync**: No data leaves the device
- **Ephemeral State**: Game state not persisted between sessions
- **Asset Files**: Read-only verse data in assets folder

### Security Best Practices
- **Input Validation**: All user input validated
- **Safe Parsing**: Exception handling for JSON parsing
- **Resource Management**: Proper try-catch blocks
- **No Code Injection**: No dynamic code execution

---

## Future Enhancement Roadmap

### Phase 2: Enhanced Gameplay (Q2 2026)
- [ ] **Difficulty Levels**: Easy, Medium, Hard variants
- [ ] **Hint System**: Provide hints for points cost
- [ ] **Sound Effects**: Audio feedback for actions
- [ ] **Animations**: Smooth transitions and effects
- [ ] **Achievements**: Badges for milestones

### Phase 3: Content Expansion (Q3 2026)
- [ ] **More Verses**: Expand to 50+ verses
- [ ] **Themed Levels**: Verse categories (Psalms, Proverbs, Gospels)
- [ ] **Daily Challenges**: Special challenges each day
- [ ] **Larger Grids**: 5x5 and 6x6 word grids
- [ ] **More Matching Levels**: Expand to 10+ levels

### Phase 4: Social & Persistence (Q4 2026)
- [ ] **Leaderboards**: High score tracking
- [ ] **Statistics**: Personal best, averages, streaks
- [ ] **Room Database**: Persistent storage
- [ ] **Share Results**: Share scores on social media
- [ ] **Custom Themes**: Color theme options

### Phase 5: Advanced Features (2027)
- [ ] **Multiplayer**: Challenge friends
- [ ] **Verse Collections**: Save and organize favorites
- [ ] **Search**: Find specific verses
- [ ] **Notifications**: Daily reminder to play
- [ ] **Widgets**: Home screen game widgets

---

## Success Metrics & KPIs

### Launch Metrics
- **Downloads**: Target 1,000 in first 3 months
- **Rating**: Maintain 4.0+ stars
- **Crashes**: <1% crash rate
- **Retention**: 40% Day-7 retention

### Engagement Metrics
- **Daily Active Users (DAU)**: Track daily players
- **Session Length**: Average time per session
- **Games Played**: Average games per session
- **Mode Preferences**: Which game modes are most popular

### Quality Metrics
- **Crash-Free Sessions**: 99%+ target
- **ANR Rate**: <0.1% Application Not Responding errors
- **Load Time**: <2 seconds to playable state
- **Battery Usage**: Minimal battery impact

---

## Release Checklist

### Pre-Launch Requirements
- [x] All game modes implemented
- [x] Unit tests passing
- [x] UI tests created
- [x] Analytics integrated
- [x] Privacy policy prepared
- [ ] Play Store listing ready
- [ ] App icon finalized
- [ ] Screenshots created
- [ ] Feature graphic designed
- [ ] Promo video recorded (optional)

### Technical Requirements
- [x] Min SDK 24, Target SDK 34
- [x] ProGuard rules configured
- [x] Release build tested
- [ ] App signing configured
- [ ] AAB bundle generated
- [x] Permissions minimized
- [x] Accessibility validated
- [ ] Security scan completed

### Play Store Requirements
- [ ] Developer account created ($25 fee)
- [ ] Privacy policy hosted
- [ ] Content rating completed
- [ ] Target audience defined
- [ ] Data safety form completed
- [ ] Store listing written
- [ ] Assets uploaded
- [ ] Release track selected

---

## Conclusion

PureWords1611-Android delivers a complete, polished word game experience with three distinct game modes. The implementation follows modern Android development best practices with:

✅ **Clean Architecture**: MVVM pattern with proper separation of concerns  
✅ **Modern Tech Stack**: Jetpack Compose, Material Design 3, Kotlin Coroutines  
✅ **Comprehensive Testing**: Unit tests for all core logic  
✅ **Accessibility**: Screen reader friendly, clear visual design  
✅ **Privacy-First**: No personal data, no permissions, completely offline  
✅ **Production Ready**: Follows all Android best practices  

The app is ready for final testing, Play Store assets creation, and submission to Google Play Store.

---

**Document Status**: ✅ Complete  
**Implementation Status**: ✅ Complete  
**Next Steps**: Play Store submission preparation  
**Target Launch**: March 2026  
