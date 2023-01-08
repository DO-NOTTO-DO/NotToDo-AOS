package kr.co.nottodo.view.calendar.weekly

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewWeeklyCalendarDayBinding
import kr.co.nottodo.view.calendar.weekly.listener.OnWeeklyDayClickListener
import java.time.LocalDate

class WeeklyAdapter(
    private val onWeeklyDayClickListener: OnWeeklyDayClickListener
) : RecyclerView.Adapter<WeeklyViewHolder>() {

    private val weeklyDays = mutableListOf<LocalDate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewWeeklyCalendarDayBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.view_weekly_calendar_day, parent, false
        )
        return WeeklyViewHolder(binding, onWeeklyDayClickListener)
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