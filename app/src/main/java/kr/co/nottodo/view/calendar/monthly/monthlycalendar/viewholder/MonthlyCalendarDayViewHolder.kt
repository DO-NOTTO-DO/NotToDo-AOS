package kr.co.nottodo.view.calendar.monthly.monthlycalendar.viewholder

import android.text.format.DateUtils
import android.view.View
import kr.co.nottodo.databinding.ViewMonthlyCalendarDayBinding
import kr.co.nottodo.view.calendar.monthly.model.MonthlyCalendarDay

class MonthlyCalendarDayViewHolder(
    private val binding: ViewMonthlyCalendarDayBinding
) : MonthlyCalendarViewHolder(binding.root) {

    private lateinit var dayData: MonthlyCalendarDay.DayMonthly

    override fun onBind(data: MonthlyCalendarDay) {
        if (data is MonthlyCalendarDay.DayMonthly) {
            dayData = data
            binding.apply {
                ivToday.visibility = if(DateUtils.isToday(data.date.time)) View.VISIBLE else View.GONE
                dayItem = data
                executePendingBindings()
            }
        }
    }
}