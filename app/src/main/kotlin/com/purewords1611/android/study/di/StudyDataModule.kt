package com.purewords1611.android.study.di

import android.content.Context
import androidx.room.Room
import com.purewords1611.android.study.data.OfflineStudyRepository
import com.purewords1611.android.study.data.StudyRepository
import com.purewords1611.android.study.data.local.ExplanationDao
import com.purewords1611.android.study.data.local.BookmarkDao
import com.purewords1611.android.study.data.local.MarginalNoteDao
import com.purewords1611.android.study.data.local.HighlightDao
import com.purewords1611.android.study.data.local.PersonalNoteDao
import com.purewords1611.android.study.data.local.ReadingPreferenceDao
import com.purewords1611.android.study.data.local.StudyDatabase
import com.purewords1611.android.study.data.local.VerseDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StudyDatabaseModule {
    @Provides
    @Singleton
    fun provideStudyDatabase(
        @ApplicationContext context: Context,
    ): StudyDatabase {
        return Room.databaseBuilder(
            context,
            StudyDatabase::class.java,
            "pure_words_study.db"
        )
            .createFromAsset("database/full_1611_bible.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideVerseDao(database: StudyDatabase): VerseDao = database.verseDao()

    @Provides
    fun provideMarginalNoteDao(database: StudyDatabase): MarginalNoteDao = database.marginalNoteDao()

    @Provides
    fun provideExplanationDao(database: StudyDatabase): ExplanationDao = database.explanationDao()

    @Provides
    fun provideReadingPreferenceDao(database: StudyDatabase): ReadingPreferenceDao =
        database.readingPreferenceDao()

    @Provides
    fun provideBookmarkDao(database: StudyDatabase): BookmarkDao = database.bookmarkDao()

    @Provides
    fun provideHighlightDao(database: StudyDatabase): HighlightDao = database.highlightDao()

    @Provides
    fun providePersonalNoteDao(database: StudyDatabase): PersonalNoteDao = database.personalNoteDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class StudyRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindStudyRepository(
        implementation: OfflineStudyRepository
    ): StudyRepository
}
