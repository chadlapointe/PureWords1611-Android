package com.purewords1611.android.study.ui

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.purewords1611.android.analytics.AnalyticsManager
import com.purewords1611.android.study.data.ChapterIndexEntry
import com.purewords1611.android.study.data.ExplanationDepth
import com.purewords1611.android.study.data.ExplanationEntry
import com.purewords1611.android.study.data.OrthographyMode
import com.purewords1611.android.study.data.SeekerStepEntry
import com.purewords1611.android.study.data.SeekerTrackEntry
import com.purewords1611.android.study.data.TestamentSection
import com.purewords1611.android.study.data.VerseText
import java.util.Locale

private enum class RootDestination(
    val label: String
) {
    READ("Read"),
    NOTES("Notes"),
    SEEKER_PATH("Seeker Path")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyAppRoot(
    analyticsManager: AnalyticsManager,
    viewModel: StudyViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var ttsReady by remember { mutableStateOf(false) }
    val textToSpeech = remember {
        TextToSpeech(context) { status ->
            ttsReady = status == TextToSpeech.SUCCESS
        }
    }
    DisposableEffect(Unit) {
        onDispose {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }
    var destination by rememberSaveable { mutableStateOf(RootDestination.READ) }

    LaunchedEffect(destination) {
        analyticsManager.trackScreenView("Study_${destination.name}")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pure Words 1611 Study") }
            )
        },
        bottomBar = {
            NavigationBar {
                RootDestination.entries.forEach { item ->
                    NavigationBarItem(
                        selected = destination == item,
                        onClick = { destination = item },
                        label = { Text(item.label) },
                        icon = { Text(item.label.take(1)) }
                    )
                }
            }
        }
    ) { padding ->
        when (destination) {
            RootDestination.READ -> ReadScreen(
                state = uiState,
                onQueryChanged = viewModel::updateQuery,
                onOrthographyChanged = viewModel::setOrthographyMode,
                onExplanationDepthChanged = viewModel::setExplanationDepth,
                onPreviousChapter = viewModel::goToPreviousChapter,
                onNextChapter = viewModel::goToNextChapter,
                onChapterSelected = viewModel::selectChapter,
                onVerseSelected = viewModel::selectVerse,
                onClearSelectedVerse = viewModel::clearSelectedVerse,
                onSpeakSelectedVerse = {
                    if (ttsReady && uiState.selectedVerseDisplayText != null) {
                        textToSpeech.language = Locale.US
                        textToSpeech.speak(
                            uiState.selectedVerseDisplayText,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            "selected_verse"
                        )
                    }
                },
                modifier = Modifier.padding(padding)
            )
            RootDestination.NOTES -> NotesScreen(
                state = uiState,
                onAddBookmark = viewModel::addBookmarkForSelectedVerse,
                onAddHighlight = viewModel::addHighlightForSelectedVerse,
                onSavePersonalNote = viewModel::savePersonalNoteForSelectedVerse,
                modifier = Modifier.padding(padding)
            )
            RootDestination.SEEKER_PATH -> SeekerPathScreen(
                tracks = uiState.seekerTracks,
                activeTrackId = uiState.activeSeekerTrackId,
                steps = uiState.seekerSteps,
                onSelectTrack = viewModel::selectSeekerTrack,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
private fun ReadScreen(
    state: StudyUiState,
    onQueryChanged: (String) -> Unit,
    onOrthographyChanged: (OrthographyMode) -> Unit,
    onExplanationDepthChanged: (ExplanationDepth) -> Unit,
    onPreviousChapter: () -> Unit,
    onNextChapter: () -> Unit,
    onChapterSelected: (String, Int) -> Unit,
    onVerseSelected: (Long) -> Unit,
    onClearSelectedVerse: () -> Unit,
    onSpeakSelectedVerse: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Reading & Study",
                style = MaterialTheme.typography.headlineSmall
            )
        }
        item {
            Text(
                text = "Includes Old Testament, Apocrypha, and New Testament structure.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        item {
            Text(
                text = "Apocrypha verses loaded: ${state.apocryphaVerseCount}",
                style = MaterialTheme.typography.labelLarge
            )
        }
        state.activeChapter?.let { chapter ->
            item {
                ChapterNavigationCard(
                    chapterLabel = "${chapter.book} ${chapter.chapter}",
                    onPreviousChapter = onPreviousChapter,
                    onNextChapter = onNextChapter
                )
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.query,
                    onValueChange = onQueryChanged,
                    label = { Text("Search pure text") },
                    modifier = Modifier.fillMaxWidth()
                )
                OrthographyToggle(
                    mode = state.orthographyMode,
                    onModeChanged = onOrthographyChanged
                )
                ExplanationDepthToggle(
                    depth = state.explanationDepth,
                    onDepthChanged = onExplanationDepthChanged
                )
            }
        }
        if (state.chapterIndex.isNotEmpty()) {
            item {
                ChapterJumpRow(
                    chapters = state.chapterIndex,
                    activeChapter = state.activeChapter,
                    onChapterSelected = onChapterSelected
                )
            }
        }
        if (state.selectedVerseId != null) {
            item {
                TextButton(onClick = onSpeakSelectedVerse) {
                    Text("Play Audio for Selected Verse")
                }
            }
            item {
                SelectedVerseNotesCard(
                    notes = state.selectedVerseNotes,
                    onClose = onClearSelectedVerse
                )
            }
            item {
                SelectedVerseExplanationsCard(
                    explanations = state.selectedVerseExplanations,
                    depth = state.explanationDepth
                )
            }
        }
        items(state.verses) { verse ->
            VerseCard(
                verse = verse,
                mode = state.orthographyMode,
                isSelected = state.selectedVerseId == verse.id,
                onClick = { onVerseSelected(verse.id) }
            )
        }
    }
}

@Composable
private fun ChapterNavigationCard(
    chapterLabel: String,
    onPreviousChapter: () -> Unit,
    onNextChapter: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Current Chapter",
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = chapterLabel,
                style = MaterialTheme.typography.titleMedium
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onPreviousChapter) {
                    Text("Previous")
                }
                TextButton(onClick = onNextChapter) {
                    Text("Next")
                }
            }
        }
    }
}

@Composable
private fun ChapterJumpRow(
    chapters: List<ChapterIndexEntry>,
    activeChapter: ChapterIndexEntry?,
    onChapterSelected: (String, Int) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "Jump to Chapter",
            style = MaterialTheme.typography.labelLarge
        )
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(chapters) { chapter ->
                AssistChip(
                    onClick = { onChapterSelected(chapter.book, chapter.chapter) },
                    label = {
                        val isActive = activeChapter?.book == chapter.book &&
                            activeChapter.chapter == chapter.chapter
                        Text(
                            text = if (isActive) {
                                "• ${chapter.book} ${chapter.chapter}"
                            } else {
                                "${chapter.book} ${chapter.chapter}"
                            }
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun ExplanationDepthToggle(
    depth: ExplanationDepth,
    onDepthChanged: (ExplanationDepth) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Explanation Depth", style = MaterialTheme.typography.labelLarge)
        FilterChip(
            selected = depth == ExplanationDepth.MINIMAL,
            onClick = { onDepthChanged(ExplanationDepth.MINIMAL) },
            label = { Text("Minimal") },
            colors = FilterChipDefaults.filterChipColors()
        )
        FilterChip(
            selected = depth == ExplanationDepth.HISTORICAL_LINGUISTIC,
            onClick = { onDepthChanged(ExplanationDepth.HISTORICAL_LINGUISTIC) },
            label = { Text("Historical & Linguistic") },
            colors = FilterChipDefaults.filterChipColors()
        )
        FilterChip(
            selected = depth == ExplanationDepth.HISTORICAL_LIGHT_DOCTRINAL,
            onClick = { onDepthChanged(ExplanationDepth.HISTORICAL_LIGHT_DOCTRINAL) },
            label = { Text("Historical + Light Doctrinal") },
            colors = FilterChipDefaults.filterChipColors()
        )
    }
}

@Composable
private fun OrthographyToggle(
    mode: OrthographyMode,
    onModeChanged: (OrthographyMode) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = "Orthography", style = MaterialTheme.typography.labelLarge)
        FilterChip(
            selected = mode == OrthographyMode.ORIGINAL_1611,
            onClick = { onModeChanged(OrthographyMode.ORIGINAL_1611) },
            label = { Text("Original 1611") },
            colors = FilterChipDefaults.filterChipColors()
        )
        FilterChip(
            selected = mode == OrthographyMode.MODERNIZED,
            onClick = { onModeChanged(OrthographyMode.MODERNIZED) },
            label = { Text("Modernized Spelling") },
            colors = FilterChipDefaults.filterChipColors()
        )
    }
}

@Composable
private fun VerseCard(
    verse: VerseText,
    mode: OrthographyMode,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val sectionLabel = when (verse.section) {
                TestamentSection.OLD_TESTAMENT -> "Old Testament"
                TestamentSection.APOCRYPHA -> "Apocrypha"
                TestamentSection.NEW_TESTAMENT -> "New Testament"
            }
            Text(
                text = "${verse.book} ${verse.chapter}:${verse.verse} • $sectionLabel",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = if (mode == OrthographyMode.ORIGINAL_1611) verse.originalText else verse.modernizedText,
                style = MaterialTheme.typography.bodyLarge
            )
            if (isSelected) {
                Text(
                    text = "Selected for notes, explanations, and study tools.",
                    style = MaterialTheme.typography.labelMedium
                )
            }
            if (verse.hasItalicWords) {
                Text(
                    text = "Contains italicized supplied words.",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Composable
private fun SelectedVerseNotesCard(
    notes: List<String>,
    onClose: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Marginal Notes",
                style = MaterialTheme.typography.titleMedium
            )
            if (notes.isEmpty()) {
                Text(
                    text = "No marginal notes are loaded for this verse yet.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                notes.forEachIndexed { index, note ->
                    Text(
                        text = "${index + 1}. $note",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            TextButton(onClick = onClose) {
                Text("Close")
            }
        }
    }
}

@Composable
private fun SelectedVerseExplanationsCard(
    explanations: List<ExplanationEntry>,
    depth: ExplanationDepth
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Explanations (${depth.name})",
                style = MaterialTheme.typography.titleMedium
            )
            if (explanations.isEmpty()) {
                Text(
                    text = "No explanations available for this verse and depth yet.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                explanations.forEach { explanation ->
                    Text(
                        text = explanation.contentMarkdown,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun NotesScreen(
    state: StudyUiState,
    onAddBookmark: () -> Unit,
    onAddHighlight: (String) -> Unit,
    onSavePersonalNote: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var noteDraft by rememberSaveable { mutableStateOf("") }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Study Tools", style = MaterialTheme.typography.headlineSmall)
        }
        item {
            Text(
                text = "Selected verse: ${state.selectedVerseId ?: "None selected in Read tab"}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onAddBookmark) {
                    Text("Add Bookmark")
                }
                TextButton(onClick = { onAddHighlight("yellow") }) {
                    Text("Add Highlight")
                }
            }
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = noteDraft,
                    onValueChange = { noteDraft = it },
                    label = { Text("Personal note for selected verse") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextButton(
                    onClick = {
                        onSavePersonalNote(noteDraft)
                        noteDraft = ""
                    }
                ) {
                    Text("Save Note")
                }
            }
        }
        item {
            Text("Bookmarks", style = MaterialTheme.typography.titleMedium)
        }
        items(state.bookmarks) { bookmark ->
            Text("Verse ID ${bookmark.verseId}", style = MaterialTheme.typography.bodyMedium)
        }
        item {
            Text("Personal Notes", style = MaterialTheme.typography.titleMedium)
        }
        items(state.personalNotes) { note ->
            Text(
                text = "Verse ID ${note.verseId}: ${note.note}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        item {
            Text("Highlights", style = MaterialTheme.typography.titleMedium)
        }
        items(state.highlights) { highlight ->
            Text(
                text = "Verse ID ${highlight.verseId}: ${highlight.colorName}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun SeekerPathScreen(
    tracks: List<SeekerTrackEntry>,
    activeTrackId: String?,
    steps: List<SeekerStepEntry>,
    onSelectTrack: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Seeker Path", style = MaterialTheme.typography.headlineSmall)
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(tracks) { track ->
                    FilterChip(
                        selected = activeTrackId == track.trackId,
                        onClick = { onSelectTrack(track.trackId) },
                        label = { Text(track.title) },
                        colors = FilterChipDefaults.filterChipColors()
                    )
                }
            }
        }
        item {
            val active = tracks.firstOrNull { it.trackId == activeTrackId }
            if (active != null) {
                Text(active.description, style = MaterialTheme.typography.bodyMedium)
            }
        }
        items(steps) { step ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Step ${step.sequence}: ${step.title}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = step.bodyMarkdown,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
