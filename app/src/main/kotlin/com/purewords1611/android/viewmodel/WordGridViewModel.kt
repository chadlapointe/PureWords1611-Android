package com.purewords1611.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purewords1611.android.data.GridPosition
import com.purewords1611.android.data.WordDictionary
import com.purewords1611.android.data.WordGameEngine
import com.purewords1611.android.data.WordGrid
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds
import javax.inject.Inject

/**
 * Game states for the word grid game.
 */
sealed class WordGridGameState {
    data object Loading : WordGridGameState()
    data object Playing : WordGridGameState()
    data object Paused : WordGridGameState()
    data object TimeUp : WordGridGameState()
    data object Victory : WordGridGameState()
}

/**
 * UI state for the word grid game.
 */
data class WordGridUiState(
    val grid: WordGrid? = null,
    val currentPath: List<GridPosition> = emptyList(),
    val foundWords: List<String> = emptyList(),
    val score: Int = 0,
    val timeRemaining: Int = WordGridViewModel.INITIAL_TIME_SECONDS,
    val gameState: WordGridGameState = WordGridGameState.Loading,
    val feedback: String = "",
)

/**
 * ViewModel for the word grid game.
 * Uses Hilt for dependency injection.
 */
@HiltViewModel
class WordGridViewModel @Inject constructor(
    private val wordDictionary: WordDictionary
) : ViewModel() {
    
    companion object {
        private const val TAG = "WordGridViewModel"
        const val INITIAL_TIME_SECONDS = 120 // 2 minutes
        const val WIN_WORDS_REQUIRED = 10
    }
    
    private val gameEngine = WordGameEngine(wordDictionary, gridSize = 4)
    
    private val _uiState = MutableStateFlow(WordGridUiState())
    val uiState: StateFlow<WordGridUiState> = _uiState.asStateFlow()
    
    private var timerJob: Job? = null
    
    init {
        startNewGame()
    }
    
    /**
     * Start a new game with a fresh grid.
     */
    fun startNewGame() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(gameState = WordGridGameState.Loading)
            
            try {
                val grid = gameEngine.generateGrid()
                _uiState.value = WordGridUiState(
                    grid = grid,
                    gameState = WordGridGameState.Playing,
                    timeRemaining = INITIAL_TIME_SECONDS
                )
                startTimer()
            } catch (e: Exception) {
                android.util.Log.e(TAG, "Failed to generate grid", e)
                _uiState.value = _uiState.value.copy(
                    feedback = "Failed to start game",
                    gameState = WordGridGameState.Loading
                )
            }
        }
    }
    
    /**
     * Add a position to the current path.
     */
    fun addToPath(position: GridPosition) {
        val currentPath = _uiState.value.currentPath
        val grid = _uiState.value.grid ?: return
        
        // Check if position is valid
        if (!grid.isValidPosition(position)) return
        
        // Check if already in path
        if (currentPath.contains(position)) return
        
        // Check if adjacent to last position (if path is not empty)
        if (currentPath.isNotEmpty()) {
            val lastPos = currentPath.last()
            if (!grid.areAdjacent(lastPos, position)) return
        }
        
        _uiState.value = _uiState.value.copy(
            currentPath = currentPath + position
        )
    }
    
    /**
     * Clear the current path.
     */
    fun clearPath() {
        _uiState.value = _uiState.value.copy(
            currentPath = emptyList(),
            feedback = ""
        )
    }
    
    /**
     * Submit the current path as a word.
     */
    fun submitWord() {
        viewModelScope.launch {
            val grid = _uiState.value.grid ?: return@launch
            val path = _uiState.value.currentPath
            
            if (path.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    feedback = "Select letters to form a word"
                )
                return@launch
            }
            
            val result = gameEngine.validateWord(grid, path)
            
            if (result.isValid) {
                // Check if word was already found
                if (_uiState.value.foundWords.contains(result.word)) {
                    _uiState.value = _uiState.value.copy(
                        feedback = "Already found: ${result.word}",
                        currentPath = emptyList()
                    )
                    return@launch
                }
                
                // Add word to found words and update score
                val newScore = _uiState.value.score + gameEngine.calculateScore(result.word)
                val newFoundWords = _uiState.value.foundWords + result.word
                
                _uiState.value = _uiState.value.copy(
                    foundWords = newFoundWords,
                    score = newScore,
                    currentPath = emptyList(),
                    feedback = "+${gameEngine.calculateScore(result.word)} points: ${result.word}"
                )
                
                // Check win condition (10+ unique words)
                if (newFoundWords.size >= WIN_WORDS_REQUIRED) {
                    stopTimer()
                    _uiState.value = _uiState.value.copy(
                        gameState = WordGridGameState.Victory,
                        feedback = "Victory! Found ${newFoundWords.size} words!"
                    )
                }
            } else {
                _uiState.value = _uiState.value.copy(
                    feedback = result.message,
                    currentPath = emptyList()
                )
            }
        }
    }
    
    /**
     * Start the game timer.
     */
    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while ((_uiState.value.timeRemaining > 0) &&
                   (_uiState.value.gameState == WordGridGameState.Playing)) {
                delay(1.seconds)
                val newTime = _uiState.value.timeRemaining - 1
                _uiState.value = _uiState.value.copy(timeRemaining = newTime)
                
                if (newTime <= 0) {
                    _uiState.value = _uiState.value.copy(
                        gameState = WordGridGameState.TimeUp,
                        feedback = "Time's up! Found ${_uiState.value.foundWords.size} words"
                    )
                }
            }
        }
    }
    
    /**
     * Stop the game timer.
     */
    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }
    
    /**
     * Pause the game.
     */
    fun pauseGame() {
        if (_uiState.value.gameState == WordGridGameState.Playing) {
            stopTimer()
            _uiState.value = _uiState.value.copy(gameState = WordGridGameState.Paused)
        }
    }
    
    /**
     * Resume the game.
     */
    fun resumeGame() {
        if (_uiState.value.gameState == WordGridGameState.Paused) {
            _uiState.value = _uiState.value.copy(gameState = WordGridGameState.Playing)
            startTimer()
        }
    }
    
    /**
     * Reset the game.
     */
    fun resetGame() {
        stopTimer()
        startNewGame()
    }
    
    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}
