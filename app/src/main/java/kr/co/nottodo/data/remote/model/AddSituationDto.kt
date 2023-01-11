package kr.co.nottodo.data.remote.model

import kotlinx.serialization.Serializable


@Serializable
data class ResponseAddSituationDto(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ResponseAddSituationDetailDto,
) {
    @Serializable
    data class ResponseAddSituationDetailDto(
        val recommends: List<Recommend>,
        val recents: List<Recent>
    )

    @Serializable
    data class Recommend(
        val name: String
    )

    @Serializable
    data class Recent(
        val name: String
    )
}
