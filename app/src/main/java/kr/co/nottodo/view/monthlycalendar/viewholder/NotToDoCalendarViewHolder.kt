package kr.co.nottodo.view.monthlycalendar.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.view.monthlycalendar.model.NotToDoCalendarDay

abstract class NotToDoCalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(data: NotToDoCalendarDay)
}