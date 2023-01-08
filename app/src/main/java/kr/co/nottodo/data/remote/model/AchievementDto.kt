package kr.co.nottodo.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseAchievementDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<Achievement>
) {
    @Serializable
    data class Achievement(
        val actionDate: String,
        val count: Int
    )
}