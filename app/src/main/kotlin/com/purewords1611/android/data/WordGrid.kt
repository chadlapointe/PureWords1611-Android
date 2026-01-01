package com.purewords1611.android.data

/**
 * Represents a position in the word grid.
 */
data class GridPosition(
    val row: Int,
    val col: Int
)

/**
 * Represents a word grid for the word search game.
 * Grid can be 4x4 or 5x5.
 */
data class WordGrid(
    val size: Int = 4,
    val letters: List<List<Char>>
) {
    init {
        require(letters.size == size) { "Grid must have $size rows" }
        require(letters.all { it.size == size }) { "All rows must have $size columns" }
    }
    
    /**
     * Get the letter at a specific position.
     */
    fun getLetterAt(position: GridPosition): Char? {
        return if (isValidPosition(position)) {
            letters[position.row][position.col]
        } else {
            null
        }
    }
    
    /**
     * Check if a position is valid in the grid.
     */
    fun isValidPosition(position: GridPosition): Boolean {
        return position.row in 0 until size && position.col in 0 until size
    }
    
    /**
     * Check if two positions are adjacent (horizontally, vertically, or diagonally).
     */
    fun areAdjacent(pos1: GridPosition, pos2: GridPosition): Boolean {
        val rowDiff = kotlin.math.abs(pos1.row - pos2.row)
        val colDiff = kotlin.math.abs(pos1.col - pos2.col)
        return rowDiff <= 1 && colDiff <= 1 && !(rowDiff == 0 && colDiff == 0)
    }
    
    /**
     * Get the word formed by a path of positions.
     */
    fun getWordFromPath(path: List<GridPosition>): String {
        return path.mapNotNull { getLetterAt(it) }.joinToString("")
    }
    
    /**
     * Validate that a path forms a valid chain (each position is adjacent to next).
     */
    fun isValidPath(path: List<GridPosition>): Boolean {
        if (path.size < 2) return false
        
        // Check all positions are unique
        if (path.toSet().size != path.size) return false
        
        // Check all positions are valid
        if (!path.all { isValidPosition(it) }) return false
        
        // Check all consecutive positions are adjacent
        for (i in 0 until path.size - 1) {
            if (!areAdjacent(path[i], path[i + 1])) {
                return false
            }
        }
        
        return true
    }
    
    /**
     * Convert grid to string representation for debugging.
     */
    override fun toString(): String {
        return letters.joinToString("\n") { row ->
            row.joinToString(" ") { it.toString() }
        }
    }
}
