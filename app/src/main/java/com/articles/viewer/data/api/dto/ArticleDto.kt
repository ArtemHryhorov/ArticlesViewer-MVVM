package com.articles.viewer.data.api.dto

import com.articles.viewer.domain.model.Article

data class ArticleDto(
    val title: String,
    val description: String,
    val url: String,
    val publishedAt: String,
) {

    fun toDomain(): Article = Article(
        title = title,
        description = description,
        url = url,
        publishedAt = publishedAt,
    )

    companion object {

        fun Article.toDto(): ArticleDto = ArticleDto(
            title = title,
            description = description,
            url = url,
            publishedAt = publishedAt,
        )
    }
}
