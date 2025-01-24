package dev.miladanbari.newsreader.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.miladanbari.newsreader.data.api.NewsService
import dev.miladanbari.newsreader.data.model.ArticleDto
import dev.miladanbari.newsreader.data.model.ErrorCodeDto
import dev.miladanbari.newsreader.data.model.NetworkException
import dev.miladanbari.newsreader.data.model.ResponseDto
import dev.miladanbari.newsreader.data.util.toNetworkException

class NewsPagingSource(
    private val newsService: NewsService,
    private val query: String,
    private val sortBy: String?
) : PagingSource<Int, ArticleDto>() {

    override fun getRefreshKey(state: PagingState<Int, ArticleDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleDto> {
        val page = params.key ?: 1
        return try {
            val response: ResponseDto.Success<List<ArticleDto>> = newsService.getNews(
                query,
                sortBy,
                page,
                pageSize = params.loadSize
            )
            LoadResult.Page(
                data = response.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            val networkException = e.toNetworkException()
            return if (networkException is NetworkException.Server
                && networkException.code == ErrorCodeDto.MAXIMUM_RESULTS_REACHED
            ) {
                LoadResult.Page(
                    data = listOf(),
                    prevKey = page - 1,
                    nextKey = null
                )
            } else {
                LoadResult.Error(throwable = networkException)
            }
        }
    }
}
