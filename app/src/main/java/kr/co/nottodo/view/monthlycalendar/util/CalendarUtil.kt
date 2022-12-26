package kr.co.nottodo.view.monthlycalendar.util

import android.os.Build.VERSION_CODES.M
import retrofit2.http.GET
import java.util.*
import java.util.Calendar.*

fun Calendar.toPrettyMonthString(
    style: Int = LONG,
    locale: Locale = Locale.getDefault()
): String {
    val month = getDisplayName(MONTH, style, locale)
    val year = get(YEAR).toString()
    require(month != null) { throw IllegalStateException("Cannot get pretty name") }
    // TODO 순서는 바뀔 수 있습니다.
    return "$month $year"
}

fun Calendar.toPrettyDateString(): String {
    val month = (get(MONTH) + 1).toString()
    val year = get(YEAR).toString()
    val day = get(DAY_OF_MONTH).toString()
    return "$year.$month.$day"
}

// 현재 날짜로 부터 날이 이미 지났는지 체크하는 함수
fun Calendar.isBefore(otherCalendar: Calendar): Boolean {
    return get(YEAR) == otherCalendar.get(YEAR)
            && get(MONTH) == otherCalendar.get(MONTH)
            && get(DAY_OF_MONTH) == otherCalendar.get(DAY_OF_MONTH)
}

// 현재 날짜로 부터 이후의 날인지 체크하는 함수
fun Calendar.isAfter(otherCalendar: Calendar): Boolean {
    return get(YEAR) == otherCalendar.get(YEAR)
            && get(MONTH) == otherCalendar.get(MONTH)
            && get(DAY_OF_MONTH) < otherCalendar.get(DAY_OF_MONTH)
}

// 주말을 알려주는 함수
fun Calendar.isWeekend(): Boolean {
    return get(DAY_OF_WEEK) == SATURDAY || get(DAY_OF_WEEK) == SUNDAY
}

// 총 달수의 차이
fun Calendar.totalMonthDifference(startCalendar: Calendar): Int {
    val yearDiff = get(YEAR) - startCalendar.get(YEAR)
    val monthDiff = get(MONTH) - startCalendar.get(MONTH)

    return monthDiff + (yearDiff * 12)
}