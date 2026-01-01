package com.purewords1611.android.data

/**
 * Data class representing a Bible verse from KJV 1611.
 *
 * @property reference The verse reference (e.g., "Genesis 1:1")
 * @property text The complete verse text
 * @property blankedText The verse with blanks for the game (e.g., "In the _____ God created...")
 * @property missingWords List of words that should fill the blanks
 */
data class Verse(
    val reference: String,
    val text: String,
    val blankedText: String,
    val missingWords: List<String>
)
