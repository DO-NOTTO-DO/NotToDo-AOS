package kr.co.nottodo.view.monthlycalendar.binding

import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import kr.co.nottodo.R
import kr.co.nottodo.view.monthlycalendar.model.DateType

object NotToDoMonthlyCalendarBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:setCalendarDayTextColor")
    fun bindCalendarDayTextColor(view: TextView, type: DateType) {
        val context = view.context
        view.setTextColor(
            when (type) {
                DateType.WEEKDAY -> ContextCompat.getColor(context, R.color.black)
                DateType.DISABLED -> ContextCompat.getColor(context, R.color.gray_2_8e8e93)
                DateType.WEEKEND -> ContextCompat.getColor(context, R.color.red_ff4141)
            }
        )
    }
}