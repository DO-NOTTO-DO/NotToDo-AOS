package kr.co.nottodo.view.calendar.monthly.monthlycalendar.listener

import android.view.View

fun interface MonthlyCalendarNextMonthListener {
    fun onShowNextMonth(view: View, dateString: String)
}