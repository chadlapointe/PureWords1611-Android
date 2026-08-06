package com.purewords1611.android.study.data.importer

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudyDataImporter @Inject constructor() {
    private val _importProgress = MutableStateFlow(1.0f)
    val importProgress: StateFlow<Float> = _importProgress.asStateFlow()

    fun importIfEmpty() {
        // Legacy importer disabled. Database is now pre-populated from assets.
        android.util.Log.i("StudyDataImporter", "Importer skipped. Using pre-populated database.")
    }
}
