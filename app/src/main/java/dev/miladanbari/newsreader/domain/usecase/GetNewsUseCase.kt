package dev.miladanbari.newsreader.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import dev.miladanbari.newsreader.domain.model.ArticleModel
import dev.miladanbari.newsreader.domain.model.toArticleModel
import dev.miladanbari.newsreader.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * This is a simple use case class. It could be omitted to reduce the code complexity.
 */
class GetNewsUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {

    /**
     * An operator function to get processed data from the use case.
     */
    operator fun invoke(
        query: String,
        sortBy: String? = null,
        pageSize: Int,
        prefetchDistance: Int
    ): Flow<PagingData<ArticleModel>> {

        // Do some business logics
        return newsRepository.getNewsFlow(query, sortBy, pageSize, prefetchDistance)
            // Map the DTO to the domain model.
            .map { it.map { it.toArticleModel() } }
            .flowOn(Dispatchers.IO)
    }
}
