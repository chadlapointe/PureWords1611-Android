package com.purewords1611.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.purewords1611.android.data.WordDictionary

/**
 * Factory for creating WordGridViewModel with dependencies.
 * 
 * @deprecated Use Hilt dependency injection with @HiltViewModel instead.
 * This factory is maintained for backward compatibility but is no longer needed.
 */
@Deprecated(
    message = "Use Hilt dependency injection with @HiltViewModel and hiltViewModel() composable instead",
    replaceWith = ReplaceWith("hiltViewModel()", "androidx.hilt.navigation.compose.hiltViewModel")
)
class WordGridViewModelFactory(
    private val wordDictionary: WordDictionary
) : ViewModelProvider.Factory {
    
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordGridViewModel::class.java)) {
            return WordGridViewModel(wordDictionary) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
