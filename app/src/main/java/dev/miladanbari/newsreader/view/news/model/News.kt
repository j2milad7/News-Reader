package dev.miladanbari.newsreader.view.news.model

import androidx.annotation.StringRes
import dev.miladanbari.newsreader.R

data class FilterAndSort(val searchQuery: String, val sort: NewsSort)

enum class NewsSort(@StringRes val titleResId: Int, val value: String) {
    RELEVANCE(titleResId = R.string.title_sort_relevance, value = "relevancy"),
    POPULARITY(titleResId = R.string.title_sort_popular, value = "popularity"),
    NEWEST(titleResId = R.string.title_sort_newest, value = "publishedAt"),
}
