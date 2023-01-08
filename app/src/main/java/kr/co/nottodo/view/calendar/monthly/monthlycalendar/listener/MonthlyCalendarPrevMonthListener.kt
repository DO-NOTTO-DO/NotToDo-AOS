package kr.co.nottodo.view.calendar.monthly.monthlycalendar.listener

import android.view.View

fun interface MonthlyCalendarPrevMonthListener {
    fun onShowPrevMonth(view: View, dateString: String)
}