package kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseBase<T>(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T,
)