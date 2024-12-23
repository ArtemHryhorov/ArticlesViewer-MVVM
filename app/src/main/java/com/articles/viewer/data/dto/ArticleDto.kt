package com.articles.viewer.data.dto

import com.articles.viewer.domain.model.Article

data class ArticleDto(
    val title: String,
    val description: String,
    val url: String,
) {

    fun toDomain(): Article = Article(
        title = title,
        description = description,
        url = url,
    )
}
