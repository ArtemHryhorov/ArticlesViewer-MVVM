package com.articles.viewer.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// TODO("Parcelable should be in UI layer model")
@Parcelize
data class Article(
    val title: String,
    val description: String,
    val url: String,
    val publishedAt: String,
) : Parcelable {

    companion object {
        fun testList(): List<Article> = listOf(
            Article(
                title = "Article 1",
                description = "Description 1",
                url = "mocked.server.url?id=1",
                publishedAt = "2024-12-12T17:51:58Z",
            ),
            Article(
                title = "Article 2",
                description = "Description 2",
                url = "mocked.server.url?id=",
                publishedAt = "2024-12-12T17:51:58Z",
            ),
            Article(
                title = "Article 3",
                description = "Description 3",
                url = "mocked.server.url?id=3",
                publishedAt = "2024-12-12T17:51:58Z",
            ),
            Article(
                title = "Article 4",
                description = "Description 4",
                url = "mocked.server.url?id=4",
                publishedAt = "2024-12-12T17:51:58Z",
            ),
        )
    }
}
