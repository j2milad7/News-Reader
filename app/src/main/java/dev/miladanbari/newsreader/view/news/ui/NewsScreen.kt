package dev.miladanbari.newsreader.view.news.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.miladanbari.newsreader.R
import dev.miladanbari.newsreader.base.Failure
import dev.miladanbari.newsreader.base.Loading
import dev.miladanbari.newsreader.base.toErrorMessageResId
import dev.miladanbari.newsreader.view.news.model.ArticleItem
import dev.miladanbari.newsreader.view.news.model.SourceItem
import dev.miladanbari.newsreader.view.theme.NewsReaderTheme
import dev.miladanbari.newsreader.view.theme.ThemePreview
import dev.miladanbari.newsreader.view.theme.space
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NewsScreen(
    lazyPagingItems: LazyPagingItems<ArticleItem>,
    lazyListState: LazyListState,
    snackbarHostState: SnackbarHostState,
    showSnackbar: (String) -> Unit,
    onArticleClick: (ArticleItem) -> Unit
) {
    Scaffold(
        contentColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(stringResource(id = R.string.title_news))
                }
            )
        },
        content = {
            NewsScreenContent(
                lazyPagingItems,
                lazyListState,
                showSnackbar,
                onArticleClick,
                modifier = Modifier.padding(it)
            )
        }
    )
}

@Composable
internal fun NewsScreenContent(
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
        items(
            count = lazyPagingItems.itemCount,
            key = { index -> (lazyPagingItems[index])!!.id }
        ) { index ->
            val item = requireNotNull(lazyPagingItems[index])
            ArticleListItem(item, onItemClick = onArticleClick)
        }

        with(lazyPagingItems) {
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
                            modifier = Modifier,
                            onRetry = ::retry
                        )
                    }
                }
            }
        }
    }
}

@ThemePreview
@Composable
fun PreviewNewsScreen() {
    NewsReaderTheme {
        NewsScreen(
            lazyPagingItems = flowOf(
                PagingData.from(
                    listOf(
                        ArticleItem(
                            source = SourceItem(id = "test source id", name = "test source name"),
                            author = "test author",
                            title = "test title",
                            description = "test description",
                            url = "test url",
                            urlToImage = "test url to image",
                            publishedAt = "test published at",
                            content = "test content"
                        )
                    ),
                    sourceLoadStates = LoadStates(
                        refresh = LoadState.NotLoading(true),
                        prepend = LoadState.NotLoading(true),
                        append = LoadState.NotLoading(true)
                    )
                )
            ).collectAsLazyPagingItems(),
            lazyListState = LazyListState(),
            snackbarHostState = SnackbarHostState(),
            showSnackbar = { },
            onArticleClick = { }
        )
    }
}
