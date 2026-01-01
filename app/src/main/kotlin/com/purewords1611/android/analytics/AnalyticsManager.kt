package com.purewords1611.android.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

/**
 * Centralized analytics manager for tracking user interactions and events.
 * Wraps Firebase Analytics to provide a clean, testable interface.
 */
class AnalyticsManager private constructor(context: Context) {
    
    private val firebaseAnalytics: FirebaseAnalytics = Firebase.analytics
    
    companion object {
        @Volatile
        private var instance: AnalyticsManager? = null
        
        fun getInstance(context: Context): AnalyticsManager {
            return instance ?: synchronized(this) {
                instance ?: AnalyticsManager(context.applicationContext).also { instance = it }
            }
        }
    }
    
    /**
     * Track when user launches the app
     */
    fun trackAppLaunch() {
        logEvent("app_launch", null)
    }
    
    /**
     * Track screen views
     */
    fun trackScreenView(screenName: String) {
        val params = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenName)
        }
        logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
    }
    
    /**
     * Track game mode selection
     */
    fun trackGameModeSelected(gameMode: String) {
        val params = Bundle().apply {
            putString("game_mode", gameMode)
        }
        logEvent("game_mode_selected", params)
    }
    
    /**
     * Track when a game starts
     */
    fun trackGameStart(gameMode: String) {
        val params = Bundle().apply {
            putString("game_mode", gameMode)
        }
        logEvent("game_start", params)
    }
    
    /**
     * Track when a game completes
     */
    fun trackGameComplete(gameMode: String, score: Int? = null, duration: Long? = null) {
        val params = Bundle().apply {
            putString("game_mode", gameMode)
            score?.let { putInt("score", it) }
            duration?.let { putLong("duration_ms", it) }
        }
        logEvent("game_complete", params)
    }
    
    /**
     * Track word submissions in Word Grid game
     */
    fun trackWordSubmitted(word: String, isValid: Boolean, score: Int) {
        val params = Bundle().apply {
            putString("word", word)
            putBoolean("is_valid", isValid)
            putInt("score", score)
            putInt("word_length", word.length)
        }
        logEvent("word_submitted", params)
    }
    
    /**
     * Track verse game answer validation
     */
    fun trackVerseAnswerValidated(isCorrect: Boolean, attemptNumber: Int) {
        val params = Bundle().apply {
            putBoolean("is_correct", isCorrect)
            putInt("attempt_number", attemptNumber)
        }
        logEvent("verse_answer_validated", params)
    }
    
    /**
     * Track when user returns to menu
     */
    fun trackReturnToMenu(fromScreen: String) {
        val params = Bundle().apply {
            putString("from_screen", fromScreen)
        }
        logEvent("return_to_menu", params)
    }
    
    /**
     * Track when user resets a game
     */
    fun trackGameReset(gameMode: String) {
        val params = Bundle().apply {
            putString("game_mode", gameMode)
        }
        logEvent("game_reset", params)
    }
    
    /**
     * Internal method to log events with error handling
     */
    private fun logEvent(eventName: String, params: Bundle?) {
        try {
            firebaseAnalytics.logEvent(eventName, params)
        } catch (e: Exception) {
            // Silent fail - analytics should never crash the app
            e.printStackTrace()
        }
    }
    
    /**
     * Set user property (optional, for segmentation)
     */
    fun setUserProperty(propertyName: String, value: String) {
        try {
            firebaseAnalytics.setUserProperty(propertyName, value)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
