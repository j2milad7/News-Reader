package dev.miladanbari.newsreader.base

import dev.miladanbari.newsreader.R
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

fun Throwable.toErrorMessageResId(): Int {
    return when (this) {
        is UnknownHostException,
        is IOException,
        is HttpException -> R.string.error_network

        else -> R.string.error_general
    }
}
