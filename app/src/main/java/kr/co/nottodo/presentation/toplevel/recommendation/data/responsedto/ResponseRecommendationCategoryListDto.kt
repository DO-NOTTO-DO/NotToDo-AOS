package kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseRecommendationCategoryListDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<CategoryList>,
) {
    @Serializable
    data class CategoryList(
        val title: String,
        val recommendationActions: List<RecommendationActions>,
    )

    @Serializable
    data class RecommendationActions(
        val name: List<String>
    )


}

