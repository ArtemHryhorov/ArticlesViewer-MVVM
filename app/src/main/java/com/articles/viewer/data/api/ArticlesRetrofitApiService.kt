package com.articles.viewer.data.api

import com.articles.viewer.data.api.dto.ArticlesListResponse
import retrofit2.http.GET
import retrofit2.http.Query

fun interface ArticlesRetrofitApiService {

    @GET("v2/everything")
    suspend fun getArticles(@Query("q") query: String): ArticlesListResponse
}