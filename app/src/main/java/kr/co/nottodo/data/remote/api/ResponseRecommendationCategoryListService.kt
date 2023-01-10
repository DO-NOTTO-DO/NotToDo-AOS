package kr.co.nottodo.data.remote.api

import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategoryListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RecommendationCategoryListService {
    @GET("environment/{id}")
    fun getCategoryList(@Path("id") id: Int): Call<ResponseRecommendationCategoryListDto>

}