package kr.co.nottodo.view.monthcalendar.binding

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kr.co.nottodo.R
import kr.co.nottodo.view.monthcalendar.DateType

object NotToDoMonthlyCalendarBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:setCalendarDayTextColor")
    fun bindCalendarDayTextColor(view: TextView, type: DateType) {
        val context = view.context
        view.setTextColor(
            when (type) {
                DateType.WEEKDAY -> ContextCompat.getColor(context, R.color.black)
                DateType.DISABLED -> ContextCompat.getColor(context, R.color.gray2_8E8E93)
                DateType.WEEKEND -> ContextCompat.getColor(context, R.color.color_FF4141)
            }
        )
    }
}