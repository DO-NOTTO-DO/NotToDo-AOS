package kr.co.nottodo.data.remote.api

import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategoryListDto
import retrofit2.Call
import retrofit2.http.GET

interface ResponseRecommendationCategoryList {
    @GET("environment/:categoryId")
    fun getCategoryList(): Call<ResponseRecommendationCategoryListDto>

    @GET("environment/:categoryId")
    fun getRecommendationActions(): Call<ResponseRecommendationCategoryListDto.CategoryList>
}