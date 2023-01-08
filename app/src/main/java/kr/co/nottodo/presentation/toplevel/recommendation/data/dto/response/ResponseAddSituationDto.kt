package kr.co.nottodo.presentation.toplevel.recommendation.data.dto.response

import kotlinx.serialization.Serializable


@Serializable
data class ResponseAddSituationDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: List<ResponseAddSituationDetailDto>,
)
{
    @Serializable
    data class ResponseAddSituationDetailDto(
        val title: String,
        val action: String,
    )
}
