package kr.co.nottodo.data.remote.api

import kr.co.nottodo.data.remote.model.ResponseRecommendationCategorySituationDto
import retrofit2.http.GET

interface RecommendationCategorySituationService {
    @GET("environment/category")
    suspend fun getCategorySituation(): ResponseRecommendationCategorySituationDto
}