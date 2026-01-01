package com.purewords1611.android.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

/**
 * Repository for loading and parsing Bible verses from JSON asset file.
 */
class VerseRepository(private val context: Context) {
    
    private var verses: List<Verse> = emptyList()
    
    /**
     * Load verses from the JSON asset file.
     * @return List of Verse objects
     */
    suspend fun loadVerses(): List<Verse> = withContext(Dispatchers.IO) {
        if (verses.isEmpty()) {
            try {
                val json = context.assets.open("verses.json").bufferedReader().use { it.readText() }
                verses = parseVerses(json)
            } catch (e: java.io.IOException) {
                // Log error and return empty list if file cannot be read
                android.util.Log.e("VerseRepository", "Failed to load verses.json", e)
                verses = emptyList()
            } catch (e: org.json.JSONException) {
                // Log error and return empty list if JSON parsing fails
                android.util.Log.e("VerseRepository", "Failed to parse verses.json", e)
                verses = emptyList()
            }
        }
        verses
    }
    
    /**
     * Get a random verse from the loaded verses.
     * @return A random Verse or null if no verses are loaded
     */
    suspend fun getRandomVerse(): Verse? {
        val loadedVerses = loadVerses()
        return if (loadedVerses.isNotEmpty()) {
            loadedVerses.random()
        } else {
            null
        }
    }
    
    /**
     * Parse JSON string into list of Verse objects.
     */
    private fun parseVerses(json: String): List<Verse> {
        val verses = mutableListOf<Verse>()
        val jsonArray = JSONArray(json)
        
        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            verses.add(
                Verse(
                    reference = obj.getString("reference"),
                    text = obj.getString("text"),
                    blankedText = obj.getString("blankedText"),
                    missingWords = parseMissingWords(obj.getJSONArray("missingWords"))
                )
            )
        }
        
        return verses
    }
    
    /**
     * Parse JSON array of missing words into a list of strings.
     */
    private fun parseMissingWords(jsonArray: JSONArray): List<String> {
        val words = mutableListOf<String>()
        for (i in 0 until jsonArray.length()) {
            words.add(jsonArray.getString(i))
        }
        return words
    }
}
