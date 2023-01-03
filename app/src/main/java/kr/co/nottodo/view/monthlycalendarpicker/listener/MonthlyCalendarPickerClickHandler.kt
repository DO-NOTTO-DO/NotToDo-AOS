package kr.co.nottodo.view.monthlycalendarpicker.listener

import android.view.View
import java.util.Date

fun interface MonthlyCalendarPickerClickHandler {
    fun onDayClick(view: View, date: Date)
}