package com.almaniya.darija.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.almaniya.darija.domain.model.TopicCategory
import com.almaniya.darija.domain.model.WordGender

@Entity(tableName = "words")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val topicId: String,
    val german: String,
    val darija: String,
    val phonetic: String = "",
    val exampleGerman: String = "",
    val exampleDarija: String = "",
    val gender: String = "NONE",
    val isFavorite: Boolean = false,
    val isLearned: Boolean = false
)

@Entity(tableName = "topics")
data class TopicEntity(
    @PrimaryKey
    val id: String,
    val titleDarija: String,
    val titleGerman: String,
    val descriptionDarija: String,
    val iconName: String,
    val category: String,
    val grammarNoteDarija: String = ""
)

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey
    val id: Int = 1,
    val streakDays: Int = 1,
    val lastStudyDate: Long = 0,
    val totalQuizzesCompleted: Int = 0,
    val totalCorrectAnswers: Int = 0,
    val totalQuestionsAnswered: Int = 0
)
