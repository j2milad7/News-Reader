package dev.miladanbari.newsreader.view.article

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.miladanbari.newsreader.base.toErrorMessageResId
import dev.miladanbari.newsreader.view.article.navigation.ArticleDetailsRoute
import dev.miladanbari.newsreader.view.base.ViewState
import dev.miladanbari.newsreader.view.news.model.ArticleItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
internal class ArticleDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _viewStateFlow = MutableStateFlow(value = getArticleItem(savedStateHandle))
    internal val viewStateFlow: StateFlow<ViewState<ArticleItem>> = _viewStateFlow.asStateFlow()

    private fun getArticleItem(savedStateHandle: SavedStateHandle): ViewState<ArticleItem> {
        return try {
            ViewState.Success(ArticleDetailsRoute.getArgFrom(savedStateHandle))
        } catch (e: Exception) {
            ViewState.Failure(e.toErrorMessageResId())
        }
    }
}
