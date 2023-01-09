package kr.co.nottodo.data.remote.api

import kr.co.nottodo.data.remote.model.ResponseAchievementDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AchievementService {
    @GET("mission/month/{month}")
    fun getAchievement(@Path("month") month: String): Call<ResponseAchievementDto>
}