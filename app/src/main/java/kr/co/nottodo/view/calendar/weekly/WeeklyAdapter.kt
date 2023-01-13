package kr.co.nottodo.view.calendar.weekly

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewWeeklyCalendarDayBinding
import kr.co.nottodo.view.calendar.monthly.model.MonthlyCalendarDay
import kr.co.nottodo.view.calendar.monthly.util.isTheSameDay
import kr.co.nottodo.view.calendar.weekly.listener.OnWeeklyDayClickListener
import java.math.RoundingMode.valueOf
import java.time.LocalDate
import java.util.*


class WeeklyAdapter(
    private val onWeeklyDayClickListener: OnWeeklyDayClickListener
) : RecyclerView.Adapter<WeeklyViewHolder>() {

    companion object {
        private const val WEEKLY_CALENDAR_START_POSITION = 0
        private const val WEEKLY_CALENDAR_END_POSITION = 7
    }

    private val weeklyDays = mutableListOf<LocalDate>()

    private var selectedDay: LocalDate = LocalDate.now()

    private val notToDoCountList = mutableListOf<Pair<LocalDate?,Double>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewWeeklyCalendarDayBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.view_weekly_calendar_day, parent, false
        )
        return WeeklyViewHolder(binding, onWeeklyDayClickListener)
    }

    override fun onBindViewHolder(holder: WeeklyViewHolder, position: Int) {
        notToDoCountList.indexOfLast {
            it.first?.isEqual(weeklyDays[position]) == true
        }.also {
            if (it != -1) {
                holder.onNotToDoBind(weeklyDays[position],notToDoCountList[it].second)
            } else {
                if (selectedDay.isEqual(weeklyDays[position])) {
                    holder.onSelectBind(weeklyDays[position])
                } else {
                    holder.onBind(weeklyDays[position])
                }
            }
        }
    }

    override fun getItemCount(): Int = weeklyDays.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<LocalDate>) {
        weeklyDays.clear()
        weeklyDays.addAll(list)
        notifyItemRangeChanged(
            WEEKLY_CALENDAR_START_POSITION, WEEKLY_CALENDAR_END_POSITION
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectedDay(localDate: LocalDate) {
        val lastPosition = weeklyDays.indexOfLast { it == selectedDay }
        selectedDay = localDate
        val currentPosition = weeklyDays.indexOfLast { it == selectedDay }
        if (lastPosition != -1) {
            notifyItemChanged(lastPosition)
        }
        if (currentPosition != -1) {
            notifyItemChanged(currentPosition)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitNotTodoCountList(list: List<Pair<LocalDate?, Double>>) {
        notToDoCountList.clear()
        notToDoCountList.addAll(list)
        notifyDataSetChanged()
    }
}