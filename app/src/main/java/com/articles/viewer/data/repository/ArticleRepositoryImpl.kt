package com.articles.viewer.data.repository

import com.articles.viewer.core.exceptions.ServerUnavailableException
import com.articles.viewer.data.api.ArticlesRetrofitApiService
import com.articles.viewer.data.database.ArticleDao
import com.articles.viewer.data.database.ArticleEntity.Companion.toDomain
import com.articles.viewer.data.database.ArticleEntity.Companion.toEntity
import com.articles.viewer.domain.model.Article
import com.articles.viewer.domain.repository.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articlesRetrofitApiService: ArticlesRetrofitApiService,
    private val articleDao: ArticleDao,
) : ArticleRepository {

    override suspend fun getArticlesList(query: String): List<Article> {
        return try {
            val articlesResponse = articlesRetrofitApiService.getArticles(query)
            if (articlesResponse.status != "ok") throw ServerUnavailableException()

            val articles = articlesResponse.articles.map { it.toDomain() }
            val articleEntities = articles.map { it.toEntity() }

            articleDao.insertArticles(articleEntities)
            articles
        } catch (error: Throwable) {
            val cachedArticles = articleDao.getArticles("%$query%")
            if (cachedArticles.isEmpty()) throw error
            cachedArticles.map { it.toDomain() }
        }
    }
}