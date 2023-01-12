package kr.co.nottodo.data.remote.model


import kotlinx.serialization.Serializable

@Serializable
data class HomeBottomMissionDto(
    val `data`: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    @Serializable
    data class Data(
        val dates: List<String>
    )
}