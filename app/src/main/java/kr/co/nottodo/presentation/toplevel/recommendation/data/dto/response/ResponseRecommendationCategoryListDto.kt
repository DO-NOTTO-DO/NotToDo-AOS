package kr.co.nottodo.presentation.toplevel.recommendation.data.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRecommendationCategoryListDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<ResponserecommendationCategoryListDetailDto>,
)
{
    @Serializable
    data class ResponserecommendationCategoryListDetailDto(
        val id: Int,
        val name: String,
        val image: String,
        val activeImage: String,
    )
}

