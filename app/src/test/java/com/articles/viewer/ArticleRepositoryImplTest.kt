package com.articles.viewer

import com.articles.viewer.data.api.ArticlesRetrofitApiService
import com.articles.viewer.data.api.dto.ArticleDto.Companion.toDto
import com.articles.viewer.data.api.dto.ArticlesListResponse
import com.articles.viewer.data.database.ArticleDao
import com.articles.viewer.data.database.ArticleEntity.Companion.toEntity
import com.articles.viewer.data.repository.ArticleRepositoryImpl
import com.articles.viewer.domain.model.Article
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ArticleRepositoryImplTest {

    private lateinit var repository: ArticleRepositoryImpl
    private lateinit var articlesRetrofitApiService: ArticlesRetrofitApiService
    private lateinit var articleDao: ArticleDao

    @Before
    fun setUp() {
        articlesRetrofitApiService = mockk()
        articleDao = mockk()
        repository = ArticleRepositoryImpl(articlesRetrofitApiService, articleDao)
    }

    @Test
    fun `getArticlesList should return remote articles on success`() = runTest {
        // Arrange
        val query = "test"
        val remoteArticles = Article.testList().map { it.toDto() }
        val response = ArticlesListResponse("ok", 0, remoteArticles)
        coEvery { articlesRetrofitApiService.getArticles(query) } returns response
        coEvery { articleDao.insertArticles(any()) } just Runs

        // Act
        val result = repository.getArticlesList(query)

        // Assert
        assertEquals(remoteArticles.map { it.toDomain() }, result)
    }

    @Test
    fun `getArticlesList should return cached articles on remote failure`() = runTest {
        // Arrange
        val query = "test"
        val cachedArticles = Article.testList()
        val exception = Exception("Remote API failure")
        coEvery { articlesRetrofitApiService.getArticles(query) } throws exception
        coEvery { articleDao.getArticles("%$query%") } returns cachedArticles.map { it.toEntity() }

        // Act
        val result = repository.getArticlesList(query)

        // Assert
        assertEquals(cachedArticles, result)
    }

    @Test(expected = Exception::class)
    fun `getArticlesList should throw exception on both remote and cache failure`() = runTest {
        // Arrange
        val query = "test"
        val exception = Exception("Both remote and cache failed")
        coEvery { articlesRetrofitApiService.getArticles(query) } throws exception
        coEvery { articleDao.getArticles("%$query%") } returns emptyList()

        // Act
        repository.getArticlesList(query)

        // Assert
        // Expected exception is verified by @Test(expected = Exception::class)
    }
}
