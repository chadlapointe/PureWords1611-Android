package com.purewords1611.android.data

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for the Verse data class.
 */
class VerseTest {
    
    @Test
    fun verse_createsCorrectly() {
        val verse = Verse(
            reference = "Genesis 1:1",
            text = "In the beginning God created the heaven and the earth.",
            blankedText = "In the _____ God created the heaven and the earth.",
            missingWords = listOf("beginning")
        )
        
        assertEquals("Genesis 1:1", verse.reference)
        assertEquals("In the beginning God created the heaven and the earth.", verse.text)
        assertEquals("In the _____ God created the heaven and the earth.", verse.blankedText)
        assertEquals(1, verse.missingWords.size)
        assertEquals("beginning", verse.missingWords[0])
    }
    
    @Test
    fun verse_withMultipleMissingWords() {
        val verse = Verse(
            reference = "John 3:16",
            text = "For God so loved the world...",
            blankedText = "For God so _____ the _____...",
            missingWords = listOf("loved", "world")
        )
        
        assertEquals(2, verse.missingWords.size)
        assertEquals("loved", verse.missingWords[0])
        assertEquals("world", verse.missingWords[1])
    }
}
