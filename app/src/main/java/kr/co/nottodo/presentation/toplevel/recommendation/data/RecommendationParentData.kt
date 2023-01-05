package kr.co.nottodo.presentation.toplevel.recommendation.data

data class RecommendationParentData(
    val id: Int,
    val title: String,
    val childDatas: List<RecommendationChildData>
)
