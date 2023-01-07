package kr.co.nottodo.view.calendar.weekly

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.bumptech.glide.Glide.init
import kr.co.nottodo.databinding.ViewCalendarWeekDescriptionBinding

class WeeklyCalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    private val calendarWeekDescriptionView = ViewCalendarWeekDescriptionBinding.inflate(
        LayoutInflater.from(context), this, false
    )

    private val weeklyCalendar = WeeklyCalendar(context)

    init {
        orientation = LinearLayout.VERTICAL

        addView(calendarWeekDescriptionView.root)
        addView(weeklyCalendar)
    }

}