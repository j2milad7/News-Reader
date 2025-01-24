package dev.miladanbari.newsreader.view.news.model

import dev.miladanbari.newsreader.domain.model.ArticleModel
import dev.miladanbari.newsreader.domain.model.SourceModel

fun SourceModel.toSourceItem(): SourceItem {
    return SourceItem(id, name)
}

fun ArticleModel.toArticleItem(): ArticleItem {
    return ArticleItem(
        source.toSourceItem(),
        author,
        title,
        description,
        url,
        urlToImage,
        publishedAt,
        content
    )
}
