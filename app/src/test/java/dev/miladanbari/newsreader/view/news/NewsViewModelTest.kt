package dev.miladanbari.newsreader.view.news

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import dev.miladanbari.newsreader.domain.usecase.GetNewsUseCase
import dev.miladanbari.newsreader.util.CoroutinesTestRule
import dev.miladanbari.newsreader.util.testArticleModelList
import dev.miladanbari.newsreader.view.news.model.toArticleItem
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
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
class NewsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val getNewsUseCase: GetNewsUseCase = mock()
    private lateinit var viewModel: NewsViewModel

    @Test
    fun `successful response should be received once 'newsPagerFlow' is being observed`() =
        runTest {
            // GIVEN
            val pagingData = PagingData.from(
                testArticleModelList,
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(true),
                    prepend = LoadState.NotLoading(true),
                    append = LoadState.NotLoading(true)
                )
            )
            val expectedResult = testArticleModelList.map { it.toArticleItem() }
            whenever(
                getNewsUseCase(query = any(), pageSize = any(), prefetchDistance = any())
            ) doReturn flowOf(pagingData)

            // WHEN
            viewModel = NewsViewModel(getNewsUseCase)
            val actualResult = viewModel.newsPagerFlow.asSnapshot()

            // THEN
            verify(getNewsUseCase).invoke(query = any(), pageSize = any(), prefetchDistance = any())
            assertThat(actualResult, `is`(expectedResult))
        }

    @Test(expected = Exception::class)
    fun `an error should be received once 'newsPagerFlow' is being observed`() = runTest {
        // GIVEN
        val exception = IOException()
        whenever(
            getNewsUseCase(query = any(), pageSize = any(), prefetchDistance = any())
        ) doThrow exception

        // WHEN
        viewModel = NewsViewModel(getNewsUseCase)
        viewModel.newsPagerFlow.asSnapshot()

        // THEN
        verify(getNewsUseCase).invoke(query = any(), pageSize = any(), prefetchDistance = any())
    }
}
