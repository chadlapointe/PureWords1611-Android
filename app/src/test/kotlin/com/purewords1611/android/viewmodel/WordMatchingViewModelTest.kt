package com.purewords1611.android.viewmodel

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for WordMatchingViewModel.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class WordMatchingViewModelTest {
    
    private lateinit var viewModel: WordMatchingViewModel
    private val testDispatcher = StandardTestDispatcher()
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = WordMatchingViewModel()
    }
    
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    
    @Test
    fun testInitialState() {
        val state = viewModel.uiState.value
        assertEquals(0, state.currentLevel)
        assertEquals(5, state.leftWords.size)
        assertEquals(5, state.rightWords.size)
        assertNull(state.selectedLeftId)
        assertNull(state.selectedRightId)
        assertTrue(state.completedMatches.isEmpty())
        assertEquals(0, state.score)
        assertEquals(0, state.mistakes)
        assertEquals(MatchingGameState.Playing, state.gameState)
    }
    
    @Test
    fun testSelectLeftWord() {
        val state = viewModel.uiState.value
        val firstLeftId = state.leftWords.first().id
        
        viewModel.selectLeftWord(firstLeftId)
        
        val newState = viewModel.uiState.value
        assertEquals(firstLeftId, newState.selectedLeftId)
        assertNull(newState.selectedRightId)
    }
    
    @Test
    fun testSelectLeftWord_toggle() {
        val state = viewModel.uiState.value
        val firstLeftId = state.leftWords.first().id
        
        // Select
        viewModel.selectLeftWord(firstLeftId)
        assertEquals(firstLeftId, viewModel.uiState.value.selectedLeftId)
        
        // Toggle off
        viewModel.selectLeftWord(firstLeftId)
        assertNull(viewModel.uiState.value.selectedLeftId)
    }
    
    @Test
    fun testSelectRightWord() {
        val state = viewModel.uiState.value
        val firstRightId = state.rightWords.first().id
        
        viewModel.selectRightWord(firstRightId)
        
        val newState = viewModel.uiState.value
        assertNull(newState.selectedLeftId)
        assertEquals(firstRightId, newState.selectedRightId)
    }
    
    @Test
    fun testSelectRightWord_toggle() {
        val state = viewModel.uiState.value
        val firstRightId = state.rightWords.first().id
        
        // Select
        viewModel.selectRightWord(firstRightId)
        assertEquals(firstRightId, viewModel.uiState.value.selectedRightId)
        
        // Toggle off
        viewModel.selectRightWord(firstRightId)
        assertNull(viewModel.uiState.value.selectedRightId)
    }
    
    @Test
    fun testValidMatch() {
        val state = viewModel.uiState.value
        
        // Find a valid pair: "joy" and "gladness" are in level 0
        val joyWord = state.leftWords.find { it.text == "joy" }
        val gladnessWord = state.rightWords.find { it.text == "gladness" }
        
        assertNotNull("joy should be in left words", joyWord)
        assertNotNull("gladness should be in right words", gladnessWord)
        
        if (joyWord != null && gladnessWord != null) {
            // Select both
            viewModel.selectLeftWord(joyWord.id)
            viewModel.selectRightWord(gladnessWord.id)
            
            val newState = viewModel.uiState.value
            
            // Should have completed the match
            assertEquals(1, newState.completedMatches.size)
            assertTrue(newState.completedMatches.contains(joyWord.id to gladnessWord.id))
            
            // Should clear selections
            assertNull(newState.selectedLeftId)
            assertNull(newState.selectedRightId)
            
            // Should update score
            assertTrue(newState.score > 0)
            
            // Should show success feedback
            assertTrue(newState.feedback.contains("Correct"))
        }
    }
    
    @Test
    fun testInvalidMatch() {
        val state = viewModel.uiState.value
        
        // Select two words that don't match
        val firstLeft = state.leftWords.first()
        val firstRight = state.rightWords.first()
        
        // Find if they're actually a match
        val leftWord = firstLeft.text
        val rightWord = firstRight.text
        
        // Find a pair that we know doesn't match
        val joyWord = state.leftWords.find { it.text == "joy" }
        val charityWord = state.rightWords.find { it.text == "charity" }
        
        if (joyWord != null && charityWord != null) {
            // "joy" pairs with "gladness", not "charity"
            viewModel.selectLeftWord(joyWord.id)
            viewModel.selectRightWord(charityWord.id)
            
            val newState = viewModel.uiState.value
            
            // Should not complete the match
            assertTrue(newState.completedMatches.isEmpty())
            
            // Should clear selections
            assertNull(newState.selectedLeftId)
            assertNull(newState.selectedRightId)
            
            // Should increment mistakes
            assertEquals(1, newState.mistakes)
            
            // Should show error feedback
            assertTrue(newState.feedback.contains("Not a match"))
        }
    }
    
    @Test
    fun testResetGame() {
        // Make some progress
        val state = viewModel.uiState.value
        val firstLeft = state.leftWords.first()
        viewModel.selectLeftWord(firstLeft.id)
        
        // Reset
        viewModel.resetGame()
        
        val newState = viewModel.uiState.value
        assertEquals(0, newState.currentLevel)
        assertNull(newState.selectedLeftId)
        assertNull(newState.selectedRightId)
        assertTrue(newState.completedMatches.isEmpty())
        assertEquals(0, newState.score)
        assertEquals(0, newState.mistakes)
    }
    
    @Test
    fun testCannotSelectAlreadyMatchedWord() {
        val state = viewModel.uiState.value
        
        // Find a valid pair and match them
        val joyWord = state.leftWords.find { it.text == "joy" }
        val gladnessWord = state.rightWords.find { it.text == "gladness" }
        
        if (joyWord != null && gladnessWord != null) {
            viewModel.selectLeftWord(joyWord.id)
            viewModel.selectRightWord(gladnessWord.id)
            
            // Try to select the matched word again
            viewModel.selectLeftWord(joyWord.id)
            
            val newState = viewModel.uiState.value
            // Should not select it (it's already matched)
            assertNull(newState.selectedLeftId)
        }
    }
    
    @Test
    fun testTotalLevels() {
        val state = viewModel.uiState.value
        assertEquals(5, state.totalLevels)
    }
}
