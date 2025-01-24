package dev.miladanbari.newsreader.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.miladanbari.newsreader.data.api.NewsService
import dev.miladanbari.newsreader.data.model.ArticleDto
import dev.miladanbari.newsreader.data.paging.NewsPagingSource
import dev.miladanbari.newsreader.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsService: NewsService
) : NewsRepository {

    override fun getNewsFlow(
        query: String,
        pageSize: Int,
        prefetchDistance: Int
    ): Flow<PagingData<ArticleDto>> {
        return Pager(
            PagingConfig(
                pageSize = pageSize,
                prefetchDistance = prefetchDistance,
                initialLoadSize = pageSize
            )
        ) { NewsPagingSource(newsService, query) }.flow
    }
}
