package kr.co.nottodo.view.calendar.monthly.monthlycalendar.viewholder

import kr.co.nottodo.databinding.ViewMonthlyCalendarDayBinding
import kr.co.nottodo.view.calendar.monthly.model.CalendarDay

class MonthlyCalendarDayViewHolder(
    private val binding: ViewMonthlyCalendarDayBinding
) : MonthlyCalendarViewHolder(binding.root) {

    private lateinit var dayData: CalendarDay.Day

    override fun onBind(data: CalendarDay) {
        if (data is CalendarDay.Day) {
            dayData = data
            binding.apply {
                dayItem = data
                executePendingBindings()
            }
        }
    }
}