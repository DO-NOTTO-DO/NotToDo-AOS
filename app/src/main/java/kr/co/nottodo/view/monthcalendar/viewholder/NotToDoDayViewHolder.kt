package kr.co.nottodo.view.monthcalendar.viewholder

import kr.co.nottodo.databinding.ViewNotToDoCalendarDayBinding
import kr.co.nottodo.view.monthcalendar.NotToDoCalendarDay

// TODO 클릭 시 어떻게 처리해야할 지에 대한 로직이 들어가야 합니다
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

        if (!binding.root.hasOnClickListeners()) {
            // TODO ClickListener를 달아주면 됩니다.
        }
    }
}