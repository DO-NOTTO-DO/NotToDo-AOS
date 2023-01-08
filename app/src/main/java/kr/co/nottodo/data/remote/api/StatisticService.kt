package kr.co.nottodo.data.remote.api

import kr.co.nottodo.data.remote.model.ResponseMissionStatisticDto
import kr.co.nottodo.data.remote.model.ResponseSituationStatisticDto
import retrofit2.Call
import retrofit2.http.GET

interface StatisticService {
    @GET("mission/stat/notTodo")
    fun getMissionStatistic(): Call<ResponseMissionStatisticDto>

    @GET("mission/stat/situation")
    fun getSituationStatistic(): Call<ResponseSituationStatisticDto>
}