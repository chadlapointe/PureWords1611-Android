package com.purewords1611.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.purewords1611.android.data.VerseRepository
import com.purewords1611.android.ui.gameplay.GameplayScreen
import com.purewords1611.android.ui.theme.PureWords1611Theme
import com.purewords1611.android.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PureWords1611Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GameScreen()
                }
            }
        }
    }
}

@Composable
fun GameScreen() {
    // Create repository and ViewModel
    val repository = remember { VerseRepository(androidx.compose.ui.platform.LocalContext.current) }
    val viewModel = remember { GameViewModel(repository) }
    val uiState by viewModel.uiState.collectAsState()
    
    GameplayScreen(
        uiState = uiState,
        onInputChange = { index, text -> viewModel.updateInput(index, text) },
        onValidate = { viewModel.validateAnswer() },
        onContinue = { viewModel.continueGame() },
        onReset = { viewModel.resetGame() }
    )
}
