package kr.co.nottodo.data.remote.model

import com.google.android.datatransport.runtime.dagger.multibindings.IntoMap
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

@Serializable
data class ResponseSituationStatisticsDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<SituationStatistic>
){
    @Serializable
    data class SituationStatistic(
        val id: Int,
        val count: Int,
        val name: Int,
        val missions: List<ResponseMissionStatisticsDto.MissionStatistic>
    )
}