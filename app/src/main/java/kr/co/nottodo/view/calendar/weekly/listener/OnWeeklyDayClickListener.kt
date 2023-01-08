package kr.co.nottodo.view.calendar.weekly.listener

import android.view.View
import java.time.LocalDate

fun interface OnWeeklyDayClickListener {
    fun onWeeklyDayClick(view: View, date: LocalDate)
}