package dev.miladanbari.newsreader.view.news.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import dev.miladanbari.newsreader.base.Failure
import dev.miladanbari.newsreader.base.Loading
import dev.miladanbari.newsreader.base.toErrorMessageResId
import dev.miladanbari.newsreader.view.news.model.ArticleItem
import dev.miladanbari.newsreader.view.theme.space

@Composable
internal fun NewsScreenContent(
    lazyPagingItems: LazyPagingItems<ArticleItem>,
    lazyListState: LazyListState,
    showSnackbar: (String) -> Unit,
    onArticleClick: (ArticleItem) -> Unit,
    modifier: Modifier = Modifier
) {
    NewsLazyList(
        lazyPagingItems, lazyListState, showSnackbar, onArticleClick, modifier
    )
}

@Composable
internal fun NewsLazyList(
    lazyPagingItems: LazyPagingItems<ArticleItem>,
    lazyListState: LazyListState,
    showSnackbar: (String) -> Unit,
    onArticleClick: (ArticleItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(MaterialTheme.space.small),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.space.small),
        modifier = modifier.fillMaxSize()
    ) {
        with(lazyPagingItems) {
            if (loadState.refresh !is LoadState.Loading && loadState.refresh !is LoadState.Loading) {
                items(
                    count = lazyPagingItems.itemCount,
                    key = { index -> (lazyPagingItems[index])!!.id }
                ) { index ->
                    val item = requireNotNull(lazyPagingItems[index])
                    ArticleListItem(item, onItemClick = onArticleClick)
                }
            }

            when {
                loadState.refresh is LoadState.Loading -> item {
                    Loading(Modifier.fillParentMaxHeight())
                }

                loadState.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    item {
                        Failure(
                            error.error.toErrorMessageResId(),
                            showSnackbar,
                            modifier = Modifier.fillParentMaxHeight(),
                            onRetry = ::retry,
                        )
                    }
                }

                loadState.append is LoadState.Loading -> item {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .padding(vertical = MaterialTheme.space.xSmall)
                            .fillMaxWidth()
                    )
                }

                loadState.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item {
                        Failure(
                            error.error.toErrorMessageResId(),
                            showSnackbar,
                            modifier = Modifier.background(Color.Red),
                            onRetry = ::retry
                        )
                    }
                }
            }
        }
    }
}
