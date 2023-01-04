package kr.co.nottodo.view.calendar.monthly.monthlycalendar.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewMonthlyCalendarDayBinding
import kr.co.nottodo.databinding.ViewMonthlyCalendarEmptyBinding
import kr.co.nottodo.view.calendar.monthly.model.CalendarDay
import kr.co.nottodo.view.calendar.monthly.model.CalendarType
import kr.co.nottodo.view.calendar.monthly.monthlycalendar.viewholder.MonthlyCalendarDayViewHolder
import kr.co.nottodo.view.calendar.monthly.monthlycalendar.viewholder.MonthlyCalendarEmptyViewHolder
import kr.co.nottodo.view.calendar.monthly.monthlycalendar.viewholder.MonthlyCalendarViewHolder

class MonthlyCalendarDayAdapter : RecyclerView.Adapter<MonthlyCalendarViewHolder>() {

    private val calendarItems = mutableListOf<CalendarDay>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthlyCalendarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CalendarType.DAY.ordinal -> {
                val binding: ViewMonthlyCalendarDayBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.view_monthly_calendar_day, parent, false
                )
                MonthlyCalendarDayViewHolder(binding)
            }
            CalendarType.EMPTY.ordinal -> {
                val binding: ViewMonthlyCalendarEmptyBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.view_monthly_calendar_empty, parent, false
                )
                MonthlyCalendarEmptyViewHolder(binding)
            }
            CalendarType.WEEK.ordinal -> {
                val binding: ViewMonthlyCalendarEmptyBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.view_monthly_calendar_empty, parent, false
                )
                MonthlyCalendarEmptyViewHolder(binding)
            }
            else -> {
                val binding: ViewMonthlyCalendarEmptyBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.view_monthly_calendar_empty, parent, false
                )
                MonthlyCalendarEmptyViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: MonthlyCalendarViewHolder, position: Int) {
        holder.onBind(calendarItems[position])
    }

    override fun getItemCount(): Int = calendarItems.size

    override fun getItemViewType(position: Int): Int = calendarItems[position].calendarType

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<CalendarDay>) {
        calendarItems.clear()
        calendarItems.addAll(list)
        notifyDataSetChanged()
    }
}