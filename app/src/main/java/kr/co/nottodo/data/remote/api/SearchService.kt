package kr.co.nottodo.data.remote.api

import kr.co.nottodo.data.remote.model.ResponseHistoryDto
import retrofit2.Call
import retrofit2.http.GET

interface SearchService {
    @GET("mission/recent")
    fun getHistory(): Call<ResponseHistoryDto>
}