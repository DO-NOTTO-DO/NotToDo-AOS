package kr.co.nottodo.view.monthlycalendar

import java.util.*

const val TOTAL_COLUMN_COUNT = 7
const val MONTH_COLUMN_COUNT = 7
const val WEEK_COLUMN_COUNT = 7
const val DAY_COLUMN_COUNT = 1
const val EMPTY_COLUMN_COUNT = 1

sealed class CalendarData(
    val columnCount: Int,
    val calendarType: Int
) {
    data class Month(val label: String, val year: String) :
            CalendarData(MONTH_COLUMN_COUNT,CalendarType.MONTH.ordinal)

    object Week : CalendarData(WEEK_COLUMN_COUNT, CalendarType.WEEK.ordinal)
    data class Day(
        val label: String,
        val prettyLabel: String,
        val date: Date,
        val state: DateType = DateType.WEEKDAY
    ): CalendarData(DAY_COLUMN_COUNT,CalendarType.DAY.ordinal)

    object Empty : CalendarData(EMPTY_COLUMN_COUNT, CalendarType.EMPTY.ordinal)
}
