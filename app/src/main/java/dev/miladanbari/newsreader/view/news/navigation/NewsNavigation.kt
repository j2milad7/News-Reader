package dev.miladanbari.newsreader.view.news.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.miladanbari.newsreader.view.news.ui.NewScreenRoute
import kotlinx.serialization.Serializable

@Serializable
internal object NewsRoute

internal fun NavGraphBuilder.newsScreen(navController: NavController) {
    composable<NewsRoute> {
        NewScreenRoute(navigateToArticleDetails = {})
    }
}
