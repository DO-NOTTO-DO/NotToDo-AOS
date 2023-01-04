package kr.co.nottodo.view.calendar.monthly.monthlycalendarpicker.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.databinding.ViewMonthlyCalendarPickerEmptyBinding
import kr.co.nottodo.view.calendar.monthly.model.CalendarDay

class MonthlyCalendarPickerEmptyViewHolder (
    private val binding: ViewMonthlyCalendarPickerEmptyBinding
) : ViewHolder(binding.root) {

    private lateinit var empty: CalendarDay.Empty

    fun onBind(data: CalendarDay) {
        if (data is CalendarDay.Empty) {
            empty = data
            binding.apply {
                emptyDayItem = data
                executePendingBindings()
            }
        }
    }
}
