package dev.miladanbari.newsreader.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import dev.miladanbari.newsreader.view.news.navigation.NewsRoute
import dev.miladanbari.newsreader.view.news.navigation.newsScreen

@Composable
fun NewsReaderNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Any = NewsRoute
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        newsScreen(navController)
    }
}
