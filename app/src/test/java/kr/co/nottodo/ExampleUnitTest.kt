package kr.co.nottodo

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kr.co.nottodo.data.local.HomeDailyResponse
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val json = Json {
            isLenient = true
            coerceInputValues = true
        }
        val result = """
            {
              "status": 200,
              "success": true,
              "message": "일일 낫투두 조회 성공",
              "data": [
                {
                  "id": 29,
                  "title": "유튜브안보기",
                  "situation": "매일",
                  "completionStatus": "FINISH",
                  "goal": "스크린타임 1시간",
                  "actions": []
                },
                {
                  "id": 28,
                  "title": "남지윤d",
                  "situation": "매일",
                  "completionStatus": "NOTYET",
                  "goal": "스크린타임 1시간",
                  "actions": []
                }
              ]
            }
        """.trimIndent()
        val resultObject = json.decodeFromString<HomeDailyResponse>(result)
        val expected = HomeDailyResponse(
            status = 200,
            success = true,
            message = "일일 낫투두 조회 성공",
            data = listOf(
                HomeDailyResponse.HomeDaily(
                    id = 29,
                    title = "유튜브안보기",
                    situation = "매일",
                    completionStatus = "FINISH",
                    goal = "스크린타임 1시간",
                    actions = emptyList()
                ),
                HomeDailyResponse.HomeDaily(
                    id = 28,
                    title = "남지윤d",
                    situation = "매일",
                    completionStatus = "NOTYET",
                    goal = "스크린타임 1시간",
                    actions = emptyList()
                )
            )
        )
        assert(expected == resultObject)
    }
}