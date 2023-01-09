package kr.co.nottodo.data.remote.api

import kr.co.nottodo.data.local.HomeDailyResponse
import kr.co.nottodo.data.remote.RequestHomeMission
import kr.co.nottodo.data.remote.response.ResponseWrapper
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface HomeService {

    @PATCH("mission/:missionId/check")
    suspend fun patchHomeMission(
        @Body body: RequestHomeMission
    ): ResponseWrapper<RequestHomeMission>

    @GET("banner")
    suspend fun getBanner()

    @GET("mission/daily/{date}")
    suspend fun getHomeDaily(
        @Path("date") date: String
    ): HomeDailyResponse
    /*
     patchNotificationSettings(
@Body body: NotificationSettingsRequest
): NoDataResponse
     */
}