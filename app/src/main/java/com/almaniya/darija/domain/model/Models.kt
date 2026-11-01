package com.almaniya.darija.domain.model

data class Word(
    val id: Long = 0,
    val topicId: String,
    val german: String,
    val darija: String,
    val phonetic: String = "",
    val exampleGerman: String = "",
    val exampleDarija: String = "",
    val gender: WordGender = WordGender.NONE,
    val isFavorite: Boolean = false,
    val isLearned: Boolean = false
)

enum class WordGender {
    DER, // مذكر
    DIE, // مؤنث
    DAS, // محايد
    NONE
}

data class Topic(
    val id: String,
    val titleDarija: String,
    val titleGerman: String,
    val descriptionDarija: String,
    val iconName: String,
    val category: TopicCategory,
    val wordCount: Int = 0,
    val learnedCount: Int = 0,
    val grammarNoteDarija: String = ""
)

enum class TopicCategory(val labelDarija: String) {
    BASICS("الأساسيات"),
    DAILY_LIFE("الحياة اليومية"),
    GRAMMAR("القواعد والشروحات"),
    CONVERSATION("المحادثات والتواصل")
}

data class QuizQuestion(
    val id: Long,
    val prompt: String,
    val correctAnswer: String,
    val options: List<String>,
    val germanWord: String,
    val isAudioQuiz: Boolean = false
)

data class ProgressStats(
    val totalWordsCount: Int,
    val learnedWordsCount: Int,
    val totalQuizzesCompleted: Int,
    val quizAverageScore: Int,
    val streakDays: Int
)

data class AlphabetLetter(
    val letter: String,
    val nameGerman: String,
    val pronunciationDarija: String,
    val exampleWord: String,
    val exampleDarija: String
)
