package kr.co.nottodo.data.local

import kotlinx.serialization.Serializable

@Serializable
data class HomeDaily(
    val id: Int,
    val title: String,
    val situation: String,
    val completionStatus: String,
    val goal: String,
    val actions: List<Action>
) {
    @Serializable
    data class Action(
        val name: String
    )
}