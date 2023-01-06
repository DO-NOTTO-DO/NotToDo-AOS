package kr.co.nottodo.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class ApiFactory {
    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()
    }
}