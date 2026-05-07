---
title: "Define Core Gameplay Mechanics – Initial Concept Document"
task_id: "30b19f13-0a9c-817e-9e5a-fd57f824aa55"
created: "2026-05-07T15:42:34.571817"
type: "deliverable"
---

📋 **Task: Define Core Gameplay Mechanics – Initial Concept Document**

🔍 **Research Complete** (via Perplexity Api)

# Core Gameplay Mechanics – Initial Concept Document
## Project: PureWords1611-Android

### Executive Summary
PureWords1611-Android will be a **competitive, turn-based word battle game** for Android, inspired by strategic grid-based word games like *WordPlus*. Players construct words on a shared board to score points while blocking opponents, blending vocabulary skill with territorial strategy. Core loop: **Place letters → Form words → Control board → Outscore opponent**. Targets casual-competitive players seeking 10-30 minute sessions. Differentiator: **Pure focus on strategy** (no power-ups/ads), offline bot + online multiplayer, scalable grid sizes. Ready for rapid prototyping via Unity/Android Studio.

**Key Metrics Goal**: 4.5+ Play Store rating, 10K+ downloads in first quarter post-launch (by 2026-07-26).

### 1. Core Gameplay Loop
```
1. START: Central seed letter on empty grid (7x7/8x8/9x9).
2. TURN: Player adds **1 letter** adjacent to existing letters (orthogonal/diagonal).
3. FORM: All new words created by your letters score points (1 pt/letter; longer = higher).
4. BLOCK: Position letters to limit opponent options.
5. END: Board fills → Highest score wins.
```
- **Scoring**: Word length multiplier (e.g., 5+ letters = 1.5x bonus). Backwards/diagonal/partial overlaps allowed.
- **Win Condition**: Max points when no moves left (or time limit in online mode).
- **Session Length**: 10min (7x7), 20min (8x8), 30min (9x9) – matches *WordPlus* benchmarks [1].

**Visual Example (7x7 Grid Turn)**:
```
Board State Before:  [E] (center)
Player Adds: "A" north → Forms "EA" (diag/backward valid) = 2 pts.
New Board: A
             E
```

### 2. Key Mechanics Breakdown
| Mechanic | Description | Strategic Depth | PureWords Twist |
|----------|-------------|-----------------|-----------------|
| **Letter Placement** | Tap empty adjacent cell; auto-validates dictionary words. | Blocks opponent extensions; creates forks. | Limited "wildcards" (unlockable via combos). |
| **Word Formation** | Vertical/horizontal/diagonal; overlaps/reverse allowed. | Chain multiple words per turn. | "Pure Mode": Bonus for no repeats (vocab purity). |
| **Blocking** | Place defensively to isolate opponent letters. | Territorial control like *Words With Friends* but faster. | "Lock" cells after 3-word chains. |
| **Scoring** | 1pt/letter + length bonus. | Rewards risk (long words = vulnerability). | Daily "Hardword" challenge [6] for streaks. |
| **Special Actions** | Swap 1 letter (cooldown); undo last (limited). | Balances luck/skill. | None – "Pure" philosophy: skill only. |

- **Validation**: Integrated dictionary (e.g., ENABLE/Scrabble list) for fairness.
- **Edge Cases**: Min word 2 letters; no proper nouns.

### 3. Game Modes
| Mode | Players | Features | Target Playstyle |
|------|---------|----------|------------------|
| **Offline (vs Bot)** | 1v1 AI | 3 difficulties (Easy: Predictable; Hard: Adaptive blocking). | Solo practice/commute. |
| **Online Multiplayer** | 2-player | Room creation; async challenges; global leaderboards. | Competitive friends/tournaments. |
| **Daily Challenge** | Solo | "Hardword" puzzle [6]: Guess 8-try word on mini-grid. | Retention hook (daily login). |
| **Endless** | 1vAI | Infinite board expansion. | High-score chaser. |

### 4. Competitive Analysis & Differentiation
| Game | Strengths | Weaknesses | PureWords Edge |
|------|-----------|------------|---------------|
| **WordPlus [1]** | Grid battles; multi-grid; online/offline. | Generic UI; ad-heavy? | Cleaner "pure" design; no IAP; better bot AI. |
| **Game of Words [3]** | 10K+ levels; relaxing. | Solo puzzles only. | Adds PvP strategy. |
| **Hardword [6]** | Daily Wordle-like. | Single mechanic. | Integrates as mode for retention. |
| **Rokform Recs [2]** | Strategic depth; no ads ideal. | Varies by app. | Optimized UI/UX: Portrait/landscape; lag-free swipes. |

**Avoid**: Core platform [4,5,7] – irrelevant (3D game dev); focus Android-native.

**UI/UX Priorities [2]**:
- Readable fonts (min 18pt).
- Swipe-to-place letters; haptic feedback.
- Minimalist: Board 80% screen; no clutter.
- Monetization: Free + cosmetic skins ($0.99); **no pay-to-win**.

### 5. Technical Feasibility (Android Focus)
- **Engine**: Unity (easy grid impl.; Play Games integration for multiplayer).
- **Core Systems**:
  - Grid: 2D array; A* pathfinding for valid placements.
  - Dictionary: Offline SQLite (100K+ words).
  - Bot AI: Minimax (depth 4-6); difficulty via pruning.
  - Multiplayer: Google Play Games Services (real-time/turn-based).
- **MVP Scope**: 7x7 grid + offline bot (2 weeks dev).
- **GitHub Integration**: https://github.com/chadlapointe/PureWords1611-Android – Branch: `core-mechanics`.

**Perf Targets**: 60FPS on mid-range Android (SDK 24+); offline-first.

### 6. Actionable Next Steps
1. **Prototype (Week 1)**: Unity scene with 5x5 grid; basic placement/scoring.
2. **Assets**: Free dictionary download; grid tiles from OpenGameArt.
3. **Test**: Playtest 10 users for balance (target: 70% win rate vs medium bot).
4. **Monetize Plan**: Beta free; post-launch cosmetics.
5. **Milestones**:
   | Date | Deliverable |
   |------|-------------|
   | 2026-05-14 | MVP playable build.
   | 2026-06-01 | Beta multiplayer.
   | 2026-07-01 | Store-ready APK.
   | 2026-07-26 | Published.

### Relevant Links
- **Primary Inspiration**: [WordPlus on Google Play](https://play.google.com/store/apps/details?id=app.vinztech.wordplus) [1]
- **UI/Strategy Benchmarks**: [24 Best Word Apps](https://www.rokform.com/blogs/rokform-blog/best-word-game-apps-for-adults) [2]
- **Daily Mode**: [Hardword](https://hardword.co) [6]
- **Repo**: [GitHub PureWords](https://github.com/chadlapointe/PureWords1611-Android)

This defines a **polished, addictive core** ready for iteration. PureWords1611 positions us as word-game experts on Play Store. Let's build! 🚀

✅ Task marked for review. Please verify findings and mark complete if satisfied.