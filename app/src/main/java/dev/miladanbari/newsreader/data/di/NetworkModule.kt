package dev.miladanbari.newsreader.data.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.miladanbari.newsreader.data.api.NewsService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val API_KEY_HEADER_KEY = "X-Api-Key"
    private const val API_KEY_HEADER_VALUE = "d397f5fc59f44bce8dea3f414348659c"

    private const val API_BASE_URL = "https://newsapi.org/"

    @Reusable
    @Provides
    fun provideOkHttpBuilder(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain: Interceptor.Chain ->
                val originalRequest: Request = chain.request()
                val requestBuilder: Request.Builder = chain.request().newBuilder()
                    .header(API_KEY_HEADER_KEY, API_KEY_HEADER_VALUE)
                    .method(originalRequest.method, originalRequest.body)
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    @Reusable
    @Provides
    fun provideRetrofit(okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Reusable
    @Provides
    fun provideNewsService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }
}
