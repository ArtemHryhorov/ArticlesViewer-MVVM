package com.articles.viewer.di

import com.articles.viewer.data.repository.ArticleRepositoryImpl
import com.articles.viewer.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ArticleModule {

    @Binds
    abstract fun bindArticleRepository(
        articleRepositoryImpl: ArticleRepositoryImpl,
    ): ArticleRepository
}