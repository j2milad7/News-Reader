package dev.miladanbari.newsreader.view.news.model

import dev.miladanbari.newsreader.domain.model.ArticleModel
import dev.miladanbari.newsreader.domain.model.SourceModel
import java.text.SimpleDateFormat

private const val DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private val inputFormat = SimpleDateFormat(DATE_TIME_FORMAT)
private val outputFormat = SimpleDateFormat("MMMM dd, yyyy HH:mm")

fun SourceModel.toSourceItem(): SourceItem {
    return SourceItem(id, name)
}

fun ArticleModel.toArticleItem(id: Long? = null): ArticleItem {
    val formattedPublishedDateTime = try {
        val date = requireNotNull(inputFormat.parse(publishedAt))
        outputFormat.format(date)
    } catch (e: Exception) {
        ""
    }

    return ArticleItem(
        source.toSourceItem(),
        author,
        title,
        description,
        url,
        urlToImage,
        formattedPublishedDateTime,
        content,
        id = id ?: System.nanoTime()
    )
}
