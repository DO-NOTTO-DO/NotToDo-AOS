package kr.co.nottodo.data.remote.model


import kotlinx.serialization.Serializable

@Serializable
data class RequestHomeMissionCheck(val completionStatus: String)

@Serializable
data class ResponseHomeMissionCheckDto(
    val message: String,
    val status: Int,
    val success: Boolean,
    val data: HomeMissionCheckDto
) {
    @Serializable
    data class HomeMissionCheckDto(
        val completionStatus: String,
        val id: Int
    )
}
