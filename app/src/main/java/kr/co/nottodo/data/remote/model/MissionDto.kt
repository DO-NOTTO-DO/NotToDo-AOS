package kr.co.nottodo.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestMissionDto(
    val title: String,
    val situation: String,
    val actions: List<String>,
    val goal: String,
    val actionDate: String
)

@Serializable
data class ResponseMissionDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: Mission
) {
    @Serializable
    data class Mission(
        val id: Int,
        val title: String,
        val goal: String,
        val situation: Situation,
        val actions: List<String>,
        val actionDate: String
    ){
        @Serializable
        data class Situation(
            val name: String
        )
    }
}