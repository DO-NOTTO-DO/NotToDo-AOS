package kr.co.nottodo.data.remote.model


import kotlinx.serialization.Serializable

@Serializable
data class HomeMissionCheckDto(
    val `data`: CheckTodo,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    @Serializable
    data class CheckTodo(
        val completionStatus: String,
        val id: Int
    )
}