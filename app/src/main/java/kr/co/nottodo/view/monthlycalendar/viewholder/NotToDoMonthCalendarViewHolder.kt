package kr.co.nottodo.view.monthlycalendar.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.databinding.ViewNotToDoCalendarDayBinding
import kr.co.nottodo.databinding.ViewNotToDoCalendarEmptyBinding
import kr.co.nottodo.databinding.ViewNotToDoCalendarMonthBinding
import kr.co.nottodo.view.monthlycalendar.CalendarData

// 리스너
abstract class NotToDoCalendarViewHolder(view: View) : ViewHolder(view) {
    abstract fun onBind(data: CalendarData)
}

class NotToDoMonthViewHolder(
    private val binding: ViewNotToDoCalendarMonthBinding
) : NotToDoCalendarViewHolder(binding.root) {

    private lateinit var monthData: CalendarData.Month

    override fun onBind(data: CalendarData) {
        if (data is CalendarData.Month) {
            monthData = data
            binding.monthItem = data
        }
    }
}

// TODO 클릭 시 어떻게 처리해야할 지에 대한 로직이 들어가야 합니다
class NotToDoDayViewHolder(
    private val binding: ViewNotToDoCalendarDayBinding
) : NotToDoCalendarViewHolder(binding.root) {

    private lateinit var dayData: CalendarData.Day

    override fun onBind(data: CalendarData) {
        if (data is CalendarData.Day) {
            dayData = data
        }

        if (!binding.root.hasOnClickListeners()) {
            // TODO ClickListener를 달아주면 됩니다.
        }
    }
}

// TODO Empty가 아니라 DISABLE이 되도록 보여져야 합니다
class NotToDoEmptyViewHolder(
    private val binding: ViewNotToDoCalendarEmptyBinding
) : NotToDoCalendarViewHolder(binding.root) {
    override fun onBind(data: CalendarData) {}
}