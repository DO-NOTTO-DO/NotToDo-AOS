package kr.co.nottodo.view.calendar.monthly.monthlycalendarpicker.listener

import android.view.View
import java.util.Date

fun interface MonthlyCalendarPickerClickListener {
    fun onDayClick(view: View, date: Date)
}