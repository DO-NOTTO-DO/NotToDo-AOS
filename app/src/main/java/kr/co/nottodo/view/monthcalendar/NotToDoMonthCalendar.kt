package kr.co.nottodo.view.monthcalendar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewNotToDoCalendarWeekDescriptionBinding
import kr.co.nottodo.view.monthcalendar.adapter.NotToDoDayAdapter
import kr.co.nottodo.view.monthcalendar.util.isBefore
import kr.co.nottodo.view.monthcalendar.util.isWeekend
import kr.co.nottodo.view.monthcalendar.util.toPrettyDateString
import kr.co.nottodo.view.monthcalendar.util.toPrettyMonthString
import java.util.*

/**
 * created by ssong-develop on 2022.12.28
 *
 * 메서드를 직접 호출하는 식으로 해야할 거 같고
 */
class NotToDoMonthCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val timeZone = TimeZone.getDefault()
    private val locale = Locale.KOREA
    private val notToDoDayAdapter = NotToDoDayAdapter()
    private val calendar = Calendar.getInstance(timeZone, locale)
    private var calendarDataList: List<NotToDoCalendarDay> = listOf()
    private var currentDate = calendar.toPrettyMonthString(locale = locale)
        set(value) {
            field = value
            updateCurrentDateTextView()
        }

    private val currentDateTextView = TextView(context).apply {
        id = ViewCompat.generateViewId()
        text = currentDate
        layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    private val calendarHeaderLinearLayout = LinearLayout(context).apply {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        addView(
            ImageView(this.context).apply {
                setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_arrow_left))
                setOnClickListener {
                    calendar.add(Calendar.MONTH, -1)
                    currentDate = calendar.toPrettyMonthString(locale = locale)
                    initCalendarData()
                }
            }
        )

        addView(currentDateTextView)

        addView(
            ImageView(this.context).apply {
                setImageDrawable(ContextCompat.getDrawable(this.context, R.drawable.ic_arrow_right))
                setOnClickListener {
                    calendar.add(Calendar.MONTH, 1)
                    currentDate = calendar.toPrettyMonthString(locale = locale)
                    initCalendarData()
                }
            }
        )
    }

    private val calendarWeekDescriptionView = ViewNotToDoCalendarWeekDescriptionBinding.inflate(
        LayoutInflater.from(context),this,false
    )

    private val monthRecyclerView = RecyclerView(context).apply {
        id = ViewCompat.generateViewId()
        adapter = notToDoDayAdapter
        layoutManager = GridLayoutManager(context, TOTAL_COLUMN_COUNT).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return DAY_COLUMN_COUNT
                }
            }
        }
        layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    init {
        if (attrs != null) {
            getStyleableAttrs(attrs)
        }

        orientation = LinearLayout.VERTICAL

        addView(calendarHeaderLinearLayout)
        addView(calendarWeekDescriptionView.root)
        addView(monthRecyclerView)
        initBackgroundColor()
        initializeNotToDoMonthCalendar()
    }

    private fun updateCurrentDateTextView() {
        currentDateTextView.text = currentDate
    }

    private fun initializeNotToDoMonthCalendar() {
        initCalendarData()
    }

    private fun initCalendarData() {
        calendarDataList = buildCalendarData()
        notToDoDayAdapter.submitList(calendarDataList)
    }

    private fun buildCalendarData(): List<NotToDoCalendarDay> {
        // 현재의 Calendar
        val currentCalendar = Calendar.getInstance()

        // 현재 달력이 보여주고 있는 Calendar instance의 복사본
        val proxyCalendar = Calendar.getInstance().apply {
            set(Calendar.MONTH, calendar.get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))
            set(Calendar.YEAR, calendar.get(Calendar.YEAR))
        }

        val totalDayInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val calendarDayList = mutableListOf<NotToDoCalendarDay>()
        (1..totalDayInMonth).forEach { day ->
            proxyCalendar.set(Calendar.DAY_OF_MONTH, day)
            val dayOfWeek = proxyCalendar.get(Calendar.DAY_OF_WEEK)
            val dateType = if (!currentCalendar.isBefore(proxyCalendar)) {
                DateType.DISABLED
            } else if (proxyCalendar.isWeekend()) {
                DateType.WEEKEND
            } else {
                DateType.WEEKDAY
            }

            when (day) {
                1 -> {
                    calendarDayList.addAll(createStartEmptyView(dayOfWeek))
                    calendarDayList.add(
                        NotToDoCalendarDay.Day(
                            day.toString(),
                            proxyCalendar.toPrettyDateString(),
                            proxyCalendar.time,
                            state = dateType
                        )
                    )
                }
                totalDayInMonth -> {
                    calendarDayList.add(
                        NotToDoCalendarDay.Day(
                            day.toString(),
                            proxyCalendar.toPrettyDateString(),
                            proxyCalendar.time,
                            state = dateType
                        )
                    )
                    calendarDayList.addAll(createEndEmptyView(dayOfWeek))
                }
                else -> {
                    calendarDayList.add(
                        NotToDoCalendarDay.Day(
                            day.toString(),
                            proxyCalendar.toPrettyDateString(),
                            proxyCalendar.time,
                            state = dateType
                        )
                    )
                }
            }
        }
        return calendarDayList
    }

    private fun createEndEmptyView(dayOfWeek: Int): List<NotToDoCalendarDay.Empty> {
        val numberOfEmptyView = when (dayOfWeek) {
            Calendar.SUNDAY -> 6
            Calendar.MONDAY -> 5
            Calendar.TUESDAY -> 4
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 2
            Calendar.FRIDAY -> 1
            else -> 6
        }

        val listEmpty = mutableListOf<NotToDoCalendarDay.Empty>()
        repeat((0 until numberOfEmptyView).count()) { listEmpty.add(NotToDoCalendarDay.Empty) }
        return listEmpty
    }

    private fun createStartEmptyView(dayOfWeek: Int): List<NotToDoCalendarDay.Empty> {
        val numberOfEmptyView = when (dayOfWeek) {
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            else -> 0
        }

        val listEmpty = mutableListOf<NotToDoCalendarDay.Empty>()
        repeat((0 until numberOfEmptyView).count()) { listEmpty.add(NotToDoCalendarDay.Empty) }
        return listEmpty
    }

    private fun initBackgroundColor() {
        setBackgroundColor(Color.parseColor("#ffffff"))
    }

    private fun getStyleableAttrs(attrs: AttributeSet) {

    }
}