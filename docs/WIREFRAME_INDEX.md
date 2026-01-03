# UI/UX Wireframe Index - PureWords1611 Game Screens

**Document Version**: 1.0  
**Created**: January 3, 2026  
**Last Updated**: January 3, 2026  
**Purpose**: Master index of all game screen wireframes  
**Status**: Complete ✅

---

## 📋 Overview

This document serves as the master index for all UI/UX wireframe documentation for the PureWords1611-Android application. Each wireframe provides comprehensive design specifications, interaction patterns, and implementation guidelines for the respective game screen.

---

## 🎮 Game Screen Wireframes

### 1. Main Menu - Game Mode Selection ✅

**Document**: [WIREFRAME_MAIN_SCREEN.md](WIREFRAME_MAIN_SCREEN.md)

**Description**: Primary navigation hub where users select one of three game modes.

**Key Features**:
- Three game mode cards with descriptions
- Material Design 3 card-based layout
- Clear feature descriptions and scoring rules
- Direct navigation to each game mode

**Implementation Status**: ✅ Complete  
**File**: `app/src/main/kotlin/com/purewords1611/android/ui/GameModeSelectionScreen.kt`

---

### 2. Verse Challenge Game Screen ✅

**Document**: [WIREFRAME_VERSE_CHALLENGE.md](WIREFRAME_VERSE_CHALLENGE.md)

**Description**: Fill-in-the-blank Bible verse completion gameplay with lives system.

**Key Features**:
- Dynamic verse display with blanks (1-3 missing words)
- Text input fields for answer entry
- Score and lives tracking (3 lives system)
- Immediate feedback (correct/incorrect)
- Complete verse reveal for learning
- Game over screen with final score

**Game Mechanics**:
- **Lives**: Start with 3, lose 1 per wrong answer
- **Scoring**: +10 points per correct answer
- **Validation**: Case-insensitive, whitespace trimmed
- **Win Condition**: Survive as long as possible

**Screen States**:
1. Loading - Fetching verse
2. Playing - User entering answers
3. Correct Feedback - Answer validation success
4. Incorrect Feedback - Answer validation failure
5. Game Over - All lives depleted

**Implementation Status**: ✅ Complete  
**File**: `app/src/main/kotlin/com/purewords1611/android/ui/gameplay/GameplayLoop.kt`  
**ViewModel**: `GameViewModel.kt`  
**Data Model**: `Verse.kt`

---

### 3. Word Grid Game Screen ✅

**Document**: [WIREFRAME_WORD_GRID.md](WIREFRAME_WORD_GRID.md)

**Description**: Boggle-style word search with 4×4 letter grid and 2-minute timer.

**Key Features**:
- Interactive 4×4 letter grid (16 cells)
- Path building with adjacency validation (8 directions)
- 2-minute countdown timer with color warnings
- Real-time word display as letters selected
- Found words list with individual scores
- Progress tracking (X/10 words needed)
- Victory or Time's Up end screens

**Game Mechanics**:
- **Grid**: 4×4 random letters (weighted distribution)
- **Timer**: 120 seconds countdown (green → yellow → red)
- **Scoring**: +10 base + (+5 per letter beyond 3)
- **Adjacency**: 8-directional (horizontal, vertical, diagonal)
- **Win Condition**: Find 10+ unique valid words within time limit
- **Validation**: Dictionary check, minimum 3 letters, no duplicates

**Screen States**:
1. Loading - Generating grid
2. Playing - Active gameplay with timer
3. Paused - Not currently implemented
4. Victory - 10+ words found within time
5. Time's Up - Timer expired before goal

**Implementation Status**: ✅ Complete  
**File**: `app/src/main/kotlin/com/purewords1611/android/ui/wordgrid/WordGridGameScreen.kt`  
**ViewModel**: `WordGridViewModel.kt`  
**Data Model**: `WordGrid.kt`, `GridPosition.kt`  
**Engine**: `WordGameEngine.kt`

---

### 4. Word Matching Game Screen ✅

**Document**: [WIREFRAME_WORD_MATCHING.md](WIREFRAME_WORD_MATCHING.md)

**Description**: Biblical word pair matching across 5 progressive levels.

**Key Features**:
- Two-column card layout (5 pairs per level)
- Tap-to-select interaction pattern
- Immediate match validation with visual feedback
- 5 progressive levels with different categories
- Perfect level bonus (+50 points for zero mistakes)
- Comprehensive game complete screen with statistics

**Game Mechanics**:
- **Levels**: 5 total (Basic Synonyms → Theological Terms)
- **Pairs per Level**: 5 word pairs
- **Scoring**: +10 per match, -2 per mistake, +50 perfect level bonus
- **Matching**: Tap one word from each column
- **Win Condition**: Complete all 5 levels

**Level Categories**:
1. **Level 1**: Basic Synonyms (joy/gladness, love/charity)
2. **Level 2**: Biblical Opposites (light/darkness, good/evil)
3. **Level 3**: Related Concepts (prayer/supplication, wisdom/understanding)
4. **Level 4**: Advanced Synonyms (truth/verity, hope/expectation)
5. **Level 5**: Theological Terms (covenant/testament, eternal/everlasting)

**Screen States**:
1. Playing - Active word matching
2. Level Complete - Level summary and transition
3. Game Complete - Final statistics and scores

**Implementation Status**: ✅ Complete  
**File**: `app/src/main/kotlin/com/purewords1611/android/ui/wordmatching/WordMatchingGameScreen.kt`  
**ViewModel**: `WordMatchingViewModel.kt`  
**Data Model**: `WordMatchingGame.kt`, `MatchableWord.kt`, `WordPair.kt`

---

## 🎨 Design System Overview

All game screens follow consistent design principles:

### Color Palette
- **Primary**: Deep Blue (#1A4D8F) - Trust and reverence
- **Secondary**: Gold (#D4AF37) - Sacred, special
- **Success**: Green (#4CAF50) - Correct answers
- **Error**: Red (#D32F2F) - Incorrect answers
- **Background**: Off-white/Parchment (#FAF8F3)

### Typography
- **Display**: Serif fonts (Merriweather) for verse text
- **UI**: Sans-serif (Roboto) for interface elements
- **Sizes**: 
  - Display: 32-57sp
  - Headlines: 22-32sp
  - Body: 14-18sp (16sp minimum for readability)
  - Labels: 12-14sp

### Material Design 3
- Elevation: 2dp (default cards), 4dp (selected/interactive)
- Corner radius: 8-12dp for cards, 4dp for inputs
- Touch targets: 48dp × 48dp minimum (WCAG compliant)
- Animations: 200-400ms standard durations
- Color transitions: Smooth, 200-300ms

---

## ♿ Accessibility Standards

All wireframes include comprehensive accessibility features:

### Screen Reader Support
- Semantic labels for all interactive elements
- Live regions for dynamic content (scores, timers, feedback)
- Role definitions (Button, Text, etc.)
- State descriptions (Selected, Matched, etc.)

### Touch Targets
- Minimum 48dp × 48dp for all interactive elements
- Grid cells: 72dp × 72dp (exceeds minimum)
- Buttons: 56dp height standard

### Color Contrast
- WCAG AA minimum (4.5:1 for normal text)
- WCAG AAA preferred (7:1 for body text)
- All text meets or exceeds standards
- High contrast mode compatible

### Keyboard & D-pad Navigation
- Tab order defined for all screens
- Arrow key support for grid navigation
- Enter/Space for selection
- Escape for back/cancel actions

---

## 📱 Responsive Design

All screens are optimized for:

### Phone Portrait (Primary)
- 360dp × 640dp (minimum)
- 412dp × 915dp (typical)
- Single-column layouts
- Scrollable content where needed

### Phone Landscape
- Two-column layouts where appropriate
- Increased side padding (32dp)
- Optimized for horizontal viewing

### Tablet (7" and above)
- Max content width: 600dp (centered)
- Larger touch targets (56dp)
- Increased font sizes (+2sp)
- More generous spacing

---

## 🔄 Navigation Flow

```
App Launch
    ↓
[Main Menu / Game Mode Selection]
    ├──→ [Verse Challenge] ──→ Playing ──→ Game Over ──┐
    │                                                     │
    ├──→ [Word Grid] ──→ Playing ──→ Victory/Time's Up ─┤
    │                                                     │
    └──→ [Word Matching] ──→ Level 1-5 ──→ Complete ────┘
                                                          ↓
                                              Back to Main Menu
```

### Back Navigation
- All screens: Back button returns to main menu
- Android back button: Handled appropriately
- Confirmation dialog: Optional for active games

---

## 📊 Feature Comparison Matrix

| Feature | Verse Challenge | Word Grid | Word Matching |
|---------|----------------|-----------|---------------|
| **Primary Mechanic** | Fill-in-blanks | Word search | Pair matching |
| **Time Limit** | None | 2 minutes | None |
| **Lives System** | 3 lives | Unlimited | Unlimited |
| **Levels** | Continuous | Single session | 5 levels |
| **Scoring** | Flat +10 | Length-based | Flat +10 + bonus |
| **Win Condition** | Survive | Find 10+ words | Complete all levels |
| **Educational Focus** | Memorization | Vocabulary | Relationships |
| **Difficulty** | Medium | Medium-High | Easy-Medium |
| **Session Length** | 5-10 min | 2-5 min | 5-15 min |
| **Replay Value** | High | Very High | Medium |

---

## 🔧 Technical Architecture

### MVVM Pattern
All screens follow Model-View-ViewModel architecture:

```
UI Layer (Composables)
    ↓ observes
ViewModel (StateFlow)
    ↓ calls
Data Layer (Repositories, Engines)
```

### State Management
- **StateFlow**: Reactive state updates
- **Kotlin Coroutines**: Async operations (timer, loading)
- **Compose**: Declarative UI rendering
- **Hilt**: Dependency injection

### File Structure
```
app/src/main/kotlin/com/purewords1611/android/
├── ui/
│   ├── GameModeSelectionScreen.kt          ✅ Main menu
│   ├── gameplay/
│   │   └── GameplayLoop.kt                 ✅ Verse Challenge
│   ├── wordgrid/
│   │   └── WordGridGameScreen.kt           ✅ Word Grid
│   └── wordmatching/
│       └── WordMatchingGameScreen.kt       ✅ Word Matching
├── viewmodel/
│   ├── GameViewModel.kt                    ✅ Verse Challenge VM
│   ├── WordGridViewModel.kt                ✅ Word Grid VM
│   └── WordMatchingViewModel.kt            ✅ Word Matching VM
└── data/
    ├── Verse.kt                            ✅ Verse model
    ├── WordGrid.kt                         ✅ Grid model
    ├── WordMatchingGame.kt                 ✅ Matching model
    ├── WordGameEngine.kt                   ✅ Grid engine
    └── VerseRepository.kt                  ✅ Data access
```

---

## ✅ Implementation Status

### Completed Features
- [x] Main Menu / Game Mode Selection
- [x] Verse Challenge (complete gameplay loop)
- [x] Word Grid (complete gameplay loop)
- [x] Word Matching (complete gameplay loop)
- [x] Score tracking across all modes
- [x] Material Design 3 theming
- [x] Accessibility support
- [x] Responsive layouts
- [x] Navigation between screens
- [x] State management (StateFlow/MVVM)
- [x] Data models and repositories
- [x] Game engines and validation

### Future Enhancements (Post-Launch)
- [ ] Sound effects and music
- [ ] Enhanced animations and transitions
- [ ] Statistics tracking (Room database)
- [ ] Achievements system
- [ ] Difficulty levels
- [ ] Expanded verse library (10 → 100+)
- [ ] Daily challenges
- [ ] Leaderboards (optional)

---

## 📚 Related Documentation

### Design Documents
- [Game Design Document](../GAME_DESIGN_DOCUMENT.md) - Complete game design specification
- [Feature Set Definition](../FEATURE_SET_DEFINITION.md) - Master feature reference
- [Screenshot Mockup Guide](SCREENSHOT_MOCKUP_GUIDE.md) - Play Store screenshot specs

### Technical Documents
- [Project Structure](../PROJECT_STRUCTURE.md) - Code organization
- [Implementation Summary](../IMPLEMENTATION_SUMMARY.md) - Implementation overview
- [Build Environment](../BUILD_ENVIRONMENT_ISSUE.md) - Build setup notes

### Play Store Documents
- [Play Store Submission Guide](PLAY_STORE_SUBMISSION_GUIDE.md) - Complete submission workflow
- [Assets Guide](ASSETS_GUIDE.md) - Required graphics specifications
- [Store Listing](STORE_LISTING.md) - Marketing copy and descriptions

---

## 📝 Usage Guidelines

### For Developers
1. Reference these wireframes when implementing UI changes
2. Follow specified dimensions and spacing
3. Maintain consistency with design system
4. Implement accessibility features as documented
5. Test on multiple screen sizes

### For Designers
1. Use these as baseline for future enhancements
2. Maintain visual consistency across screens
3. Consider accessibility in all design decisions
4. Update wireframes when making changes
5. Create mockups based on these specifications

### For QA/Testing
1. Verify implementations match wireframes
2. Test all documented user flows
3. Validate accessibility features
4. Check responsive behavior on various devices
5. Ensure animations match specifications

---

## 🎯 Quick Reference

### Screen Access Routes
- **Main Menu**: App launch (default screen)
- **Verse Challenge**: Main Menu → "Verse Challenge" card
- **Word Grid**: Main Menu → "Word Grid" card
- **Word Matching**: Main Menu → "Word Matching" card

### Key Metrics
- **Minimum Screen Width**: 360dp
- **Typical Screen Width**: 412dp
- **Tablet Breakpoint**: 600dp
- **Maximum Content Width**: 600dp (tablet)
- **Standard Padding**: 16dp
- **Card Elevation**: 2-4dp
- **Touch Target**: 48dp minimum

### Color References
- Primary: `#1A4D8F`
- Success: `#4CAF50`
- Error: `#D32F2F`
- Background: `#FAF8F3`

---

## 📞 Contact & Updates

For questions, updates, or contributions related to these wireframes:

- **Repository**: [chadlapointe/PureWords1611-Android](https://github.com/chadlapointe/PureWords1611-Android)
- **Issues**: [GitHub Issues](https://github.com/chadlapointe/PureWords1611-Android/issues)
- **Documentation**: `/docs` directory

---

## 📝 Version History

| Version | Date | Changes | Author |
|---------|------|---------|--------|
| 1.0 | Jan 3, 2026 | Initial wireframe index document | GitHub Copilot |

---

**Complete Wireframe Set:**
1. ✅ [Main Menu / Game Mode Selection](WIREFRAME_MAIN_SCREEN.md)
2. ✅ [Verse Challenge Game Screen](WIREFRAME_VERSE_CHALLENGE.md)
3. ✅ [Word Grid Game Screen](WIREFRAME_WORD_GRID.md)
4. ✅ [Word Matching Game Screen](WIREFRAME_WORD_MATCHING.md)

---

*"Study to shew thyself approved unto God, a workman that needeth not to be ashamed, rightly dividing the word of truth." - 2 Timothy 2:15 (KJV)*

**All wireframes complete and ready for implementation reference! 🎉**
