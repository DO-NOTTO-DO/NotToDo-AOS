package kr.co.nottodo.presentation.toplevel.recommendation.data

data class RecommendationData(
    val image: String, // 서버에서 이미지 url이 내려오는 경우 String으로 받아야합니다.
    val name: String,
)
