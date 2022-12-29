package kr.co.nottodo.view.monthcalendar

import java.util.*

const val TOTAL_COLUMN_COUNT = 7
const val MONTH_COLUMN_COUNT = 7
const val WEEK_COLUMN_COUNT = 7
const val DAY_COLUMN_COUNT = 1
const val EMPTY_COLUMN_COUNT = 1

// TODO renaming
data class Month(
    val label: String,
    val year: String,
    val dayList: List<CalendarData>
)

sealed class CalendarData(
    val columnCount: Int,
    val calendarType: Int
) {
    object Week : CalendarData(WEEK_COLUMN_COUNT, CalendarType.WEEK.ordinal)

    data class Day(
        val label: String,
        val prettyLabel: String,
        val date: Date,
        val state: DateType = DateType.WEEKDAY
    ) : CalendarData(DAY_COLUMN_COUNT, CalendarType.DAY.ordinal)

    object Empty : CalendarData(EMPTY_COLUMN_COUNT, CalendarType.EMPTY.ordinal)
}
