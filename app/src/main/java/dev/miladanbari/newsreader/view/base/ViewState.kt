package dev.miladanbari.newsreader.view.base

import dev.miladanbari.newsreader.base.LocalError

/**
 * A sealed interface for handling different states of a view
 */
sealed interface ViewState<out T> {

    object Loading : ViewState<Nothing>

    class Success<T>(val data: T) : ViewState<T>

    class Failure(val localError: LocalError) : ViewState<Nothing>
}

val ViewState<*>.isLoading: Boolean
    get() = this is ViewState.Loading

inline fun ViewState<*>.doOnError(func: (LocalError) -> Unit): ViewState<*> {
    if (this is ViewState.Failure) {
        func.invoke(localError)
    }
    return this
}
