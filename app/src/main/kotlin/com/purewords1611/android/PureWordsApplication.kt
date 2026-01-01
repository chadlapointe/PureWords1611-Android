package com.purewords1611.android

import android.app.Application
import com.google.firebase.FirebaseApp
import com.purewords1611.android.analytics.AnalyticsManager

/**
 * Application class for PureWords1611
 * Handles app-wide initialization including Firebase and Analytics
 */
class PureWordsApplication : Application() {
    
    private lateinit var analyticsManager: AnalyticsManager
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        
        // Initialize Analytics Manager
        analyticsManager = AnalyticsManager.getInstance(this)
        
        // Track app launch
        analyticsManager.trackAppLaunch()
    }
    
    /**
     * Get the analytics manager instance
     */
    fun getAnalyticsManager(): AnalyticsManager {
        return analyticsManager
    }
}
