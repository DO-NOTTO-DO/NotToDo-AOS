package kr.co.nottodo.data.remote.api

import kr.co.nottodo.data.remote.model.RequestMissionDto
import kr.co.nottodo.data.remote.model.ResponseMissionDto
import retrofit2.http.Body
import retrofit2.http.POST

interface MissionService {
    @POST("mission")
    suspend fun postMission(@Body request: RequestMissionDto): ResponseMissionDto
}