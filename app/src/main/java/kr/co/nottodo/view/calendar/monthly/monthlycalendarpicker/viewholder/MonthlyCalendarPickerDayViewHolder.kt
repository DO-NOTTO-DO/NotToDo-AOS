package kr.co.nottodo.view.calendar.monthly.monthlycalendarpicker.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.databinding.ViewMonthlyCalendarPickerDayBinding
import kr.co.nottodo.view.calendar.monthly.model.CalendarDay
import kr.co.nottodo.view.calendar.monthly.monthlycalendarpicker.listener.MonthlyCalendarPickerClickListener

class MonthlyCalendarPickerDayViewHolder(
    private val binding: ViewMonthlyCalendarPickerDayBinding,
    private val clickHandler: MonthlyCalendarPickerClickListener
): ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var dayData: CalendarDay.Day

    init {
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun onBind(data: CalendarDay) {
        if (data is CalendarDay.Day) {
            dayData = data
            binding.apply {
                dayItem = data
                ivMonthlyCalendarPickerSelect.visibility = View.GONE
                executePendingBindings()
            }
        }
    }

    fun onBindSelectedState(data: CalendarDay) {
        if (data is CalendarDay.Day) {
            dayData = data
            binding.apply {
                dayItem = data
                ivMonthlyCalendarPickerSelect.visibility = View.VISIBLE
                executePendingBindings()
            }
        }
    }

    override fun onClick(view: View) {
        clickHandler.onDayClick(view, dayData.date)
    }

    override fun onLongClick(view: View): Boolean = false
}