package kr.co.nottodo.data.remote.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kr.co.nottodo.BuildConfig
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object ApiFactory {

    private val client by lazy {
        OkHttpClient.Builder().addInterceptor(TokenInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client).build()
    }

    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java)
}

object ServicePool {
    //TODO: 각자 서비스 만드시고 해당 페이지에 서비스 객체 생성해주시면 됩니다 !!
    val searchService = ApiFactory.create<SearchService>()
    val statisticService = ApiFactory.create<StatisticService>()
    val missionService = ApiFactory.create<MissionService>()
    val achievementService = ApiFactory.create<AchievementService>()
    val recommendationCategoryListService = ApiFactory.create<RecommendationCategoryListService>()
    val recommendationCategorySituationService = ApiFactory.create<RecommendationCategorySituationService>()
}