package com.almaniya.darija.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.almaniya.darija.domain.model.Topic
import com.almaniya.darija.domain.model.TopicCategory
import com.almaniya.darija.domain.repository.AlmaniyaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val topics: List<Topic> = emptyList(),
    val selectedCategory: TopicCategory? = null,
    val wordOfTheDayGerman: String = "Guten Tag",
    val wordOfTheDayDarija: String = "نهاركم مبروك / السلام عليكم",
    val isLoading: Boolean = false
)

class HomeViewModel(
    private val repository: AlmaniyaRepository
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow<TopicCategory?>(null)

    val uiState: StateFlow<HomeUiState> = combine(
        repository.getAllTopics(),
        _selectedCategory
    ) { topics, category ->
        val filtered = if (category == null) topics else topics.filter { it.category == category }
        HomeUiState(
            topics = filtered,
            selectedCategory = category,
            wordOfTheDayGerman = "Guten Tag",
            wordOfTheDayDarija = "نهاركم مبروك / السلام عليكم"
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState(isLoading = true))

    fun selectCategory(category: TopicCategory?) {
        _selectedCategory.value = category
    }
}
