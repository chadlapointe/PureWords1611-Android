package com.purewords1611.android.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for managing KJV word dictionary.
 * Provides words for grid generation and validation.
 */
class WordDictionary(private val context: Context) {
    
    private var words: Set<String> = emptySet()
    
    /**
     * Load words from verses and create a dictionary of valid KJV words.
     * Filters words to be 3+ characters for gameplay.
     */
    suspend fun loadWords(): Set<String> = withContext(Dispatchers.IO) {
        if (words.isEmpty()) {
            try {
                val verseRepository = VerseRepository(context)
                val verses = verseRepository.loadVerses()
                
                // Extract all words from verses, normalize them
                val allWords = mutableSetOf<String>()
                verses.forEach { verse ->
                    // Parse words from the complete verse text
                    val verseWords = verse.text
                        .lowercase()
                        .replace(Regex("[^a-z ]"), "") // Remove punctuation
                        .split("\\s+".toRegex())
                        .filter { it.length >= 3 } // Only words 3+ characters
                    
                    allWords.addAll(verseWords)
                }
                
                words = allWords
                android.util.Log.d("WordDictionary", "Loaded ${words.size} unique words")
            } catch (e: Exception) {
                android.util.Log.e("WordDictionary", "Failed to load words", e)
                words = getDefaultWords()
            }
        }
        words
    }
    
    /**
     * Check if a word is valid in the KJV dictionary.
     */
    suspend fun isValidWord(word: String): Boolean {
        val dictionary = loadWords()
        return dictionary.contains(word.lowercase())
    }
    
    /**
     * Get a random word from the dictionary.
     */
    suspend fun getRandomWord(): String? {
        val dictionary = loadWords()
        return dictionary.randomOrNull()
    }
    
    /**
     * Get words of specific length.
     */
    suspend fun getWordsByLength(length: Int): List<String> {
        val dictionary = loadWords()
        return dictionary.filter { it.length == length }
    }
    
    /**
     * Default fallback word list for common KJV words.
     */
    private fun getDefaultWords(): Set<String> {
        return setOf(
            "god", "lord", "jesus", "christ", "heaven", "earth", "spirit",
            "love", "faith", "hope", "life", "peace", "word", "light",
            "truth", "way", "shepherd", "beginning", "grace", "mercy",
            "kingdom", "righteousness", "eternal", "salvation", "blessed",
            "holy", "strength", "power", "glory", "world", "soul", "heart"
        )
    }
}
