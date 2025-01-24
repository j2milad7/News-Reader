package dev.miladanbari.newsreader.data.util

import com.google.gson.GsonBuilder
import dev.miladanbari.newsreader.data.model.ErrorCode
import dev.miladanbari.newsreader.data.model.NetworkException
import dev.miladanbari.newsreader.data.model.Response
import dev.miladanbari.newsreader.data.model.Status
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

fun Throwable.toNetworkException(): NetworkException {
    return when (this) {
        is NetworkException -> this
        is UnknownHostException -> NetworkException.Disconnected(cause = this)
        is IOException -> NetworkException.IO(cause = this)
        is HttpException -> this.toHttpException()
        else -> NetworkException.Unexpected
    }
}

private fun Throwable.toHttpException(): NetworkException {
    check(this is HttpException) { "$this is not a HttpException" }
    return when {
        isServerError() -> NetworkException.Http(cause = this)
        isBadRequest() -> {
            val serverError = getServerError()
            NetworkException.Server(
                code = serverError.code,
                message = serverError.message,
                cause = this
            )
        }

        else -> NetworkException.Unexpected
    }
}

private fun HttpException.getServerError(): Response.Error {
    val errorBody = response()?.errorBody()?.string()
    return try {
        GsonBuilder().create().fromJson(errorBody, Response.Error::class.java)
    } catch (ignored: Exception) {
        Response.Error(Status.ERROR, ErrorCode.UNEXPECTED_ERROR, message.orEmpty())
    }
}

private fun HttpException.isBadRequest(): Boolean {
    /* 4XX: bad request */
    return (code() / 100) == 4
}

private fun HttpException.isServerError(): Boolean {
    /* 5XX: server error */
    return (code() / 100) == 5
}
