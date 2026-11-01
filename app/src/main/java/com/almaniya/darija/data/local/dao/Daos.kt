package com.almaniya.darija.data.local.dao

import androidx.room.*
import com.almaniya.darija.data.local.entity.TopicEntity
import com.almaniya.darija.data.local.entity.UserProgressEntity
import com.almaniya.darija.data.local.entity.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM words WHERE topicId = :topicId ORDER BY id ASC")
    fun getWordsByTopic(topicId: String): Flow<List<WordEntity>>

    @Query("SELECT * FROM words WHERE topicId = :topicId")
    suspend fun getWordsByTopicDirect(topicId: String): List<WordEntity>

    @Query("SELECT * FROM words WHERE isFavorite = 1 ORDER BY id DESC")
    fun getFavoriteWords(): Flow<List<WordEntity>>

    @Query("SELECT * FROM words WHERE german LIKE '%' || :query || '%' OR darija LIKE '%' || :query || '%'")
    fun searchWords(query: String): Flow<List<WordEntity>>

    @Query("SELECT * FROM words WHERE isLearned = 1")
    fun getLearnedWords(): Flow<List<WordEntity>>

    @Query("SELECT COUNT(*) FROM words")
    fun getTotalWordsCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM words WHERE isLearned = 1")
    fun getLearnedWordsCount(): Flow<Int>

    @Update
    suspend fun updateWord(word: WordEntity)

    @Query("UPDATE words SET isFavorite = :isFavorite WHERE id = :wordId")
    suspend fun setFavorite(wordId: Long, isFavorite: Boolean)

    @Query("UPDATE words SET isLearned = :isLearned WHERE id = :wordId")
    suspend fun setLearned(wordId: Long, isLearned: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<WordEntity>)

    @Query("SELECT COUNT(*) FROM words")
    suspend fun getCount(): Int
}

@Dao
interface TopicDao {
    @Query("SELECT * FROM topics ORDER BY id ASC")
    fun getAllTopics(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topics WHERE id = :topicId LIMIT 1")
    suspend fun getTopicById(topicId: String): TopicEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopics(topics: List<TopicEntity>)

    @Query("SELECT COUNT(*) FROM topics")
    suspend fun getCount(): Int
}

@Dao
interface ProgressDao {
    @Query("SELECT * FROM user_progress WHERE id = 1 LIMIT 1")
    fun getUserProgress(): Flow<UserProgressEntity?>

    @Query("SELECT * FROM user_progress WHERE id = 1 LIMIT 1")
    suspend fun getUserProgressDirect(): UserProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(progress: UserProgressEntity)
}
