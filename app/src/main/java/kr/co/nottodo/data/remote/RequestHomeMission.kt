package kr.co.nottodo.data.remote

data class RequestHomeMission(
    val `data`: Data
) {
    data class Data(
        val completionStatus: String,
        val id: Int
    )
}