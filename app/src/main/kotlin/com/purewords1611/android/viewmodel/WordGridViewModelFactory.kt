package com.purewords1611.android.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.purewords1611.android.data.WordDictionary

/**
 * Factory for creating WordGridViewModel with dependencies.
 */
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
