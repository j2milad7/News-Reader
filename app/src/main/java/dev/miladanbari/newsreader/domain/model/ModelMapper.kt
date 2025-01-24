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
        title.orEmpty(),
        description.orEmpty(),
        url.orEmpty(),
        urlToImage,
        publishedAt.orEmpty(),
        content.orEmpty()
    )
}
