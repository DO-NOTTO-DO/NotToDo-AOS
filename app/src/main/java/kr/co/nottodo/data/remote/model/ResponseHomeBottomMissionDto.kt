package kr.co.nottodo.data.remote.model


import kotlinx.serialization.Serializable

@Serializable
data class ResponseHomeBottomMissionDto(
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

@Serializable
data class RequestHomeBottomMissionDto(
    val dates: List<String>
)