package kr.co.nottodo.data.remote.api

import kr.co.nottodo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val tokenAddedRequest = originalRequest.newBuilder()
            .header(
                "Authorization",
                BuildConfig.ANDROID_TOKEN
            )
            .build()
        return chain.proceed(tokenAddedRequest)
    }
}