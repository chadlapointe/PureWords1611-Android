# UI/UX Wireframe - Word Matching Game Screen

**Document Version**: 1.0  
**Created**: January 3, 2026  
**Last Updated**: January 3, 2026  
**Screen Name**: Word Matching Game Screen  
**Purpose**: Biblical word pair matching gameplay screen  
**Implementation Status**: ✅ Implemented in `WordMatchingGameScreen.kt`

---

## 📋 Executive Summary

The Word Matching Game Screen presents a word pair matching challenge where players connect related biblical terms. Featuring 5 progressive levels with synonyms, antonyms, and related concepts, this mode teaches vocabulary relationships in an engaging card-matching interface.

### Key Objectives

1. **Clear Pairing**: Display two columns of words for intuitive matching
2. **Immediate Feedback**: Show match validation instantly with visual cues
3. **Educational Focus**: Teach word relationships and biblical vocabulary
4. **Progress Tracking**: Display level progress and scoring
5. **Achievement Recognition**: Reward perfect levels with bonus points

---

## 🎯 Screen Overview

### Screen Identity
- **Screen Name**: Word Matching Game Screen
- **Route/Screen ID**: `GameMode.WORD_MATCHING`
- **Entry Point**: From Main Menu → "Word Matching" card
- **File Location**: `app/src/main/kotlin/com/purewords1611/android/ui/wordmatching/WordMatchingGameScreen.kt`

### Game Mechanics Summary
- **Type**: Card-based pair matching
- **Levels**: 5 progressive levels
- **Pairs per Level**: 5 word pairs
- **Scoring**: +10 per match, +50 perfect level bonus
- **Mistakes**: Tracked but not limiting
- **Win Condition**: Complete all 5 levels

### User Flow
```
Main Menu
    ↓
Tap "Word Matching"
    ↓
[Level 1: Playing] ← YOU START HERE
    ├─→ Tap word in left column
    ├─→ Tap matching word in right column
    ├─→ Auto-validate match
    ├─→ Repeat until all 5 pairs matched
    ↓
[Level 1: Complete] → Review score
    ↓
Tap "Next Level"
    ↓
[Level 2: Playing] → ... → [Level 5: Complete]
    ↓
[Game Complete] → Final score & Play Again or Menu
```

---

## 📐 Wireframe Layout

### State 1: Playing Screen - Level Start

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Back        WORD MATCHING                                    │ 16dp top
├─────────────────────────────────────────────────────────────────┤
│                                                                 │ 16dp padding
│  Level 1/5                                    Score: 0          │ titleMedium
│  ████░░░░░░░░░░░░░░░░                                          │ Progress bar
│                                                                 │
│                                                                 │ 16dp spacing
│  Basic Synonyms                                                │ headlineSmall
│  Tap words to match them                                       │ bodyMedium
│                                                                 │ 8dp spacing
│                                                                 │
│     Left Column              Right Column                       │ Labels
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │              │        │              │                     │
│  │     joy      │        │   gladness   │                     │ Word cards
│  │              │        │              │                     │ 48dp height
│  └──────────────┘        └──────────────┘                     │
│                                                                 │ 12dp spacing
│  ┌──────────────┐        ┌──────────────┐                     │
│  │              │        │              │                     │
│  │     love     │        │   charity    │                     │
│  │              │        │              │                     │
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │              │        │              │                     │
│  │    faith     │        │    trust     │                     │
│  │              │        │              │                     │
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │              │        │              │                     │
│  │    peace     │        │     rest     │                     │
│  │              │        │              │                     │
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │              │        │              │                     │
│  │    grace     │        │    mercy     │                     │
│  │              │        │              │                     │
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│                                                                 │ 16dp spacing
│  Matches: 0/5                            Mistakes: 0           │ Status
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Card Dimensions:**
- Width: 45% of screen width each column
- Height: 48dp (minimum touch target)
- Gap between columns: 5% of screen width
- Vertical spacing: 12dp between cards
- Corner radius: 8dp
- Elevation: 2dp (default), 4dp (selected)

**Visual Hierarchy:**
1. **Progress bar & score** - Track overall game progress
2. **Category label** - Understand the relationship type
3. **Instructions** - Brief, clear guidance
4. **Word cards** - Primary interaction area
5. **Status bar** - Match progress and mistakes

---

### State 2: Playing Screen - One Word Selected

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Back        WORD MATCHING                                    │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Level 1/5                                    Score: 20         │
│  ████░░░░░░░░░░░░░░░░                                          │
│                                                                 │
│  Basic Synonyms                                                │
│  Tap words to match them                                       │
│                                                                 │
│     Left Column              Right Column                       │
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │              │        │              │                     │
│  │     joy      │◀──────│   gladness   │                     │ Matched!
│  │      ✓       │  ✓    │      ✓       │                     │ (Green,
│  └──────────────┘        └──────────────┘                     │  locked)
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │  ╔═════════╗ │        │              │                     │
│  │  ║  love   ║ │◀───Selected           │   charity    │                     │ Selected
│  │  ╚═════════╝ │        │              │                     │ (Blue bg)
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │              │        │              │                     │
│  │    faith     │        │    trust     │                     │ Available
│  │              │        │              │                     │
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │              │        │              │                     │
│  │    peace     │        │     rest     │                     │
│  │              │        │              │                     │
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │              │        │              │                     │
│  │    grace     │        │    mercy     │                     │
│  │              │        │              │                     │
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│                                                                 │
│  Matches: 1/5                            Mistakes: 0           │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Card States:**
- **Default**: White background, gray border (1dp)
- **Selected**: Blue background (#2196F3), white text, bold border (2dp)
- **Matched**: Green background (#4CAF50), white text, checkmark icon, locked
- **Error (brief)**: Red flash, shake animation, then deselect

---

### State 3: Playing Screen - Incorrect Match Feedback

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Back        WORD MATCHING                                    │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Level 1/5                                    Score: 18         │ Score reduced!
│  ████░░░░░░░░░░░░░░░░                                          │
│                                                                 │
│  Basic Synonyms                                                │
│  Tap words to match them                                       │
│                                                                 │
│  ✗ Not a match. Try again!                                     │ Error feedback
│                                                  (Red text)     │
│                                                                 │
│     Left Column              Right Column                       │
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │              │        │              │                     │
│  │     joy      │        │   gladness   │                     │ Matched
│  │      ✓       │        │      ✓       │                     │ (locked)
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│  [Cards deselected after brief error display...]               │
│                                                                 │
│  Matches: 1/5                            Mistakes: 1           │ Mistake count
│                                                                 │ increased
└─────────────────────────────────────────────────────────────────┘
```

**Error Sequence:**
1. Both cards flash red (100ms)
2. Cards shake horizontally (200ms)
3. Feedback text appears (red)
4. Score -2 (animated count down)
5. Mistakes +1
6. After 1 second: Cards deselect, return to default
7. Feedback fades out (300ms)

---

### State 4: Playing Screen - All Pairs Matched

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Back        WORD MATCHING                                    │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Level 1/5                                    Score: 50         │
│  ████░░░░░░░░░░░░░░░░                                          │
│                                                                 │
│  Basic Synonyms                                                │
│                                                                 │
│  🎉 Level Complete!                                            │ Success!
│  Perfect! +50 bonus points                                     │ (Green text)
│                                                                 │
│     Left Column              Right Column                       │
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │     joy  ✓   │────────│  gladness ✓  │                     │ All matched
│  └──────────────┘        └──────────────┘                     │ (Green,
│                                                                 │  checkmarks)
│  ┌──────────────┐        ┌──────────────┐                     │
│  │    love  ✓   │────────│  charity ✓   │                     │
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │   faith  ✓   │────────│   trust  ✓   │                     │
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │   peace  ✓   │────────│    rest  ✓   │                     │
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │   grace  ✓   │────────│   mercy  ✓   │                     │
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│                                                                 │
│  Matches: 5/5  ✓                         Mistakes: 0  ✓       │ Perfect!
│                                                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Next Level                                 │   │ Primary
│  └─────────────────────────────────────────────────────────┘   │ button
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Celebration Elements:**
- Success message: Large, green, party emoji
- Perfect bonus: Displayed if mistakes = 0
- Visual connection: Lines/arrows between matched pairs
- All cards: Green background with checkmarks
- Next Level button: Prominent CTA

---

### State 5: Level Complete Screen (Detailed)

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Menu        WORD MATCHING                                    │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│                                                                 │
│                  🎉 Level 1 Complete! 🎉                       │ displaySmall
│                                                                 │
│                     Basic Synonyms                             │ Category
│                                                                 │
│                  ╔═════════════════╗                           │
│                  ║   Level Score   ║                           │ Score card
│                  ║       100       ║                           │ Large text
│                  ╚═════════════════╝                           │
│                                                                 │
│                5 matches, 0 mistakes                           │ Stats
│                Perfect! +50 bonus                              │
│                                                                 │
│                Total Score: 100                                │ Running total
│                                                                 │
│                                                                 │ 48dp spacing
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Next Level                                 │   │ Primary
│  └─────────────────────────────────────────────────────────┘   │ button
│                                                                 │ 16dp spacing
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Retry Level                                │   │ Secondary
│  └─────────────────────────────────────────────────────────┘   │ button
│                                                                 │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Level Complete Features:**
- Celebration animation on entry
- Level number and category name
- Level score breakdown
- Perfect bonus indicator (if applicable)
- Running total score
- Options: Next Level or Retry

---

### State 6: Later Level (Level 3 Example)

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Back        WORD MATCHING                                    │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Level 3/5                                    Score: 212        │
│  ████████████░░░░░░░░                                          │ 60% progress
│                                                                 │
│  Related Concepts                                              │ Category
│  Find words with similar meanings                              │ Hint
│                                                                 │
│     Left Column              Right Column                       │
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │              │        │              │                     │
│  │   prayer     │        │ supplication │                     │ Different
│  │              │        │              │                     │ word pairs
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│  ┌──────────────┐        ┌──────────────┐                     │
│  │              │        │              │                     │
│  │   wisdom     │        │understanding │                     │
│  │              │        │              │                     │
│  └──────────────┘        └──────────────┘                     │
│                                                                 │
│  [Additional word pairs...]                                    │
│                                                                 │
│  Matches: 2/5                            Mistakes: 3           │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Progressive Difficulty:**
- Level 1: Basic Synonyms (joy/gladness)
- Level 2: Biblical Opposites (light/darkness)
- Level 3: Related Concepts (prayer/supplication)
- Level 4: Advanced Synonyms (truth/verity)
- Level 5: Theological Terms (covenant/testament)

---

### State 7: Game Complete Screen

```
┌─────────────────────────────────────────────────────────────────┐
│  ← Menu        WORD MATCHING                                    │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│                                                                 │
│                  🏆 Congratulations! 🏆                        │ displayMedium
│                                                                 │
│              You completed all 5 levels!                       │ headlineSmall
│                                                                 │
│                                                                 │ 24dp spacing
│                  ╔═════════════════╗                           │
│                  ║  Final Score    ║                           │ Large card
│                  ║      475        ║                           │ Primary color
│                  ╚═════════════════╝                           │
│                                                                 │
│                                                                 │ 24dp spacing
│             Your Performance:                                  │ titleMedium
│                                                                 │
│  ┌───────────────────────────────────────────────────────────┐ │
│  │  Level 1: 100 points (Perfect! ✓)                        │ │ Stats card
│  │  Level 2:  94 points (3 mistakes)                        │ │
│  │  Level 3:  96 points (2 mistakes)                        │ │
│  │  Level 4: 100 points (Perfect! ✓)                        │ │
│  │  Level 5:  85 points (7 mistakes)                        │ │
│  │                                                           │ │
│  │  Perfect Levels: 2/5                                     │ │
│  │  Total Mistakes: 12                                      │ │
│  └───────────────────────────────────────────────────────────┘ │
│                                                                 │
│                                                                 │ 32dp spacing
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Play Again                                 │   │ Primary
│  └─────────────────────────────────────────────────────────┘   │ button
│                                                                 │ 16dp spacing
│  ┌─────────────────────────────────────────────────────────┐   │
│  │              Back to Menu                               │   │ Secondary
│  └─────────────────────────────────────────────────────────┘   │ button
│                                                                 │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

**Game Complete Features:**
- Trophy celebration
- Final total score (large, prominent)
- Per-level breakdown with scores
- Perfect level indicators
- Aggregate statistics
- Play again or return to menu options

---

## 🎨 Design Specifications

### Color Palette

```kotlin
// Card states
val CardDefault = Color(0xFFFFFFFF)         // White
val CardSelected = Color(0xFF2196F3)        // Blue
val CardMatched = Color(0xFF4CAF50)         // Green
val CardError = Color(0xFFD32F2F)           // Red (flash)

// Borders
val BorderDefault = Color(0xFFBDBDBD)       // Gray
val BorderSelected = Color(0xFF1976D2)      // Dark blue
val BorderMatched = Color(0xFF2E7D32)       // Dark green

// Feedback
val SuccessColor = Color(0xFF4CAF50)        // Green
val ErrorColor = Color(0xFFD32F2F)          // Red
val PerfectBonusColor = Color(0xFFFFD700)   // Gold

// Progress bar
val ProgressFilled = Color(0xFF2196F3)      // Blue
val ProgressEmpty = Color(0xFFE0E0E0)       // Light gray
```

### Typography

```kotlin
// Level title
headlineSmall = TextStyle(
    fontSize = 24.sp,
    fontWeight = FontWeight.Bold,
    color = Primary
)

// Category name
titleLarge = TextStyle(
    fontSize = 20.sp,
    fontWeight = FontWeight.SemiBold
)

// Word text on cards
bodyLarge = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.Medium,
    letterSpacing = 0.5.sp
)

// Instructions
bodyMedium = TextStyle(
    fontSize = 14.sp,
    color = OnSurfaceVariant
)

// Status text
labelMedium = TextStyle(
    fontSize = 12.sp,
    fontWeight = FontWeight.Medium
)
```

### Component Specifications

#### 1. **Progress Bar**
- Height: 8dp
- Width: Full width minus padding
- Filled: Blue (#2196F3)
- Empty: Light gray (#E0E0E0)
- Corner radius: 4dp
- Smooth animation on level change

#### 2. **Category Label**
- Style: headlineSmall or titleLarge
- Color: Primary
- Position: Below progress bar, centered
- Margin: 16dp bottom

#### 3. **Word Card**
- Width: 45% of screen width
- Height: 48dp (minimum touch target)
- Corner radius: 8dp
- Elevation:
  - Default: 2dp
  - Selected: 4dp
  - Matched: 2dp
- Border:
  - Default: 1dp gray
  - Selected: 2dp blue
  - Matched: 2dp green
- Padding: 12dp horizontal, 14dp vertical
- Text alignment: Center

**Card States:**
```kotlin
// Default card
Card(
    backgroundColor = Color.White,
    border = BorderStroke(1.dp, Color.Gray),
    elevation = 2.dp
)

// Selected card
Card(
    backgroundColor = Color(0xFF2196F3),
    border = BorderStroke(2.dp, Color(0xFF1976D2)),
    elevation = 4.dp
) {
    Text(color = Color.White, fontWeight = Bold)
}

// Matched card
Card(
    backgroundColor = Color(0xFF4CAF50),
    border = BorderStroke(2.dp, Color(0xFF2E7D32)),
    elevation = 2.dp
) {
    Row {
        Text(color = Color.White)
        Icon(Icons.Check, tint = Color.White)
    }
}
```

#### 4. **Checkmark Icon (Matched Cards)**
- Size: 16dp × 16dp
- Color: White
- Position: Right side of text
- Animation: Scale from 0 → 1.0 (200ms)

#### 5. **Feedback Text**
- Position: Below category, above cards
- Success: "✓ Correct match! +10 points" (green)
- Error: "✗ Not a match. Try again!" (red)
- Perfect: "🎉 Perfect! +50 bonus points" (gold)
- Height: 24dp (reserved space)
- Animation: Fade in/out

#### 6. **Status Bar (Bottom)**
- Layout: Row with SpaceBetween
- Left: "Matches: X/5"
- Right: "Mistakes: X"
- Style: labelMedium
- Color: OnSurfaceVariant

#### 7. **Next Level Button**
- Height: 56dp
- Width: Match parent with 16dp padding
- Style: Filled button (primary color)
- Text: "Next Level"
- Corner radius: 28dp (fully rounded)

---

## 🔄 State Transitions & Animations

### Match Validation Sequence

**Successful Match:**
```
User taps second word
    ↓
Check if pairId matches
    ↓
Match is valid!
    ↓
1. Both cards: Color transition white → green (300ms)
2. Checkmark icons: Scale 0 → 1.0 (200ms)
3. Score: Animate +10 (500ms count-up)
4. Feedback: Fade in "✓ Correct match! +10 points" (200ms)
5. Matches count: Increment (with animation)
6. Cards: Lock (prevent further interaction)
    ↓
Wait 1 second for user to see feedback
    ↓
Feedback: Fade out (300ms)
    ↓
Check if level complete (all 5 matched)
    ↓
If complete: Transition to Level Complete screen
```

**Failed Match:**
```
User taps second word
    ↓
Check if pairId matches
    ↓
Match is invalid!
    ↓
1. Both cards: Flash red (100ms)
2. Both cards: Shake animation (200ms, ±5dp horizontal)
3. Score: Animate -2 (500ms count-down)
4. Mistakes: Increment
5. Feedback: Fade in "✗ Not a match. Try again!" (200ms)
    ↓
Wait 1 second for user to see feedback
    ↓
Both cards: Color transition → white (300ms)
Both cards: Deselect (remove selection)
Feedback: Fade out (300ms)
```

### Level Transition

```
All 5 pairs matched
    ↓
Calculate level score
    ↓
Check for perfect bonus (mistakes = 0)
    ↓
Animate all matched cards (wave effect, stagger by 50ms)
    ↓
Fade out current level (300ms)
    ↓
Show Level Complete screen (fade + slide up, 400ms)
    ↓
User taps "Next Level"
    ↓
Fade out Level Complete screen (300ms)
    ↓
Load next level data
    ↓
Fade in new level (fade + slide up, 400ms)
```

### Card Selection Animation

```
User taps card
    ↓
Scale: 0.95 → 1.0 (100ms, spring)
Elevation: 2dp → 4dp (100ms)
Background: White → Blue (200ms)
Text color: Black → White (200ms)
Border: 1dp gray → 2dp dark blue (200ms)
```

---

## ♿ Accessibility Features

### Screen Reader Support

```kotlin
// Word card
semanticProperties {
    contentDescription = if (isMatched) {
        "$word, matched with pair"
    } else if (isSelected) {
        "$word, selected, tap another word to match"
    } else {
        "$word, not matched, tap to select"
    }
    role = Role.Button
    stateDescription = when {
        isMatched -> "Matched"
        isSelected -> "Selected"
        else -> "Available"
    }
}

// Progress
semanticProperties {
    contentDescription = "Level $currentLevel of $totalLevels. 
                         $matchedPairs out of 5 pairs matched"
}

// Score
semanticProperties {
    contentDescription = "Current score: $score points"
    liveRegion = LiveRegionMode.Polite
}

// Feedback
semanticProperties {
    contentDescription = feedback
    liveRegion = LiveRegionMode.Assertive  // Immediate announcement
}
```

### TalkBack Behavior
- Cards: Read word, state, and instruction
- Match success: Announce "Correct match, 10 points added"
- Match failure: Announce "Not a match, minus 2 points, try again"
- Level complete: Announce "Level complete, X points earned"

### Minimum Touch Targets
- Word cards: 48dp height minimum (meets WCAG)
- Buttons: 56dp height
- All interactive elements: 48dp × 48dp minimum

### Color Contrast
- Default card text: #2C2C2C on #FFFFFF = 13:1 (WCAG AAA)
- Selected card text: #FFFFFF on #2196F3 = 4.8:1 (WCAG AA)
- Matched card text: #FFFFFF on #4CAF50 = 4.5:1 (WCAG AA)
- Feedback success: #2E7D32 on light background = 7.5:1
- Feedback error: #C62828 on light background = 8:1

---

## 📱 Responsive Design

### Portrait Orientation (Primary)
- Two columns: 45% width each, 5% gap
- Cards: Full width of column
- Optimized for: 360dp × 640dp to 412dp × 915dp
- All cards visible without scrolling

### Landscape Orientation
- Same two-column layout
- Increased side padding: 32dp
- Cards may be slightly wider
- Buttons: Max width 400dp, centered

### Tablet Layout (7" and above)
- Max content width: 600dp, centered
- Card height: 56dp (larger for easier tapping)
- Text size: Increased by 2sp
- More generous spacing between elements

---

## 🎯 User Interaction Patterns

### Primary User Flow
1. **Read category** - Understand relationship type
2. **Scan words** - Identify potential matches
3. **Select first word** - Tap card in either column
4. **Select match** - Tap matching card in other column
5. **View feedback** - See if match is correct
6. **Repeat** - Continue until all 5 pairs matched
7. **Complete level** - View score and proceed to next level
8. **Finish game** - Complete all 5 levels

### Strategy Tips
- Read category label for hints
- Look for obvious pairs first
- Eliminate matched pairs mentally
- Don't worry about mistakes (not limiting)
- Aim for perfect levels for bonus points

### Edge Cases Handled

#### Selection Logic
- Can't select two from same column
- Can't select already matched cards
- Tapping selected card: Deselects it
- Auto-validation when second card selected

#### Level Progression
- Must complete levels in order
- Can retry any level
- Scores accumulate across all levels
- Perfect bonus awarded per level

#### Scoring
- Correct match: +10 points
- Incorrect match: -2 points
- Perfect level (0 mistakes): +50 bonus
- Minimum score: 0 (doesn't go negative)

---

## 🔧 Technical Implementation Notes

### Data Flow
```kotlin
ViewModel (WordMatchingViewModel)
    ↓ exposes
StateFlow<WordMatchingUiState>
    ↓ observed by
Composable (WordMatchingGameScreen)
    ↓ renders
UI based on gameState enum
    ↓ user actions
Events back to ViewModel
```

### State Management
```kotlin
data class WordMatchingUiState(
    val currentLevel: Int = 1,
    val totalLevels: Int = 5,
    val category: String = "",
    val leftWords: List<MatchableWord> = emptyList(),
    val rightWords: List<MatchableWord> = emptyList(),
    val selectedLeft: MatchableWord? = null,
    val selectedRight: MatchableWord? = null,
    val matchedPairs: Set<Int> = emptySet(),
    val score: Int = 0,
    val mistakes: Int = 0,
    val levelMistakes: Int = 0,  // Reset per level
    val gameState: MatchingGameState = MatchingGameState.Playing,
    val feedback: String = ""
)

enum class MatchingGameState {
    Playing,        // Active matching
    LevelComplete,  // Level finished
    GameComplete    // All 5 levels done
}
```

### Word Pair Data
```kotlin
data class WordPair(
    val id: Int,
    val leftWord: String,
    val rightWord: String,
    val category: String,
    val level: Int
)

sealed class MatchableWord {
    abstract val id: Int
    abstract val pairId: Int
    abstract val text: String
    abstract val isMatched: Boolean
    
    data class LeftWord(...) : MatchableWord()
    data class RightWord(...) : MatchableWord()
}
```

### Match Validation
```kotlin
fun validateMatch(
    left: MatchableWord,
    right: MatchableWord
): Boolean {
    // Both must be unmatched
    if (left.isMatched || right.isMatched) return false
    
    // Must be from different columns
    if (left is LeftWord && right is LeftWord) return false
    if (left is RightWord && right is RightWord) return false
    
    // PairId must match
    return left.pairId == right.pairId
}
```

### Scoring Calculation
```kotlin
fun calculateLevelScore(
    matchCount: Int = 5,
    mistakes: Int
): Int {
    val baseScore = matchCount * 10  // 50 points
    val penalty = mistakes * 2
    val perfectBonus = if (mistakes == 0) 50 else 0
    return (baseScore - penalty + perfectBonus).coerceAtLeast(0)
}

// Examples:
// Perfect (0 mistakes): 50 + 50 = 100 points
// 2 mistakes: 50 - 4 = 46 points
// 5 mistakes: 50 - 10 = 40 points
```

---

## ✅ Implementation Checklist

- [x] Playing screen with two-column layout
- [x] Word card rendering (5 pairs per level)
- [x] Card selection (tap to select)
- [x] Selection state management (one per column)
- [x] Match validation (pairId comparison)
- [x] Success feedback (green cards, checkmarks)
- [x] Error feedback (red flash, shake animation)
- [x] Score tracking and display
- [x] Mistake counter per level
- [x] Level progress bar
- [x] Category labels for each level
- [x] Level complete screen
- [x] Perfect bonus calculation (+50)
- [x] 5 levels with progressive difficulty
- [x] Level transition animations
- [x] Game complete screen with statistics
- [x] Play again functionality
- [x] Return to menu navigation
- [x] Accessibility labels
- [x] Material Design 3 theming

---

## 📚 Related Screens

- **Previous Screen**: [Game Mode Selection](WIREFRAME_MAIN_SCREEN.md)
- **Related Screens**: 
  - [Verse Challenge Game Screen](WIREFRAME_VERSE_CHALLENGE.md)
  - [Word Grid Game Screen](WIREFRAME_WORD_GRID.md)

---

## 📝 Version History

| Version | Date | Changes | Author |
|---------|------|---------|--------|
| 1.0 | Jan 3, 2026 | Initial wireframe document | GitHub Copilot |

---

**Implementation Reference**: `app/src/main/kotlin/com/purewords1611/android/ui/wordmatching/WordMatchingGameScreen.kt`

**ViewModel**: `app/src/main/kotlin/com/purewords1611/android/viewmodel/WordMatchingViewModel.kt`

**Data Model**: `app/src/main/kotlin/com/purewords1611/android/data/WordMatchingGame.kt`

---

*"Let your speech be always with grace, seasoned with salt, that ye may know how ye ought to answer every man." - Colossians 4:6 (KJV)*
