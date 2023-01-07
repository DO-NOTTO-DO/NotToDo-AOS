package kr.co.nottodo.view.calendar.weekly

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.databinding.ViewWeeklyCalendarDayBinding
import java.time.LocalDate

class WeeklyViewHolder(
    private val binding: ViewWeeklyCalendarDayBinding
) : ViewHolder(binding.root) {

    init {
        // TODO : 리스너를 달아줘야 합니다
    }

    fun onBind(weeklyDate: LocalDate) {
        with(binding) {
            testData = weeklyDate.dayOfMonth.toString()
        }
    }
}