package com.articles.viewer.data.dto

data class ArticlesListResponse(
    val status: String,
    val totalResult: Int,
    val articles: List<ArticleDto>,
)
