package com.articles.viewer.ui.articles.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.articles.viewer.databinding.ItemViewArticleBinding
import com.articles.viewer.domain.model.Article

class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>() {

    var articles: List<Article> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var onArticleClicked: ((Article) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val binding = ItemViewArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ArticlesViewHolder(binding)
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    inner class ArticlesViewHolder(
        private val binding: ItemViewArticleBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            with(binding) {
                titleTextView.text = article.title
                descriptionTextView.text = article.description
                root.setOnClickListener {
                    onArticleClicked?.invoke(article)
                }
            }
        }
    }
}