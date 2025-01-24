package dev.miladanbari.newsreader.data.model

import com.google.gson.annotations.SerializedName

sealed class ResponseDto<out T> {

    abstract val status: StatusDto

    data class Success<out T>(
        @SerializedName("status")
        override val status: StatusDto,
        @SerializedName("totalResults")
        val totalResults: Int,
        @SerializedName("articles")
        val data: T
    ) : ResponseDto<T>()

    data class Error(
        @SerializedName("status")
        override val status: StatusDto,
        @SerializedName("code")
        val code: ErrorCodeDto?,
        @SerializedName("message")
        val message: String
    ) : ResponseDto<Nothing>()
}

enum class StatusDto {
    @SerializedName("ok")
    OK,

    @SerializedName("error")
    ERROR
}

// TODO: More error codes can be added. Check this link for more information:
//  https://newsapi.org/docs/errors
enum class ErrorCodeDto {

    @SerializedName("maximumResultsReached")
    MAXIMUM_RESULTS_REACHED,

    @SerializedName("unexpectedError")
    UNEXPECTED_ERROR,

    @SerializedName("parameterInvalid")
    PARAMETER_INVALID
}
