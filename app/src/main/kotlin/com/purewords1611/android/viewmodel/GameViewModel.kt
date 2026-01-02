package com.purewords1611.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purewords1611.android.data.Verse
import com.purewords1611.android.data.VerseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Game states representing the current phase of gameplay.
 */
sealed class GameState {
    object Loading : GameState()
    object Playing : GameState()
    object Correct : GameState()
    object Incorrect : GameState()
    object GameOver : GameState()
}

/**
 * UI state for the game.
 */
data class GameUiState(
    val currentVerse: Verse? = null,
    val userInputs: List<String> = emptyList(),
    val score: Int = 0,
    val lives: Int = 3,
    val gameState: GameState = GameState.Loading,
    val feedback: String = ""
)

/**
 * ViewModel for managing game state and logic.
 * Uses Hilt for dependency injection.
 */
@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: VerseRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()
    
    init {
        loadNextVerse()
    }
    
    /**
     * Load the next verse from the repository.
     */
    fun loadNextVerse() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(gameState = GameState.Loading)
            
            val verse = repository.getRandomVerse()
            if (verse != null) {
                _uiState.value = _uiState.value.copy(
                    currentVerse = verse,
                    userInputs = List(verse.missingWords.size) { "" },
                    gameState = GameState.Playing,
                    feedback = ""
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    gameState = GameState.GameOver,
                    feedback = "No verses available"
                )
            }
        }
    }
    
    /**
     * Update user input for a specific blank index.
     */
    fun updateInput(index: Int, text: String) {
        val currentInputs = _uiState.value.userInputs.toMutableList()
        if (index in currentInputs.indices) {
            currentInputs[index] = text
            _uiState.value = _uiState.value.copy(userInputs = currentInputs)
        }
    }
    
    /**
     * Validate user input against the correct answer.
     * Case-insensitive comparison.
     */
    fun validateAnswer() {
        val currentVerse = _uiState.value.currentVerse ?: return
        val userInputs = _uiState.value.userInputs
        
        // Check if all inputs are filled
        if (userInputs.any { it.isBlank() }) {
            _uiState.value = _uiState.value.copy(
                feedback = "Please fill all blanks"
            )
            return
        }
        
        // Validate each word (case-insensitive)
        val isCorrect = userInputs.size == currentVerse.missingWords.size &&
                userInputs.zip(currentVerse.missingWords).all { (input, correct) ->
                    input.trim().equals(correct, ignoreCase = true)
                }
        
        if (isCorrect) {
            _uiState.value = _uiState.value.copy(
                score = _uiState.value.score + 10,
                gameState = GameState.Correct,
                feedback = "Correct! +10 points"
            )
        } else {
            val newLives = _uiState.value.lives - 1
            if (newLives <= 0) {
                _uiState.value = _uiState.value.copy(
                    lives = 0,
                    gameState = GameState.GameOver,
                    feedback = "Game Over! Final Score: ${_uiState.value.score}"
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    lives = newLives,
                    gameState = GameState.Incorrect,
                    feedback = "Incorrect. Lives remaining: $newLives"
                )
            }
        }
    }
    
    /**
     * Continue to the next verse after a correct or incorrect answer.
     */
    fun continueGame() {
        if (_uiState.value.gameState != GameState.GameOver) {
            loadNextVerse()
        }
    }
    
    /**
     * Reset the game to initial state.
     */
    fun resetGame() {
        _uiState.value = GameUiState()
        loadNextVerse()
    }
}
