package kr.co.nottodo.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseHistoryDto(
    @SerialName("status") val status: Int,
    @SerialName("success") val success: Boolean,
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<History>
) {
    @Serializable
    data class History(
        @SerialName("title") val title: String
    )
}