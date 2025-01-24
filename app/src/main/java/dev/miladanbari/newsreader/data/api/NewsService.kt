package dev.miladanbari.newsreader.data.api

import dev.miladanbari.newsreader.data.model.Article
import dev.miladanbari.newsreader.data.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response.Success<List<Article>>
}
