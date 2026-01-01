package com.purewords1611.android.data

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for WordMatchingEngine.
 */
class WordMatchingEngineTest {
    
    private lateinit var engine: WordMatchingEngine
    
    @Before
    fun setup() {
        engine = WordMatchingEngine()
    }
    
    @Test
    fun testGetLevelPairs_validLevel() {
        val level0Pairs = engine.getLevelPairs(0)
        assertNotNull(level0Pairs)
        assertEquals(5, level0Pairs.size)
        assertTrue(level0Pairs.any { it.leftWord == "joy" && it.rightWord == "gladness" })
    }
    
    @Test
    fun testGetLevelPairs_invalidLevel() {
        val invalidPairs = engine.getLevelPairs(-1)
        assertTrue(invalidPairs.isEmpty())
        
        val tooHighPairs = engine.getLevelPairs(100)
        assertTrue(tooHighPairs.isEmpty())
    }
    
    @Test
    fun testGetTotalLevels() {
        val totalLevels = engine.getTotalLevels()
        assertEquals(5, totalLevels)
    }
    
    @Test
    fun testIsValidMatch_correctPair() {
        val pairs = engine.getLevelPairs(0)
        assertTrue(engine.isValidMatch("joy", "gladness", pairs))
        assertTrue(engine.isValidMatch("love", "charity", pairs))
        assertTrue(engine.isValidMatch("faith", "trust", pairs))
    }
    
    @Test
    fun testIsValidMatch_caseInsensitive() {
        val pairs = engine.getLevelPairs(0)
        assertTrue(engine.isValidMatch("JOY", "GLADNESS", pairs))
        assertTrue(engine.isValidMatch("Joy", "Gladness", pairs))
        assertTrue(engine.isValidMatch("jOy", "gLaDnEsS", pairs))
    }
    
    @Test
    fun testIsValidMatch_incorrectPair() {
        val pairs = engine.getLevelPairs(0)
        assertFalse(engine.isValidMatch("joy", "charity", pairs))
        assertFalse(engine.isValidMatch("love", "gladness", pairs))
        assertFalse(engine.isValidMatch("invalid", "word", pairs))
    }
    
    @Test
    fun testCalculateScore_singleMatch() {
        val score = engine.calculateScore(1, 5, 0)
        assertEquals(10, score)
    }
    
    @Test
    fun testCalculateScore_multipleMatches() {
        val score = engine.calculateScore(3, 5, 0)
        assertEquals(30, score)
    }
    
    @Test
    fun testCalculateScore_perfectMatch() {
        // All matches, no mistakes = +50 bonus
        val score = engine.calculateScore(5, 5, 0)
        assertEquals(100, score) // 50 base + 50 bonus
    }
    
    @Test
    fun testCalculateScore_withMistakes() {
        // 3 matches, 2 mistakes = 30 - 4 = 26
        val score = engine.calculateScore(3, 5, 2)
        assertEquals(26, score)
    }
    
    @Test
    fun testCalculateScore_neverNegative() {
        // Even with many mistakes, score shouldn't go negative
        val score = engine.calculateScore(1, 5, 100)
        assertEquals(0, score)
    }
    
    @Test
    fun testWordPair_dataClass() {
        val pair = WordPair("test1", "test2", "TestCategory")
        assertEquals("test1", pair.leftWord)
        assertEquals("test2", pair.rightWord)
        assertEquals("TestCategory", pair.category)
    }
    
    @Test
    fun testMatchableWord_dataClass() {
        val word = MatchableWord("testword", 42)
        assertEquals("testword", word.text)
        assertEquals(42, word.id)
    }
    
    @Test
    fun testMatchState_dataClass() {
        val state = MatchState(
            selectedLeftId = 1,
            selectedRightId = 2,
            completedMatches = setOf(3 to 4, 5 to 6)
        )
        assertEquals(1, state.selectedLeftId)
        assertEquals(2, state.selectedRightId)
        assertEquals(2, state.completedMatches.size)
    }
    
    @Test
    fun testAllLevelsHaveFivePairs() {
        for (i in 0 until engine.getTotalLevels()) {
            val pairs = engine.getLevelPairs(i)
            assertEquals("Level $i should have 5 pairs", 5, pairs.size)
        }
    }
    
    @Test
    fun testAllLevelsHaveUniqueLeftWords() {
        for (i in 0 until engine.getTotalLevels()) {
            val pairs = engine.getLevelPairs(i)
            val leftWords = pairs.map { it.leftWord }
            val uniqueLeftWords = leftWords.toSet()
            assertEquals("Level $i should have unique left words", 
                leftWords.size, uniqueLeftWords.size)
        }
    }
    
    @Test
    fun testAllLevelsHaveUniqueRightWords() {
        for (i in 0 until engine.getTotalLevels()) {
            val pairs = engine.getLevelPairs(i)
            val rightWords = pairs.map { it.rightWord }
            val uniqueRightWords = rightWords.toSet()
            assertEquals("Level $i should have unique right words", 
                rightWords.size, uniqueRightWords.size)
        }
    }
}
