package kr.co.nottodo.presentation.toplevel.recommendation.data.recommendationlistdata

data class RecommendationParentData(
    val id: Int,
    val title: String,
    val childDatas: List<RecommendationChildData>
)
