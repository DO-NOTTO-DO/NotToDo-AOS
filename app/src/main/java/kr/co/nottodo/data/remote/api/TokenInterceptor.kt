package kr.co.nottodo.data.remote.api

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val tokenAddedRequest = originalRequest.newBuilder()
            .header(
                "Authorization",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjksImlhdCI6MTY3Mjk5ODQ2MywiZXhwIjoxNjc1MDcyMDYzfQ.Wha5lYpaT6uDJcRila843w7UGmwRn-2arrNBb2ljT0A"
            )
            .build()
        return chain.proceed(tokenAddedRequest)
    }
}