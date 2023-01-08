package kr.co.nottodo.view.calendar.monthly.monthlycalendar.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewMonthlyCalendarDayBinding
import kr.co.nottodo.databinding.ViewMonthlyCalendarEmptyBinding
import kr.co.nottodo.view.calendar.monthly.model.MonthlyCalendarDay
import kr.co.nottodo.view.calendar.monthly.model.CalendarType
import kr.co.nottodo.view.calendar.monthly.monthlycalendar.viewholder.MonthlyCalendarDayViewHolder
import kr.co.nottodo.view.calendar.monthly.monthlycalendar.viewholder.MonthlyCalendarEmptyViewHolder
import kr.co.nottodo.view.calendar.monthly.util.isTheSameDay
import java.util.Date

class MonthlyCalendarDayAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val calendarItems = mutableListOf<MonthlyCalendarDay>()

    private val notToDoCountList = mutableListOf<Pair<Date?,Int>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (calendarItems[position]) {
            is MonthlyCalendarDay.DayMonthly -> {
                notToDoCountList.indexOfLast {
                    it.first?.isTheSameDay((calendarItems[position] as MonthlyCalendarDay.DayMonthly).date) == true
                }.also {
                    if (it != -1) {
                        (holder as MonthlyCalendarDayViewHolder).onNotToDoBind(calendarItems[position], notToDoCountList[it].second)
                    } else {
                        (holder as MonthlyCalendarDayViewHolder).onBind(calendarItems[position])
                    }
                }
            }
            is MonthlyCalendarDay.Empty -> {
                (holder as MonthlyCalendarEmptyViewHolder).onBind(calendarItems[position])
            }
            MonthlyCalendarDay.Week -> {
                (holder as MonthlyCalendarEmptyViewHolder).onBind(calendarItems[position])
            }
        }
    }

    override fun getItemCount(): Int = calendarItems.size

    override fun getItemViewType(position: Int): Int = calendarItems[position].calendarType

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<MonthlyCalendarDay>) {
        calendarItems.clear()
        calendarItems.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitNotTodoCountList(list: List<Pair<Date?, Int>>) {
        notToDoCountList.clear()
        notToDoCountList.addAll(list)
        notifyDataSetChanged()
    }
}