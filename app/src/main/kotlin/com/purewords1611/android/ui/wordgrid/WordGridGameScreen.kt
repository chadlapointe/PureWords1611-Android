package com.purewords1611.android.ui.wordgrid

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.purewords1611.android.data.GridPosition
import com.purewords1611.android.viewmodel.WordGridGameState
import com.purewords1611.android.viewmodel.WordGridUiState

/**
 * Main screen for the word grid game.
 */
@Composable
fun WordGridGameScreen(
    uiState: WordGridUiState,
    onCellClick: (GridPosition) -> Unit,
    onSubmitWord: () -> Unit,
    onClearPath: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState.gameState) {
        WordGridGameState.Loading -> LoadingScreen(modifier)
        WordGridGameState.Playing -> PlayingScreen(
            uiState = uiState,
            onCellClick = onCellClick,
            onSubmitWord = onSubmitWord,
            onClearPath = onClearPath,
            modifier = modifier
        )
        WordGridGameState.Paused -> PausedScreen(uiState, onReset, modifier)
        WordGridGameState.TimeUp -> GameEndScreen(
            uiState = uiState,
            title = "Time's Up!",
            onReset = onReset,
            modifier = modifier
        )
        WordGridGameState.Victory -> GameEndScreen(
            uiState = uiState,
            title = "Victory!",
            onReset = onReset,
            modifier = modifier
        )
    }
}

/**
 * Loading screen.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

/**
 * Main playing screen with grid and controls.
 */
@Composable
fun PlayingScreen(
    uiState: WordGridUiState,
    onCellClick: (GridPosition) -> Unit,
    onSubmitWord: () -> Unit,
    onClearPath: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with score and timer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Score",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${uiState.score}",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Time",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = formatTime(uiState.timeRemaining),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (uiState.timeRemaining < 30) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Words found counter
        Text(
            text = "Words Found: ${uiState.foundWords.size}/10",
            style = MaterialTheme.typography.titleMedium
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Word Grid
        uiState.grid?.let { grid ->
            WordGridDisplay(
                grid = grid,
                selectedPath = uiState.currentPath,
                onCellClick = onCellClick,
                modifier = Modifier.weight(1f, fill = false)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Current word display
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.grid?.getWordFromPath(uiState.currentPath) ?: "",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Feedback message
        if (uiState.feedback.isNotEmpty()) {
            Text(
                text = uiState.feedback,
                style = MaterialTheme.typography.bodyMedium,
                color = if (uiState.feedback.contains("+")) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.error
                },
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Control buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onClearPath,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Clear")
            }
            
            Button(
                onClick = onSubmitWord,
                modifier = Modifier.weight(1f),
                enabled = uiState.currentPath.size >= 3
            ) {
                Text("Submit")
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Found words list
        if (uiState.foundWords.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.padding(8.dp)
                ) {
                    items(uiState.foundWords) { word ->
                        Text(
                            text = "✓ $word",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Display the word grid with clickable cells.
 */
@Composable
fun WordGridDisplay(
    grid: com.purewords1611.android.data.WordGrid,
    selectedPath: List<GridPosition>,
    onCellClick: (GridPosition) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        for (row in 0 until grid.size) {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                for (col in 0 until grid.size) {
                    val position = GridPosition(row, col)
                    val isSelected = selectedPath.contains(position)
                    val selectionIndex = selectedPath.indexOf(position)
                    
                    GridCell(
                        letter = grid.getLetterAt(position) ?: ' ',
                        isSelected = isSelected,
                        selectionOrder = if (isSelected) selectionIndex + 1 else 0,
                        onClick = { onCellClick(position) }
                    )
                }
            }
        }
    }
}

/**
 * Individual grid cell.
 */
@Composable
fun GridCell(
    letter: Char,
    isSelected: Boolean,
    selectionOrder: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(70.dp)
            .padding(4.dp)
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surfaceVariant
                },
                shape = MaterialTheme.shapes.medium
            )
            .border(
                width = 2.dp,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimary
                } else {
                    MaterialTheme.colorScheme.outline
                },
                shape = MaterialTheme.shapes.medium
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter.toString(),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
        
        if (selectionOrder > 0) {
            Text(
                text = selectionOrder.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
            )
        }
    }
}

/**
 * Paused screen.
 */
@Composable
fun PausedScreen(
    uiState: WordGridUiState,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Game Paused",
                    style = MaterialTheme.typography.headlineMedium
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(onClick = onReset) {
                    Text("New Game")
                }
            }
        }
    }
}

/**
 * Game end screen (time up or victory).
 */
@Composable
fun GameEndScreen(
    uiState: WordGridUiState,
    title: String,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Final Score",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = "${uiState.score}",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Words Found: ${uiState.foundWords.size}",
                    style = MaterialTheme.typography.titleMedium
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                if (uiState.foundWords.isNotEmpty()) {
                    Text(
                        text = "Your Words:",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = uiState.foundWords.joinToString(", "),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                Button(
                    onClick = onReset,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Play Again")
                }
            }
        }
    }
}

/**
 * Format time in MM:SS format.
 */
private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val secs = seconds % 60
    return "%d:%02d".format(minutes, secs)
}
