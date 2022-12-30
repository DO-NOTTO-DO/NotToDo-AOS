package kr.co.nottodo.view.monthcalendar.viewholder

import kr.co.nottodo.databinding.ViewNotToDoCalendarEmptyBinding
import kr.co.nottodo.view.monthcalendar.NotToDoCalendarDay

// TODO Empty가 아니라 DISABLE이 되도록 보여져야 합니다
class NotToDoEmptyViewHolder(
    private val binding: ViewNotToDoCalendarEmptyBinding
) : NotToDoCalendarViewHolder(binding.root) {
    override fun onBind(data: NotToDoCalendarDay) {}
}
