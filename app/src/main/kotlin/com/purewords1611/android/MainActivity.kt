package com.purewords1611.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.purewords1611.android.analytics.AnalyticsManager
import com.purewords1611.android.study.ui.StudyAppRoot
import com.purewords1611.android.ui.theme.PureWords1611Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Main activity for PureWords1611 app
 * Uses Hilt for dependency injection
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var analyticsManager: AnalyticsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        analyticsManager.trackAppLaunch()

        setContent {
            PureWords1611Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StudyAppRoot(analyticsManager = analyticsManager)
                }
            }
        }
    }
}
