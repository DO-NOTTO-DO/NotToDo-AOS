package kr.co.nottodo.data.remote.api

import kr.co.nottodo.data.remote.model.ResponseRecommendationCategoryListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RecommendationCategoryListService {
    @GET("environment/{id}")
    suspend fun getCategoryList(@Path("id") id: Int): ResponseRecommendationCategoryListDto
}