package kr.co.nottodo.view.calendar.monthly.monthlycalendar.viewholder

import kr.co.nottodo.databinding.ViewMonthlyCalendarEmptyBinding
import kr.co.nottodo.view.calendar.monthly.model.CalendarDay

class MonthlyCalendarEmptyViewHolder(
    private val binding: ViewMonthlyCalendarEmptyBinding
) : MonthlyCalendarViewHolder(binding.root) {

    private lateinit var empty: CalendarDay.Empty

    override fun onBind(data: CalendarDay) {
        if (data is CalendarDay.Empty) {
            empty = data
            binding.apply {
                emptyDayItem = data
                executePendingBindings()
            }
        }
    }
}
