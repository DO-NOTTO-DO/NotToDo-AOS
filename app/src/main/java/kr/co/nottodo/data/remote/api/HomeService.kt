package kr.co.nottodo.data.remote.api

import kr.co.nottodo.data.local.HomeDailyResponse
import kr.co.nottodo.data.remote.model.HomeMissionCheckDto
import kr.co.nottodo.data.remote.model.ResponseHomeBannerDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface HomeService {

    @PATCH("mission/:missionId/check")
    suspend fun patchHomeTodoChekc(
        @Body body: HomeMissionCheckDto
    ): HomeMissionCheckDto

    @GET("banner")
    suspend fun getBanner(): ResponseHomeBannerDto

    @GET("mission/daily/{date}")
    suspend fun getHomeDaily(
        @Path("date") date: String
    ): HomeDailyResponse

}