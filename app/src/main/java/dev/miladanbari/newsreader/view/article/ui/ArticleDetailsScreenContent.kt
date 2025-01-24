package dev.miladanbari.newsreader.view.article.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextDecoration
import coil.compose.AsyncImage
import dev.miladanbari.newsreader.R
import dev.miladanbari.newsreader.base.Failure
import dev.miladanbari.newsreader.base.Loading
import dev.miladanbari.newsreader.base.ViewStateComponent
import dev.miladanbari.newsreader.view.base.ViewState
import dev.miladanbari.newsreader.view.news.model.ArticleItem
import dev.miladanbari.newsreader.view.theme.space

@Composable
internal fun ArticleDetailsScreenContent(
    viewState: State<ViewState<ArticleItem>>,
    showSnackbar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ViewStateComponent(
        modifier = modifier,
        viewState = viewState.value,
        loadingComponent = { Loading(it) },
        failureComponent = { messageResId, modifier ->
            Failure(messageResId, showSnackbar, modifier)
        }
    ) {
        ArticleDetails(articleItem = it, modifier = modifier)
    }
}

@Composable
internal fun ArticleDetails(
    articleItem: ArticleItem,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = articleItem.urlToImage,
            contentDescription = articleItem.title,
            contentScale = ContentScale.FillWidth,
            placeholder = rememberVectorPainter(
                ImageVector.vectorResource(id = R.drawable.placeholder)
            ),
            error = rememberVectorPainter(
                ImageVector.vectorResource(id = R.drawable.placeholder_error)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        )

        val contentPadding = MaterialTheme.space.medium
        Text(
            text = articleItem.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(
                    top = contentPadding,
                    start = contentPadding,
                    end = contentPadding
                )
                .fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = contentPadding,
                    start = contentPadding,
                    end = contentPadding
                )
        ) {
            Text(
                text = articleItem.source.name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = articleItem.formattedPublishedDateTime,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.wrapContentWidth()
            )
        }

        Text(
            text = articleItem.content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(
                    top = contentPadding,
                    start = contentPadding,
                    end = contentPadding
                )
                .fillMaxWidth()
        )

        val uriHandler = LocalUriHandler.current
        Text(
            text = articleItem.url,
            style = MaterialTheme.typography.bodyMedium,
            textDecoration = TextDecoration.Underline,
            color = Color.Blue,
            modifier = Modifier
                .padding(
                    top = contentPadding,
                    start = contentPadding,
                    end = contentPadding
                )
                .fillMaxWidth()
                .clickable { uriHandler.openUri(articleItem.url) }
        )
    }
}


