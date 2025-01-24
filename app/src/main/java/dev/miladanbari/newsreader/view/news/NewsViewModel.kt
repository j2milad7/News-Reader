package dev.miladanbari.newsreader.view.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.miladanbari.newsreader.domain.usecase.GetNewsUseCase
import dev.miladanbari.newsreader.view.news.model.ArticleItem
import dev.miladanbari.newsreader.view.news.model.toArticleItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    val newsPagerFlow: Flow<PagingData<ArticleItem>> = getNewsUseCase(
        query = QUERY,
        pageSize = PAGE_SIZE,
        prefetchDistance = PREFETCH_DISTANCE
    )
        // Map the domain model to the UI model.
        .map { it.map { it.toArticleItem() } }
        .flowOn(Dispatchers.Default)
        .cachedIn(viewModelScope)

    private companion object {

        // TODO: This is a search query for finding the related news. It can be replaced by any other
        //  query from UI which is entered by the user.
        const val QUERY = "android"
        const val PAGE_SIZE = 30
        const val PREFETCH_DISTANCE = 2
    }
}
