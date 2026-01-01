package com.purewords1611.android.data

import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Unit tests for WordGameEngine.
 */
class WordGameEngineTest {
    
    private lateinit var wordDictionary: WordDictionary
    private lateinit var engine: WordGameEngine
    
    @Before
    fun setup() {
        wordDictionary = mock(WordDictionary::class.java)
        engine = WordGameEngine(wordDictionary, gridSize = 4)
    }
    
    @Test
    fun `generateGrid creates correct size grid`() = runTest {
        val grid = engine.generateGrid()
        
        assertEquals(4, grid.size)
        assertEquals(4, grid.letters.size)
        assertEquals(4, grid.letters[0].size)
    }
    
    @Test
    fun `calculateScore base score for 3-letter word`() {
        val score = engine.calculateScore("cat")
        assertEquals(10, score) // Base score only
    }
    
    @Test
    fun `calculateScore adds bonus for longer words`() {
        val score4 = engine.calculateScore("word") // 4 letters
        assertEquals(15, score4) // 10 + 5
        
        val score5 = engine.calculateScore("bible") // 5 letters
        assertEquals(20, score5) // 10 + 10
        
        val score6 = engine.calculateScore("christ") // 6 letters
        assertEquals(25, score6) // 10 + 15
    }
    
    @Test
    fun `validateWord rejects path less than 3 letters`() = runTest {
        val grid = WordGrid(
            size = 4,
            letters = listOf(
                listOf('A', 'B', 'C', 'D'),
                listOf('E', 'F', 'G', 'H'),
                listOf('I', 'J', 'K', 'L'),
                listOf('M', 'N', 'O', 'P')
            )
        )
        
        val path = listOf(
            GridPosition(0, 0), // A
            GridPosition(0, 1)  // B
        )
        
        val result = engine.validateWord(grid, path)
        assertFalse(result.isValid)
        assertTrue(result.message.contains("at least 3 letters"))
    }
    
    @Test
    fun `validateWord rejects invalid path`() = runTest {
        val grid = WordGrid(
            size = 4,
            letters = listOf(
                listOf('A', 'B', 'C', 'D'),
                listOf('E', 'F', 'G', 'H'),
                listOf('I', 'J', 'K', 'L'),
                listOf('M', 'N', 'O', 'P')
            )
        )
        
        // Non-adjacent path
        val path = listOf(
            GridPosition(0, 0), // A
            GridPosition(0, 2), // C (not adjacent)
            GridPosition(0, 3)  // D
        )
        
        val result = engine.validateWord(grid, path)
        assertFalse(result.isValid)
        assertTrue(result.message.contains("adjacent"))
    }
    
    @Test
    fun `validateWord accepts valid word in dictionary`() = runTest {
        `when`(wordDictionary.isValidWord("cat")).thenReturn(true)
        
        val grid = WordGrid(
            size = 4,
            letters = listOf(
                listOf('C', 'A', 'T', 'D'),
                listOf('E', 'F', 'G', 'H'),
                listOf('I', 'J', 'K', 'L'),
                listOf('M', 'N', 'O', 'P')
            )
        )
        
        val path = listOf(
            GridPosition(0, 0), // C
            GridPosition(0, 1), // A
            GridPosition(0, 2)  // T
        )
        
        val result = engine.validateWord(grid, path)
        assertTrue(result.isValid)
        assertEquals("cat", result.word)
    }
    
    @Test
    fun `validateWord rejects word not in dictionary`() = runTest {
        `when`(wordDictionary.isValidWord("xyz")).thenReturn(false)
        
        val grid = WordGrid(
            size = 4,
            letters = listOf(
                listOf('X', 'Y', 'Z', 'D'),
                listOf('E', 'F', 'G', 'H'),
                listOf('I', 'J', 'K', 'L'),
                listOf('M', 'N', 'O', 'P')
            )
        )
        
        val path = listOf(
            GridPosition(0, 0), // X
            GridPosition(0, 1), // Y
            GridPosition(0, 2)  // Z
        )
        
        val result = engine.validateWord(grid, path)
        assertFalse(result.isValid)
        assertTrue(result.message.contains("Not a valid word"))
    }
}
