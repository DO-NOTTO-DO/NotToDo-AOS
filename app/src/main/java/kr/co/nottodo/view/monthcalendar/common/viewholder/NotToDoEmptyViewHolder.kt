package kr.co.nottodo.view.monthcalendar.common.viewholder

import kr.co.nottodo.databinding.ViewNotToDoCalendarEmptyBinding
import kr.co.nottodo.view.monthcalendar.common.NotToDoCalendarDay

class NotToDoEmptyViewHolder(
    private val binding: ViewNotToDoCalendarEmptyBinding
) : NotToDoCalendarViewHolder(binding.root) {

    private lateinit var empty : NotToDoCalendarDay.Empty

    override fun onBind(data: NotToDoCalendarDay) {
        if (data is NotToDoCalendarDay.Empty) {
            empty = data
            binding.apply {
                emptyDayItem = data
                executePendingBindings()
            }
        }
    }
}
