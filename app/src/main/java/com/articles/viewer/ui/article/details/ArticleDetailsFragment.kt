package com.articles.viewer.ui.article.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.articles.viewer.databinding.FragmentArticleDetailsBinding
import com.articles.viewer.domain.model.Article
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {

    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!
    private var article: Article? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val article = ArticleDetailsFragmentArgs.fromBundle(it).article
            displayArticleDetails(article)
            this.article = article
        }
        bindActions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayArticleDetails(article: Article) {
        with(binding) {
            titleTextView.text = article.title
            descriptionTextView.text = article.description
        }
    }

    private fun bindActions() {
        binding.viewFullArticleButton.setOnClickListener {
            article?.url?.let { navigateToWebView(it) }
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun navigateToWebView(url: String) {
        val action = ArticleDetailsFragmentDirections.actionDetailsFragmentToWebViewFragment(url)
        findNavController().navigate(action)
    }
}