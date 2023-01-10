package kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRecommendationCategoryListDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<CategoryList>,
) {
    @Serializable
    data class CategoryList(
        @SerialName("title")
        val title: String,
        @SerialName("recommendActions")
        val recommendationActions: List<RecommendationActions>,
    )

    @Serializable
    data class RecommendationActions(
        @SerialName("name")
        val name: String
    )
}

