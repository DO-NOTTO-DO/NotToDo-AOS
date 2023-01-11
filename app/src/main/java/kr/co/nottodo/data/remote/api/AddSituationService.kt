package kr.co.nottodo.data.remote.api

import kr.co.nottodo.data.remote.model.ResponseAddSituationDto
import retrofit2.Call
import retrofit2.http.GET

interface AddSituationService {
    @GET("situation")
    fun getResponseAddSituationDto(): Call<ResponseAddSituationDto>
}