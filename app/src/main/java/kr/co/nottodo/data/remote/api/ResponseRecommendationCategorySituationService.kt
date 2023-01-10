package kr.co.nottodo.data.remote.api

import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategorySituationDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface RecommendationCategorySituationService {
    @GET("environment/category")
    suspend fun getCategorySituation(): ResponseRecommendationCategorySituationDto
}