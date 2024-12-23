package com.articles.viewer.domain.repository

import com.articles.viewer.domain.model.Article

fun interface ArticleRepository {

    suspend fun getArticlesList(query: String): List<Article>
}