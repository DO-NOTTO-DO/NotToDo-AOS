package kr.co.nottodo.data.remote.model


import kotlinx.serialization.Serializable

@Serializable
data class HomeBottomMissionDto(
    val data: BottomCalender,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    @Serializable
    data class BottomCalender(
        val dates: List<String>
    )
}