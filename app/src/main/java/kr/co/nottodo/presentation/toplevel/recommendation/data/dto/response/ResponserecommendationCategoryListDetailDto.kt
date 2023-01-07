package kr.co.nottodo.presentation.toplevel.recommendation.data.dto.response

import android.security.identity.AccessControlProfileId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponserecommendationCategoryListDetailDto(
    val id: Int,
    val name: Boolean,
    val image: String,
    val activeImage: String,

)

//"id": 1,
//"name": "SNS",
//"image": "https://nottodo-bucket.s3.ap-northeast-2.amazonaws.com/%EC%B6%94%EC%B2%9C+%ED%83%AD+%EC%95%84%EC%9D%B4%EC%BD%98/Property+1%3Dic_sns_default%402x.png",
//"activeImage":