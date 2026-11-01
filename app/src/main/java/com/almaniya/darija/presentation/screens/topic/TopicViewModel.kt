package com.almaniya.darija.presentation.screens.topic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almaniya.darija.domain.model.Topic
import com.almaniya.darija.domain.model.Word
import com.almaniya.darija.domain.repository.AlmaniyaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TopicDetailUiState(
    val topic: Topic? = null,
    val words: List<Word> = emptyList(),
    val selectedTab: Int = 0, // 0: Vocabulary, 1: Grammar Notes
    val isLoading: Boolean = true
)

class TopicViewModel(
    private val topicId: String,
    private val repository: AlmaniyaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TopicDetailUiState())
    val uiState: StateFlow<TopicDetailUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val topic = repository.getTopicById(topicId)
            _uiState.value = _uiState.value.copy(topic = topic)
        }
        viewModelScope.launch {
            repository.getWordsByTopic(topicId).collect { words ->
                _uiState.value = _uiState.value.copy(words = words, isLoading = false)
            }
        }
    }

    fun selectTab(index: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = index)
    }

    fun toggleFavorite(word: Word) {
        viewModelScope.launch {
            repository.toggleFavorite(word.id, word.isFavorite)
        }
    }
}
