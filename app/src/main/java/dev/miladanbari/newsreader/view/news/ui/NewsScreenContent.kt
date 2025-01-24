package dev.miladanbari.newsreader.view.news.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import dev.miladanbari.newsreader.R
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
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchTextField(
            onSearchQueryChange = onSearchQueryChange,
            hintTextResId = R.string.hint_search
        )
        NewsLazyList(
            lazyPagingItems,
            lazyListState,
            showSnackbar,
            onArticleClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
internal fun SearchTextField(
    onSearchQueryChange: (String) -> Unit,
    @StringRes hintTextResId: Int,
    modifier: Modifier = Modifier
) {
    val defaultSearchQuery = stringResource(id = R.string.text_default_search_query)
    var textFieldValue by rememberSaveable { mutableStateOf(defaultSearchQuery) }
    val onValueChange: (String) -> Unit = remember {
        { text ->
            textFieldValue = text
            onSearchQueryChange(text)
        }
    }

    TextField(
        value = textFieldValue,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = stringResource(id = hintTextResId),
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
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
                            modifier = Modifier.padding(vertical = MaterialTheme.space.xSmall),
                            onRetry = ::retry
                        )
                    }
                }
            }
        }
    }
}
