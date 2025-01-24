package dev.miladanbari.newsreader.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.miladanbari.newsreader.R
import dev.miladanbari.newsreader.view.base.ViewState
import dev.miladanbari.newsreader.view.theme.ThemePreview

/**
 * These components are designed to handle different states of the view
 */

@Composable
fun <T> ViewStateComponent(
    modifier: Modifier = Modifier,
    viewState: ViewState<T>,
    loadingComponent: @Composable (Modifier) -> Unit,
    failureComponent: @Composable (LocalError, Modifier) -> Unit,
    content: @Composable (T) -> Unit
) {

    @Suppress("UNCHECKED_CAST")
    when (viewState) {
        is ViewState.Loading -> loadingComponent.invoke(modifier)
        is ViewState.Failure -> failureComponent.invoke(viewState.localError, modifier)
        is ViewState.Success<T> -> content.invoke(viewState.data)
    }
}

@Composable
internal fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier.wrapContentSize(),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
internal fun Failure(
    localError: LocalError,
    showSnackbar: (String) -> Unit,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null
) {
    val message = localError.run { message ?: messageResId?.let { stringResource(id = it) } }
    message?.let { showSnackbar(it) }

    onRetry?.let {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(modifier = modifier.wrapContentSize(), onClick = it) {
                Text(text = stringResource(id = R.string.label_retry))
            }
        }
    }
}

@ThemePreview
@Composable
fun ViewStateComponentPreView() {
    ViewStateComponent(
        modifier = Modifier,
        viewState = ViewState.Loading,
        loadingComponent = {},
        failureComponent = { message, modifier -> }
    ) {}
}

@ThemePreview
@Composable
fun LoadingPreView() {
    Loading(modifier = Modifier)
}

@ThemePreview
@Composable
fun FailurePreView() {
    Failure(
        localError = LocalError(messageResId = R.string.error_general),
        onRetry = {},
        showSnackbar = {},
        modifier = Modifier
    )
}
