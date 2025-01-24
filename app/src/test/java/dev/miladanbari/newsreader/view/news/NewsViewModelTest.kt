package dev.miladanbari.newsreader.view.news

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.shazam.shazamcrest.matcher.Matchers.sameBeanAs
import dev.miladanbari.newsreader.domain.usecase.GetNewsUseCase
import dev.miladanbari.newsreader.util.CoroutinesTestRule
import dev.miladanbari.newsreader.util.testArticleModelList
import dev.miladanbari.newsreader.view.news.model.FilterAndSort
import dev.miladanbari.newsreader.view.news.model.NewsSort
import dev.miladanbari.newsreader.view.news.model.toArticleItem
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class NewsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val getNewsUseCase: GetNewsUseCase = mock()
    private val pagingData = PagingData.from(
        testArticleModelList,
        sourceLoadStates = LoadStates(
            refresh = LoadState.NotLoading(true),
            prepend = LoadState.NotLoading(true),
            append = LoadState.NotLoading(true)
        )
    )
    private val expectedResult = testArticleModelList.mapIndexed { index, model ->
        model.toArticleItem(
            id = index.toLong()
        )
    }
    private lateinit var viewModel: NewsViewModel

    @Before
    fun setUp() {
        whenever(
            getNewsUseCase(
                query = any(),
                sortBy = any(),
                pageSize = any(),
                prefetchDistance = any()
            )
        ) doReturn flowOf(pagingData)
        viewModel = NewsViewModel(getNewsUseCase)
    }

    @Test
    fun `successful response should be received once 'newsPagerFlow' is being observed`() =
        runTest {
            // GIVEN
            val expectedFilterAndSort = FilterAndSort(searchQuery = "Android", NewsSort.NEWEST)

            // WHEN
            val actualResult = viewModel.newsPagerFlow.asSnapshot()
            val actualFilterAndSort = viewModel.filterAndSortStateFlow.value

            // THEN
            verify(getNewsUseCase).invoke(
                query = any(),
                sortBy = any(),
                pageSize = any(),
                prefetchDistance = any()
            )

            // Since the ArticleItem contains a local random incremental id, it should be ignored from
            // assertion
            assertThat(expectedResult, sameBeanAs(actualResult).ignoring("id"))
            assertThat(actualFilterAndSort, `is`(expectedFilterAndSort))
        }

    @Test
    fun `new response should be received once 'onSearchQueryChange' is called with a new query`() =
        runTest {
            // GIVEN
            val newSearchQuery = "new query"
            val expectedFilterAndSort = FilterAndSort(newSearchQuery, NewsSort.NEWEST)

            // WHEN
            viewModel.onSearchQueryChange(newSearchQuery)
            val actualResult = viewModel.newsPagerFlow.asSnapshot()
            val actualFilterAndSort = viewModel.filterAndSortStateFlow.value

            // THEN
            verify(getNewsUseCase).invoke(
                query = any(),
                sortBy = any(),
                pageSize = any(),
                prefetchDistance = any()
            )
            assertThat(actualFilterAndSort, `is`(expectedFilterAndSort))
            assertThat(expectedResult, sameBeanAs(actualResult).ignoring("id"))
        }

    @Test
    fun `getting new result should be ignored once'onSearchQueryChange' is called with an empty query`() =
        runTest {
            // GIVEN
            val newSearchQuery = ""
            val expectedFilterAndSort = FilterAndSort(newSearchQuery, NewsSort.NEWEST)

            // WHEN
            viewModel.onSearchQueryChange(newSearchQuery)
            val actualFilterAndSort = viewModel.filterAndSortStateFlow.value

            // THEN
            verify(getNewsUseCase, never()).invoke(
                query = any(),
                sortBy = any(),
                pageSize = any(),
                prefetchDistance = any()
            )
            assertThat(actualFilterAndSort, `is`(expectedFilterAndSort))
        }

    @Test
    fun `getting new result should be ignored one'onSearchQueryChange' is called with a same query`() =
        runTest {
            // GIVEN
            val newSearchQuery = "android"
            val expectedFilterAndSort = FilterAndSort(newSearchQuery, NewsSort.NEWEST)

            // WHEN
            viewModel.onSearchQueryChange(newSearchQuery)
            val actualFilterAndSort = viewModel.filterAndSortStateFlow.value

            // THEN
            verify(getNewsUseCase, never()).invoke(
                query = any(),
                sortBy = any(),
                pageSize = any(),
                prefetchDistance = any()
            )
            assertThat(actualFilterAndSort, `is`(expectedFilterAndSort))
        }

    @Test
    fun `new response should be received once 'onSortChange' is called with a new sort type`() =
        runTest {
            // GIVEN
            val newSort = NewsSort.POPULARITY
            val expectedFilterAndSort = FilterAndSort(searchQuery = "Android", newSort)

            // WHEN
            viewModel = NewsViewModel(getNewsUseCase)
            viewModel.onSortChange(newSort)
            val actualResult = viewModel.newsPagerFlow.asSnapshot()
            val actualFilterAndSort = viewModel.filterAndSortStateFlow.value

            // THEN
            verify(getNewsUseCase).invoke(
                query = any(),
                sortBy = any(),
                pageSize = any(),
                prefetchDistance = any()
            )
            assertThat(actualFilterAndSort, `is`(expectedFilterAndSort))
            assertThat(expectedResult, sameBeanAs(actualResult).ignoring("id"))
        }

    @Test(expected = Exception::class)
    fun `an error should be received once 'newsPagerFlow' is being observed`() = runTest {
        // GIVEN
        val exception = IOException()
        whenever(
            getNewsUseCase(
                query = any(),
                sortBy = any(),
                pageSize = any(),
                prefetchDistance = any()
            )
        ) doThrow exception

        // WHEN
        viewModel.newsPagerFlow.asSnapshot()

        // THEN
        verify(getNewsUseCase).invoke(
            query = any(),
            sortBy = any(),
            pageSize = any(),
            prefetchDistance = any()
        )
    }
}
