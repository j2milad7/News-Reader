package dev.miladanbari.newsreader.util

import dev.miladanbari.newsreader.data.model.ArticleDto
import dev.miladanbari.newsreader.data.model.ResponseDto
import dev.miladanbari.newsreader.data.model.SourceDto
import dev.miladanbari.newsreader.data.model.StatusDto
import dev.miladanbari.newsreader.domain.model.ArticleModel
import dev.miladanbari.newsreader.domain.model.SourceModel

val testErrorJson = """
{
   "status":"error",
   "code":"maximumResultsReached",
   "message":"You have requested too many results. Developer accounts are limited to a max of 100 results. You are trying to request results 200 to 400. Please upgrade to a paid plan if you need more results."
}
""".trimIndent()

val testArticleDtoList = listOf(
    ArticleDto(
        source = SourceDto(id = "test source id 1", name = "test source name 1"),
        author = "test author 1",
        title = "test title 1",
        description = "test description 1",
        url = "test url 1",
        urlToImage = "test url to image 1",
        publishedAt = "test published at 1",
        content = "test content 1"
    ),
    ArticleDto(
        source = SourceDto(id = "test source id 2", name = "test source name 2"),
        author = "test author 2",
        title = "test title 2",
        description = "test description 2",
        url = "test url 2",
        urlToImage = "test url to image 2",
        publishedAt = "test published at 2",
        content = "test content 2"
    )
)

val testSuccessResponseDto = ResponseDto.Success(
    status = StatusDto.OK,
    totalResults = 2,
    data = testArticleDtoList
)

val testArticleModelList = listOf(
    ArticleModel(
        source = SourceModel(id = "test source id 1", name = "test source name 1"),
        author = "test author 1",
        title = "test title 1",
        description = "test description 1",
        url = "test url 1",
        urlToImage = "test url to image 1",
        publishedAt = "test published at 1",
        content = "test content 1"
    ),
    ArticleModel(
        source = SourceModel(id = "test source id 2", name = "test source name 2"),
        author = "test author 2",
        title = "test title 2",
        description = "test description 2",
        url = "test url 2",
        urlToImage = "test url to image 2",
        publishedAt = "test published at 2",
        content = "test content 2"
    )
)

