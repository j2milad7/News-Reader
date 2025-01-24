package dev.miladanbari.newsreader.data.paging

import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import dev.miladanbari.newsreader.data.api.NewsService
import dev.miladanbari.newsreader.data.model.NetworkException
import dev.miladanbari.newsreader.data.util.toNetworkException
import dev.miladanbari.newsreader.util.testErrorJson
import dev.miladanbari.newsreader.util.testSuccessResponseDto
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class NewsPagingSourceTest {

    private val newsService: NewsService = mock()
    private lateinit var pagingSource: NewsPagingSource
    private val query = "test query"
    private val sortBy = "test"

    @Before
    fun setUp() {
        pagingSource = NewsPagingSource(newsService, query, sortBy)
    }

    @Test
    fun `a successful response should be get once 'load' is called`() = runTest {
        // GIVEN
        val key = 1
        val loadSize = 10
        val loadParams = LoadParams.Refresh(
            key = key,
            loadSize = loadSize,
            placeholdersEnabled = false
        )
        whenever(
            newsService.getNews(query, sortBy, page = key, pageSize = loadSize)
        ) doReturn testSuccessResponseDto

        // WHEN
        val actualResult = pagingSource.load(loadParams) as LoadResult.Page

        // THEN
        assertThat(actualResult.data, `is`(testSuccessResponseDto.data))
        assertThat(actualResult.prevKey, `is`(nullValue()))
        assertThat(actualResult.nextKey, `is`(key + 1))
        verify(newsService).getNews(query, sortBy, page = key, pageSize = loadSize)
    }

    @Test(expected = Exception::class)
    fun `an error response should be get when 'load' is called`() = runTest {
        // GIVEN
        val expectedException = Exception("Error").toNetworkException()
        val key = 1
        val loadSize = 10
        val loadParams = LoadParams.Refresh(
            key = key,
            loadSize = loadSize,
            placeholdersEnabled = false
        )
        whenever(
            newsService.getNews(query, sortBy, page = key, pageSize = loadSize)
        ) doThrow expectedException

        // WHEN
        val actualResult = pagingSource.load(loadParams) as LoadResult.Error

        // THEN
        assertThat(actualResult.throwable, `is`(NetworkException.Unexpected))
        verify(newsService).getNews(query, sortBy, page = key, pageSize = loadSize)
    }

    @Test
    fun `the next key should be null in case of specific error when 'load' is called`() =
        runTest {
            // GIVEN
            Exception("Error")
            val key = 1
            val loadSize = 10
            val loadParams = LoadParams.Refresh(
                key = key,
                loadSize = loadSize,
                placeholdersEnabled = false
            )
            val responseBody: ResponseBody = mock()
            whenever(responseBody.string()) doReturn testErrorJson
            val response: Response<Any> = mock()
            whenever(response.code()) doReturn 400
            whenever(response.message()) doReturn "Test error"
            whenever(response.errorBody()) doReturn responseBody
            val expectedException = HttpException(response)
            whenever(
                newsService.getNews(query, sortBy, page = key, pageSize = loadSize)
            ) doThrow expectedException

            // WHEN
            val actualResult = pagingSource.load(loadParams) as LoadResult.Page

            // THEN
            assertThat(actualResult.data.isEmpty(), `is`(true))
            assertThat(actualResult.prevKey, `is`(key - 1))
            assertThat(actualResult.nextKey, `is`(nullValue()))
            verify(newsService).getNews(query, sortBy, page = key, pageSize = loadSize)
        }
}
