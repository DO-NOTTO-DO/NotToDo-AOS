package kr.co.nottodo.view.calendar.monthly.monthlycalendarpicker.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewMonthlyCalendarPickerDayBinding
import kr.co.nottodo.databinding.ViewMonthlyCalendarPickerEmptyBinding
import kr.co.nottodo.view.calendar.monthly.model.CalendarType
import kr.co.nottodo.view.calendar.monthly.model.MonthlyCalendarDay
import kr.co.nottodo.view.calendar.monthly.monthlycalendarpicker.listener.MonthlyCalendarPickerClickListener
import kr.co.nottodo.view.calendar.monthly.monthlycalendarpicker.viewholder.MonthlyCalendarPickerDayViewHolder
import kr.co.nottodo.view.calendar.monthly.monthlycalendarpicker.viewholder.MonthlyCalendarPickerEmptyViewHolder
import kr.co.nottodo.view.calendar.monthly.util.isTheSameDay
import java.util.Date

// 오늘과 이전은 클릭되면 안된다.
class MonthlyCalendarMultiplePickerDayAdapter(
    private val monthlyCalendarPickerClickListener: MonthlyCalendarPickerClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val calendarPickerItems = mutableListOf<MonthlyCalendarDay>()

    private val selectedDateList = mutableListOf<Date>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CalendarType.DAY.ordinal -> {
                val binding: ViewMonthlyCalendarPickerDayBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.view_monthly_calendar_picker_day, parent, false
                )
                MonthlyCalendarPickerDayViewHolder(binding, monthlyCalendarPickerClickListener)
            }
            CalendarType.EMPTY.ordinal -> {
                val binding: ViewMonthlyCalendarPickerEmptyBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.view_monthly_calendar_picker_empty, parent, false
                )
                MonthlyCalendarPickerEmptyViewHolder(binding)
            }
            CalendarType.WEEK.ordinal -> {
                val binding: ViewMonthlyCalendarPickerEmptyBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.view_monthly_calendar_picker_empty, parent, false
                )
                MonthlyCalendarPickerEmptyViewHolder(binding)
            }
            else -> {
                val binding: ViewMonthlyCalendarPickerEmptyBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.view_monthly_calendar_picker_empty, parent, false
                )
                MonthlyCalendarPickerEmptyViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MonthlyCalendarPickerDayViewHolder -> {
                if (calendarPickerItems[position] is MonthlyCalendarDay.DayMonthly) {
                    if (selectedDateList.any { it.isTheSameDay((calendarPickerItems[position] as MonthlyCalendarDay.DayMonthly).date)}) {
                        holder.onBindSelectedState(calendarPickerItems[position])
                    } else {
                        holder.onBind(calendarPickerItems[position])
                    }
                } else {
                    holder.onBind(calendarPickerItems[position])
                }
            }
            is MonthlyCalendarPickerEmptyViewHolder -> {
                holder.onBind(calendarPickerItems[position])
            }
            else -> {
                throw IllegalStateException("how dare you....")
            }
        }
    }

    override fun getItemCount(): Int = calendarPickerItems.size

    override fun getItemViewType(position: Int): Int = calendarPickerItems[position].calendarType

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<MonthlyCalendarDay>) {
        calendarPickerItems.clear()
        calendarPickerItems.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedDay(date: Date) {
        if (selectedDateList.contains(date)) {
            selectedDateList.remove(date)
        } else {
            selectedDateList.add(date)
        }
        notifyDataSetChanged()
    }
}