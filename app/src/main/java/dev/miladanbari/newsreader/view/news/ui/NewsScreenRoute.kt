package dev.miladanbari.newsreader.view.news.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import dev.miladanbari.newsreader.view.news.NewsViewModel
import dev.miladanbari.newsreader.view.news.model.ArticleItem
import kotlinx.coroutines.launch

@Composable
internal fun NewScreenRoute(
    navigateToArticleDetails: (ArticleItem) -> Unit,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val filterAndSortStat = viewModel.filterAndSortStateFlow.collectAsStateWithLifecycle()
    val lazyPagingItems = viewModel.newsPagerFlow.collectAsLazyPagingItems()
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    NewsScreen(
        filterAndSortStat = filterAndSortStat,
        lazyPagingItems = lazyPagingItems,
        lazyListState = lazyListState,
        snackbarHostState = snackbarHostState,
        showSnackbar = { scope.launch { snackbarHostState.showSnackbar(it) } },
        onArticleClick = navigateToArticleDetails,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onSortChange = viewModel::onSortChange
    )
}
