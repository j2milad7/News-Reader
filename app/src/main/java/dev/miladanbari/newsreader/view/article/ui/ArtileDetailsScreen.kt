package dev.miladanbari.newsreader.view.article.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.miladanbari.newsreader.R
import dev.miladanbari.newsreader.view.base.ViewState
import dev.miladanbari.newsreader.view.news.model.ArticleItem
import dev.miladanbari.newsreader.view.news.model.SourceItem
import dev.miladanbari.newsreader.view.theme.NewsReaderTheme
import dev.miladanbari.newsreader.view.theme.ThemePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ArticleDetailsScreen(
    viewState: State<ViewState<ArticleItem>>,
    snackbarHostState: SnackbarHostState,
    showSnackbar: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        contentColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                title = {
                    Text(stringResource(id = R.string.title_article_details))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "back",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            )
        },
        content = {
            ArticleDetailsScreenContent(
                viewState = viewState,
                showSnackbar = showSnackbar,
                modifier = Modifier.padding(it)
            )
        }
    )
}

@SuppressLint("UnrememberedMutableState")
@ThemePreview
@Composable
fun PreViewArticleDetailsScreen() {
    NewsReaderTheme {
        ArticleDetailsScreen(
            viewState = mutableStateOf(
                ViewState.Success(
                    ArticleItem(
                        source = SourceItem(id = "test source id", name = "test source name"),
                        author = "test author",
                        title = "test title",
                        description = "test description",
                        url = "test url",
                        urlToImage = "test url to image",
                        formattedPublishedDateTime = "test published at",
                        content = "test content"
                    )
                )
            ),
            snackbarHostState = SnackbarHostState(),
            showSnackbar = {},
            onBackClick = {}
        )
    }
}
