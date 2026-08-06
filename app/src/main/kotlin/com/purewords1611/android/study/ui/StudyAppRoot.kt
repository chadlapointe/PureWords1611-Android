package com.purewords1611.android.study.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.purewords1611.android.analytics.AnalyticsManager
import com.purewords1611.android.study.data.ExplanationDepth
import com.purewords1611.android.study.data.ExplanationEntry
import com.purewords1611.android.study.data.MarginalNote
import com.purewords1611.android.study.data.OrthographyMode
import com.purewords1611.android.study.data.ReaderItem
import com.purewords1611.android.study.data.SeekerStepEntry
import com.purewords1611.android.study.data.SeekerTrackEntry
import com.purewords1611.android.study.data.TestamentSection
import com.purewords1611.android.study.data.TranslationMode
import com.purewords1611.android.study.data.VerseText
import java.util.Locale

private enum class RootDestination(
    val label: String,
) {
    READ("Read"),
    NOTES("Notes"),
    SEEKER_PATH("Seeker Path")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudyAppRoot(
    analyticsManager: AnalyticsManager,
    viewModel: StudyViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var destination by rememberSaveable { mutableStateOf(RootDestination.READ) }
    var showSettings by remember { mutableStateOf(false) }
    var showBibleSelector by remember { mutableStateOf(false) }
    var showStudyHub by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.selectedVerseId) {
        if (uiState.selectedVerseId != null) {
            showStudyHub = true
        }
    }

    LaunchedEffect(destination) {
        analyticsManager.trackScreenView("Study_${destination.name}")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.clickable { showBibleSelector = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val title = uiState.activeChapter?.let { chapter ->
                            val bookName = if (uiState.orthographyMode == OrthographyMode.ORIGINAL_1611) {
                                chapter.bookOriginal ?: chapter.book
                            } else {
                                chapter.book
                            }
                            val chapterDisplay = if (uiState.orthographyMode == OrthographyMode.ORIGINAL_1611) {
                                toRomanNumeral(chapter.chapter)
                            } else {
                                chapter.chapter.toString()
                            }
                            "$bookName $chapterDisplay"
                        } ?: "Select Chapter"
                        Text(
                            text = title,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                },
                actions = {
                    if (destination == RootDestination.READ) {
                        if (uiState.isReadingChapter) {
                            IconButton(onClick = viewModel::stopReading) {
                                Icon(Icons.Default.Clear, contentDescription = "Stop Reading")
                            }
                        } else {
                            IconButton(onClick = {
                                viewModel.readFullChapter()
                            }) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Read Chapter")
                            }
                        }
                        TextButton(onClick = viewModel::toggleTranslationMode) {
                            Text(if (uiState.translationMode == TranslationMode.KJV_1611) "1611" else "ESV")
                        }
                        IconButton(onClick = { showSettings = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Settings")
                        }
                    }
                }
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
        if (showBibleSelector) {
            BibleSelectorDialog(
                chapters = uiState.chapterIndex,
                orthographyMode = uiState.orthographyMode,
                onChapterSelected = viewModel::selectChapter,
                onDismiss = { showBibleSelector = false }
            )
        }

        if (showStudyHub) {
            ModalBottomSheet(
                onDismissRequest = {
                    showStudyHub = false
                    viewModel.clearSelectedVerse()
                },
                sheetState = rememberModalBottomSheetState()
            ) {
                StudyHubSheet(
                    state = uiState,
                    onSpeakSelectedVerse = viewModel::speakSelectedVerse,
                    onReadFromHere = {
                        if (uiState.selectedVerseId != null) {
                            viewModel.readFullChapter(uiState.selectedVerseId)
                            showStudyHub = false
                        }
                    },
                    onAddBookmark = viewModel::addBookmarkForSelectedVerse,
                    onAddHighlight = viewModel::addHighlightForSelectedVerse,
                    onSavePersonalNote = viewModel::savePersonalNoteForSelectedVerse,
                    onGoToChapter = { book, chapter ->
                        viewModel.updateQuery("")
                        viewModel.selectChapter(book, chapter)
                        showStudyHub = false
                    },
                    onClose = {
                        showStudyHub = false
                        viewModel.clearSelectedVerse()
                    }
                )
            }
        }

        if (showSettings) {
            ModalBottomSheet(
                onDismissRequest = { showSettings = false },
                sheetState = rememberModalBottomSheetState()
            ) {
                StudySettingsSheet(
                    state = uiState,
                    onQueryChanged = viewModel::updateQuery,
                    onOrthographyChanged = viewModel::setOrthographyMode,
                    onExplanationDepthChanged = viewModel::setExplanationDepth,
                    onClose = { showSettings = false }
                )
            }
        }

        when (destination) {
            RootDestination.READ -> ReadScreen(
                state = uiState,
                onVerseSelected = viewModel::selectVerse,
                onScrollHandled = viewModel::onScrollToVerseHandled,
                onUpdateActiveChapter = viewModel::updateActiveChapterFromScroll,
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
private fun StudySettingsSheet(
    state: StudyUiState,
    onQueryChanged: (String) -> Unit,
    onOrthographyChanged: (OrthographyMode) -> Unit,
    onExplanationDepthChanged: (ExplanationDepth) -> Unit,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Study Preferences", style = MaterialTheme.typography.titleLarge)
        
        OutlinedTextField(
            value = state.query,
            onValueChange = onQueryChanged,
            label = { Text("Search pure text") },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
        )

        OrthographyToggle(
            mode = state.orthographyMode,
            onModeChanged = onOrthographyChanged
        )

        ExplanationDepthToggle(
            depth = state.explanationDepth,
            onDepthChanged = onExplanationDepthChanged
        )

        Button(onClick = onClose, modifier = Modifier.align(Alignment.End)) {
            Text("Done")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ReadScreen(
    state: StudyUiState,
    onVerseSelected: (Long) -> Unit,
    onScrollHandled: () -> Unit,
    onUpdateActiveChapter: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LaunchedEffect(state.scrollToVerseId) {
        state.scrollToVerseId?.let { verseId ->
            val index = state.readerItems.indexOfFirst { 
                it is ReaderItem.VerseLine && it.verse.id == verseId 
            }
            if (index >= 0) {
                listState.animateScrollToItem(index)
                onScrollHandled()
            }
        }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { index ->
                if (index < state.readerItems.size) {
                    val item = state.readerItems[index]
                    if (item is ReaderItem.VerseLine) {
                        onUpdateActiveChapter(item.verse.book, item.verse.chapter)
                    } else if (item is ReaderItem.ChapterHeader) {
                        onUpdateActiveChapter(item.book, item.chapter)
                    }
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 80.dp),
    ) {
        if (state.readerItems.isEmpty()) {
            item {
                Box(
                    modifier = Modifier.fillParentMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Reading Pure Words...")
                        if (state.importProgress > 0f && state.importProgress < 1f) {
                            val percentage = (state.importProgress * 100).toInt()
                            Text(
                                text = "Initial indexing: $percentage%",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            androidx.compose.material3.LinearProgressIndicator(
                                progress = { state.importProgress },
                                modifier = Modifier.width(200.dp)
                            )
                        }
                        if (state.query.isNotBlank()) {
                            Text("No results found for \"${state.query}\"", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }

        state.readerItems.forEachIndexed { index, item ->
            when (item) {
                is ReaderItem.SectionHeader -> {
                    item(key = "section_${item.section.name}") {
                        SectionHeaderItem(section = item.section)
                    }
                }
                is ReaderItem.BookHeader -> {
                    stickyHeader(key = "book_${item.book}") {
                        BookHeaderItem(
                            book = item.book,
                            bookOriginal = item.bookOriginal,
                            orthographyMode = state.orthographyMode
                        )
                    }
                }
                is ReaderItem.ChapterHeader -> {
                    item(key = "chapter_${item.book}_${item.chapter}") {
                        ChapterHeaderItem(
                            chapter = item.chapter,
                            orthographyMode = state.orthographyMode
                        )
                    }
                }
                is ReaderItem.VerseLine -> {
                    item(key = "verse_${item.verse.id}") {
                        val isFirstInChapter = remember(state.readerItems, index) {
                            index > 0 && state.readerItems[index - 1] is ReaderItem.ChapterHeader
                        }
                        
                        if (isFirstInChapter && state.query.isBlank()) {
                            DropCapVerseLine(
                                verse = item.verse,
                                mode = state.orthographyMode,
                                translation = state.translationMode,
                                isHighlighted = state.highlightedVerseId == item.verse.id,
                                isSelected = state.selectedVerseId == item.verse.id,
                                onClick = { onVerseSelected(item.verse.id) }
                            )
                        } else {
                            VerseTextLine(
                                verse = item.verse,
                                mode = state.orthographyMode,
                                translation = state.translationMode,
                                isHighlighted = state.highlightedVerseId == item.verse.id,
                                isSelected = state.selectedVerseId == item.verse.id,
                                isSearchMode = state.query.isNotBlank(),
                                onClick = { onVerseSelected(item.verse.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeaderItem(section: TestamentSection) {
    val text = when (section) {
        TestamentSection.OLD_TESTAMENT -> "THE OLD TESTAMENT"
        TestamentSection.APOCRYPHA -> "THE APOCRYPHA"
        TestamentSection.NEW_TESTAMENT -> "THE NEW TESTAMENT"
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, bottom = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun BookHeaderItem(
    book: String,
    bookOriginal: String?,
    orthographyMode: OrthographyMode
) {
    val display = if (orthographyMode == OrthographyMode.ORIGINAL_1611) {
        bookOriginal ?: book
    } else {
        book
    }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
        tonalElevation = 2.dp
    ) {
        Text(
            text = display,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun ChapterHeaderItem(
    chapter: Int,
    orthographyMode: OrthographyMode
) {
    val chapterDisplay = if (orthographyMode == OrthographyMode.ORIGINAL_1611) {
        "CHAP. ${toRomanNumeral(chapter)}."
    } else {
        "Chapter $chapter"
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(modifier = Modifier.width(64.dp))
        Text(
            text = chapterDisplay,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
private fun VerseTextLine(
    verse: VerseText,
    mode: OrthographyMode,
    translation: TranslationMode,
    isHighlighted: Boolean,
    isSelected: Boolean,
    isSearchMode: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isHighlighted -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        isSelected -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
        else -> androidx.compose.ui.graphics.Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = verse.verse.toString(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(24.dp).padding(top = 4.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            if (isSearchMode) {
                Text(
                    text = "${verse.book} ${verse.chapter}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
            val rawText = if (translation == TranslationMode.ESV) {
                verse.comparativeText ?: verse.modernizedText
            } else {
                if (mode == OrthographyMode.ORIGINAL_1611) verse.originalText else verse.modernizedText
            }
            Text(
                text = parseItalicText(rawText),
                style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 28.sp)
            )
        }
    }
}

@Composable
private fun DropCapVerseLine(
    verse: VerseText,
    mode: OrthographyMode,
    translation: TranslationMode,
    isHighlighted: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isHighlighted -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        isSelected -> MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
        else -> androidx.compose.ui.graphics.Color.Transparent
    }

    val rawText = if (translation == TranslationMode.ESV) {
        verse.comparativeText ?: verse.modernizedText
    } else {
        if (mode == OrthographyMode.ORIGINAL_1611) verse.originalText else verse.modernizedText
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = verse.verse.toString(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(24.dp).padding(top = 8.dp)
        )
        
        if (rawText.isNotEmpty()) {
            val dropCap = rawText.take(1)
            val remainingText = rawText.drop(1)
            
            Row(modifier = Modifier.weight(1f)) {
                Text(
                    text = dropCap,
                    style = MaterialTheme.typography.displayLarge.copy(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = parseItalicText(remainingText),
                    style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 28.sp),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

private fun toRomanNumeral(number: Int): String {
    val romanNumerals = listOf(
        1000 to "M", 900 to "CM", 500 to "D", 400 to "CD",
        100 to "C", 90 to "XC", 50 to "L", 40 to "XL",
        10 to "X", 9 to "IX", 5 to "V", 4 to "IV", 1 to "I"
    )
    var n = number
    val result = StringBuilder()
    for ((value, numeral) in romanNumerals) {
        while (n >= value) {
            result.append(numeral)
            n -= value
        }
    }
    return result.toString()
}

private fun parseItalicText(text: String): AnnotatedString {
    return buildAnnotatedString {
        val parts = text.split("_")
        parts.forEachIndexed { index, part ->
            if (index % 2 == 1) {
                withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                    append(part)
                }
            } else {
                append(part)
            }
        }
    }
}

@Composable
private fun StudyHubSheet(
    state: StudyUiState,
    onSpeakSelectedVerse: () -> Unit,
    onReadFromHere: () -> Unit,
    onAddBookmark: () -> Unit,
    onAddHighlight: (String) -> Unit,
    onSavePersonalNote: (String) -> Unit,
    onGoToChapter: (String, Int) -> Unit,
    onClose: () -> Unit
) {
    var noteDraft by rememberSaveable { mutableStateOf("") }
    val selectedVerse = remember(state.verses, state.selectedVerseId) {
        state.verses.firstOrNull { it.id == state.selectedVerseId }
    }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Study Hub", style = MaterialTheme.typography.headlineSmall)
                selectedVerse?.let { 
                    Text(
                        text = "${it.book} ${it.chapter}:${it.verse}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            IconButton(onClick = onClose) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = onSpeakSelectedVerse, modifier = Modifier.weight(1f)) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Listen")
            }
            Button(onClick = onReadFromHere, modifier = Modifier.weight(1f)) {
                Text("Read from here")
            }
            if (state.query.isNotBlank()) {
                Button(
                    onClick = { 
                        selectedVerse?.let { onGoToChapter(it.book, it.chapter) }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Go to Chapter")
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AssistChip(onClick = onAddBookmark, label = { Text("Bookmark") })
            AssistChip(onClick = { onAddHighlight("yellow") }, label = { Text("Highlight") })
        }

        HorizontalDivider()

        Text("Marginal Notes", style = MaterialTheme.typography.titleMedium)
        if (state.selectedVerseNotes.isEmpty()) {
            Text("No marginal notes for this verse.", style = MaterialTheme.typography.bodyMedium)
        } else {
            state.selectedVerseNotes.forEach { note ->
                Text("• $note", style = MaterialTheme.typography.bodyMedium)
            }
        }

        HorizontalDivider()

        Text("Explanations", style = MaterialTheme.typography.titleMedium)
        if (state.selectedVerseExplanations.isEmpty()) {
            Text("No explanations for this depth.", style = MaterialTheme.typography.bodyMedium)
        } else {
            state.selectedVerseExplanations.forEach { exp ->
                Text(exp.contentMarkdown, style = MaterialTheme.typography.bodyMedium)
            }
        }

        HorizontalDivider()

        Text("Personal Note", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = noteDraft,
            onValueChange = { noteDraft = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Add your thoughts...") }
        )
        Button(
            onClick = {
                onSavePersonalNote(noteDraft)
                noteDraft = ""
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save Note")
        }

        Spacer(modifier = Modifier.height(32.dp))
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
            tracks.firstOrNull { it.trackId == activeTrackId }?.let { active ->
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
