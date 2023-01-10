package kr.co.nottodo.data.remote.model


import kotlinx.serialization.Serializable

@Serializable
data class ResponseHomeBannerDto(
    val message: String,
    val status: Int,
    val success: Boolean,
    val `data`: HomeBanner
) {
    @Serializable
    data class HomeBanner(
        val image: String,
        val title: String
    )
}