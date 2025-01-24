package dev.miladanbari.newsreader.view.article

import androidx.lifecycle.SavedStateHandle
import dev.miladanbari.newsreader.R
import dev.miladanbari.newsreader.util.CoroutinesTestRule
import dev.miladanbari.newsreader.util.testArticleItem
import dev.miladanbari.newsreader.view.base.ViewState
import dev.miladanbari.newsreader.view.news.model.ArticleItem
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ArticleDetailsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private lateinit var viewModel: ArticleDetailsViewModel

    @Test
    fun `the argument should be retrived from savedStateHandle when view model is created`() =
        runTest {
            // GIVEN
            val expectedResult = testArticleItem
            val savedStateHandle = SavedStateHandle(mapOf("articleItem" to expectedResult))

            // WHEN
            viewModel = ArticleDetailsViewModel(savedStateHandle)
            val actualResult = viewModel.viewStateFlow.value as ViewState.Success<ArticleItem>

            // THEN
            assertThat(actualResult.data, `is`(expectedResult))
        }

    @Test
    fun `an error should be thrown when the argument is missing`() = runTest {
        // GIVEN
        val savedStateHandle = SavedStateHandle()

        // WHEN
        viewModel = ArticleDetailsViewModel(savedStateHandle)
        val actualResult = viewModel.viewStateFlow.value as ViewState.Failure

        // THEN
        assertThat(actualResult.localError.messageResId, `is`(R.string.error_general))
    }
}
