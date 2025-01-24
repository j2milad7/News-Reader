package dev.miladanbari.newsreader.view.news.model

data class ArticleItem(
    val source: SourceItem,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
)

data class SourceItem(
    val id: String?,
    val name: String,
)
