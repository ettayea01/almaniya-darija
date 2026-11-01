package com.almaniya.darija.domain.repository

import com.almaniya.darija.domain.model.AlphabetLetter
import com.almaniya.darija.domain.model.ProgressStats
import com.almaniya.darija.domain.model.Topic
import com.almaniya.darija.domain.model.Word
import kotlinx.coroutines.flow.Flow

interface AlmaniyaRepository {
    fun getAllTopics(): Flow<List<Topic>>
    suspend fun getTopicById(topicId: String): Topic?
    fun getWordsByTopic(topicId: String): Flow<List<Word>>
    suspend fun getWordsByTopicDirect(topicId: String): List<Word>
    fun getFavoriteWords(): Flow<List<Word>>
    fun searchWords(query: String): Flow<List<Word>>
    fun getProgressStats(): Flow<ProgressStats>
    suspend fun toggleFavorite(wordId: Long, currentStatus: Boolean)
    suspend fun markWordLearned(wordId: Long, isLearned: Boolean)
    suspend fun recordQuizResult(correctCount: Int, totalCount: Int)
    fun getAlphabetLetters(): List<AlphabetLetter>
}
