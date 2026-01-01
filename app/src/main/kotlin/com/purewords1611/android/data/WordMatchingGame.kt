package com.purewords1611.android.data

/**
 * Represents a pair of related words that should be matched.
 * @param leftWord The word on the left side
 * @param rightWord The matching word on the right side
 * @param category The category or relationship (e.g., "Synonyms", "Antonyms", "Categories")
 */
data class WordPair(
    val leftWord: String,
    val rightWord: String,
    val category: String
)

/**
 * Represents a word that can be matched in the UI.
 * @param text The word text
 * @param id Unique identifier for this word instance
 */
data class MatchableWord(
    val text: String,
    val id: Int
)

/**
 * Represents the current state of a match attempt.
 */
data class MatchState(
    val selectedLeftId: Int? = null,
    val selectedRightId: Int? = null,
    val completedMatches: Set<Pair<Int, Int>> = emptySet()
)

/**
 * Engine for managing word matching game mechanics.
 * Handles level generation, match validation, and scoring.
 */
class WordMatchingEngine {
    
    companion object {
        private const val BASE_SCORE = 10
        private const val PERFECT_MATCH_BONUS = 50
        
        /**
         * Pre-defined levels with word pairs from biblical context.
         * Using simple synonyms and related words suitable for all audiences.
         */
        private val LEVELS = listOf(
            // Level 1: Basic Synonyms
            listOf(
                WordPair("joy", "gladness", "Synonyms"),
                WordPair("love", "charity", "Synonyms"),
                WordPair("faith", "trust", "Synonyms"),
                WordPair("peace", "rest", "Synonyms"),
                WordPair("grace", "mercy", "Synonyms")
            ),
            // Level 2: Biblical Pairs
            listOf(
                WordPair("light", "darkness", "Opposites"),
                WordPair("heaven", "earth", "Opposites"),
                WordPair("good", "evil", "Opposites"),
                WordPair("life", "death", "Opposites"),
                WordPair("strength", "weakness", "Opposites")
            ),
            // Level 3: Related Words
            listOf(
                WordPair("prayer", "supplication", "Related"),
                WordPair("wisdom", "understanding", "Related"),
                WordPair("righteousness", "holiness", "Related"),
                WordPair("blessing", "favor", "Related"),
                WordPair("glory", "honor", "Related")
            ),
            // Level 4: More Synonyms
            listOf(
                WordPair("word", "saying", "Synonyms"),
                WordPair("truth", "verity", "Synonyms"),
                WordPair("hope", "expectation", "Synonyms"),
                WordPair("power", "might", "Synonyms"),
                WordPair("salvation", "deliverance", "Synonyms")
            ),
            // Level 5: Advanced
            listOf(
                WordPair("covenant", "testament", "Synonyms"),
                WordPair("repentance", "contrition", "Related"),
                WordPair("praise", "worship", "Related"),
                WordPair("kingdom", "dominion", "Synonyms"),
                WordPair("eternal", "everlasting", "Synonyms")
            )
        )
    }
    
    /**
     * Get the word pairs for a specific level.
     * @param level The level number (0-indexed)
     * @return List of word pairs for that level, or empty list if invalid level
     */
    fun getLevelPairs(level: Int): List<WordPair> {
        return if (level in LEVELS.indices) {
            LEVELS[level]
        } else {
            emptyList()
        }
    }
    
    /**
     * Get the total number of levels available.
     */
    fun getTotalLevels(): Int = LEVELS.size
    
    /**
     * Check if two words form a valid match.
     * @param leftWord The word from the left side
     * @param rightWord The word from the right side
     * @param pairs The valid pairs for the current level
     * @return true if the match is valid
     */
    fun isValidMatch(leftWord: String, rightWord: String, pairs: List<WordPair>): Boolean {
        return pairs.any { 
            (it.leftWord.equals(leftWord, ignoreCase = true) && 
             it.rightWord.equals(rightWord, ignoreCase = true))
        }
    }
    
    /**
     * Calculate score for completed matches.
     * @param matchCount Number of correct matches
     * @param totalPairs Total pairs in the level
     * @param mistakes Number of incorrect attempts
     * @return Calculated score
     */
    fun calculateScore(matchCount: Int, totalPairs: Int, mistakes: Int): Int {
        val basePoints = matchCount * BASE_SCORE
        val perfectBonus = if (matchCount == totalPairs && mistakes == 0) PERFECT_MATCH_BONUS else 0
        val mistakePenalty = mistakes * 2
        return maxOf(0, basePoints + perfectBonus - mistakePenalty)
    }
}
