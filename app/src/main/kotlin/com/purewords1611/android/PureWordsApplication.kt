package com.purewords1611.android

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for PureWords1611
 * Handles app-wide initialization including Firebase and Analytics
 * 
 * @HiltAndroidApp triggers Hilt's code generation for dependency injection
 */
@HiltAndroidApp
class PureWordsApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
    }
}
