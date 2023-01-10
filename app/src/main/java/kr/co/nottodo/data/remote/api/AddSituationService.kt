package kr.co.nottodo.data.remote.api

import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseAddSituationDto
import retrofit2.http.GET

interface AddSituationService {
    @GET("situation")
    suspend fun getResponseAddSituationDetailDto(): ResponseAddSituationDto
}