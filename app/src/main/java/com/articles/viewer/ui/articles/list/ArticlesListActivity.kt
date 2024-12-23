package com.articles.viewer.ui.articles.list

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.articles.viewer.databinding.ActivityArticlesListBinding
import com.articles.viewer.domain.model.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticlesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticlesListBinding
    private val viewModel: ArticlesListViewModel by viewModels()
    private val articlesAdapter = ArticlesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticlesListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureArticlesList()
        bindViewModel()
    }

    private fun configureArticlesList() {
        binding.articlesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ArticlesListActivity)
            adapter = articlesAdapter
        }
        viewModel.loadArticles()
    }

    private fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.articlesState.collectLatest(::updateArticlesList)
        }
    }

    private fun updateArticlesList(articles: List<Article>) {
        articles.forEach {
            Log.d("Articles", "$it")
        }
        articlesAdapter.articles = articles
    }
}