package com.articles.viewer.ui.articles.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.articles.viewer.domain.model.Article
import com.articles.viewer.domain.usecase.GetAllArticles
import com.articles.viewer.ui.common.UiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesListViewModel @Inject constructor(
    private val getAllArticles: GetAllArticles,
) : ViewModel() {

    private val _articlesState = MutableSharedFlow<List<Article>>(replay = 1)
    val articlesState: SharedFlow<List<Article>> get() = _articlesState

    private val _errorState = MutableSharedFlow<UiError?>(replay = 1)
    val errorState: SharedFlow<UiError?> get() = _errorState

    // TODO("Inject dispatchers")
    fun loadArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllArticles().fold(
                onSuccess = { articles -> _articlesState.emit(articles) },
                onFailure = { error -> _errorState.emit(UiError.fromThrowable(error)) },
            )
        }
    }

    fun clearErrorState() {
        viewModelScope.launch {
            _errorState.emit(null)
        }
    }
}