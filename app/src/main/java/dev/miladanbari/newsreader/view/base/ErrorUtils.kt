package dev.miladanbari.newsreader.base

import androidx.annotation.StringRes
import dev.miladanbari.newsreader.R
import dev.miladanbari.newsreader.data.model.NetworkException

data class LocalError(@StringRes val messageResId: Int? = null, val message: String? = null)

// TODO: Cover more error cases to provide a better user experience with a relevant error message
fun Throwable.toLocalError(): LocalError {
    return when (this) {
        is NetworkException.Server -> {
            LocalError(messageResId = R.string.error_network, message = this.message)
        }

        is NetworkException.Disconnected -> LocalError(messageResId = R.string.error_connection)
        is NetworkException.Unexpected,
        is NetworkException.IO,
        is NetworkException.Http,
        -> LocalError(messageResId = R.string.error_network)

        else -> LocalError(messageResId = R.string.error_general)
    }
}
