package dev.miladanbari.newsreader.view.article.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.miladanbari.newsreader.view.article.ArticleDetailsViewModel
import kotlinx.coroutines.launch

@Composable
internal fun ArticleDetailsScreenRoute(
    onBackClick: () -> Unit,
    viewModel: ArticleDetailsViewModel = hiltViewModel()
) {
    val viewState = viewModel.viewStateFlow.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    ArticleDetailsScreen(
        viewState = viewState,
        snackbarHostState = snackbarHostState,
        showSnackbar = { scope.launch { snackbarHostState.showSnackbar(it) } },
        onBackClick = onBackClick
    )
}
