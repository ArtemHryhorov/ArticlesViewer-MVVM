package com.articles.viewer.ui.articles.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.articles.viewer.databinding.FragmentArticlesListBinding
import com.articles.viewer.domain.model.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticlesListFragment : Fragment() {

    // TODO("Avoid using not-null assertion operator")
    private var _binding: FragmentArticlesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ArticlesListViewModel by viewModels()
    private val articlesAdapter = ArticlesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureArticlesList()
        bindViewModelState()
        bindActions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configureArticlesList() {
        with(binding.articlesRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articlesAdapter
        }
        viewModel.loadArticles()
    }

    private fun bindViewModelState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articlesState.collectLatest(::updateArticlesList)
            }
        }
    }

    private fun bindActions() {
        articlesAdapter.onArticleClicked = { navigateToArticleDetails(it) }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadArticles()
        }
    }

    private fun updateArticlesList(articles: List<Article>) {
        binding.swipeRefreshLayout.isRefreshing = false
        if (articles.isNotEmpty()) binding.progressBar.visibility = View.GONE
        articlesAdapter.articles = articles
    }

    private fun navigateToArticleDetails(article: Article) {
        val action = ArticlesListFragmentDirections.actionArticleListToArticleDetails(article)
        findNavController().navigate(action)
    }
}