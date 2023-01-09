package kr.co.nottodo.data.remote.api

import kr.co.nottodo.data.local.HomeDaily
import kr.co.nottodo.data.remote.RequestHomeMission
import kr.co.nottodo.data.remote.response.ResponseWrapper
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface HomeService {

    @PATCH("mission/:missionId/check")
    suspend fun patchHomeMission(
        @Body body: RequestHomeMission
    ): ResponseWrapper<RequestHomeMission>

    /*
     @GET("post/{postId}")
    fun getPost(
        @Path("postId") postId: Int
    ): Call<ResponsePostDTO>
     */

    @GET("mission/daily/{date}")
    suspend fun getHomeDaily(
        @Path("date") date: String
    ): Call<ResponseWrapper<HomeDaily>>
    /*
     patchNotificationSettings(
@Body body: NotificationSettingsRequest
): NoDataResponse
     */
}