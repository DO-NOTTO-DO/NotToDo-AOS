package kr.co.nottodo.view.calendar.weekly.listener

import java.time.LocalDate

interface OnWeeklyCalendarSwipeListener {
    fun onSwipe(mondayDate: LocalDate?)
}