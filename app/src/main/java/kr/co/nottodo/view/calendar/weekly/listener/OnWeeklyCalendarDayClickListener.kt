package kr.co.nottodo.view.calendar.weekly.listener

import android.view.View
import java.time.LocalDate

fun interface OnWeeklyCalendarDayClickListener {
    fun onWeeklyCalendarDayClick(view: View, date: LocalDate)
}