package com.articles.viewer.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.articles.viewer.domain.model.Article

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val title: String,
    val description: String,
    val url: String,
    val publishedAt: String,
) {

    companion object {

        fun Article.toEntity(): ArticleEntity = ArticleEntity(
            title = title,
            description = description,
            url = url,
            publishedAt = publishedAt,
        )

        fun ArticleEntity.toDomain(): Article = Article(
            title = title,
            description = description,
            url = url,
            publishedAt = publishedAt,
        )
    }
}