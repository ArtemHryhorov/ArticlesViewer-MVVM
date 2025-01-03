package com.articles.viewer.ui.articles.list

import android.app.AlertDialog
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
import com.articles.viewer.R
import com.articles.viewer.databinding.FragmentArticlesListBinding
import com.articles.viewer.domain.model.Article
import com.articles.viewer.ui.common.UiError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticlesListFragment : Fragment() {

    // TODO("Avoid using not-null assertion operator for binding")
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
                launch { viewModel.articlesState.collectLatest(::updateArticlesList) }
                launch { viewModel.errorState.collectLatest(::showErrorDialog) }
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

    private fun showErrorDialog(error: UiError?) {
        if (error == null) return
        binding.progressBar.visibility = View.GONE
        binding.swipeRefreshLayout.isRefreshing = false
        AlertDialog.Builder(requireContext())
            .setTitle(error.title)
            .setMessage(error.description)
            .setPositiveButton(getString(R.string.close)) { dialog, _ ->
                dialog.dismiss()
                viewModel.clearErrorState()
            }
            .show()
    }

    private fun navigateToArticleDetails(article: Article) {
        val action = ArticlesListFragmentDirections.actionArticleListToArticleDetails(article)
        findNavController().navigate(action)
    }
}