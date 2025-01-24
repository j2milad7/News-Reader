package dev.miladanbari.newsreader.view.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.miladanbari.newsreader.domain.usecase.GetNewsUseCase
import dev.miladanbari.newsreader.view.news.model.ArticleItem
import dev.miladanbari.newsreader.view.news.model.FilterAndSort
import dev.miladanbari.newsreader.view.news.model.NewsSort
import dev.miladanbari.newsreader.view.news.model.toArticleItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val _filterAndSortStateFlow = MutableStateFlow(
        value = FilterAndSort(searchQuery = DEFAULT_SEARCH_QUERY, sort = NewsSort.NEWEST)
    )
    val filterAndSortStateFlow = _filterAndSortStateFlow.asStateFlow()

    val newsPagerFlow: Flow<PagingData<ArticleItem>> = filterAndSortStateFlow
        .debounce(DEBOUNCE_MILLIS) // Wait for 500ms after the last emission
        .filter { it.searchQuery.isNotEmpty() }
        .flatMapLatest {
            getNewsUseCase(
                query = it.searchQuery,
                sortBy = it.sort.value,
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE
            )
        }
        // Map the domain model to the UI model.
        .map { it.map { it.toArticleItem() } }
        .flowOn(Dispatchers.Default)
        .cachedIn(viewModelScope)

    fun onSearchQueryChange(searchQuery: String) {
        with(_filterAndSortStateFlow) {
            value = value.copy(searchQuery = searchQuery)
        }
    }

    fun onSortChange(sort: NewsSort) {
        with(_filterAndSortStateFlow) {
            value = value.copy(sort = sort)
        }
    }

    private companion object {

        const val DEFAULT_SEARCH_QUERY = "Android"
        const val PAGE_SIZE = 30
        const val PREFETCH_DISTANCE = 2
        const val DEBOUNCE_MILLIS = 500L
    }
}
