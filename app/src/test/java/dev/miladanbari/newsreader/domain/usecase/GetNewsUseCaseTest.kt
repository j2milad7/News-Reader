package dev.miladanbari.newsreader.domain.usecase

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import dev.miladanbari.newsreader.domain.model.ArticleModel
import dev.miladanbari.newsreader.domain.model.toArticleModel
import dev.miladanbari.newsreader.domain.repository.NewsRepository
import dev.miladanbari.newsreader.util.testArticleDtoList
import kotlinx.coroutines.flow.flowOf
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
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class GetNewsUseCaseTest {

    private val newsRepository: NewsRepository = mock()
    private lateinit var useCase: GetNewsUseCase

    @Before
    fun setUp() {
        useCase = GetNewsUseCase(newsRepository)
    }

    @Test
    fun `use case should get the news list from repository successfully`() = runTest {
        // GIVEN
        val expectedResult: List<ArticleModel> = testArticleDtoList.map { it.toArticleModel() }
        whenever(
            newsRepository.getNewsFlow(
                query = any(),
                sortBy = any(),
                pageSize = any(),
                prefetchDistance = any()
            )
        ) doReturn flowOf(PagingData.from(testArticleDtoList))

        // WHEN
        val actualResult = useCase(
            query = "test query",
            sortBy = "test",
            pageSize = 10,
            prefetchDistance = 0
        ).asSnapshot()

        // THEN
        assertThat(actualResult, `is`(expectedResult))
        verify(newsRepository).getNewsFlow(
            query = any(),
            sortBy = any(),
            pageSize = any(),
            prefetchDistance = any()
        )
    }

    @Test(expected = Exception::class)
    fun `an error should be returned when repository throws an exception`() = runTest {
        // GIVEN
        val exception = IOException()
        whenever(
            newsRepository.getNewsFlow(
                query = any(),
                sortBy = any(),
                pageSize = any(),
                prefetchDistance = any()
            )
        ) doThrow exception

        // WHEN
        useCase(
            query = "test query",
            sortBy = "test",
            pageSize = 10,
            prefetchDistance = 0
        ).asSnapshot()

        // THEN
        verify(newsRepository).getNewsFlow(
            query = any(),
            sortBy = any(),
            pageSize = any(),
            prefetchDistance = any()
        )
    }
}

