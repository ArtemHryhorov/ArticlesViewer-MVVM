package com.articles.viewer.domain.usecase

import android.util.Log
import com.articles.viewer.domain.model.Article
import com.articles.viewer.domain.repository.ArticleRepository
import javax.inject.Inject

class GetAllArticles @Inject constructor(
    private val articleRepository: ArticleRepository,
) {

    suspend operator fun invoke(query: String = "android"): Result<List<Article>> = try {
        Result.success(articleRepository.getArticlesList(query))
    } catch (error: Throwable) {
        Log.e("Articles", "GetAllArticles invoke() failed with ${error.message}", error)
        Result.failure(error)
    }
}