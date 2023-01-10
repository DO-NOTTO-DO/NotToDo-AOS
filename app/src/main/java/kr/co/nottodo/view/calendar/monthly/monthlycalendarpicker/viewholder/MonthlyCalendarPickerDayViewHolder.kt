package kr.co.nottodo.view.calendar.monthly.monthlycalendarpicker.viewholder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewMonthlyCalendarPickerDayBinding
import kr.co.nottodo.view.calendar.monthly.model.DateType
import kr.co.nottodo.view.calendar.monthly.model.MonthlyCalendarDay
import kr.co.nottodo.view.calendar.monthly.monthlycalendarpicker.listener.MonthlyCalendarPickerClickListener

class MonthlyCalendarPickerDayViewHolder(
    private val binding: ViewMonthlyCalendarPickerDayBinding,
    private val clickHandler: MonthlyCalendarPickerClickListener
) : ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var dayData: MonthlyCalendarDay.DayMonthly

    init {
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun onBind(data: MonthlyCalendarDay) {
        if (data is MonthlyCalendarDay.DayMonthly) {
            dayData = data
            binding.apply {
                dayItem = data
                ivMonthlyCalendarPickerSelect.visibility = View.GONE
                setPickerDayColor(this, data.state)
                executePendingBindings()
            }
        }
    }

    fun onBindSelectedState(data: MonthlyCalendarDay) {
        if (data is MonthlyCalendarDay.DayMonthly) {
            dayData = data
            binding.apply {
                dayItem = data
                ivMonthlyCalendarPickerSelect.visibility = View.VISIBLE
                executePendingBindings()
            }
        }
    }

    private fun setPickerDayColor(
        binding: ViewMonthlyCalendarPickerDayBinding,
        state: DateType
    ) {
        binding.tvDayName.setTextColor(
            ContextCompat.getColor(
                binding.root.context,
                when (state) {
                    DateType.WEEKDAY, DateType.WEEKEND -> {
                        R.color.black_2a292d
                    }
                    DateType.DISABLED -> {
                        R.color.gray_4_d7d7d8
                    }
                }
            )
        )
    }

    override fun onClick(view: View) {
        if (dayData.state != DateType.DISABLED) {
            clickHandler.onDayClick(view, dayData.date)
        }
    }

    override fun onLongClick(view: View): Boolean = false
}