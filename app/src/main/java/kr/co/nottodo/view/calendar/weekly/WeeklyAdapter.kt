package kr.co.nottodo.view.calendar.weekly

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewWeeklyCalendarDayBinding
import java.time.LocalDate

class WeeklyAdapter : RecyclerView.Adapter<WeeklyViewHolder>() {

    private val weeklyDays = mutableListOf<LocalDate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewWeeklyCalendarDayBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.view_weekly_calendar_day, parent, false
        )
        return WeeklyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeeklyViewHolder, position: Int) {
        holder.onBind(weeklyDays[position])
    }

    override fun getItemCount(): Int = weeklyDays.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<LocalDate>) {
        weeklyDays.clear()
        weeklyDays.addAll(list)
        notifyDataSetChanged()
    }
}