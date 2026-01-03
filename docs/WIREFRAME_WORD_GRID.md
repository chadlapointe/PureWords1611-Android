# UI/UX Wireframe - Word Grid Game Screen

**Document Version**: 1.0  
**Created**: January 3, 2026  
**Last Updated**: January 3, 2026  
**Screen Name**: Word Grid Game Screen  
**Purpose**: Boggle-style word search gameplay screen  
**Implementation Status**: ✅ Implemented in `WordGridGameScreen.kt`

---

## 📋 Executive Summary

The Word Grid Game Screen provides a time-based word search challenge where players find words by connecting adjacent letters in a 4×4 grid. This Boggle-style gameplay tests pattern recognition and vocabulary knowledge with KJV 1611 biblical terms.

### Key Objectives

1. **Interactive Grid**: Provide intuitive letter selection with visual feedback
2. **Time Pressure**: Display countdown timer to create engaging urgency
3. **Word Validation**: Immediate feedback on word validity and scoring
4. **Progress Tracking**: Show found words, score, and progress toward goal
5. **Clear Win/Loss**: Distinct victory and time-up end states

---

## 🎯 Screen Overview

### Screen Identity
- **Screen Name**: Word Grid Game Screen
- **Route/Screen ID**: `GameMode.WORD_GRID`
- **Entry Point**: From Main Menu → "Word Grid" card
- **File Location**: `app/src/main/kotlin/com/purewords1611/android/ui/wordgrid/WordGridGameScreen.kt`

### Game Mechanics Summary
- **Type**: Boggle-style word search with path building
- **Grid Size**: 4×4 (16 letters)
- **Timer**: 2 minutes (120 seconds) countdown
- **Scoring**: +10 base + (+5 per letter beyond 3)
- **Win Condition**: Find 10+ unique valid words within time limit
- **Adjacency**: 8-directional (horizontal, vertical, diagonal)

### User Flow
```
Main Menu
    ↓
Tap "Word Grid"
    ↓
[Loading] → Generate Grid
    ↓
[Playing State] ← YOU START HERE
    ├─→ Tap letters to build path
    ├─→ Submit word or clear path
    ├─→ Repeat until timer expires or victory
    ↓
[Victory] (10+ words found) ──→ Play Again or Menu
    OR
[Time's Up] (< 10 words) ──→ Play Again or Menu
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
│                   Generating Grid...                           │
│                                                                 │
│                  (Circular Progress Indicator)                 │
│                                                                 │
│                                                                 │
│                                                                 │
│                                                                 │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

### State 2: Playing Screen (Main Gameplay)

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Back           WORD GRID                                     │ 16dp top
├─────────────────────────────────────────────────────────────────┤
│                                                                 │ 16dp padding
│  Score: 85                              Timer: 1:23            │ titleMedium
│                                                     (Green)     │
│  Words Found: 7/10                                             │ bodyMedium
│  ▓▓▓▓▓▓▓▓▓▓▓▓▓▓░░░░░                                         │ Progress bar
│                                                                 │
│                                                                 │ 16dp spacing
│  ┌───────────────────────────────────────────────────────────┐ │
│  │                                                           │ │
│  │     ┌────┬────┬────┬────┐                                │ │
│  │     │ C① │ A② │ T  │ S  │                                │ │ 4×4 Grid
│  │     ├────┼────┼────┼────┤                                │ │ Each cell:
│  │     │ R③ │ O  │ N  │ E  │                                │ │ 72dp × 72dp
│  │     ├────┼────┼────┼────┤                                │ │
│  │     │ P  │ W  │ D  │ L  │                                │ │ Letters:
│  │     ├────┼────┼────┼────┤                                │ │ 24sp bold
│  │     │ E  │ Y  │ A  │ K  │                                │ │
│  │     └────┴────┴────┴────┘                                │ │
│  │                                                           │ │
│  │     Current Word: CARO                                    │ │ headlineSmall
│  │                                                           │ │
│  └───────────────────────────────────────────────────────────┘ │ Card
│                                                                 │
│  ┌─────────────┐    ┌─────────────────────────────────────┐   │
│  │   Clear     │    │      Submit Word                    │   │ Buttons
│  └─────────────┘    └─────────────────────────────────────┘   │ 48dp height
│                                                                 │
│  ✓ CARO is valid! +15 points                                   │ Feedback
│                                                                 │ (Green text)
│                                                                 │ 16dp spacing
│  Found Words:                                                  │ labelMedium
│  ┌───────────────────────────────────────────────────────────┐ │
│  │ • CATS (10)    • RAT (10)     • WARDEN (25)              │ │ Scrollable
│  │ • CAT (10)     • TSAR (15)    • CARO (15)                │ │ list
│  │ • ART (10)                                                │ │
│  └───────────────────────────────────────────────────────────┘ │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Grid Cell States:**
- **Default**: White background, black border (1dp)
- **Selected (in path)**: Blue background (#2196F3), white text, numbered badge
- **Adjacent available**: Light highlight to show valid next moves
- **Previously used in submitted word**: Gray tint (10% opacity overlay)

**Key Dimensions:**
- Grid cell size: 72dp × 72dp (total grid: ~296dp)
- Cell border: 1dp
- Cell letter: 24sp, bold, center-aligned
- Selection badge: 16dp circle, top-right corner
- Grid padding: 8dp between cells

---

### State 3: Playing Screen - Word Selected

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Back           WORD GRID                                     │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Score: 85                              Timer: 1:15            │
│                                                     (Yellow)    │ Warning
│  Words Found: 7/10                                             │
│  ▓▓▓▓▓▓▓▓▓▓▓▓▓▓░░░░░                                         │
│                                                                 │
│  ┌───────────────────────────────────────────────────────────┐ │
│  │                                                           │ │
│  │     ┌────┬────┬────┬────┐                                │ │
│  │     │ C  │ A  │ T  │ S④ │  ← Path shown                 │ │
│  │     ├────┼────┼────┼────┤     with connecting            │ │
│  │     │ R  │ O② │ N③ │ E  │     lines (optional)           │ │
│  │     ├────┼────┼────┼────┤                                │ │
│  │     │ P  │ W① │ D  │ L  │                                │ │
│  │     ├────┼────┼────┼────┤                                │ │
│  │     │ E  │ Y  │ A  │ K  │                                │ │
│  │     └────┴────┴────┴────┘                                │ │
│  │                                                           │ │
│  │     Current Word: WONS                                    │ │ Building
│  │                                                           │ │ word...
│  └───────────────────────────────────────────────────────────┘ │
│                                                                 │
│  ┌─────────────┐    ┌─────────────────────────────────────┐   │
│  │ ◄ Clear     │    │      Submit Word                    │   │ Enabled
│  └─────────────┘    └─────────────────────────────────────┘   │
│                                                                 │
│                                                                 │
│  Found Words:                                                  │
│  ┌───────────────────────────────────────────────────────────┐ │
│  │ • CATS (10)    • RAT (10)     • WARDEN (25)              │ │
│  │ • CAT (10)     • TSAR (15)    • CARO (15)                │ │
│  │ • ART (10)                                                │ │
│  └───────────────────────────────────────────────────────────┘ │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Selection Visualization:**
- Selected cells: Blue background (#2196F3)
- Selection numbers: ①②③④ (small badge in corner)
- Path lines: Optional visual connecting selected cells
- Current word display: Updates in real-time as letters selected

---

### State 4: Playing Screen - Timer Warning

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Back           WORD GRID                                     │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Score: 105                             Timer: 0:23            │
│                                                  ⚠️ (RED!)     │ Critical!
│  Words Found: 9/10                                             │
│  ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓░░                                         │ Almost there!
│                                                                 │
│  [Grid and gameplay continues as normal...]                    │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Timer Warning Colors:**
- **Green** (>60 seconds): Normal gameplay
- **Yellow** (30-60 seconds): Caution, speed up
- **Red** (<30 seconds): Critical, hurry!
- Timer text animates: subtle pulse when red

---

### State 5: Feedback - Invalid Word

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Back           WORD GRID                                     │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Score: 85                              Timer: 1:18            │
│  Words Found: 7/10                                             │
│  ▓▓▓▓▓▓▓▓▓▓▓▓▓▓░░░░░                                         │
│                                                                 │
│  [Grid shown here - cleared after attempt]                     │
│                                                                 │
│  ✗ "XYZ" is not a valid word                                   │ Error
│                                                  (Red text)     │ feedback
│                                                                 │
│  [Buttons and found words list...]                             │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Error Messages:**
- "✗ '{word}' is not a valid word" - Not in dictionary
- "✗ Already found this word!" - Duplicate submission
- "✗ Too short! Minimum 3 letters" - Length requirement
- "✗ Invalid path! Letters must be adjacent" - Path validation failure

---

### State 6: Victory Screen

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Menu            WORD GRID                                    │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│                                                                 │
│                      🎉 Victory! 🎉                            │ displaySmall
│                                                                 │
│                   You found 12 words!                          │ headlineMedium
│                                                                 │
│                  ╔═════════════════╗                           │
│                  ║  Final Score    ║                           │ Score card
│                  ║      155        ║                           │
│                  ╚═════════════════╝                           │
│                                                                 │
│                  Time Remaining: 0:34                          │ bodyMedium
│                                                                 │
│  Your Words:                                                   │ labelLarge
│  ┌───────────────────────────────────────────────────────────┐ │
│  │ • CATS (10)    • WARDEN (25)   • WONDER (25)              │ │ All words
│  │ • CAT (10)     • CARO (15)     • DEAR (15)                │ │ found with
│  │ • RAT (10)     • TSAR (15)     • CARE (15)                │ │ scores
│  │ • ART (10)     • WARD (15)     • CAR (10)                 │ │
│  └───────────────────────────────────────────────────────────┘ │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Play Again                                 │   │ Primary
│  └─────────────────────────────────────────────────────────┘   │ button
│                                                                 │ 16dp spacing
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Back to Menu                               │   │ Secondary
│  └─────────────────────────────────────────────────────────┘   │ button
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Victory Elements:**
- **Celebration**: Trophy/party emoji, large title
- **Achievement**: "You found X words!" with final count
- **Score**: Prominent display with border
- **Time Bonus**: Shows remaining time (potential future feature)
- **Word List**: Complete list with individual scores

---

### State 7: Time's Up Screen

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Menu            WORD GRID                                    │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│                                                                 │
│                      ⏱️ Time's Up!                             │ displaySmall
│                                                                 │
│                   You found 8 words                            │ headlineMedium
│                 (Goal was 10 words)                            │
│                                                                 │
│                  ╔═════════════════╗                           │
│                  ║  Final Score    ║                           │ Score card
│                  ║       95        ║                           │
│                  ╚═════════════════╝                           │
│                                                                 │
│             Good effort! Try again!                            │ bodyLarge
│                                                                 │
│  Your Words:                                                   │ labelLarge
│  ┌───────────────────────────────────────────────────────────┐ │
│  │ • CATS (10)    • CARO (15)     • WARD (15)                │ │ Words
│  │ • CAT (10)     • TSAR (15)                                │ │ found
│  │ • RAT (10)     • WARDEN (25)                              │ │
│  │ • ART (10)                                                │ │
│  └───────────────────────────────────────────────────────────┘ │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Try Again                                  │   │ Primary
│  └─────────────────────────────────────────────────────────┘   │ button
│                                                                 │ 16dp spacing
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Back to Menu                               │   │ Secondary
│  └─────────────────────────────────────────────────────────┘   │ button
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Time's Up Elements:**
- **Title**: Clock emoji, clear message
- **Progress**: Shows words found vs. goal
- **Encouragement**: Positive message despite not reaching goal
- **Score**: Final score displayed
- **Word List**: Shows what was accomplished

---

## 🎨 Design Specifications

### Color Palette

```kotlin
// Grid cell states
val CellDefault = Color(0xFFFFFFFF)         // White background
val CellSelected = Color(0xFF2196F3)        // Blue (selected)
val CellBorder = Color(0xFFBDBDBD)          // Gray border
val CellUsed = Color(0xFFE0E0E0)            // Light gray (used)

// Timer colors
val TimerNormal = Color(0xFF4CAF50)         // Green (>60s)
val TimerWarning = Color(0xFFFFA726)        // Yellow (30-60s)
val TimerCritical = Color(0xFFD32F2F)       // Red (<30s)

// Feedback colors
val SuccessColor = Color(0xFF4CAF50)        // Valid word
val ErrorColor = Color(0xFFD32F2F)          // Invalid word

// Progress bar
val ProgressFilled = Color(0xFF2196F3)      // Blue filled
val ProgressEmpty = Color(0xFFE0E0E0)       // Gray empty
```

### Typography

```kotlin
// Grid letters
val gridLetterStyle = TextStyle(
    fontSize = 24.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = FontFamily.SansSerif,
    color = Color(0xFF2C2C2C)
)

// Current word display
headlineSmall = TextStyle(
    fontSize = 24.sp,
    fontWeight = FontWeight.SemiBold,
    letterSpacing = 2.sp  // Spaced for clarity
)

// Timer
titleMedium = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.Medium,
    fontFamily = FontFamily.Monospace  // Fixed width for timer
)

// Found words list
bodyMedium = TextStyle(
    fontSize = 14.sp,
    fontFamily = FontFamily.SansSerif
)
```

### Component Specifications

#### 1. **4×4 Letter Grid**
- Total size: ~296dp × 296dp
- Cell size: 72dp × 72dp
- Cell border: 1dp solid gray
- Cell padding: 0dp (letter centered)
- Grid gap: 2dp between cells
- Corner radius: 4dp per cell
- Touch target: Full cell (72dp × 72dp)

#### 2. **Grid Cell**
- **Default State**:
  - Background: White
  - Border: 1dp gray
  - Text: Black, 24sp, bold
  - Elevation: 0dp
  
- **Selected State**:
  - Background: Blue (#2196F3)
  - Border: 2dp dark blue
  - Text: White, 24sp, bold
  - Badge: Circle with number (top-right)
  - Elevation: 4dp
  
- **Adjacent Available**:
  - Background: Light blue tint (#E3F2FD)
  - Border: 1dp blue
  - Subtle highlight
  
- **Used State**:
  - Background: Light gray (#F5F5F5)
  - Text: Medium gray (#757575)
  - Reduced opacity: 70%

#### 3. **Selection Badge**
- Size: 16dp diameter circle
- Position: Top-right corner (-4dp, -4dp offset)
- Background: Dark blue (#1976D2)
- Text: White, 10sp, bold
- Z-index: Above cell

#### 4. **Current Word Display**
- Height: 48dp
- Style: headlineSmall
- Letter spacing: 2sp
- Alignment: Center
- Color: Primary
- Animation: Letters appear as selected

#### 5. **Action Buttons**
- Clear button:
  - Width: 30% of screen
  - Height: 48dp
  - Style: OutlinedButton
  - Text: "Clear"
  - Icon: Back arrow
  
- Submit button:
  - Width: 65% of screen
  - Height: 48dp
  - Style: FilledButton
  - Text: "Submit Word"
  - Primary color
  - Disabled when path empty

#### 6. **Timer Display**
- Format: "M:SS" (e.g., "1:23")
- Font: Monospace for alignment
- Size: 16sp, bold
- Color changes based on time:
  - Green: >60 seconds
  - Yellow: 30-60 seconds
  - Red: <30 seconds
- Animation: Pulse when red (<10s)

#### 7. **Progress Bar**
- Height: 8dp
- Corner radius: 4dp
- Filled: Blue (#2196F3)
- Empty: Light gray (#E0E0E0)
- Smooth animation on update
- Shows "X/10" text above

#### 8. **Found Words List**
- Layout: FlowRow or Grid (2-3 columns)
- Item format: "• WORD (score)"
- Font: 14sp, medium weight
- Color: Dark gray
- Background: Light surface with border
- Max height: 120dp, scrollable

---

## 🔄 State Transitions & Animations

### Grid Cell Interactions

1. **Cell Tap - Selection**
   ```
   User taps cell
     ↓
   Check if valid (adjacent or first)
     ↓
   If valid:
     - Add to path
     - Change background to blue
     - Show selection number badge
     - Update current word display
     - Animate: scale 0.9 → 1.0 (150ms)
   ```

2. **Cell Tap - Deselection**
   ```
   User taps selected cell
     ↓
   If it's last in path:
     - Remove from path
     - Reset to default style
     - Remove badge
     - Update current word display
     - Animate: scale 1.0 → 0.9 → 1.0 (200ms)
   ```

3. **Clear Path**
   ```
   User taps Clear button
     ↓
   For each selected cell:
     - Fade out badge (150ms)
     - Color transition to default (200ms)
     - Stagger by 50ms per cell
   Current word display:
     - Fade out text (150ms)
   ```

4. **Submit Word**
   ```
   User taps Submit button
     ↓
   Validate word (dictionary + path)
     ↓
   If valid:
     - Success feedback (green text)
     - Add to found words list
     - Update score (animated count)
     - Mark cells as "used" (gray tint)
     - Clear selection
     - Celebrate animation (scale pulse)
   If invalid:
     - Error feedback (red text)
     - Shake grid slightly (200ms)
     - Clear selection
   ```

### Timer Animations

1. **Countdown**
   - Text updates every second
   - Smooth color transition at thresholds
   - No jarring changes

2. **Critical Timer (<10s)**
   - Pulse animation: scale 1.0 → 1.15 → 1.0
   - Duration: 1000ms (1 second)
   - Repeat: Continuous while <10s

3. **Timer Expires**
   - Final: "0:00"
   - Fade to red
   - Brief pause (500ms)
   - Transition to Time's Up screen (fade + slide)

---

## ♿ Accessibility Features

### Screen Reader Support

```kotlin
// Grid cell
semanticProperties {
    contentDescription = "Letter ${letter}. Cell ${row}, ${col}. 
                         ${if (isSelected) "Selected, position $position in word" 
                         else "Not selected"}"
    role = Role.Button
}

// Timer
semanticProperties {
    contentDescription = "${minutes} minutes, ${seconds} seconds remaining"
    liveRegion = LiveRegionMode.Polite
}

// Score
semanticProperties {
    contentDescription = "Current score: ${score} points"
}

// Progress
semanticProperties {
    contentDescription = "${foundWords} words found out of 10. 
                         ${10 - foundWords} more needed to win"
}

// Submit button
semanticProperties {
    contentDescription = "Submit word ${currentWord}. 
                         ${currentWord.length} letters"
    role = Role.Button
}
```

### TalkBack Navigation
- Grid cells: Navigate in reading order (row by row)
- Action buttons: Clear, then Submit
- Found words: Announce each word as added to list
- Timer warnings: Announce "30 seconds remaining", "10 seconds remaining"

### Minimum Touch Targets
- Grid cells: 72dp × 72dp (exceeds 48dp minimum)
- Buttons: 48dp height minimum
- Back button: 48dp × 48dp

### Color Contrast
- Grid letters on white: #2C2C2C on #FFFFFF = 13:1 (WCAG AAA)
- Selected letters on blue: #FFFFFF on #2196F3 = 4.8:1 (WCAG AA)
- Timer red: #D32F2F sufficient contrast with background

### Alternative Input Methods
- Keyboard navigation: Tab through cells, Space to select
- D-pad support: Arrow keys move focus, Enter selects
- Voice input: Spell word letter-by-letter

---

## 📱 Responsive Design

### Portrait Orientation (Primary)
- Grid: Centered horizontally
- Optimized for: 360dp × 640dp to 412dp × 915dp
- All content fits without scrolling (except found words list)

### Landscape Orientation
- Grid: Positioned on left (50% width)
- Found words list: Expanded on right (50% width)
- Timer and score: Top bar spans full width
- Buttons: Remain below grid on left side

### Tablet Layout (7" and above)
- Grid cell size: 96dp × 96dp (larger for easier tapping)
- Letter size: 32sp (increased readability)
- Side panels: More spacious found words display
- Two-column layout:
  - Left: Grid + controls
  - Right: Status + found words

### Foldable Devices
- Unfolded: Use tablet layout
- Folded: Use phone portrait layout
- Avoid placing critical elements on fold crease

---

## 🎯 User Interaction Patterns

### Primary User Flow
1. **Scan grid** - Look for word patterns
2. **Start path** - Tap first letter
3. **Build word** - Tap adjacent letters in sequence
4. **Submit** - Tap "Submit Word" when complete
5. **View feedback** - See if word is valid
6. **Repeat** - Continue finding more words
7. **Race timer** - Monitor countdown, aim for 10 words
8. **End game** - Victory or time's up

### Advanced Strategies
- **Long words prioritized**: More points for longer words
- **Common prefixes**: "UN-", "RE-", "PRE-" to build longer words
- **Systematic search**: Scan row-by-row, column-by-column
- **Double-check**: Ensure path is valid before submitting

### Edge Cases Handled

#### Invalid Path Detection
- Non-adjacent cells: "Invalid path! Letters must be adjacent"
- Reused cells: Automatically prevented (can't tap same cell twice)
- Empty path: Submit button disabled

#### Word Validation
- Too short (<3 letters): "Too short! Minimum 3 letters"
- Not in dictionary: "Not a valid word"
- Already found: "Already found this word!"
- Case handling: Automatic uppercase conversion

#### Timer Management
- Paused: Not implemented (game continues)
- Expired during selection: Word submission blocked
- Exact zero: Immediate transition to end screen

#### Grid Generation
- Weighted letter distribution for playable grids
- Ensures minimum viable words (tested during generation)
- Vowel balance maintained

---

## 🔧 Technical Implementation Notes

### Data Flow
```kotlin
ViewModel (WordGridViewModel)
    ↓ exposes
StateFlow<WordGridUiState>
    ↓ observed by
Composable (WordGridGameScreen)
    ↓ renders
UI based on gameState enum
    ↓ user actions
Events back to ViewModel
    ↓ updates state
Timer coroutine running in background
```

### State Management
```kotlin
data class WordGridUiState(
    val grid: WordGrid? = null,
    val currentPath: List<GridPosition> = emptyList(),
    val currentWord: String = "",
    val foundWords: List<String> = emptyList(),
    val score: Int = 0,
    val timeRemaining: Int = 120,  // seconds
    val gameState: WordGridGameState = WordGridGameState.Loading,
    val feedback: String = "",
    val wordsNeeded: Int = 10
)

enum class WordGridGameState {
    Loading,   // Generating grid
    Playing,   // Active gameplay
    Paused,    // Not used currently
    Victory,   // Won (10+ words)
    TimeUp     // Lost (time expired)
}
```

### Grid Data Structure
```kotlin
data class WordGrid(
    val size: Int = 4,
    val letters: List<List<Char>>
) {
    fun getLetter(position: GridPosition): Char
    fun isValidPosition(position: GridPosition): Boolean
    fun areAdjacent(pos1: GridPosition, pos2: GridPosition): Boolean
    fun getWordFromPath(path: List<GridPosition>): String
    fun isValidPath(path: List<GridPosition>): Boolean
}

data class GridPosition(
    val row: Int,
    val col: Int
) {
    fun isAdjacentTo(other: GridPosition): Boolean {
        val rowDiff = abs(row - other.row)
        val colDiff = abs(col - other.col)
        return rowDiff <= 1 && colDiff <= 1 && (rowDiff + colDiff > 0)
    }
}
```

### Letter Distribution
```kotlin
// Weighted distribution for playable grids
val letterWeights = mapOf(
    'E' to 13, 'A' to 9, 'I' to 9, 'O' to 8, 'U' to 4,  // Vowels
    'T' to 9, 'N' to 7, 'S' to 7, 'H' to 6, 'R' to 6,    // Common
    'D' to 5, 'L' to 5, 'C' to 4, 'M' to 4,              // Medium
    'P' to 3, 'F' to 3, 'G' to 3, 'W' to 3, 'Y' to 3,    // Less common
    'B' to 2, 'V' to 2, 'K' to 2,                         // Rare
    'J' to 1, 'X' to 1, 'Q' to 1, 'Z' to 1               // Very rare
)
```

### Scoring Algorithm
```kotlin
fun calculateScore(word: String): Int {
    val baseScore = 10
    val lengthBonus = (word.length - 3).coerceAtLeast(0) * 5
    return baseScore + lengthBonus
}

// Examples:
// "CAT" (3 letters) = 10 + (0 * 5) = 10 points
// "CATS" (4 letters) = 10 + (1 * 5) = 15 points
// "WARDEN" (6 letters) = 10 + (3 * 5) = 25 points
```

---

## ✅ Implementation Checklist

- [x] Loading screen with grid generation
- [x] 4×4 interactive grid with tap selection
- [x] Path building with adjacency validation
- [x] Current word display (real-time update)
- [x] Clear button functionality
- [x] Submit button with validation
- [x] Timer countdown (2 minutes)
- [x] Timer color changes (green/yellow/red)
- [x] Score tracking and display
- [x] Progress tracking (words found / goal)
- [x] Found words list with scores
- [x] Dictionary validation
- [x] Duplicate word prevention
- [x] Scoring based on word length
- [x] Victory screen (10+ words)
- [x] Time's up screen (<10 words)
- [x] Play again functionality
- [x] Return to menu navigation
- [x] Accessibility labels
- [x] Material Design 3 theming
- [x] Responsive layout

---

## 📚 Related Screens

- **Previous Screen**: [Game Mode Selection](WIREFRAME_MAIN_SCREEN.md)
- **Related Screens**: 
  - [Verse Challenge Game Screen](WIREFRAME_VERSE_CHALLENGE.md)
  - [Word Matching Game Screen](WIREFRAME_WORD_MATCHING.md)

---

## 📝 Version History

| Version | Date | Changes | Author |
|---------|------|---------|--------|
| 1.0 | Jan 3, 2026 | Initial wireframe document | GitHub Copilot |

---

**Implementation Reference**: `app/src/main/kotlin/com/purewords1611/android/ui/wordgrid/WordGridGameScreen.kt`

**ViewModel**: `app/src/main/kotlin/com/purewords1611/android/viewmodel/WordGridViewModel.kt`

**Data Model**: `app/src/main/kotlin/com/purewords1611/android/data/WordGrid.kt`

**Game Engine**: `app/src/main/kotlin/com/purewords1611/android/data/WordGameEngine.kt`

---

*"The entrance of thy words giveth light; it giveth understanding unto the simple." - Psalm 119:130 (KJV)*
