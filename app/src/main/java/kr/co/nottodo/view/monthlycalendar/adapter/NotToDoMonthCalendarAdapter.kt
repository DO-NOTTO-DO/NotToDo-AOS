package kr.co.nottodo.view.monthlycalendar.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewNotToDoCalendarDayBinding
import kr.co.nottodo.databinding.ViewNotToDoCalendarEmptyBinding
import kr.co.nottodo.databinding.ViewNotToDoCalendarMonthBinding
import kr.co.nottodo.view.monthlycalendar.CalendarData
import kr.co.nottodo.view.monthlycalendar.CalendarType
import kr.co.nottodo.view.monthlycalendar.viewholder.NotToDoCalendarViewHolder
import kr.co.nottodo.view.monthlycalendar.viewholder.NotToDoDayViewHolder
import kr.co.nottodo.view.monthlycalendar.viewholder.NotToDoEmptyViewHolder
import kr.co.nottodo.view.monthlycalendar.viewholder.NotToDoMonthViewHolder

class NotToDoMonthCalendarAdapter : RecyclerView.Adapter<NotToDoCalendarViewHolder>() {

    private val calendarItems = mutableListOf<CalendarData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotToDoCalendarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CalendarType.MONTH.ordinal -> {
                val binding: ViewNotToDoCalendarMonthBinding = DataBindingUtil.inflate(
                    layoutInflater,
                    R.layout.view_not_to_do_calendar_month, parent, false
                )
                NotToDoMonthViewHolder(binding)
            }
            CalendarType.DAY.ordinal -> {
                val binding: ViewNotToDoCalendarDayBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.view_not_to_do_calendar_day, parent, false
                )
                NotToDoDayViewHolder(binding)
            }
            else -> {
                val binding: ViewNotToDoCalendarEmptyBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.view_not_to_do_calendar_empty, parent, false
                )
                NotToDoEmptyViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: NotToDoCalendarViewHolder, position: Int) {
        holder.onBind(calendarItems[position])
    }

    override fun getItemCount(): Int = calendarItems.size

    override fun getItemViewType(position: Int): Int = calendarItems[position].calendarType

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<CalendarData>) {
        calendarItems.clear()
        calendarItems.addAll(list)
        notifyDataSetChanged()
    }
}