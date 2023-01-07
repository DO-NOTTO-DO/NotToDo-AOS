package kr.co.nottodo.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseMissionStatisticsDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<MissionStatistic>
){
    @Serializable
    data class MissionStatistic(
        val count: Int,
        val title: String
    )
}