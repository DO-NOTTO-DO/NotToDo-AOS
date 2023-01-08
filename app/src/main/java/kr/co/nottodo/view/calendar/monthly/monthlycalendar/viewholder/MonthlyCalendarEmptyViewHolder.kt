package kr.co.nottodo.view.calendar.monthly.monthlycalendar.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.databinding.ViewMonthlyCalendarEmptyBinding
import kr.co.nottodo.view.calendar.monthly.model.MonthlyCalendarDay

class MonthlyCalendarEmptyViewHolder(
    private val binding: ViewMonthlyCalendarEmptyBinding
) : ViewHolder(binding.root) {

    private lateinit var empty: MonthlyCalendarDay.Empty

    fun onBind(data: MonthlyCalendarDay) {
        if (data is MonthlyCalendarDay.Empty) {
            empty = data
            binding.apply {
                emptyDayItem = data
                executePendingBindings()
            }
        }
    }
}
