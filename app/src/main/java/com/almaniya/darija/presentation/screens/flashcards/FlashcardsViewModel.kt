package com.almaniya.darija.presentation.screens.flashcards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almaniya.darija.domain.model.Word
import com.almaniya.darija.domain.repository.AlmaniyaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FlashcardsUiState(
    val words: List<Word> = emptyList(),
    val currentIndex: Int = 0,
    val isFlipped: Boolean = false,
    val isLoading: Boolean = true
)

class FlashcardsViewModel(
    private val topicId: String,
    private val repository: AlmaniyaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FlashcardsUiState())
    val uiState: StateFlow<FlashcardsUiState> = _uiState.asStateFlow()

    init {
        loadWords()
    }

    private fun loadWords() {
        viewModelScope.launch {
            val words = repository.getWordsByTopicDirect(topicId)
            _uiState.value = _uiState.value.copy(words = words, isLoading = false)
        }
    }

    fun flip() {
        _uiState.value = _uiState.value.copy(isFlipped = !_uiState.value.isFlipped)
    }

    fun next() {
        val nextIdx = (_uiState.value.currentIndex + 1).coerceAtMost(_uiState.value.words.size - 1)
        _uiState.value = _uiState.value.copy(currentIndex = nextIdx, isFlipped = false)
    }

    fun previous() {
        val prevIdx = (_uiState.value.currentIndex - 1).coerceAtLeast(0)
        _uiState.value = _uiState.value.copy(currentIndex = prevIdx, isFlipped = false)
    }

    fun markLearned() {
        val currentWord = _uiState.value.words.getOrNull(_uiState.value.currentIndex) ?: return
        viewModelScope.launch {
            repository.markWordLearned(currentWord.id, true)
            next()
        }
    }
}
