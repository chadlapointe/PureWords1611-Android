package com.purewords1611.android.study.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.purewords1611.android.study.data.TestamentSection

@Database(
    entities = [
        VerseEntity::class,
        MarginalNoteEntity::class,
        BookmarkEntity::class,
        HighlightEntity::class,
        PersonalNoteEntity::class,
        ExplanationEntity::class,
        ReadingPreferenceEntity::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(StudyTypeConverters::class)
abstract class StudyDatabase : RoomDatabase() {
    abstract fun verseDao(): VerseDao
    abstract fun marginalNoteDao(): MarginalNoteDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun highlightDao(): HighlightDao
    abstract fun personalNoteDao(): PersonalNoteDao
    abstract fun explanationDao(): ExplanationDao
    abstract fun readingPreferenceDao(): ReadingPreferenceDao
}

class StudyTypeConverters {
    @TypeConverter
    fun fromSection(value: TestamentSection): String = value.name

    @TypeConverter
    fun toSection(value: String): TestamentSection = TestamentSection.valueOf(value)
}
