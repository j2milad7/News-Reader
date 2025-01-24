package dev.miladanbari.newsreader.data.util

suspend inline fun <T> safeApiCall(
    crossinline call: suspend () -> T
): Result<T> = try {
    Result.success(call())
} catch (e: Throwable) {
    // General error types can be mapped to specific local error types
    Result.failure(e.toNetworkException())
}
