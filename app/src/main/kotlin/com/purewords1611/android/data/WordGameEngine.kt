package com.purewords1611.android.data

import kotlin.random.Random

/**
 * Engine for managing word grid game mechanics.
 * Handles grid generation, word validation, and scoring.
 */
class WordGameEngine(
    private val wordDictionary: WordDictionary,
    private val gridSize: Int = 4
) {
    
    /**
     * Common letters in English, weighted by frequency for better gameplay.
     * Vowels and common consonants appear more often.
     */
    private val commonLetters = listOf(
        'e', 'e', 'e', 'a', 'a', 'a', 'i', 'i', 'o', 'o', 
        't', 't', 'n', 'n', 's', 's', 'r', 'r', 'h', 'h',
        'l', 'l', 'd', 'd', 'c', 'c', 'u', 'u', 'm', 'm',
        'p', 'f', 'g', 'w', 'y', 'b', 'v', 'k', 'j', 'x', 'z', 'q'
    )
    
    /**
     * Generate a new word grid using common letters.
     * Attempts to create a grid with solvable words.
     */
    suspend fun generateGrid(): WordGrid {
        // Generate random letters
        val letters = List(gridSize) { _ ->
            List(gridSize) { _ ->
                commonLetters.random().uppercaseChar()
            }
        }
        
        return WordGrid(size = gridSize, letters = letters)
    }
    
    /**
     * Validate a word formed from a path in the grid.
     * 
     * @param grid The word grid
     * @param path The path of positions forming the word
     * @return ValidationResult with success status and message
     */
    suspend fun validateWord(grid: WordGrid, path: List<GridPosition>): ValidationResult {
        // Check if path is valid (adjacent cells, no reuse)
        if (!grid.isValidPath(path)) {
            return ValidationResult(
                isValid = false,
                message = "Invalid path: cells must be adjacent"
            )
        }
        
        // Get the word from the path
        val word = grid.getWordFromPath(path).lowercase()
        
        // Check minimum length
        if (word.length < 3) {
            return ValidationResult(
                isValid = false,
                message = "Word must be at least 3 letters"
            )
        }
        
        // Check if word exists in dictionary
        if (!wordDictionary.isValidWord(word)) {
            return ValidationResult(
                isValid = false,
                message = "Not a valid word: $word"
            )
        }
        
        return ValidationResult(
            isValid = true,
            message = "Valid word: $word",
            word = word
        )
    }
    
    /**
     * Calculate score for a valid word.
     * Base: +10 points per word
     * Bonus: +5 points for each letter beyond 3
     */
    fun calculateScore(word: String): Int {
        val baseScore = 10
        val lengthBonus = (word.length - 3) * 5
        return baseScore + lengthBonus
    }
    
    /**
     * Find all possible valid words in the grid (for testing/hints).
     * This uses a depth-first search approach.
     */
    suspend fun findAllWords(grid: WordGrid): List<String> {
        val foundWords = mutableSetOf<String>()
        
        // Try starting from each position
        for (row in 0 until grid.size) {
            for (col in 0 until grid.size) {
                val startPos = GridPosition(row, col)
                findWordsFromPosition(grid, startPos, listOf(startPos), foundWords)
            }
        }
        
        return foundWords.toList().sorted()
    }
    
    /**
     * Recursive helper to find words starting from a position.
     */
    private suspend fun findWordsFromPosition(
        grid: WordGrid,
        currentPos: GridPosition,
        currentPath: List<GridPosition>,
        foundWords: MutableSet<String>
    ) {
        // Limit path length to avoid excessive recursion
        if (currentPath.size > 8) return
        
        val word = grid.getWordFromPath(currentPath).lowercase()
        
        // Check if this forms a valid word
        if (word.length >= 3 && wordDictionary.isValidWord(word)) {
            foundWords.add(word)
        }
        
        // Try extending to adjacent positions
        for (rowOffset in -1..1) {
            for (colOffset in -1..1) {
                if (rowOffset == 0 && colOffset == 0) continue
                
                val nextPos = GridPosition(
                    currentPos.row + rowOffset,
                    currentPos.col + colOffset
                )
                
                // Check if position is valid and not already in path
                if (grid.isValidPosition(nextPos) && !currentPath.contains(nextPos)) {
                    findWordsFromPosition(grid, nextPos, currentPath + nextPos, foundWords)
                }
            }
        }
    }
}

/**
 * Result of word validation.
 */
data class ValidationResult(
    val isValid: Boolean,
    val message: String,
    val word: String = ""
)
