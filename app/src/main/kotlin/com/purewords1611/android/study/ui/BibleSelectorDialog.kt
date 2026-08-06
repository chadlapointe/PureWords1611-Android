package com.purewords1611.android.study.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.purewords1611.android.study.data.ChapterIndexEntry
import com.purewords1611.android.study.data.OrthographyMode
import com.purewords1611.android.study.data.TestamentSection

@Composable
fun BibleSelectorDialog(
    chapters: List<ChapterIndexEntry>,
    orthographyMode: OrthographyMode,
    onChapterSelected: (String, Int) -> Unit,
    onDismiss: () -> Unit,
) {
    var selectedSection by remember { mutableStateOf(TestamentSection.OLD_TESTAMENT) }
    
    val filteredBooks = remember(selectedSection, chapters) {
        chapters.asSequence()
            .filter { it.section == selectedSection }
            .map { it.book }
            .distinct()
            .toList()
    }
    
    var selectedBook by remember(selectedSection, filteredBooks) { 
        mutableStateOf(filteredBooks.firstOrNull()) 
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f),
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
        ) {
            Column {
                val sections = TestamentSection.entries
                TabRow(
                    selectedTabIndex = selectedSection.ordinal,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                ) {
                    sections.forEach { section ->
                        Tab(
                            selected = selectedSection == section,
                            onClick = { selectedSection = section },
                            text = { 
                                Text(
                                    text = when(section) {
                                        TestamentSection.OLD_TESTAMENT -> "Old"
                                        TestamentSection.APOCRYPHA -> "Apoc"
                                        TestamentSection.NEW_TESTAMENT -> "New"
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                )
                            }
                        )
                    }
                }

                Row(modifier = Modifier.weight(1f).padding(8.dp)) {
                    // Books Column
                    LazyColumn(modifier = Modifier.weight(1.2f)) {
                        items(filteredBooks) { book ->
                            val bookOriginal = chapters.firstOrNull { it.book == book }?.bookOriginal
                            val bookName = if (orthographyMode == OrthographyMode.ORIGINAL_1611) {
                                bookOriginal ?: book
                            } else {
                                book
                            }
                            Text(
                                text = bookName,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Start,
                                color = if (selectedBook == book) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedBook = book }
                                    .padding(vertical = 12.dp, horizontal = 16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(1.dp).fillMaxHeight().background(MaterialTheme.colorScheme.outlineVariant))

                    // Chapters Column
                    val bookChapters = remember(selectedBook, chapters) {
                        chapters.filter { it.book == selectedBook }
                    }

                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(bookChapters) { chapter ->
                            val chapterName = if (orthographyMode == OrthographyMode.ORIGINAL_1611) {
                                "Chap. ${toRomanNumeral(chapter.chapter)}"
                            } else {
                                "Chapter ${chapter.chapter}"
                            }
                            Text(
                                text = chapterName,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onChapterSelected(chapter.book, chapter.chapter)
                                        onDismiss()
                                    }
                                    .padding(vertical = 12.dp)
                            )
                        }
                    }
                }
                
                HorizontalDivider()
                
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.padding(8.dp).fillMaxWidth()
                ) {
                    Text("Cancel")
                }
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

@Composable
private fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    androidx.compose.material3.TextButton(onClick = onClick, modifier = modifier, content = { content() })
}
