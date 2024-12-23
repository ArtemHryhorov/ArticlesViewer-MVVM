package com.articles.viewer.ui.articles.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.articles.viewer.domain.model.Article
import com.articles.viewer.domain.usecase.GetAllArticles
import com.articles.viewer.ui.common.UiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesListViewModel @Inject constructor(
    private val getAllArticles: GetAllArticles,
) : ViewModel() {

    private val _articlesState = MutableStateFlow<List<Article>>(emptyList())
    val articlesState: StateFlow<List<Article>> get() = _articlesState

    private val _errorState = MutableStateFlow<UiError?>(null)
    val errorState: StateFlow<UiError?> get() = _errorState

    // TODO("Inject dispatchers")
    fun loadArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllArticles().fold(
                onSuccess = { articles -> _articlesState.update { articles } },
                onFailure = { error -> _errorState.update { UiError.fromThrowable(error) }},
            )
        }
    }

    fun clearErrorState() {
        viewModelScope.launch {
            _errorState.update { null }
        }
    }
}