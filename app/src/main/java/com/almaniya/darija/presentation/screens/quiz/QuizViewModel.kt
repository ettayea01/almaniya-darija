package com.almaniya.darija.presentation.screens.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almaniya.darija.domain.model.QuizQuestion
import com.almaniya.darija.domain.repository.AlmaniyaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class QuizUiState(
    val questions: List<QuizQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedOption: String? = null,
    val isAnswerChecked: Boolean = false,
    val isCorrect: Boolean = false,
    val score: Int = 0,
    val isQuizFinished: Boolean = false,
    val isLoading: Boolean = true
)

class QuizViewModel(
    private val topicId: String,
    private val repository: AlmaniyaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init {
        generateQuiz()
    }

    private fun generateQuiz() {
        viewModelScope.launch {
            val words = repository.getWordsByTopicDirect(topicId)
            if (words.size >= 4) {
                val questions = words.shuffled().take(10).mapIndexed { index, word ->
                    val wrongOptions = words.filter { it.id != word.id }.shuffled().take(3).map { it.darija }
                    val allOptions = (wrongOptions + word.darija).shuffled()
                    QuizQuestion(
                        id = index.toLong(),
                        prompt = "شنو كتعني هاد الكلمة بالدارجة؟",
                        correctAnswer = word.darija,
                        options = allOptions,
                        germanWord = word.german,
                        isAudioQuiz = index % 2 == 1
                    )
                }
                _uiState.value = QuizUiState(questions = questions, isLoading = false)
            } else {
                _uiState.value = QuizUiState(isLoading = false)
            }
        }
    }

    fun selectOption(option: String) {
        if (_uiState.value.isAnswerChecked) return
        _uiState.value = _uiState.value.copy(selectedOption = option)
    }

    fun checkAnswer() {
        val state = _uiState.value
        val currentQ = state.questions.getOrNull(state.currentQuestionIndex) ?: return
        val selected = state.selectedOption ?: return

        val correct = selected == currentQ.correctAnswer
        val newScore = if (correct) state.score + 1 else state.score

        _uiState.value = state.copy(
            isAnswerChecked = true,
            isCorrect = correct,
            score = newScore
        )
    }

    fun nextQuestion() {
        val state = _uiState.value
        if (state.currentQuestionIndex < state.questions.size - 1) {
            _uiState.value = state.copy(
                currentQuestionIndex = state.currentQuestionIndex + 1,
                selectedOption = null,
                isAnswerChecked = false,
                isCorrect = false
            )
        } else {
            // Quiz completed
            _uiState.value = state.copy(isQuizFinished = true)
            viewModelScope.launch {
                repository.recordQuizResult(state.score, state.questions.size)
            }
        }
    }

    fun restartQuiz() {
        generateQuiz()
    }
}
