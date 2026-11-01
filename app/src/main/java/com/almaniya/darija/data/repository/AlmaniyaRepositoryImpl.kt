package com.almaniya.darija.data.repository

import com.almaniya.darija.data.local.AppDatabase
import com.almaniya.darija.data.local.entity.TopicEntity
import com.almaniya.darija.data.local.entity.UserProgressEntity
import com.almaniya.darija.data.local.entity.WordEntity
import com.almaniya.darija.domain.model.*
import com.almaniya.darija.domain.repository.AlmaniyaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class AlmaniyaRepositoryImpl(
    private val database: AppDatabase
) : AlmaniyaRepository {

    private val wordDao = database.wordDao()
    private val topicDao = database.topicDao()
    private val progressDao = database.progressDao()

    override fun getAllTopics(): Flow<List<Topic>> {
        return topicDao.getAllTopics().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTopicById(topicId: String): Topic? {
        return topicDao.getTopicById(topicId)?.toDomain()
    }

    override fun getWordsByTopic(topicId: String): Flow<List<Word>> {
        return wordDao.getWordsByTopic(topicId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getWordsByTopicDirect(topicId: String): List<Word> {
        return wordDao.getWordsByTopicDirect(topicId).map { it.toDomain() }
    }

    override fun getFavoriteWords(): Flow<List<Word>> {
        return wordDao.getFavoriteWords().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun searchWords(query: String): Flow<List<Word>> {
        return wordDao.searchWords(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getProgressStats(): Flow<ProgressStats> {
        return combine(
            wordDao.getTotalWordsCount(),
            wordDao.getLearnedWordsCount(),
            progressDao.getUserProgress()
        ) { totalWords, learnedWords, progressEntity ->
            val totalQuizzes = progressEntity?.totalQuizzesCompleted ?: 0
            val totalQuestions = progressEntity?.totalQuestionsAnswered ?: 0
            val totalCorrect = progressEntity?.totalCorrectAnswers ?: 0
            val avgScore = if (totalQuestions > 0) (totalCorrect * 100) / totalQuestions else 0
            val streak = progressEntity?.streakDays ?: 1

            ProgressStats(
                totalWordsCount = totalWords,
                learnedWordsCount = learnedWords,
                totalQuizzesCompleted = totalQuizzes,
                quizAverageScore = avgScore,
                streakDays = streak
            )
        }
    }

    override suspend fun toggleFavorite(wordId: Long, currentStatus: Boolean) {
        wordDao.setFavorite(wordId, !currentStatus)
    }

    override suspend fun markWordLearned(wordId: Long, isLearned: Boolean) {
        wordDao.setLearned(wordId, isLearned)
    }

    override suspend fun recordQuizResult(correctCount: Int, totalCount: Int) {
        val current = progressDao.getUserProgressDirect() ?: UserProgressEntity()
        val updated = current.copy(
            totalQuizzesCompleted = current.totalQuizzesCompleted + 1,
            totalCorrectAnswers = current.totalCorrectAnswers + correctCount,
            totalQuestionsAnswered = current.totalQuestionsAnswered + totalCount,
            lastStudyDate = System.currentTimeMillis()
        )
        progressDao.insertOrUpdate(updated)
    }

    override fun getAlphabetLetters(): List<AlphabetLetter> {
        return listOf(
            AlphabetLetter("A a", "Ah", "آ (مد خفيف)", "der Apfel", "التفاحة"),
            AlphabetLetter("B b", "Beh", "ب", "das Brot", "الخبز"),
            AlphabetLetter("C c", "Tseh", "تس / ك", "der Computer", "البيسي / الحاسوب"),
            AlphabetLetter("D d", "Deh", "د", "danke", "شكراً"),
            AlphabetLetter("E e", "Eh", "إي (مفتوحة شوية)", "das Essen", "الماكلة"),
            AlphabetLetter("F f", "Eff", "ف", "die Familie", "العائلة"),
            AlphabetLetter("G g", "Geh", "ڭ (ڭاف)", "gut", "مزيان"),
            AlphabetLetter("H h", "Hah", "هـ", "Hallo", "أهلاً"),
            AlphabetLetter("I i", "Ee", "إي", "die Insel", "الجزيرة"),
            AlphabetLetter("J j", "Yott", "ي (ياء)", "das Jahr", "العام / السنة"),
            AlphabetLetter("K k", "Kah", "ك", "das Kind", "الدري / الطفل"),
            AlphabetLetter("L l", "Ell", "ل", "lernen", "تعلم"),
            AlphabetLetter("M m", "Emm", "م", "die Mutter", "الواليدة"),
            AlphabetLetter("N n", "Enn", "ن", "die Nacht", "الليل"),
            AlphabetLetter("O o", "Oh", "أو (واو مفخمة)", "das Ohr", "الودن"),
            AlphabetLetter("P p", "Peh", "پ (پاء)", "die Post", "البوسطة"),
            AlphabetLetter("Q q", "Kuh", "كف (مع واو)", "die Quelle", "العين / المنبع"),
            AlphabetLetter("R r", "Err", "غ / ر", "das Radio", "الراديو"),
            AlphabetLetter("S s", "Ess", "ز (فالأول) / س", "die Sonne", "الشمس"),
            AlphabetLetter("T t", "Teh", "ت", "der Tag", "النهار"),
            AlphabetLetter("U u", "Ooh", "أو (مضمومة)", "die Uhr", "المڭانة / الساعة"),
            AlphabetLetter("V v", "Fow", "ف (بحال الفاء)", "der Vater", "الواليد"),
            AlphabetLetter("W w", "Veh", "ڤ (بحال V بالفرنسية)", "das Wasser", "الما"),
            AlphabetLetter("X x", "Iks", "إكس", "das Xylophon", "إكسيلوفون"),
            AlphabetLetter("Y y", "Ypsilon", "إبسيلون", "das Yoga", "اليوڭا"),
            AlphabetLetter("Z z", "Tsett", "تس (T + S)", "die Zeit", "الوقت"),
            // Umlaute & Eszett
            AlphabetLetter("Ä ä", "A-Umlaut", "إي (بحال è)", "die Äpfel", "التفاحات"),
            AlphabetLetter("Ö ö", "O-Umlaut", "أو (شفايف مدورين)", "das Öl", "الزيت"),
            AlphabetLetter("Ü ü", "U-Umlaut", "إي (شفايف مضمومين)", "über", "فوق / على"),
            AlphabetLetter("ß", "Eszett", "س (سين مشددة ss)", "die Straße", "الزنقة / الشارع")
        )
    }

    private fun WordEntity.toDomain() = Word(
        id = id,
        topicId = topicId,
        german = german,
        darija = darija,
        phonetic = phonetic,
        exampleGerman = exampleGerman,
        exampleDarija = exampleDarija,
        gender = when (gender) {
            "DER" -> WordGender.DER
            "DIE" -> WordGender.DIE
            "DAS" -> WordGender.DAS
            else -> WordGender.NONE
        },
        isFavorite = isFavorite,
        isLearned = isLearned
    )

    private fun TopicEntity.toDomain() = Topic(
        id = id,
        titleDarija = titleDarija,
        titleGerman = titleGerman,
        descriptionDarija = descriptionDarija,
        iconName = iconName,
        category = when (category) {
            "BASICS" -> TopicCategory.BASICS
            "DAILY_LIFE" -> TopicCategory.DAILY_LIFE
            "GRAMMAR" -> TopicCategory.GRAMMAR
            else -> TopicCategory.CONVERSATION
        },
        grammarNoteDarija = grammarNoteDarija
    )
}
