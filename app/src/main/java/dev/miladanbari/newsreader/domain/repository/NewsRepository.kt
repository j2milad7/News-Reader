package dev.miladanbari.newsreader.domain.repository

import androidx.paging.PagingData
import dev.miladanbari.newsreader.data.model.ArticleDto
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNewsFlow(
        query: String,
        sortBy: String? = null,
        pageSize: Int,
        prefetchDistance: Int
    ): Flow<PagingData<ArticleDto>>
}
