package com.purewords1611.android.data

import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for WordGrid.
 */
class WordGridTest {
    
    @Test
    fun `create 4x4 grid`() {
        val letters = listOf(
            listOf('A', 'B', 'C', 'D'),
            listOf('E', 'F', 'G', 'H'),
            listOf('I', 'J', 'K', 'L'),
            listOf('M', 'N', 'O', 'P')
        )
        
        val grid = WordGrid(size = 4, letters = letters)
        assertEquals(4, grid.size)
        assertEquals('A', grid.getLetterAt(GridPosition(0, 0)))
        assertEquals('P', grid.getLetterAt(GridPosition(3, 3)))
    }
    
    @Test
    fun `getLetterAt returns correct letter`() {
        val letters = listOf(
            listOf('A', 'B', 'C', 'D'),
            listOf('E', 'F', 'G', 'H'),
            listOf('I', 'J', 'K', 'L'),
            listOf('M', 'N', 'O', 'P')
        )
        val grid = WordGrid(size = 4, letters = letters)
        
        assertEquals('A', grid.getLetterAt(GridPosition(0, 0)))
        assertEquals('F', grid.getLetterAt(GridPosition(1, 1)))
        assertEquals('K', grid.getLetterAt(GridPosition(2, 2)))
        assertNull(grid.getLetterAt(GridPosition(4, 4)))
    }
    
    @Test
    fun `isValidPosition checks boundaries`() {
        val letters = listOf(
            listOf('A', 'B', 'C', 'D'),
            listOf('E', 'F', 'G', 'H'),
            listOf('I', 'J', 'K', 'L'),
            listOf('M', 'N', 'O', 'P')
        )
        val grid = WordGrid(size = 4, letters = letters)
        
        assertTrue(grid.isValidPosition(GridPosition(0, 0)))
        assertTrue(grid.isValidPosition(GridPosition(3, 3)))
        assertFalse(grid.isValidPosition(GridPosition(-1, 0)))
        assertFalse(grid.isValidPosition(GridPosition(0, 4)))
        assertFalse(grid.isValidPosition(GridPosition(4, 0)))
    }
    
    @Test
    fun `areAdjacent checks horizontal adjacency`() {
        val letters = listOf(
            listOf('A', 'B', 'C', 'D'),
            listOf('E', 'F', 'G', 'H'),
            listOf('I', 'J', 'K', 'L'),
            listOf('M', 'N', 'O', 'P')
        )
        val grid = WordGrid(size = 4, letters = letters)
        
        assertTrue(grid.areAdjacent(GridPosition(0, 0), GridPosition(0, 1)))
        assertTrue(grid.areAdjacent(GridPosition(0, 1), GridPosition(0, 0)))
    }
    
    @Test
    fun `areAdjacent checks vertical adjacency`() {
        val letters = listOf(
            listOf('A', 'B', 'C', 'D'),
            listOf('E', 'F', 'G', 'H'),
            listOf('I', 'J', 'K', 'L'),
            listOf('M', 'N', 'O', 'P')
        )
        val grid = WordGrid(size = 4, letters = letters)
        
        assertTrue(grid.areAdjacent(GridPosition(0, 0), GridPosition(1, 0)))
        assertTrue(grid.areAdjacent(GridPosition(1, 0), GridPosition(0, 0)))
    }
    
    @Test
    fun `areAdjacent checks diagonal adjacency`() {
        val letters = listOf(
            listOf('A', 'B', 'C', 'D'),
            listOf('E', 'F', 'G', 'H'),
            listOf('I', 'J', 'K', 'L'),
            listOf('M', 'N', 'O', 'P')
        )
        val grid = WordGrid(size = 4, letters = letters)
        
        assertTrue(grid.areAdjacent(GridPosition(0, 0), GridPosition(1, 1)))
        assertTrue(grid.areAdjacent(GridPosition(1, 1), GridPosition(0, 0)))
    }
    
    @Test
    fun `areAdjacent returns false for non-adjacent cells`() {
        val letters = listOf(
            listOf('A', 'B', 'C', 'D'),
            listOf('E', 'F', 'G', 'H'),
            listOf('I', 'J', 'K', 'L'),
            listOf('M', 'N', 'O', 'P')
        )
        val grid = WordGrid(size = 4, letters = letters)
        
        assertFalse(grid.areAdjacent(GridPosition(0, 0), GridPosition(0, 2)))
        assertFalse(grid.areAdjacent(GridPosition(0, 0), GridPosition(2, 0)))
        assertFalse(grid.areAdjacent(GridPosition(0, 0), GridPosition(2, 2)))
    }
    
    @Test
    fun `getWordFromPath creates word from positions`() {
        val letters = listOf(
            listOf('C', 'A', 'T', 'S'),
            listOf('D', 'O', 'G', 'S'),
            listOf('B', 'I', 'R', 'D'),
            listOf('F', 'I', 'S', 'H')
        )
        val grid = WordGrid(size = 4, letters = letters)
        
        val path = listOf(
            GridPosition(0, 0), // C
            GridPosition(0, 1), // A
            GridPosition(0, 2)  // T
        )
        
        assertEquals("CAT", grid.getWordFromPath(path))
    }
    
    @Test
    fun `isValidPath checks path validity`() {
        val letters = listOf(
            listOf('A', 'B', 'C', 'D'),
            listOf('E', 'F', 'G', 'H'),
            listOf('I', 'J', 'K', 'L'),
            listOf('M', 'N', 'O', 'P')
        )
        val grid = WordGrid(size = 4, letters = letters)
        
        // Valid path: A -> B -> C
        val validPath = listOf(
            GridPosition(0, 0),
            GridPosition(0, 1),
            GridPosition(0, 2)
        )
        assertTrue(grid.isValidPath(validPath))
        
        // Invalid: path too short
        val shortPath = listOf(GridPosition(0, 0))
        assertFalse(grid.isValidPath(shortPath))
        
        // Invalid: non-adjacent positions
        val nonAdjacentPath = listOf(
            GridPosition(0, 0),
            GridPosition(0, 2)
        )
        assertFalse(grid.isValidPath(nonAdjacentPath))
        
        // Invalid: duplicate positions
        val duplicatePath = listOf(
            GridPosition(0, 0),
            GridPosition(0, 1),
            GridPosition(0, 0)
        )
        assertFalse(grid.isValidPath(duplicatePath))
    }
}
