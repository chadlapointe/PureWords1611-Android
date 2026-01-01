package com.purewords1611.android.ui.gameplay

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.purewords1611.android.viewmodel.GameState
import com.purewords1611.android.viewmodel.GameUiState

/**
 * Main gameplay screen that displays the verse with blanks and input fields.
 */
@Composable
fun GameplayScreen(
    uiState: GameUiState,
    onInputChange: (Int, String) -> Unit,
    onValidate: () -> Unit,
    onContinue: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState.gameState) {
        GameState.Loading -> LoadingScreen(modifier)
        GameState.Playing -> PlayingScreen(uiState, onInputChange, onValidate, modifier)
        GameState.Correct -> FeedbackScreen(
            uiState = uiState,
            isCorrect = true,
            onContinue = onContinue,
            modifier = modifier
        )
        GameState.Incorrect -> FeedbackScreen(
            uiState = uiState,
            isCorrect = false,
            onContinue = onContinue,
            modifier = modifier
        )
        GameState.GameOver -> GameOverScreen(uiState, onReset, modifier)
    }
}

/**
 * Loading screen while verse is being loaded.
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
 * Main playing screen with verse and input fields.
 */
@Composable
fun PlayingScreen(
    uiState: GameUiState,
    onInputChange: (Int, String) -> Unit,
    onValidate: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Score and Lives Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Score: ${uiState.score}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Lives: ${uiState.lives}/3",
                style = MaterialTheme.typography.titleMedium
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Verse Card
        uiState.currentVerse?.let { verse ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = verse.reference,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = verse.blankedText,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Input fields for each blank
                    verse.missingWords.forEachIndexed { index, _ ->
                        OutlinedTextField(
                            value = uiState.userInputs.getOrNull(index) ?: "",
                            onValueChange = { onInputChange(index, it) },
                            label = { Text("Word ${index + 1}") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            singleLine = true
                        )
                    }
                    
                    if (uiState.feedback.isNotEmpty() && uiState.gameState == GameState.Playing) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uiState.feedback,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Validate Button
        Button(
            onClick = onValidate,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Check Answer")
        }
    }
}

/**
 * Feedback screen shown after correct or incorrect answer.
 */
@Composable
fun FeedbackScreen(
    uiState: GameUiState,
    isCorrect: Boolean,
    onContinue: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isCorrect) 
                    MaterialTheme.colorScheme.primaryContainer 
                else 
                    MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isCorrect) "✓ Correct!" else "✗ Incorrect",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = uiState.feedback,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Show the correct answer
                uiState.currentVerse?.let { verse ->
                    Text(
                        text = "Complete verse:",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\"${verse.text}\"",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "- ${verse.reference}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue")
        }
    }
}

/**
 * Game over screen with final score.
 */
@Composable
fun GameOverScreen(
    uiState: GameUiState,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Game Over",
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
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = uiState.feedback,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = onReset,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Play Again")
        }
    }
}
