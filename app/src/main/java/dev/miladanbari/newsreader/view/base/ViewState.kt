package dev.miladanbari.newsreader.view.base

import androidx.annotation.StringRes

/**
 * A sealed interface for handling different states of a view
 */
sealed interface ViewState<out T> {

    object Loading : ViewState<Nothing>

    class Success<T>(val data: T) : ViewState<T>

    class Failure(@StringRes val errorMessageResId: Int) : ViewState<Nothing>
}

val ViewState<*>.isLoading: Boolean
    get() = this is ViewState.Loading

inline fun ViewState<*>.doOnError(func: (Int) -> Unit): ViewState<*> {
    if (this is ViewState.Failure) {
        func.invoke(errorMessageResId)
    }
    return this
}
