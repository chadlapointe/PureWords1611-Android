package com.purewords1611.android.ui.wordmatching

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.purewords1611.android.data.MatchableWord
import com.purewords1611.android.viewmodel.MatchingGameState
import com.purewords1611.android.viewmodel.WordMatchingUiState

/**
 * Main screen for the word matching game.
 */
@Composable
fun WordMatchingGameScreen(
    uiState: WordMatchingUiState,
    onLeftWordClick: (Int) -> Unit,
    onRightWordClick: (Int) -> Unit,
    onNextLevel: () -> Unit,
    onRetryLevel: () -> Unit,
    onResetGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState.gameState) {
        MatchingGameState.Playing -> PlayingScreen(
            uiState = uiState,
            onLeftWordClick = onLeftWordClick,
            onRightWordClick = onRightWordClick,
            modifier = modifier
        )
        MatchingGameState.LevelComplete -> LevelCompleteScreen(
            uiState = uiState,
            onNextLevel = onNextLevel,
            onRetryLevel = onRetryLevel,
            modifier = modifier
        )
        MatchingGameState.GameComplete -> GameCompleteScreen(
            uiState = uiState,
            onResetGame = onResetGame,
            modifier = modifier
        )
    }
}

/**
 * Main playing screen with two columns of words.
 */
@Composable
fun PlayingScreen(
    uiState: WordMatchingUiState,
    onLeftWordClick: (Int) -> Unit,
    onRightWordClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        GameHeader(uiState)
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Feedback message
        if (uiState.feedback.isNotEmpty()) {
            Text(
                text = uiState.feedback,
                style = MaterialTheme.typography.bodyLarge,
                color = if (uiState.feedback.startsWith("✓")) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                },
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Instructions
        Text(
            text = "Tap words to match them",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Two columns of words
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Left column
            WordColumn(
                words = uiState.leftWords,
                selectedId = uiState.selectedLeftId,
                completedMatches = uiState.completedMatches.map { it.first }.toSet(),
                onWordClick = onLeftWordClick,
                modifier = Modifier.weight(1f)
            )
            
            // Right column
            WordColumn(
                words = uiState.rightWords,
                selectedId = uiState.selectedRightId,
                completedMatches = uiState.completedMatches.map { it.second }.toSet(),
                onWordClick = onRightWordClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Game header showing score and level info.
 */
@Composable
fun GameHeader(uiState: WordMatchingUiState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Word Matching",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Level",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${uiState.currentLevel + 1}/${uiState.totalLevels}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Score",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${uiState.score}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Matches",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${uiState.completedMatches.size}/${uiState.leftWords.size}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * A column of matchable words.
 */
@Composable
fun WordColumn(
    words: List<MatchableWord>,
    selectedId: Int?,
    completedMatches: Set<Int>,
    onWordClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(words) { word ->
            WordCard(
                word = word,
                isSelected = word.id == selectedId,
                isMatched = word.id in completedMatches,
                onClick = { onWordClick(word.id) }
            )
        }
    }
}

/**
 * A card representing a single word.
 */
@Composable
fun WordCard(
    word: MatchableWord,
    isSelected: Boolean,
    isMatched: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isMatched -> MaterialTheme.colorScheme.primaryContainer
        isSelected -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.surface
    }
    
    val borderColor = when {
        isMatched -> MaterialTheme.colorScheme.primary
        isSelected -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.outline
    }
    
    val textColor = when {
        isMatched -> MaterialTheme.colorScheme.onPrimaryContainer
        isSelected -> MaterialTheme.colorScheme.onSecondaryContainer
        else -> MaterialTheme.colorScheme.onSurface
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !isMatched) { onClick() }
            .border(
                width = if (isSelected || isMatched) 2.dp else 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = word.text,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor,
                fontWeight = if (isSelected || isMatched) FontWeight.Bold else FontWeight.Normal,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Level complete screen.
 */
@Composable
fun LevelCompleteScreen(
    uiState: WordMatchingUiState,
    onNextLevel: () -> Unit,
    onRetryLevel: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎉 Level Complete!",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Stats
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Score: ${uiState.score}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Mistakes: ${uiState.mistakes}",
                    style = MaterialTheme.typography.titleMedium
                )
                
                if (uiState.mistakes == 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "✨ Perfect! +50 bonus",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Check if there's a next level
        if (uiState.currentLevel + 1 < uiState.totalLevels) {
            Button(
                onClick = onNextLevel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Next Level",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            OutlinedButton(
                onClick = onRetryLevel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Retry Level",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        } else {
            // This was the last level
            Text(
                text = "All levels complete!",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Game complete screen.
 */
@Composable
fun GameCompleteScreen(
    uiState: WordMatchingUiState,
    onResetGame: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🏆 Congratulations!",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "You've completed all levels!",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Final Score",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "${uiState.score}",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onResetGame,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Play Again",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
