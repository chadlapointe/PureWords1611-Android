package com.purewords1611.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.purewords1611.android.data.VerseRepository
import com.purewords1611.android.data.WordDictionary
import com.purewords1611.android.ui.GameModeSelectionScreen
import com.purewords1611.android.ui.gameplay.GameplayScreen
import com.purewords1611.android.ui.wordgrid.WordGridGameScreen
import com.purewords1611.android.ui.theme.PureWords1611Theme
import com.purewords1611.android.viewmodel.GameViewModel
import com.purewords1611.android.viewmodel.GameViewModelFactory
import com.purewords1611.android.viewmodel.WordGridViewModel
import com.purewords1611.android.viewmodel.WordGridViewModelFactory

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

enum class GameMode {
    MENU,
    VERSE_GAME,
    WORD_GRID
}

@Composable
fun GameScreen() {
    var currentMode by remember { mutableStateOf(GameMode.MENU) }
    
    when (currentMode) {
        GameMode.MENU -> {
            GameModeSelectionScreen(
                onVerseGameSelected = { currentMode = GameMode.VERSE_GAME },
                onWordGridSelected = { currentMode = GameMode.WORD_GRID }
            )
        }
        GameMode.VERSE_GAME -> {
            VerseGameScreen(
                onBackToMenu = { currentMode = GameMode.MENU }
            )
        }
        GameMode.WORD_GRID -> {
            WordGridScreen(
                onBackToMenu = { currentMode = GameMode.MENU }
            )
        }
    }
}

@Composable
fun VerseGameScreen(onBackToMenu: () -> Unit) {
    val context = LocalContext.current
    val repository = remember { VerseRepository(context) }
    
    val viewModel: GameViewModel = viewModel(
        factory = GameViewModelFactory(repository)
    )
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verse Challenge") },
                navigationIcon = {
                    TextButton(onClick = onBackToMenu) {
                        Text("← Menu")
                    }
                }
            )
        }
    ) { paddingValues ->
        GameplayScreen(
            uiState = uiState,
            onInputChange = { index, text -> viewModel.updateInput(index, text) },
            onValidate = { viewModel.validateAnswer() },
            onContinue = { viewModel.continueGame() },
            onReset = { viewModel.resetGame() },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun WordGridScreen(onBackToMenu: () -> Unit) {
    val context = LocalContext.current
    val wordDictionary = remember { WordDictionary(context) }
    
    val viewModel: WordGridViewModel = viewModel(
        factory = WordGridViewModelFactory(wordDictionary)
    )
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Word Grid") },
                navigationIcon = {
                    TextButton(onClick = onBackToMenu) {
                        Text("← Menu")
                    }
                }
            )
        }
    ) { paddingValues ->
        WordGridGameScreen(
            uiState = uiState,
            onCellClick = { position -> viewModel.addToPath(position) },
            onSubmitWord = { viewModel.submitWord() },
            onClearPath = { viewModel.clearPath() },
            onReset = { viewModel.resetGame() },
            modifier = Modifier.padding(paddingValues)
        )
    }
}
