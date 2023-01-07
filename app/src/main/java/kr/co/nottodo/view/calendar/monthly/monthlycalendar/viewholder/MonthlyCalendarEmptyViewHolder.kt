package kr.co.nottodo.view.calendar.monthly.monthlycalendar.viewholder

import kr.co.nottodo.databinding.ViewMonthlyCalendarEmptyBinding
import kr.co.nottodo.view.calendar.monthly.model.MonthlyCalendarDay

class MonthlyCalendarEmptyViewHolder(
    private val binding: ViewMonthlyCalendarEmptyBinding
) : MonthlyCalendarViewHolder(binding.root) {

    private lateinit var empty: MonthlyCalendarDay.Empty

    override fun onBind(data: MonthlyCalendarDay) {
        if (data is MonthlyCalendarDay.Empty) {
            empty = data
            binding.apply {
                emptyDayItem = data
                executePendingBindings()
            }
        }
    }
}
