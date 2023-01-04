package kr.co.nottodo.data.local

data class HomeDaily(
    val actions: List<Action>,
    val completionStatus: String,
    val goal: String,
    val id: Int,
    val situation: String,
    val title: String
) {
    data class Action(
        val name: String
    )
}