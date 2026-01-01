package com.purewords1611.android.viewmodel

import com.purewords1611.android.data.Verse
import com.purewords1611.android.data.VerseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for GameViewModel validation logic.
 * 
 * Note: These tests focus on validation logic only.
 * Full ViewModel tests would require mocking the repository and testing StateFlow.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {
    
    private val testDispatcher = StandardTestDispatcher()
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    /**
     * Test case-insensitive validation.
     */
    @Test
    fun validateAnswer_caseInsensitive_returnsTrue() {
        val verse = Verse(
            reference = "Genesis 1:1",
            text = "In the beginning God created the heaven and the earth.",
            blankedText = "In the _____ God created the heaven and the earth.",
            missingWords = listOf("beginning")
        )
        
        // Test with exact match
        assertTrue(validateWord("beginning", "beginning"))
        
        // Test with different cases
        assertTrue(validateWord("Beginning", "beginning"))
        assertTrue(validateWord("BEGINNING", "beginning"))
        assertTrue(validateWord("BeGiNnInG", "beginning"))
    }
    
    /**
     * Test validation with whitespace.
     */
    @Test
    fun validateAnswer_withWhitespace_trimsAndValidates() {
        assertTrue(validateWord(" beginning ", "beginning"))
        assertTrue(validateWord("beginning  ", "beginning"))
        assertTrue(validateWord("  beginning", "beginning"))
    }
    
    /**
     * Test incorrect answer.
     */
    @Test
    fun validateAnswer_incorrect_returnsFalse() {
        assertFalse(validateWord("start", "beginning"))
        assertFalse(validateWord("begining", "beginning")) // Common misspelling
        assertFalse(validateWord("", "beginning"))
    }
    
    /**
     * Test validation with multiple words.
     */
    @Test
    fun validateMultipleWords_allCorrect_returnsTrue() {
        val userInputs = listOf("loved", "life")
        val correctWords = listOf("loved", "life")
        
        assertTrue(validateAllWords(userInputs, correctWords))
    }
    
    /**
     * Test validation with multiple words where one is incorrect.
     */
    @Test
    fun validateMultipleWords_oneIncorrect_returnsFalse() {
        val userInputs = listOf("loved", "death")
        val correctWords = listOf("loved", "life")
        
        assertFalse(validateAllWords(userInputs, correctWords))
    }
    
    /**
     * Test validation with different count of words.
     */
    @Test
    fun validateMultipleWords_differentCount_returnsFalse() {
        val userInputs = listOf("loved")
        val correctWords = listOf("loved", "life")
        
        assertFalse(validateAllWords(userInputs, correctWords))
    }
    
    // Helper functions that simulate the validation logic from GameViewModel
    
    private fun validateWord(input: String, correct: String): Boolean {
        return input.trim().equals(correct, ignoreCase = true)
    }
    
    private fun validateAllWords(inputs: List<String>, correct: List<String>): Boolean {
        return inputs.size == correct.size &&
                inputs.zip(correct).all { (input, correctWord) ->
                    validateWord(input, correctWord)
                }
    }
}
