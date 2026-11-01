package com.almaniya.darija

import com.almaniya.darija.domain.model.*
import org.junit.Assert.*
import org.junit.Test

class AlmaniyaUnitTest {

    @Test
    fun testWordGender() {
        val word = Word(
            id = 1,
            topicId = "greetings",
            german = "der Vater",
            darija = "الواليد",
            gender = WordGender.DER
        )
        assertEquals(WordGender.DER, word.gender)
        assertEquals("الواليد", word.darija)
    }

    @Test
    fun testQuizQuestionValidation() {
        val question = QuizQuestion(
            id = 1,
            prompt = "شنو كتعني هاد الكلمة بالدارجة؟",
            correctAnswer = "شكراً",
            options = listOf("شكراً", "صباح الخير", "بسلامة", "أهلاً"),
            germanWord = "danke"
        )
        assertTrue(question.options.contains(question.correctAnswer))
        assertEquals(4, question.options.size)
    }

    @Test
    fun testAlphabetLettersCompleteness() {
        val alphabetLetters = listOf("A a", "B b", "C c", "D d", "E e", "F f", "G g", "H h", "I i", "J j", "K k", "L l", "M m", "N n", "O o", "P p", "Q q", "R r", "S s", "T t", "U u", "V v", "W w", "X x", "Y y", "Z z", "Ä ä", "Ö ö", "Ü ü", "ß")
        assertEquals(30, alphabetLetters.size)
    }
}
