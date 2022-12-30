package kr.co.nottodo.view.monthcalendar.viewholder

import kr.co.nottodo.databinding.ViewNotToDoCalendarDayBinding
import kr.co.nottodo.view.monthcalendar.NotToDoCalendarDay

class NotToDoDayViewHolder(
    private val binding: ViewNotToDoCalendarDayBinding
) : NotToDoCalendarViewHolder(binding.root) {

    private lateinit var dayData: NotToDoCalendarDay.Day

    override fun onBind(data: NotToDoCalendarDay) {
        if (data is NotToDoCalendarDay.Day) {
            dayData = data
            binding.apply {
                dayItem = data
                executePendingBindings()
            }
        }
    }
}