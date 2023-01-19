package kr.co.nottodo.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseRecommendationCategorySituationDto(
    @SerialName("status")
    val status: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: List<CategorySituation>
) {
    @Serializable
    data class CategorySituation(
        @SerialName("id")
        val id: Int,
        @SerialName("name")
        val name: String,
        @SerialName("image")
        val image: String,
        @SerialName("activeImage")
        val activeImage: String,
    )
}
