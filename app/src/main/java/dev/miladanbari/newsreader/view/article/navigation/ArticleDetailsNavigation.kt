package dev.miladanbari.newsreader.view.article.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.miladanbari.newsreader.view.article.ui.ArticleDetailsScreenRoute
import dev.miladanbari.newsreader.view.news.model.ArticleItem
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
internal data class ArticleDetailsRoute(val articleItem: ArticleItem) {

    companion object {

        private const val ARTICLE_ITEM_ARG_KEY = "articleItem"
        val typeMap = mapOf(typeOf<ArticleItem>() to navTypeOf<ArticleItem>(hasUrl = true))

        @Throws(IllegalArgumentException::class)
        fun getArgFrom(savedStateHandle: SavedStateHandle): ArticleItem {
            return requireNotNull(savedStateHandle[ARTICLE_ITEM_ARG_KEY])
        }
    }
}

internal fun NavController.navigateToArticleDetails(articleItem: ArticleItem) {
    navigate(ArticleDetailsRoute(articleItem))
}

internal fun NavGraphBuilder.articleDetailsScreen(navController: NavController) {
    composable<ArticleDetailsRoute>(typeMap = ArticleDetailsRoute.typeMap) {
        ArticleDetailsScreenRoute(
            onBackClick = navController::popBackStack
        )
    }
}
