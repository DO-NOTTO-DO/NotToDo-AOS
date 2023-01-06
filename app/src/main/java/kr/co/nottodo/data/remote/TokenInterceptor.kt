package kr.co.nottodo.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val tokenAddedRequest = originalRequest.newBuilder()
            .header("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjgsImlhdCI6MTY3MjgyMzI5OSwiZXhwIjoxNjc0ODk2ODk5fQ.9gPKDWIj9eA_NHK7UO9ClMJtfAupV7iaH7gcpHb2XPI")
            .build()
        return chain.proceed(tokenAddedRequest)
    }
}