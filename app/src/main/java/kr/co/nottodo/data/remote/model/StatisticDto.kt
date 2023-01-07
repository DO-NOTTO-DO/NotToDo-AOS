package kr.co.nottodo.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ResponseMissionStatisticDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<MissionStatistic>
) {
    @Serializable
    data class MissionStatistic(
        val count: Int,
        val title: String
    )
}

@Serializable
data class ResponseSituationStatisticDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<SituationStatistic>
) {
    @Serializable
    data class SituationStatistic(
        val id: Int,
        val count: Int,
        val name: Int,
        val missions: List<ResponseMissionStatisticDto.MissionStatistic>
    )
}