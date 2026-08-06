package com.purewords1611.android.study.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.purewords1611.android.study.data.TestamentSection
import kotlinx.coroutines.flow.Flow

data class ChapterIndexRow(
    val book: String,
    val bookOriginal: String?,
    val chapter: Int,
    val section: TestamentSection,
    val firstCanonicalOrder: Int,
)

@Dao
interface VerseDao {
    @Query("SELECT COUNT(*) FROM verses")
    suspend fun count(): Int

    @Query("SELECT * FROM verses ORDER BY canonicalOrder ASC")
    fun observeAllVerses(): Flow<List<VerseEntity>>

    @Query(
        """
        SELECT * FROM verses
        WHERE originalText LIKE '%' || :query || '%'
           OR modernizedText LIKE '%' || :query || '%'
           OR book LIKE '%' || :query || '%'
        ORDER BY canonicalOrder ASC
        """,
    )
    fun observeVersesByQuery(query: String): Flow<List<VerseEntity>>

    @Query(
        """
        SELECT * FROM verses
        JOIN verses_fts ON verses.id = verses_fts.docid
        WHERE verses_fts MATCH :query
        ORDER BY canonicalOrder ASC
        """
    )
    fun searchVerses(query: String): Flow<List<VerseEntity>>

    @Query(
        """
        SELECT * FROM verses
        WHERE book = :book AND chapter = :chapter
        ORDER BY canonicalOrder ASC
        """
    )
    fun observeVersesByChapter(book: String, chapter: Int): Flow<List<VerseEntity>>

    @Query(
        """
        SELECT * FROM verses
        WHERE book = :book AND chapter = :chapter
        ORDER BY canonicalOrder ASC
        """
    )
    suspend fun getVersesByChapter(book: String, chapter: Int): List<VerseEntity>

    @Query(
        """
        SELECT book, bookOriginal, chapter, section, MIN(canonicalOrder) AS firstCanonicalOrder
        FROM verses
        GROUP BY book, bookOriginal, chapter, section
        ORDER BY firstCanonicalOrder ASC
        """
    )
    fun observeChapterIndex(): Flow<List<ChapterIndexRow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(verses: List<VerseEntity>)

    @Query("DELETE FROM verses")
    suspend fun deleteAll()
}

@Dao
interface MarginalNoteDao {
    @Query("SELECT COUNT(*) FROM marginal_notes")
    suspend fun count(): Int

    @Query("SELECT * FROM marginal_notes WHERE verseId = :verseId ORDER BY id ASC")
    fun observeByVerse(verseId: Long): Flow<List<MarginalNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(notes: List<MarginalNoteEntity>)

    @Query("DELETE FROM marginal_notes")
    suspend fun deleteAll()
}

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY createdAtEpochMillis DESC")
    fun observeAll(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: BookmarkEntity)
}

@Dao
interface PersonalNoteDao {
    @Query("SELECT * FROM personal_notes ORDER BY updatedAtEpochMillis DESC")
    fun observeAll(): Flow<List<PersonalNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(note: PersonalNoteEntity)
}

@Dao
interface HighlightDao {
    @Query("SELECT * FROM highlights ORDER BY createdAtEpochMillis DESC")
    fun observeAll(): Flow<List<HighlightEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(highlight: HighlightEntity)
}

@Dao
interface ExplanationDao {
    @Query(
        """
        SELECT * FROM explanations
        WHERE verseId = :verseId AND level = :level
        ORDER BY id ASC
        """
    )
    fun observeByVerseAndLevel(verseId: Long, level: String): Flow<List<ExplanationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(explanations: List<ExplanationEntity>)

    @Query("DELETE FROM explanations")
    suspend fun deleteAll()
}

@Dao
interface ReadingPreferenceDao {
    @Query("SELECT * FROM reading_preferences WHERE id = 1")
    fun observePreferences(): Flow<ReadingPreferenceEntity?>

    @Query("SELECT * FROM reading_preferences WHERE id = 1")
    suspend fun getPreferences(): ReadingPreferenceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(preferences: ReadingPreferenceEntity)
}
