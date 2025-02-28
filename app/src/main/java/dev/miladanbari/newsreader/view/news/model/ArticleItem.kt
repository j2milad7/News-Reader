package dev.miladanbari.newsreader.view.news.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * This class represents an article item in the news feed. Also, it is used as a navigation argument.
 * ArticleArgument class is omitted due to reduce code complexity and time limitation.
 */
@Parcelize
@Serializable
data class ArticleItem(
    val source: SourceItem,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String?,
    val formattedPublishedDateTime: String,
    val content: String,
    // This ID is defined for the purposes of the LazyColumn and should be unique for each item.
    // It can be replaced by a ID which is generated by the server.
    val id: Long,
) : Parcelable

@Parcelize
@Serializable
data class SourceItem(
    val id: String?,
    val name: String,
) : Parcelable
