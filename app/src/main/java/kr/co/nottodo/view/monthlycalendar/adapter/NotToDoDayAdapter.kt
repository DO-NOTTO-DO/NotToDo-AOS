package kr.co.nottodo.view.monthlycalendar.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewNotToDoCalendarDayBinding
import kr.co.nottodo.databinding.ViewNotToDoCalendarEmptyBinding
import kr.co.nottodo.view.monthlycalendar.model.NotToDoCalendarDay
import kr.co.nottodo.view.monthlycalendar.model.CalendarType
import kr.co.nottodo.view.monthlycalendar.viewholder.NotToDoCalendarViewHolder
import kr.co.nottodo.view.monthlycalendar.viewholder.NotToDoDayViewHolder
import kr.co.nottodo.view.monthlycalendar.viewholder.NotToDoEmptyViewHolder

class NotToDoDayAdapter: RecyclerView.Adapter<NotToDoCalendarViewHolder>() {

    private val calendarItems = mutableListOf<NotToDoCalendarDay>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotToDoCalendarViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CalendarType.DAY.ordinal -> {
                val binding: ViewNotToDoCalendarDayBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.view_not_to_do_calendar_day, parent, false
                )
                NotToDoDayViewHolder(binding)
            }
            CalendarType.EMPTY.ordinal -> {
                val binding: ViewNotToDoCalendarEmptyBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.view_not_to_do_calendar_empty, parent, false
                )
                NotToDoEmptyViewHolder(binding)
            }
            CalendarType.WEEK.ordinal -> {
                val binding: ViewNotToDoCalendarEmptyBinding = DataBindingUtil.inflate(
                    layoutInflater, R.layout.view_not_to_do_calendar_empty, parent, false
                )
                NotToDoEmptyViewHolder(binding)
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
    fun submitList(list: List<NotToDoCalendarDay>) {
        calendarItems.clear()
        calendarItems.addAll(list)
        notifyDataSetChanged()
    }
}