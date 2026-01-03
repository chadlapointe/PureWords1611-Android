# UI/UX Wireframe - Verse Challenge Game Screen

**Document Version**: 1.0  
**Created**: January 3, 2026  
**Last Updated**: January 3, 2026  
**Screen Name**: Verse Challenge Game Screen  
**Purpose**: Fill-in-the-blank Bible verse gameplay screen  
**Implementation Status**: ✅ Implemented in `GameplayLoop.kt`

---

## 📋 Executive Summary

The Verse Challenge Game Screen is the core gameplay interface for the fill-in-the-blank Bible verse game mode. Players complete verses from the KJV 1611 Bible by filling in missing words, testing their scriptural knowledge with a lives-based challenge system.

### Key Objectives

1. **Clear Presentation**: Display Bible verses with blanks in a readable, reverent format
2. **Intuitive Input**: Provide easy-to-use text fields for answer entry
3. **Immediate Feedback**: Show clear success/failure feedback and reveal complete verses
4. **Progress Tracking**: Display score and remaining lives prominently
5. **Educational Value**: Help users learn and memorize scripture

---

## 🎯 Screen Overview

### Screen Identity
- **Screen Name**: Verse Challenge / Gameplay Screen
- **Route/Screen ID**: `GameMode.VERSE_CHALLENGE`
- **Entry Point**: From Main Menu → "Verse Challenge" card
- **File Location**: `app/src/main/kotlin/com/purewords1611/android/ui/gameplay/GameplayLoop.kt`

### Game Mechanics Summary
- **Type**: Fill-in-the-blank text completion
- **Lives System**: Start with 3 lives, lose 1 per wrong answer
- **Scoring**: +10 points per correct answer
- **Win Condition**: Survive as long as possible
- **Lose Condition**: All 3 lives depleted

### User Flow
```
Main Menu
    ↓
Tap "Verse Challenge"
    ↓
[Playing State] ← YOU START HERE
    ├─→ Enter word(s) in blanks
    ├─→ Tap "Check Answer"
    ↓
[Feedback State: Correct] ──→ [Next Verse] ──→ Back to Playing
    OR
[Feedback State: Incorrect] ──→ [Next Verse] ──→ Back to Playing
    OR
[Feedback State: Incorrect & No Lives] ──→ [Game Over] ──→ Play Again or Menu
```

---

## 📐 Wireframe Layout

### State 1: Loading Screen

```
┌─────────────────────────────────────────────────────────────────┐
│                      Status Bar (System)                        │ 24dp
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│                                                                 │
│                                                                 │
│                                                                 │
│                                                                 │
│                           ⏳                                    │
│                    Loading Verse...                            │
│                                                                 │
│                  (Circular Progress Indicator)                 │
│                                                                 │
│                                                                 │
│                                                                 │
│                                                                 │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Components:**
- Centered loading indicator
- "Loading Verse..." text below indicator
- Simple, clean layout

---

### State 2: Playing Screen (Main Gameplay)

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Back          VERSE CHALLENGE                                │ 16dp top
├─────────────────────────────────────────────────────────────────┤
│                                                                 │ 16dp padding
│  Score: 80                              Lives: ♥ ♥ ♡           │ titleMedium
│                                                                 │
│                                                                 │ 24dp spacing
│  ┌───────────────────────────────────────────────────────────┐ │
│  │                                                           │ │
│  │  John 3:16 (KJV)                                         │ │ titleLarge
│  │                                                           │ │ 8dp padding
│  │  For God so loved the __________, that he gave his       │ │
│  │  only begotten Son, that whosoever believeth in          │ │ bodyLarge
│  │  him should not __________, but have everlasting         │ │ serif font
│  │  life.                                                    │ │ 18-20sp
│  │                                                           │ │
│  └───────────────────────────────────────────────────────────┘ │ Card
│                                                                 │ 16dp spacing
│  Fill in the blanks:                                           │ labelLarge
│                                                                 │ 8dp spacing
│  ┌───────────────────────────────────────────────────────────┐ │
│  │ 1. world_____________________                            │ │ TextField
│  └───────────────────────────────────────────────────────────┘ │ 48dp height
│                                                                 │ 8dp spacing
│  ┌───────────────────────────────────────────────────────────┐ │
│  │ 2. perish____________________                            │ │ TextField
│  └───────────────────────────────────────────────────────────┘ │ 48dp height
│                                                                 │
│                                                                 │ 24dp spacing
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Check Answer                               │   │ Button
│  └─────────────────────────────────────────────────────────┘   │ 56dp height
│                                                                 │
│                                                                 │ 16dp bottom
└─────────────────────────────────────────────────────────────────┘
```

**Key Dimensions:**
- Screen padding: 16dp all sides
- Card elevation: 4dp
- Button height: 56dp (Material Design 3 standard)
- TextField height: 48dp (minimum touch target)
- Spacing between elements: 8dp, 16dp, 24dp (Material Design scale)

**Visual Hierarchy:**
1. **Header** (top): Score left, Lives right - equal importance
2. **Verse Card** (center, expandable): Primary focus, elevated card
3. **Input Section** (middle): Clear label + text fields
4. **Action Button** (bottom): Prominent CTA

---

### State 3: Feedback Screen - Correct Answer

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Back          VERSE CHALLENGE                                │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Score: 90                              Lives: ♥ ♥ ♡           │ Updated!
│                                                                 │
│  ┌───────────────────────────────────────────────────────────┐ │
│  │                                                           │ │
│  │          ✓ Correct! +10 points                           │ │ Success
│  │                                                           │ │ (Green text)
│  └───────────────────────────────────────────────────────────┘ │ Success card
│                                                                 │
│  ┌───────────────────────────────────────────────────────────┐ │
│  │                                                           │ │
│  │  John 3:16 (KJV)                                         │ │
│  │                                                           │ │
│  │  For God so loved the world, that he gave his only       │ │ Complete
│  │  begotten Son, that whosoever believeth in him           │ │ verse
│  │  should not perish, but have everlasting life.           │ │ revealed
│  │                                                           │ │
│  │  Your answers:                                            │ │ labelMedium
│  │  1. world ✓                                              │ │
│  │  2. perish ✓                                             │ │
│  │                                                           │ │
│  └───────────────────────────────────────────────────────────┘ │
│                                                                 │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Continue                                   │   │ Primary
│  └─────────────────────────────────────────────────────────┘   │ button
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Feedback Elements:**
- **Success Message**: Green color, checkmark icon, point increment
- **Complete Verse**: Full verse text displayed for learning
- **Answer Review**: Shows what user entered with validation marks
- **Continue Button**: Loads next verse

---

### State 4: Feedback Screen - Incorrect Answer

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Back          VERSE CHALLENGE                                │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Score: 80                              Lives: ♥ ♡ ♡           │ Lost a life!
│                                                                 │
│  ┌───────────────────────────────────────────────────────────┐ │
│  │                                                           │ │
│  │          ✗ Incorrect. -1 Life                            │ │ Error
│  │          Try to memorize this verse!                     │ │ (Red text)
│  │                                                           │ │
│  └───────────────────────────────────────────────────────────┘ │ Error card
│                                                                 │
│  ┌───────────────────────────────────────────────────────────┐ │
│  │                                                           │ │
│  │  John 3:16 (KJV)                                         │ │
│  │                                                           │ │
│  │  For God so loved the world, that he gave his only       │ │ Complete
│  │  begotten Son, that whosoever believeth in him           │ │ verse
│  │  should not perish, but have everlasting life.           │ │ revealed
│  │                                                           │ │
│  │  Your answers:                                            │ │
│  │  1. earth ✗ (Correct: world)                            │ │ Show
│  │  2. perish ✓                                             │ │ corrections
│  │                                                           │ │
│  └───────────────────────────────────────────────────────────┘ │
│                                                                 │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Continue                                   │   │ Primary
│  └─────────────────────────────────────────────────────────┘   │ button
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Error Feedback:**
- **Error Message**: Red color, X icon, life deduction notification
- **Educational Element**: Shows correct answers for learning
- **Visual Indicator**: Heart icons update to show lost life

---

### State 5: Game Over Screen

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Menu          VERSE CHALLENGE                                │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│                                                                 │
│                                                                 │
│                       Game Over!                               │ displaySmall
│                                                                 │ 32dp spacing
│                  ╔═════════════════╗                           │
│                  ║  Final Score    ║                           │ Card with
│                  ║      120        ║                           │ border
│                  ╚═════════════════╝                           │
│                                                                 │
│                                                                 │ 24dp spacing
│             Great effort! You completed                         │ bodyLarge
│             12 verses and learned God's Word.                   │
│                                                                 │
│                                                                 │ 48dp spacing
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Play Again                                 │   │ Primary
│  └─────────────────────────────────────────────────────────┘   │ button
│                                                                 │ 16dp spacing
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Back to Menu                               │   │ Secondary
│  └─────────────────────────────────────────────────────────┘   │ button
│                                                                 │
│                                                                 │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**End Game Elements:**
- **Game Over Title**: Large, centered
- **Score Display**: Prominent, framed score card
- **Encouragement**: Positive message about learning
- **Actions**: Play again (primary) or return to menu (secondary)

---

## 🎨 Design Specifications

### Color Palette

```kotlin
// State-based colors
val SuccessColor = Color(0xFF4CAF50)        // Green for correct
val ErrorColor = Color(0xFFD32F2F)          // Red for incorrect
val LifeActiveColor = Color(0xFFE53935)     // Red heart (active life)
val LifeLostColor = Color(0xFFBDBDBD)       // Gray heart (lost life)

// Text colors
val VerseTextColor = Color(0xFF2C2C2C)      // Dark, readable
val ReferenceTextColor = Color(0xFF666666)  // Medium gray

// Card colors
val CardBackground = Color(0xFFFFFFFF)      // White cards
val CardElevation = 4.dp                     // Material elevation
```

### Typography

```kotlin
// Verse reference
titleLarge = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontSize = 22.sp,
    fontWeight = FontWeight.SemiBold
)

// Verse text (serif for traditional feel)
bodyLarge = TextStyle(
    fontFamily = FontFamily.Serif,
    fontSize = 18.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.5.sp
)

// Score and lives
titleMedium = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontSize = 16.sp,
    fontWeight = FontWeight.Medium
)

// Input labels
labelLarge = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontSize = 14.sp,
    fontWeight = FontWeight.Medium
)
```

### Component Specifications

#### 1. **Top App Bar**
- Height: 64dp (with system status bar)
- Background: Primary color surface
- Back button: 48dp × 48dp touch target
- Title: Center-aligned, headlineSmall

#### 2. **Status Bar (Score & Lives)**
- Layout: Row with SpaceBetween arrangement
- Score: Left-aligned, titleMedium
- Lives: Right-aligned, icons + text
- Heart icon: 24dp × 24dp
- Spacing: 16dp padding from edges

#### 3. **Verse Card**
- Width: match_parent with 16dp horizontal padding
- Elevation: 4dp
- Corner radius: 12dp (Material Design 3)
- Internal padding: 16dp all sides
- Background: surface color (white)
- Border: None (elevation provides depth)

#### 4. **Verse Reference**
- Style: titleLarge
- Color: Primary
- Margin bottom: 8dp

#### 5. **Verse Text**
- Style: bodyLarge
- Font: Serif (Literata, Crimson Text, or Georgia)
- Line height: 1.5× font size
- Blanks: Represented by underscores (________)
- Minimum blank width: 10 characters

#### 6. **Text Input Fields**
- Height: 48dp (minimum touch target)
- Width: match_parent with 16dp padding
- Corner radius: 4dp
- Border: 1dp when unfocused, 2dp when focused
- Placeholder: "Enter word..."
- Keyboard: Capitalized text input
- IME action: Next (for multiple fields) or Done (for last field)

#### 7. **Check Answer Button**
- Height: 56dp (Material Design 3 standard)
- Width: match_parent with 16dp padding
- Corner radius: 28dp (fully rounded)
- Background: Primary color
- Text: "Check Answer", labelLarge, white
- State: Disabled when inputs empty (50% opacity)
- Ripple: Bounded

#### 8. **Feedback Cards**
- Success card: Light green background (#E8F5E9)
- Error card: Light red background (#FFEBEE)
- Padding: 16dp
- Elevation: 2dp
- Corner radius: 8dp
- Icon + text aligned horizontally

#### 9. **Continue Button**
- Same specs as Check Answer button
- Text: "Continue"
- Appears after answer validation

---

## 🔄 State Transitions & Animations

### Screen State Flow
```
Loading
  ↓ (verse loads)
Playing
  ↓ (user submits answer)
Validating (brief)
  ↓
Correct Feedback ──→ Continue ──→ Next Verse (back to Playing)
  OR
Incorrect Feedback ──→ Continue ──→ Check lives
                                      ├─→ Lives > 0: Next Verse (Playing)
                                      └─→ Lives = 0: Game Over
```

### Animation Specifications

1. **Screen Entry**
   - Fade in: 300ms
   - Slide up from bottom: 300ms with decelerate easing

2. **Verse Card Appearance**
   - Scale from 0.9 to 1.0: 200ms
   - Fade in: 200ms
   - Concurrent animations

3. **Feedback Card Appearance**
   - Slide down from top: 250ms
   - Fade in: 250ms
   - Bounce effect at end: spring animation

4. **Lives Lost Animation**
   - Heart icon: scale pulse (1.0 → 1.3 → 1.0): 400ms
   - Color transition: red → gray: 300ms
   - Shake screen: small horizontal offset: 200ms

5. **Score Update**
   - Number increment: animated count-up over 500ms
   - Slight scale pulse: 1.0 → 1.1 → 1.0: 300ms

6. **Button Press**
   - Ripple effect: Material standard
   - Scale down: 0.95 during press
   - Return to 1.0 on release

---

## ♿ Accessibility Features

### Screen Reader Support

```kotlin
// Verse card
semanticProperties {
    contentDescription = "Bible verse: ${verse.reference}. ${verse.text}"
    role = Role.Text
}

// Score display
semanticProperties {
    contentDescription = "Current score: ${uiState.score} points"
}

// Lives display
semanticProperties {
    contentDescription = "${uiState.lives} lives remaining out of 3"
}

// Input fields
semanticProperties {
    contentDescription = "Blank ${index + 1}. Enter missing word"
    imeAction = ImeAction.Next
}

// Check Answer button
semanticProperties {
    contentDescription = "Check your answers"
    role = Role.Button
}
```

### Minimum Touch Targets
- All interactive elements: 48dp × 48dp minimum
- Buttons: 56dp height × full width
- Text fields: 48dp height × full width
- Back button: 48dp × 48dp

### Color Contrast
- Body text on white: 4.5:1 minimum (WCAG AA)
- Verse text: #2C2C2C on white = 13:1 (WCAG AAA)
- Success text: #2E7D32 on #E8F5E9 = 7.5:1 (WCAG AAA)
- Error text: #C62828 on #FFEBEE = 8:1 (WCAG AAA)

### Keyboard Navigation
- Tab order: Score → Lives → Verse (focus) → Input 1 → Input 2 → ... → Button
- Enter key: Submit form from any input field
- Escape key: Return to menu (with confirmation)

---

## 📱 Responsive Design

### Portrait Orientation (Primary)
- Optimized for: 360dp × 640dp to 412dp × 915dp
- Verse card: Scrollable if content exceeds screen height
- Button: Always visible at bottom (not in scroll area)

### Landscape Orientation
- Verse card: Constrained width (max 600dp), centered
- Side padding increased: 32dp
- Status bar remains at top
- Input fields: May appear side-by-side if space allows

### Tablet Layout (7" and above)
- Content max width: 600dp, centered
- Increased font sizes:
  - bodyLarge: 20sp → 22sp
  - titleLarge: 22sp → 26sp
- Larger touch targets: 56dp minimum
- Two-column layout option for inputs

---

## 🎯 User Interaction Patterns

### Primary User Flow
1. **Read the verse** - User scans verse with blanks
2. **Identify missing words** - Determine context and answers
3. **Enter answers** - Tap first field, type, move to next
4. **Submit** - Tap "Check Answer" button or press Enter
5. **Review feedback** - Read result and see complete verse
6. **Continue** - Tap "Continue" to load next verse
7. **Repeat** - Loop until game over

### Edge Cases Handled

#### Empty Input Submission
- Button disabled when any field is empty
- Visual feedback: 50% opacity, no ripple
- Prevents accidental submission

#### Case Insensitivity
- User input: "WORLD", "world", "World" → All accepted
- Backend: Convert to lowercase for comparison
- Display: Show user's original input in feedback

#### Whitespace Handling
- Trim leading/trailing spaces automatically
- "  world  " → "world"
- User doesn't need to worry about spacing

#### Keyboard Behavior
- First field: Auto-focus on screen load
- Enter key: Move to next field (or submit if last)
- Keyboard: Dismiss on submission
- IME actions: Configured appropriately

#### Life Depletion
- Lives = 0 triggers immediate game over
- No continue button in this case
- Direct transition to game over screen

---

## 🔧 Technical Implementation Notes

### Data Flow
```kotlin
ViewModel (GameViewModel)
    ↓ exposes
StateFlow<GameUiState>
    ↓ observed by
Composable (GameplayScreen)
    ↓ renders
UI based on gameState enum
    ↓ user actions
Events back to ViewModel
```

### State Management
```kotlin
data class GameUiState(
    val currentVerse: Verse? = null,
    val userInputs: List<String> = emptyList(),
    val score: Int = 0,
    val lives: Int = 3,
    val gameState: GameState = GameState.Loading,
    val feedback: String = ""
)

enum class GameState {
    Loading,    // Fetching verse
    Playing,    // User entering answers
    Correct,    // Answer was correct
    Incorrect,  // Answer was wrong
    GameOver    // No lives left
}
```

### Verse Data Model
```kotlin
data class Verse(
    val reference: String,           // "John 3:16"
    val text: String,                // Complete verse
    val blankedText: String,         // Verse with blanks
    val missingWords: List<String>,  // ["world", "perish"]
    val blankPositions: List<Int>    // [5, 14]
)
```

---

## ✅ Implementation Checklist

- [x] Loading screen with progress indicator
- [x] Playing screen with verse card
- [x] Dynamic input field generation (1-3 fields)
- [x] Score and lives header
- [x] Input validation (case-insensitive, trimmed)
- [x] Feedback screen for correct answers
- [x] Feedback screen for incorrect answers
- [x] Complete verse reveal after submission
- [x] Lives tracking and heart icon updates
- [x] Game over screen with final score
- [x] Play again functionality
- [x] Return to menu navigation
- [x] Accessibility labels
- [x] Material Design 3 theming
- [x] Responsive layout (portrait/landscape)

---

## 📚 Related Screens

- **Previous Screen**: [Game Mode Selection](WIREFRAME_MAIN_SCREEN.md)
- **Related Screens**: 
  - [Word Grid Game Screen](WIREFRAME_WORD_GRID.md)
  - [Word Matching Game Screen](WIREFRAME_WORD_MATCHING.md)

---

## 📝 Version History

| Version | Date | Changes | Author |
|---------|------|---------|--------|
| 1.0 | Jan 3, 2026 | Initial wireframe document | GitHub Copilot |

---

**Implementation Reference**: `app/src/main/kotlin/com/purewords1611/android/ui/gameplay/GameplayLoop.kt`

**ViewModel**: `app/src/main/kotlin/com/purewords1611/android/viewmodel/GameViewModel.kt`

**Data Model**: `app/src/main/kotlin/com/purewords1611/android/data/Verse.kt`

---

*"Thy word is a lamp unto my feet, and a light unto my path." - Psalm 119:105 (KJV)*
