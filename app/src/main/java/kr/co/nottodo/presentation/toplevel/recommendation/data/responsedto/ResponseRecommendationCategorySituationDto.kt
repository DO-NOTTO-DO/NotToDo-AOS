package kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseRecommendationCategorySituationDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<ResponseRecommendationCategorySituationDetailDto>,
) {
    @Serializable
    data class ResponseRecommendationCategorySituationDetailDto(
        val title: String,
        val action: String,
    )
}
