package com.purewords1611.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.purewords1611.android.data.VerseRepository

/**
 * Factory for creating GameViewModel with required dependencies.
 * 
 * @deprecated Use Hilt dependency injection with @HiltViewModel instead.
 * This factory is maintained for backward compatibility but is no longer needed.
 */
@Deprecated(
    message = "Use Hilt dependency injection with @HiltViewModel and hiltViewModel() composable instead",
    replaceWith = ReplaceWith("hiltViewModel()", "androidx.hilt.navigation.compose.hiltViewModel")
)
class GameViewModelFactory(
    private val repository: VerseRepository
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
