package com.articles.viewer.data.repository

import com.articles.viewer.core.exceptions.ServerUnavailableException
import com.articles.viewer.data.api.ArticlesRetrofitApiService
import com.articles.viewer.domain.model.Article
import com.articles.viewer.domain.repository.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articlesRetrofitApiService: ArticlesRetrofitApiService,
) : ArticleRepository {

    override suspend fun getArticlesList(query: String): List<Article> {
        val articlesResponse = articlesRetrofitApiService.getArticles(query)
        if (articlesResponse.status != "ok") throw ServerUnavailableException()
        return articlesResponse.articles.map { it.toDomain() }
    }
}