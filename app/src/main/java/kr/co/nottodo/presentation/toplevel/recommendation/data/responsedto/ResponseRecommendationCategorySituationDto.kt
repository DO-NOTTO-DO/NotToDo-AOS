package kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseRecommendationCategorySituationDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<CategorySituation>
) {
    @Serializable
    data class CategorySituation(
        val id: Int,
        val name: String,
        val image: String,
        val activeImage: String,
    )
}
