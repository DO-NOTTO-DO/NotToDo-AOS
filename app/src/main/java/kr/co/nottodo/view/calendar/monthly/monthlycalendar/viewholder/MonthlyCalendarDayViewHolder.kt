package kr.co.nottodo.view.calendar.monthly.monthlycalendar.viewholder

import android.text.format.DateUtils
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewMonthlyCalendarDayBinding
import kr.co.nottodo.view.calendar.monthly.model.MonthlyCalendarDay

class MonthlyCalendarDayViewHolder(
    private val binding: ViewMonthlyCalendarDayBinding
) : ViewHolder(binding.root) {

    private lateinit var dayData: MonthlyCalendarDay.DayMonthly

    fun onBind(data: MonthlyCalendarDay) {
        if (data is MonthlyCalendarDay.DayMonthly) {
            dayData = data
            binding.apply {
                ivToday.visibility =
                    if (DateUtils.isToday(data.date.time)) View.VISIBLE else View.GONE
                dayItem = data
                executePendingBindings()
            }
        }
    }

    fun onNotToDoBind(data: MonthlyCalendarDay, notToDoCount: Int) {
        if (data is MonthlyCalendarDay.DayMonthly) {
            dayData = data
            binding.apply {
                when (notToDoCount) {
                    1 -> {
                        ivNotToDo.visibility = View.VISIBLE
                        ivNotToDo.setImageDrawable(
                            ContextCompat.getDrawable(
                                binding.root.context,
                                R.drawable.bg_monthly_calendar_not_to_do_1
                            )
                        )
                    }
                    2 -> {
                        ivNotToDo.visibility = View.VISIBLE
                        ivNotToDo.setImageDrawable(
                            ContextCompat.getDrawable(
                                binding.root.context,
                                R.drawable.bg_monthly_calendar_not_to_do_2
                            )
                        )
                    }
                    3 -> {
                        ivNotToDo.visibility = View.VISIBLE
                        ivNotToDo.setImageDrawable(
                            ContextCompat.getDrawable(
                                binding.root.context,
                                R.drawable.bg_monthly_calendar_not_to_do_3
                            )
                        )
                    }
                    else -> {
                        ivNotToDo.visibility = View.GONE
                    }
                }
                ivToday.visibility =
                    if (DateUtils.isToday(data.date.time)) View.VISIBLE else View.GONE
                dayItem = data
            }
        }
    }
}