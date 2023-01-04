package kr.co.nottodo.view.calendar.weekly

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewWeeklyCalendarDayBinding

// notice : weeklyCalendarDataList의 size는 항상 7개입니다.
// 일,월,화,수,목,금,토
class WeeklyAdapter(

) : RecyclerView.Adapter<WeeklyDayViewHolder>() {

    private val weeklyCalendarDataList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyDayViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewWeeklyCalendarDayBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.view_weekly_calendar_day, parent, false
        )
        return WeeklyDayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeeklyDayViewHolder, position: Int) {
        holder.onBind(weeklyCalendarDataList[position])
    }

    override fun getItemCount(): Int = weeklyCalendarDataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<String>) {
        weeklyCalendarDataList.clear()
        weeklyCalendarDataList.addAll(list)
        notifyDataSetChanged()
    }
}