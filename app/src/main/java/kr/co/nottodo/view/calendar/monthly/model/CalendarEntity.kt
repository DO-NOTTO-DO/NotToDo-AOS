package kr.co.nottodo.view.calendar.monthly.model

import java.util.*

const val TOTAL_COLUMN_COUNT = 7
const val MONTH_COLUMN_COUNT = 7
const val WEEK_COLUMN_COUNT = 7
const val DAY_COLUMN_COUNT = 1
const val EMPTY_COLUMN_COUNT = 1

data class CalendarMonth(
    val label: String,
    val year: String,
    val dayList: List<MonthlyCalendarDay>
)

sealed class MonthlyCalendarDay(
    val columnCount: Int,
    val calendarType: Int
) {
    object Week : MonthlyCalendarDay(WEEK_COLUMN_COUNT, CalendarType.WEEK.ordinal)

    data class DayMonthly(
        val label: String,
        val prettyLabel: String,
        val date: Date,
        val state: DateType = DateType.WEEKDAY
    ) : MonthlyCalendarDay(DAY_COLUMN_COUNT, CalendarType.DAY.ordinal)

    data class Empty(
        val label: String
    ) : MonthlyCalendarDay(EMPTY_COLUMN_COUNT, CalendarType.EMPTY.ordinal)
}
