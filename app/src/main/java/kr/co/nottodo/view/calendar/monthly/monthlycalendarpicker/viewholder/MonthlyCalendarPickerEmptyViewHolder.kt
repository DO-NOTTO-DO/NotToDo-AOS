package kr.co.nottodo.view.calendar.monthly.monthlycalendarpicker.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.databinding.ViewMonthlyCalendarPickerEmptyBinding
import kr.co.nottodo.view.calendar.monthly.model.MonthlyCalendarDay

class MonthlyCalendarPickerEmptyViewHolder (
    private val binding: ViewMonthlyCalendarPickerEmptyBinding
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
