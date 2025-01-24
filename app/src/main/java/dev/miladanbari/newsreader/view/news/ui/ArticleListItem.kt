package dev.miladanbari.newsreader.view.news.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import dev.miladanbari.newsreader.R
import dev.miladanbari.newsreader.view.news.model.ArticleItem
import dev.miladanbari.newsreader.view.news.model.SourceItem
import dev.miladanbari.newsreader.view.theme.NewsReaderTheme
import dev.miladanbari.newsreader.view.theme.ThemePreview
import dev.miladanbari.newsreader.view.theme.space

@Composable
internal fun ArticleListItem(
    item: ArticleItem,
    onItemClick: (ArticleItem) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(
                    vertical = MaterialTheme.space.small,
                    horizontal = MaterialTheme.space.small
                )
                .clickable { onItemClick(item) }
        ) {
            // It is better to use a thumbnail for the image.
            AsyncImage(
                model = item.urlToImage,
                contentScale = ContentScale.Crop,
                contentDescription = item.title,
                placeholder = rememberVectorPainter(
                    ImageVector.vectorResource(id = R.drawable.placeholder)
                ),
                error = rememberVectorPainter(
                    ImageVector.vectorResource(id = R.drawable.placeholder_error)
                ),
                modifier = modifier.size(128.dp)
            )

            Column(
                modifier = modifier
                    .padding(start = MaterialTheme.space.small)
                    .fillMaxWidth()
            ) {

                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.fillMaxWidth()
                )

                Text(
                    text = item.source.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(top = MaterialTheme.space.small)
                        .fillMaxWidth()
                )

                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = MaterialTheme.space.small)
                        .fillMaxWidth()
                )

            }
        }
    }
}

@ThemePreview
@Composable
fun PreviewArticleListItem() {
    NewsReaderTheme {
        ArticleListItem(
            item = ArticleItem(
                source = SourceItem(id = "test source id", name = "test source name"),
                author = "test author",
                title = "test title",
                description = "test description",
                url = "test url",
                urlToImage = "test url to image",
                publishedAt = "test published at",
                content = "test content"
            ),
            onItemClick = { }
        )
    }
}
