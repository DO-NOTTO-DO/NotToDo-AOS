package kr.co.nottodo.data.remote.api

import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategorySituationDto
import retrofit2.Call
import retrofit2.http.GET

interface ResponseRecommendationCategorySituationService {
    @GET("environment/category")
    fun getCategorySituation(): Call<ResponseRecommendationCategorySituationDto>
}