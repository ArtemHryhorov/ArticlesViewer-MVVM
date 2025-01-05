package com.articles.viewer

import com.articles.viewer.domain.model.Article
import com.articles.viewer.domain.usecase.GetAllArticles
import com.articles.viewer.ui.articles.list.ArticlesListViewModel
import com.articles.viewer.ui.common.UiError
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ArticlesListViewModelTest {

    private lateinit var viewModel: ArticlesListViewModel
    private lateinit var getAllArticles: GetAllArticles
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        getAllArticles = mockk()
        viewModel = ArticlesListViewModel(getAllArticles)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadArticles emit articles on success`() = runTest {
        // Arrange
        val expectedArticles = Article.testList()
        coEvery { getAllArticles() } returns Result.success(expectedArticles)

        // Act
        viewModel.loadArticles()
        val emittedArticles = viewModel.articlesState.first()

        // Assert
        assertEquals(expectedArticles, emittedArticles)
    }

    @Test
    fun `loadArticles emit error on failure`() = runTest {
        // Arrange
        val exception = Exception("Error")
        coEvery { getAllArticles() } returns Result.failure(exception)
        val expectedError = UiError.fromThrowable(exception)

        // Act
        viewModel.loadArticles()
        val emittedError = viewModel.errorState.first()

        // Assert
        assertEquals(expectedError, emittedError)
    }

    @Test
    fun `clearErrorState should emit null error after error`() = runTest {
        // Arrange
        val exception = Exception("Error")
        coEvery { getAllArticles() } returns Result.failure(exception)

        // Act
        val loadArticlesJob = launch { viewModel.loadArticles() } // Launch in a separate job
        loadArticlesJob.join() // Wait for loadArticles to complete
        viewModel.clearErrorState()
        val emittedError = viewModel.errorState.drop(1).first()

        // Assert
        Assert.assertNull(emittedError)
    }
}
