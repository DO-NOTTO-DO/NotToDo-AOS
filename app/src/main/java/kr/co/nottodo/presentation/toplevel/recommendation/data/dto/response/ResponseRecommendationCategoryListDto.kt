package kr.co.nottodo.presentation.toplevel.recommendation.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseRecommendationCategoryListDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<ResponserecommendationCategoryListDetailDto>,
)
//"status": 200,
//"success": true,
//"message": "추천 카테고리 조회 성공",
//"data":