package dev.miladanbari.newsreader.data.model

import com.google.gson.annotations.SerializedName

sealed class Response<out T> {

    abstract val status: Status

    data class Success<out T>(
        @SerializedName("status")
        override val status: Status,
        @SerializedName("totalResults")
        val totalResults: Int,
        @SerializedName("articles")
        val data: T
    ) : Response<T>()

    data class Error(
        @SerializedName("status")
        override val status: Status,
        @SerializedName("code")
        val code: ErrorCode,
        @SerializedName("message")
        val message: String
    ) : Response<Nothing>()
}

enum class Status {
    @SerializedName("ok")
    OK,

    @SerializedName("error")
    ERROR
}

// TODO: More error codes can be added. Check this link for more information:
//  https://newsapi.org/docs/errors
enum class ErrorCode {

    @SerializedName("maximumResultsReached")
    MAXIMUM_RESULTS_REACHED,

    @SerializedName("unexpectedError")
    UNEXPECTED_ERROR,

    @SerializedName("parameterInvalid")
    PARAMETER_INVALID
}
