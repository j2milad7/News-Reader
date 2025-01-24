package dev.miladanbari.newsreader.data.repository

import androidx.paging.testing.asSnapshot
import dev.miladanbari.newsreader.data.api.NewsService
import dev.miladanbari.newsreader.domain.repository.NewsRepository
import dev.miladanbari.newsreader.util.testSuccessResponseDto
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class NewsRepositoryTest {

    private val newsService: NewsService = mock()
    private lateinit var repository: NewsRepository

    @Before
    fun setUp() {
        repository = NewsRepositoryImpl(newsService)
    }

    @Test
    fun `successful response should be received once 'getNewsFlow' is called`() =
        runTest {
            // GIVEN
            whenever(
                newsService.getNews(query = any(), sortBy = any(), page = any(), pageSize = any())
            ) doReturn testSuccessResponseDto

            // WHEN
            val actualResult = repository.getNewsFlow(
                query = "test query",
                sortBy = "test",
                pageSize = 10,
                prefetchDistance = 0
            ).asSnapshot()

            // THEN
            assertThat(actualResult, `is`(testSuccessResponseDto.data))
            verify(newsService).getNews(
                query = any(),
                sortBy = any(),
                page = any(),
                pageSize = any()
            )
        }

    @Test(expected = Exception::class)
    fun `an error should be received once 'itemsPagerFlow' is being observed`() = runTest {
        // GIVEN
        val expectedException = Exception("Test")
        whenever(
            newsService.getNews(query = any(), sortBy = any(), page = any(), pageSize = any())
        ) doThrow expectedException

        // WHEN
        repository.getNewsFlow(
            query = "test query",
            sortBy = "test",
            pageSize = 10,
            prefetchDistance = 0
        ).asSnapshot()

        // THEN
        verify(newsService).getNews(query = any(), sortBy = any(), page = any(), pageSize = any())
    }
}
