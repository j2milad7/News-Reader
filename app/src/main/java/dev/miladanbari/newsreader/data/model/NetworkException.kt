package dev.miladanbari.newsreader.data.model

sealed class NetworkException(
    message: String? = null,
    cause: Throwable? = null
) : Throwable(message = message ?: cause?.message, cause) {

    object Unexpected : NetworkException(message = "Unexpected exception")

    class IO(
        override val cause: Throwable
    ) : NetworkException(cause = cause)

    class Http(
        override val cause: Throwable
    ) : NetworkException(cause = cause)

    class Disconnected(
        override val cause: Throwable
    ) : NetworkException(cause = cause)

    class Server(
        val code: ErrorCodeDto,
        override val message: String,
        override val cause: Throwable,
    ) : NetworkException(
        message = message,
        cause = cause
    )
}


