package kr.co.nottodo.data.remote.api

import kr.co.nottodo.data.local.HomeDailyResponse
import kr.co.nottodo.data.remote.model.HomeWeeklyDto
import kr.co.nottodo.data.remote.model.RequestHomeMissionCheck
import kr.co.nottodo.data.remote.model.ResponseHomeBannerDto
import kr.co.nottodo.data.remote.model.ResponseHomeMissionCheckDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface HomeService {

    @PATCH("mission/{missionId}/check")
    suspend fun patchHomeTodoCheck(
        @Path("missionId") missionId: Int,
        @Body body: RequestHomeMissionCheck
    ): ResponseHomeMissionCheckDto

    @GET("mission/week/{startDate}")
    suspend fun getWeekCount(
        @Path("startDate") startDate: String
    ): HomeWeeklyDto

    @GET("banner")
    suspend fun getBanner(): ResponseHomeBannerDto

    @GET("mission/daily/{date}")
    suspend fun getHomeDaily(
        @Path("date") date: String
    ): HomeDailyResponse

}
