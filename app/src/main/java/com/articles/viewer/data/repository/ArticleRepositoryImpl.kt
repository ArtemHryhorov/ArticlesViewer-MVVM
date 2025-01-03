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

    override suspend fun getArticlesList(query: String): List<Article> = try {
        val remoteArticles = loadRemoteArticles(query)
        cacheRemoteArticlesIntoDatabase(remoteArticles)
        remoteArticles
    } catch (error: Throwable) {
        loadCachedArticles(query).ifEmpty { throw error }
    }

    private suspend fun loadRemoteArticles(query: String): List<Article> {
        val articlesResponse = articlesRetrofitApiService.getArticles(query)
        if (articlesResponse.status != "ok") throw ServerUnavailableException()
        return articlesResponse.articles
            .filterNot { it.title == REMOVED_ARTICLE_TITLE }
            .map { it.toDomain() }
    }

    private suspend fun cacheRemoteArticlesIntoDatabase(remoteArticles: List<Article>) {
        val articleEntities = remoteArticles.map { it.toEntity() }
        articleDao.insertArticles(articleEntities)
    }

    private suspend fun loadCachedArticles(query: String): List<Article> = articleDao
        .getArticles("%$query%")
        .map { it.toDomain() }

    private companion object {
        const val REMOVED_ARTICLE_TITLE = "[Removed]"
    }
}