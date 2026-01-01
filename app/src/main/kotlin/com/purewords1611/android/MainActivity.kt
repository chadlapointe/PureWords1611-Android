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
import com.purewords1611.android.analytics.AnalyticsManager
import com.purewords1611.android.data.VerseRepository
import com.purewords1611.android.data.WordDictionary
import com.purewords1611.android.ui.GameModeSelectionScreen
import com.purewords1611.android.ui.gameplay.GameplayScreen
import com.purewords1611.android.ui.wordgrid.WordGridGameScreen
import com.purewords1611.android.ui.wordmatching.WordMatchingGameScreen
import com.purewords1611.android.ui.theme.PureWords1611Theme
import com.purewords1611.android.viewmodel.GameViewModel
import com.purewords1611.android.viewmodel.GameViewModelFactory
import com.purewords1611.android.viewmodel.WordGridViewModel
import com.purewords1611.android.viewmodel.WordGridViewModelFactory
import com.purewords1611.android.viewmodel.WordMatchingViewModel

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
    WORD_GRID,
    WORD_MATCHING
}

@Composable
fun GameScreen() {
    val context = LocalContext.current
    val analyticsManager = remember { AnalyticsManager.getInstance(context) }
    var currentMode by remember { mutableStateOf(GameMode.MENU) }
    
    // Track screen views when mode changes
    LaunchedEffect(currentMode) {
        when (currentMode) {
            GameMode.MENU -> analyticsManager.trackScreenView("Menu")
            GameMode.VERSE_GAME -> analyticsManager.trackScreenView("VerseGame")
            GameMode.WORD_GRID -> analyticsManager.trackScreenView("WordGrid")
            GameMode.WORD_MATCHING -> analyticsManager.trackScreenView("WordMatching")
        }
    }
    
    when (currentMode) {
        GameMode.MENU -> {
            GameModeSelectionScreen(
                onVerseGameSelected = { 
                    analyticsManager.trackGameModeSelected("verse_game")
                    currentMode = GameMode.VERSE_GAME
                },
                onWordGridSelected = { 
                    analyticsManager.trackGameModeSelected("word_grid")
                    currentMode = GameMode.WORD_GRID
                },
                onWordMatchingSelected = {
                    analyticsManager.trackGameModeSelected("word_matching")
                    currentMode = GameMode.WORD_MATCHING
                }
            )
        }
        GameMode.VERSE_GAME -> {
            VerseGameScreen(
                onBackToMenu = { 
                    analyticsManager.trackReturnToMenu("VerseGame")
                    currentMode = GameMode.MENU
                }
            )
        }
        GameMode.WORD_GRID -> {
            WordGridScreen(
                onBackToMenu = { 
                    analyticsManager.trackReturnToMenu("WordGrid")
                    currentMode = GameMode.MENU
                }
            )
        }
        GameMode.WORD_MATCHING -> {
            WordMatchingScreen(
                onBackToMenu = {
                    analyticsManager.trackReturnToMenu("WordMatching")
                    currentMode = GameMode.MENU
                }
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

@Composable
fun WordMatchingScreen(onBackToMenu: () -> Unit) {
    val viewModel: WordMatchingViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Word Matching") },
                navigationIcon = {
                    TextButton(onClick = onBackToMenu) {
                        Text("← Menu")
                    }
                }
            )
        }
    ) { paddingValues ->
        WordMatchingGameScreen(
            uiState = uiState,
            onLeftWordClick = { id -> viewModel.selectLeftWord(id) },
            onRightWordClick = { id -> viewModel.selectRightWord(id) },
            onNextLevel = { viewModel.nextLevel() },
            onRetryLevel = { viewModel.retryLevel() },
            onResetGame = { viewModel.resetGame() },
            modifier = Modifier.padding(paddingValues)
        )
    }
}
