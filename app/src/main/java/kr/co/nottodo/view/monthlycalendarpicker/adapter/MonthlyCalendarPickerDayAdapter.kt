package kr.co.nottodo.view.monthlycalendarpicker.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewMonthlyCalendarPickerDayBinding
import kr.co.nottodo.databinding.ViewMonthlyCalendarPickerEmptyBinding
import kr.co.nottodo.view.monthlycalendar.model.CalendarDay
import kr.co.nottodo.view.monthlycalendar.model.CalendarType
import kr.co.nottodo.view.monthlycalendar.util.isTheSameDay
import kr.co.nottodo.view.monthlycalendarpicker.listener.MonthlyCalendarPickerClickListener
import kr.co.nottodo.view.monthlycalendarpicker.viewholder.MonthlyCalendarPickerDayViewHolder
import kr.co.nottodo.view.monthlycalendarpicker.viewholder.MonthlyCalendarPickerEmptyViewHolder
import java.util.*

class MonthlyCalendarPickerDayAdapter(
    private val monthlyCalendarPickerClickListener: MonthlyCalendarPickerClickListener
) : RecyclerView.Adapter<ViewHolder>() {

    private val calendarItems = mutableListOf<CalendarDay>()

    var selectedDate: Date? = null
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is MonthlyCalendarPickerDayViewHolder -> {
                if (calendarItems[position] is CalendarDay.Day) {
                    selectedDate?.let {
                        if ((calendarItems[position] as CalendarDay.Day).date.isTheSameDay(it)) {
                            holder.onBindSelectedState(calendarItems[position])
                        } else {
                            holder.onBind(calendarItems[position])
                        }
                    } ?: run {
                        holder.onBind(calendarItems[position])
                    }
                } else {
                    holder.onBind(calendarItems[position])
                }
            }
            is MonthlyCalendarPickerEmptyViewHolder -> {
                holder.onBind(calendarItems[position])
            }
            else -> {
                throw IllegalStateException("how dare you....")
            }
        }
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