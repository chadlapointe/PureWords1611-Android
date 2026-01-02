package com.purewords1611.android.viewmodel

import androidx.lifecycle.ViewModel
import com.purewords1611.android.data.MatchableWord
import com.purewords1611.android.data.WordMatchingEngine
import com.purewords1611.android.data.WordPair
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Game states for word matching.
 */
sealed class MatchingGameState {
    object Playing : MatchingGameState()
    object LevelComplete : MatchingGameState()
    object GameComplete : MatchingGameState()
}

/**
 * UI state for the word matching game.
 */
data class WordMatchingUiState(
    val currentLevel: Int = 0,
    val leftWords: List<MatchableWord> = emptyList(),
    val rightWords: List<MatchableWord> = emptyList(),
    val selectedLeftId: Int? = null,
    val selectedRightId: Int? = null,
    val completedMatches: Set<Pair<Int, Int>> = emptySet(),
    val score: Int = 0,
    val mistakes: Int = 0,
    val gameState: MatchingGameState = MatchingGameState.Playing,
    val feedback: String = "",
    val totalLevels: Int = 5
)

/**
 * ViewModel for word matching game.
 * Uses Hilt for dependency injection.
 */
@HiltViewModel
class WordMatchingViewModel @Inject constructor() : ViewModel() {
    
    companion object {
        // Offset for right word IDs to avoid collision with left word IDs
        private const val RIGHT_WORD_ID_OFFSET = 1000
    }
    
    private val engine = WordMatchingEngine()
    
    private val _uiState = MutableStateFlow(WordMatchingUiState())
    val uiState: StateFlow<WordMatchingUiState> = _uiState.asStateFlow()
    
    // Maps to track word ID to actual word text
    private var leftWordMap: Map<Int, String> = emptyMap()
    private var rightWordMap: Map<Int, String> = emptyMap()
    private var currentLevelPairs: List<WordPair> = emptyList()
    
    init {
        loadLevel(0)
    }
    
    /**
     * Load a specific level.
     * @param resetScore If true, reset the score to 0 (used for game reset)
     */
    private fun loadLevel(level: Int, resetScore: Boolean = false) {
        currentLevelPairs = engine.getLevelPairs(level)
        
        if (currentLevelPairs.isEmpty()) {
            // No more levels
            _uiState.value = _uiState.value.copy(
                gameState = MatchingGameState.GameComplete
            )
            return
        }
        
        // Shuffle and create word lists
        val shuffledPairs = currentLevelPairs.shuffled()
        val leftShuffled = shuffledPairs.map { it.leftWord }.shuffled()
        val rightShuffled = shuffledPairs.map { it.rightWord }.shuffled()
        
        // Create matchable words with unique IDs
        val leftWords = leftShuffled.mapIndexed { index, word ->
            MatchableWord(text = word, id = index)
        }
        val rightWords = rightShuffled.mapIndexed { index, word ->
            MatchableWord(text = word, id = index + RIGHT_WORD_ID_OFFSET)
        }
        
        // Build maps
        leftWordMap = leftWords.associate { it.id to it.text }
        rightWordMap = rightWords.associate { it.id to it.text }
        
        _uiState.value = WordMatchingUiState(
            currentLevel = level,
            leftWords = leftWords,
            rightWords = rightWords,
            totalLevels = engine.getTotalLevels(),
            score = if (resetScore) 0 else _uiState.value.score, // Keep accumulated score unless resetting
            gameState = MatchingGameState.Playing
        )
    }
    
    /**
     * Select a word from the left column.
     */
    fun selectLeftWord(id: Int) {
        val current = _uiState.value
        
        // Don't allow selection if already matched
        if (current.completedMatches.any { it.first == id }) return
        
        // Toggle selection
        val newSelectedLeft = if (current.selectedLeftId == id) null else id
        
        _uiState.value = current.copy(
            selectedLeftId = newSelectedLeft,
            feedback = ""
        )
        
        // If both sides selected, check for match
        if (newSelectedLeft != null && current.selectedRightId != null) {
            checkMatch()
        }
    }
    
    /**
     * Select a word from the right column.
     */
    fun selectRightWord(id: Int) {
        val current = _uiState.value
        
        // Don't allow selection if already matched
        if (current.completedMatches.any { it.second == id }) return
        
        // Toggle selection
        val newSelectedRight = if (current.selectedRightId == id) null else id
        
        _uiState.value = current.copy(
            selectedRightId = newSelectedRight,
            feedback = ""
        )
        
        // If both sides selected, check for match
        if (current.selectedLeftId != null && newSelectedRight != null) {
            checkMatch()
        }
    }
    
    /**
     * Check if the selected words form a valid match.
     */
    private fun checkMatch() {
        val current = _uiState.value
        val leftId = current.selectedLeftId ?: return
        val rightId = current.selectedRightId ?: return
        
        val leftWord = leftWordMap[leftId] ?: return
        val rightWord = rightWordMap[rightId] ?: return
        
        val isValid = engine.isValidMatch(leftWord, rightWord, currentLevelPairs)
        
        if (isValid) {
            // Correct match!
            val newCompletedMatches = current.completedMatches + (leftId to rightId)
            val matchCount = newCompletedMatches.size
            val totalPairs = currentLevelPairs.size
            
            // Calculate new score
            val newScore = engine.calculateScore(matchCount, totalPairs, current.mistakes)
            
            // Check if level complete
            val newState = if (matchCount == totalPairs) {
                MatchingGameState.LevelComplete
            } else {
                MatchingGameState.Playing
            }
            
            _uiState.value = current.copy(
                selectedLeftId = null,
                selectedRightId = null,
                completedMatches = newCompletedMatches,
                score = newScore,
                feedback = "✓ Correct match!",
                gameState = newState
            )
        } else {
            // Incorrect match
            _uiState.value = current.copy(
                selectedLeftId = null,
                selectedRightId = null,
                mistakes = current.mistakes + 1,
                feedback = "✗ Not a match. Try again!"
            )
        }
    }
    
    /**
     * Proceed to the next level.
     */
    fun nextLevel() {
        val current = _uiState.value
        loadLevel(current.currentLevel + 1)
    }
    
    /**
     * Reset the game from the beginning.
     */
    fun resetGame() {
        loadLevel(0, resetScore = true)
    }
    
    /**
     * Retry the current level.
     */
    fun retryLevel() {
        val current = _uiState.value
        loadLevel(current.currentLevel)
    }
}
