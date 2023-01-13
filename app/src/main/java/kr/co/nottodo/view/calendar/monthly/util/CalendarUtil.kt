package kr.co.nottodo.view.calendar.monthly.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Calendar.*

fun Calendar.toPrettyMonthString(
    style: Int = LONG,
    locale: Locale = Locale.KOREA
): String {
    val month = getDisplayName(MONTH, style, locale)
    val year = get(YEAR).toString()
    require(month != null) { throw IllegalStateException("Cannot get pretty name") }
    return if (locale.country.equals(Locale.KOREA.country)) {
        "${year}년 $month"
    } else {
        "$year $month"
    }
}

fun Calendar.toPrettyDateString(): String {
    val month = (get(MONTH) + 1).toString()
    val year = get(YEAR).toString()
    val day = get(DAY_OF_MONTH).toString()
    return "$year.$month.$day"
}

fun Calendar.toApiDateString(): String {
    var month = (get(MONTH) + 1).toString()
    val year = get(YEAR).toString()
    if (month.length == 1) {
        month = "0${month}"
    }
    return "${year}-${month}"
}

fun String.convertStringToDate(): Date? {
    val format = SimpleDateFormat("yyyy-MM-dd")
    return runCatching {
        format.parse(this)
    }.getOrNull()
}

fun String.convertToLocalDate(): LocalDate? {
    val format = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    return runCatching {
        LocalDate.parse(this,format)
    }.getOrNull()
}


fun Date.convertDateToString(): String? {
    val dateFormat = SimpleDateFormat("yyyy.MM.dd")
    return runCatching {
        dateFormat.format(this)
    }.getOrNull()
}

// 성취 캘린더를 위한 함수
fun String.achievementConvertStringToDate(): Date? {
    val format = SimpleDateFormat("yyyy.MM.dd")
    return runCatching {
        format.parse(this)
    }.getOrNull()
}

// 현재 날짜로 부터 날이 이미 지났는지 체크하는 함수
fun Calendar.isBefore(otherCalendar: Calendar): Boolean {
    return get(YEAR) == otherCalendar.get(YEAR)
            && get(MONTH) == otherCalendar.get(MONTH)
            && get(DAY_OF_MONTH) < otherCalendar.get(DAY_OF_MONTH)
}

fun Calendar.isBeforeCalendar(otherCalendar: Calendar): Boolean {
    if (get(YEAR) < otherCalendar.get(YEAR)) return true
    return if (get(MONTH) < otherCalendar.get(MONTH)) {
        true
    } else if (get(MONTH) == otherCalendar.get(MONTH)) {
        get(DAY_OF_MONTH) <= otherCalendar.get(DAY_OF_MONTH)
    } else {
        false
    }
}

// 현재 날짜로 부터 이후의 날인지 체크하는 함수
fun Calendar.isAfter(otherCalendar: Calendar): Boolean {
    return get(YEAR) == otherCalendar.get(YEAR)
            && get(MONTH) == otherCalendar.get(MONTH)
            && get(DAY_OF_MONTH) > otherCalendar.get(DAY_OF_MONTH)
}

// 주말을 알려주는 함수
fun Calendar.isWeekend(): Boolean {
    return get(DAY_OF_WEEK) == SUNDAY
}

// 총 달수의 차이
fun Calendar.totalMonthDifference(startCalendar: Calendar): Int {
    val yearDiff = get(YEAR) - startCalendar.get(YEAR)
    val monthDiff = get(MONTH) - startCalendar.get(MONTH)

    return monthDiff + (yearDiff * 12)
}

fun String.dayNameParseToKorea(): String = when(this) {
    "SUNDAY" -> "일"
    "MONDAY" -> "월"
    "TUESDAY" -> "화"
    "WEDNESDAY" -> "수"
    "THURSDAY" -> "목"
    "FRIDAY" -> "금"
    "SATURDAY" -> "토"
    else -> throw IllegalStateException("This is not day String")
}