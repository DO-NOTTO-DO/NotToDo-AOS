package kr.co.nottodo.data.remote.model


import kotlinx.serialization.Serializable

@Serializable
data class HomeWeeklyDto(
    val data: List<WeeklyPercent>,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    @Serializable
    data class WeeklyPercent(
        val actionDate: String,
        val percentage: Double
    )
}