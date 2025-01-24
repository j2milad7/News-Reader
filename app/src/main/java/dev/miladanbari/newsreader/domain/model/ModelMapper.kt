package dev.miladanbari.newsreader.domain.model

import dev.miladanbari.newsreader.data.model.ArticleDto
import dev.miladanbari.newsreader.data.model.SourceDto

fun SourceDto.toSourceModel(): SourceModel {
    return SourceModel(id, name)
}

fun ArticleDto.toArticleModel(): ArticleModel {
    return ArticleModel(
        source.toSourceModel(),
        author,
        title,
        description,
        url,
        urlToImage,
        publishedAt,
        content
    )
}
