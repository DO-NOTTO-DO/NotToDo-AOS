package kr.co.nottodo.data.remote.api

import kr.co.nottodo.data.local.HomeDailyResponse
import kr.co.nottodo.data.remote.model.*
import retrofit2.http.*

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

    @POST("mission/{missionId}")
    suspend fun postHomeBottomCalender(
        @Path("missionId") missionId: Int,
        @Body dates: RequestHomeBottomMissionDto
    ): ResponseHomeBottomMissionDto
}
