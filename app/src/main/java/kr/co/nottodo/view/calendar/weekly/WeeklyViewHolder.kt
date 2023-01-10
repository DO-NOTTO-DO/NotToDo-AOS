package kr.co.nottodo.view.calendar.weekly

import android.text.format.DateUtils
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewWeeklyCalendarDayBinding
import kr.co.nottodo.view.calendar.monthly.model.MonthlyCalendarDay
import kr.co.nottodo.view.calendar.monthly.util.dayNameParseToKorea
import kr.co.nottodo.view.calendar.weekly.listener.OnWeeklyDayClickListener
import java.sql.Date
import java.time.LocalDate

class WeeklyViewHolder(
    private val binding: ViewWeeklyCalendarDayBinding,
    private val onWeeklyDayClickListener: OnWeeklyDayClickListener
) : ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var weeklyDate: LocalDate

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }

    fun onBind(weeklyDate: LocalDate) {
        val date: Date = Date.valueOf(weeklyDate.toString()) as Date
        this.weeklyDate = weeklyDate
        with(binding) {
            day = weeklyDate.dayOfMonth.toString()
            dayString = weeklyDate.dayOfWeek.name.dayNameParseToKorea()
            ivToday.visibility = if (DateUtils.isToday(date.time)) View.VISIBLE else View.GONE
            itemView.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.white))
            tvWeeklyCalendarDayText.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.black_2a292d
                )
            )
        }
    }

    fun onSelectBind(weeklyDate: LocalDate) {
        val date: Date = Date.valueOf(weeklyDate.toString()) as Date
        this.weeklyDate = weeklyDate
        with(binding) {
            day = weeklyDate.dayOfMonth.toString()
            dayString = weeklyDate.dayOfWeek.name.dayNameParseToKorea()
            ivToday.visibility = if (DateUtils.isToday(date.time)) View.VISIBLE else View.GONE
            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.black_2a292d
                )
            )
            tvWeeklyCalendarDayText.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.white
                )
            )
        }
    }

    fun onNotToDoBind(weeklyDate: LocalDate, notToDoCount: Int) {
        val date: Date = Date.valueOf(weeklyDate.toString()) as Date
        this.weeklyDate = weeklyDate
        binding.apply {
            day = weeklyDate.dayOfMonth.toString()
            dayString = weeklyDate.dayOfWeek.name.dayNameParseToKorea()
            ivToday.visibility = if (DateUtils.isToday(date.time)) View.VISIBLE else View.GONE
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
        }
    }

    override fun onClick(view: View) {
        onWeeklyDayClickListener.onWeeklyDayClick(view, weeklyDate)
    }

    override fun onLongClick(v: View?): Boolean = false
}