package dev.miladanbari.newsreader.view.news.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.miladanbari.newsreader.R
import dev.miladanbari.newsreader.view.news.model.ArticleItem
import dev.miladanbari.newsreader.view.news.model.FilterAndSort
import dev.miladanbari.newsreader.view.news.model.NewsSort
import dev.miladanbari.newsreader.view.news.model.SourceItem
import dev.miladanbari.newsreader.view.speech.ui.LaunchSpeechRecognizer
import dev.miladanbari.newsreader.view.theme.NewsReaderTheme
import dev.miladanbari.newsreader.view.theme.ThemePreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NewsScreen(
    filterAndSortStat: State<FilterAndSort>,
    lazyPagingItems: LazyPagingItems<ArticleItem>,
    lazyListState: LazyListState,
    snackbarHostState: SnackbarHostState,
    showSnackbar: (String) -> Unit,
    onArticleClick: (ArticleItem) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSortChange: (NewsSort) -> Unit
) {

    val scope = rememberCoroutineScope()
    val refreshList: () -> Unit = {
        scope.launch {
            lazyListState.scrollToItem(index = 0)
            lazyPagingItems.refresh()
        }
    }
    var shouldLaunchSpeechRecognizer by remember { mutableStateOf(false) }
    if (shouldLaunchSpeechRecognizer) {
        // TODO: The refresh phrase could be localized to support voice command in different languages.
        val reloadPhrase = stringResource(id = R.string.refresh_command)
        LaunchSpeechRecognizer {
            if (it.equals(reloadPhrase, ignoreCase = true)) {
                refreshList()
            }
            shouldLaunchSpeechRecognizer = false
        }
    }

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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    shouldLaunchSpeechRecognizer = true
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_voice),
                    contentDescription = "Speech Recognizer"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = {
            NewsScreenContent(
                filterAndSortStat,
                lazyPagingItems,
                lazyListState,
                showSnackbar,
                onArticleClick,
                onSearchQueryChange,
                onSortChange,
                modifier = Modifier.padding(it)
            )
        }
    )
}

@SuppressLint("UnrememberedMutableState")
@ThemePreview
@Composable
fun PreviewNewsScreen() {
    NewsReaderTheme {
        NewsScreen(
            filterAndSortStat = mutableStateOf(
                FilterAndSort(
                    searchQuery = "Test",
                    NewsSort.NEWEST
                )
            ),
            lazyPagingItems = flowOf(
                PagingData.from(
                    listOf(
                        ArticleItem(
                            source = SourceItem(
                                id = "test source id",
                                name = "test source name"
                            ),
                            author = "test author",
                            title = "test title",
                            description = "test description",
                            url = "test url",
                            urlToImage = "test url to image",
                            formattedPublishedDateTime = "test published at",
                            content = "test content",
                            id = 0
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
            onArticleClick = { },
            onSearchQueryChange = {},
            onSortChange = {}
        )
    }
}
