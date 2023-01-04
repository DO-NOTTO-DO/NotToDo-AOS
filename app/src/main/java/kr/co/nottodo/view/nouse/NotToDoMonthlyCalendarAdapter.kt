package kr.co.nottodo.view.nouse

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewNoUseCalendarMonthBinding
import kr.co.nottodo.view.monthlycalendar.model.CalendarMonth

class NotToDoMonthlyCalendarAdapter(
    private val onClickPrevMonth: () -> Unit,
    private val onClickNextMonth: () -> Unit
) : RecyclerView.Adapter<NotToDoMonthViewHolder>() {

    private val calendarMonthList = mutableListOf<CalendarMonth>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotToDoMonthViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewNoUseCalendarMonthBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.view_no_use_calendar_month, parent, false
        )
        return NotToDoMonthViewHolder(
            binding,
            onClickPrevMonth,
            onClickNextMonth
        )
    }

    override fun onBindViewHolder(holder: NotToDoMonthViewHolder, position: Int) {
        holder.onBind(calendarMonthList[position])
    }

    override fun getItemCount(): Int = calendarMonthList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<CalendarMonth>) {
        calendarMonthList.clear()
        calendarMonthList.addAll(list)
        notifyDataSetChanged()
    }
}