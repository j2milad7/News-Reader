package dev.miladanbari.newsreader.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.miladanbari.newsreader.data.repository.NewsRepositoryImpl
import dev.miladanbari.newsreader.domain.repository.NewsRepository

@Module
@InstallIn(SingletonComponent::class)
interface DomainImplModule {

    @Binds
    fun bindNewsRepository(newsRepository: NewsRepositoryImpl): NewsRepository
}
