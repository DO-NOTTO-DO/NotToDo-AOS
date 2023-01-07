package kr.co.nottodo.view.calendar.monthly.monthlycalendar.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.view.calendar.monthly.model.MonthlyCalendarDay

abstract class MonthlyCalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(data: MonthlyCalendarDay)
}