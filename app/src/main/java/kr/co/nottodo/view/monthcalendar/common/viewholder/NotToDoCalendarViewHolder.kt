package kr.co.nottodo.view.monthcalendar.common.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.view.monthcalendar.common.NotToDoCalendarDay

abstract class NotToDoCalendarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(data: NotToDoCalendarDay)
}