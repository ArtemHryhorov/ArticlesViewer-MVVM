package com.articles.viewer.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// TODO("Parcelable in UI layer")
@Parcelize
data class Article(
    val title: String,
    val description: String,
    val url: String,
) : Parcelable {

    companion object {
        fun testList(): List<Article> = listOf(
            Article(
                title = "Article 1",
                description = "Description 1",
                url = "mocked.server.url?id=1"
            ),
            Article(
                title = "Article 2",
                description = "Description 2",
                url = "mocked.server.url?id="
            ),
            Article(
                title = "Article 3",
                description = "Description 3",
                url = "mocked.server.url?id=3"
            ),
            Article(
                title = "Article 3",
                description = "Description 3",
                url = "mocked.server.url?id=3"
            ),
        )
    }
}
