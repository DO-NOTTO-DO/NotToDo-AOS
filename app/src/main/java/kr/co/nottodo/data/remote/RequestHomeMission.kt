package kr.co.nottodo.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class RequestHomeMission(
    val `data`: Data
) {
    @Serializable
    data class Data(
        val completionStatus: String,
        val id: Int
    )
}