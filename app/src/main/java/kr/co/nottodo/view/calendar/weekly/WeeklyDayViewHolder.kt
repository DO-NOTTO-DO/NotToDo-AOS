package kr.co.nottodo.view.calendar.weekly

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.databinding.ViewWeeklyCalendarDayBinding

class WeeklyDayViewHolder(
    private val binding: ViewWeeklyCalendarDayBinding
) : ViewHolder(binding.root) {

    private lateinit var weeklyDayData: String

    fun onBind(data : String) {
        weeklyDayData = data
        with(binding) {
            testData = data
            executePendingBindings()
        }
    }
}