package kr.co.nottodo.data.remote.model


import kotlinx.serialization.Serializable

@Serializable
data class HomeWeeklyDto(
    val message: String,
    val status: Int,
    val success: Boolean,
    val `data`: List<Data>
) {
    @Serializable
    data class Data(
        val actionDate: String,
        val count: Int
    )
}