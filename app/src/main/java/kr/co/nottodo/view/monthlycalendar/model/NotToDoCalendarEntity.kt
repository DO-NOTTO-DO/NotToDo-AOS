package kr.co.nottodo.view.monthlycalendar.model

import java.util.*

const val TOTAL_COLUMN_COUNT = 7
const val MONTH_COLUMN_COUNT = 7
const val WEEK_COLUMN_COUNT = 7
const val DAY_COLUMN_COUNT = 1
const val EMPTY_COLUMN_COUNT = 1

data class NotToDoCalendarMonth(
    val label: String,
    val year: String,
    val dayList: List<NotToDoCalendarDay>
)

sealed class NotToDoCalendarDay(
    val columnCount: Int,
    val calendarType: Int
) {
    object Week : NotToDoCalendarDay(WEEK_COLUMN_COUNT, CalendarType.WEEK.ordinal)

    data class Day(
        val label: String,
        val prettyLabel: String,
        val date: Date,
        val state: DateType = DateType.WEEKDAY
    ) : NotToDoCalendarDay(DAY_COLUMN_COUNT, CalendarType.DAY.ordinal)

    data class Empty(
        val label: String
    ) : NotToDoCalendarDay(EMPTY_COLUMN_COUNT, CalendarType.EMPTY.ordinal)
}
