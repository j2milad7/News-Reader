package dev.miladanbari.newsreader.data.api

import dev.miladanbari.newsreader.data.model.ArticleDto
import dev.miladanbari.newsreader.data.model.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ResponseDto.Success<List<ArticleDto>>
}
