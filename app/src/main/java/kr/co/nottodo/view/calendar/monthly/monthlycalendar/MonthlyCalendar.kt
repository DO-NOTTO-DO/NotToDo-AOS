package kr.co.nottodo.view.calendar.monthly.monthlycalendar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewCalendarWeekDescriptionBinding
import kr.co.nottodo.util.extension.dpToPx
import kr.co.nottodo.view.NoRippleRecyclerView
import kr.co.nottodo.view.calendar.monthly.monthlycalendar.adapter.MonthlyCalendarDayAdapter
import kr.co.nottodo.view.calendar.monthly.model.DAY_COLUMN_COUNT
import kr.co.nottodo.view.calendar.monthly.model.DateType
import kr.co.nottodo.view.calendar.monthly.model.MonthlyCalendarDay
import kr.co.nottodo.view.calendar.monthly.model.TOTAL_COLUMN_COUNT
import kr.co.nottodo.view.calendar.monthly.monthlycalendar.listener.MonthlyCalendarNextMonthListener
import kr.co.nottodo.view.calendar.monthly.monthlycalendar.listener.MonthlyCalendarPrevMonthListener
import kr.co.nottodo.view.calendar.monthly.util.*
import java.util.*
import java.util.Calendar.*

/**
 * created by ssong-develop on 2022.12.31
 *
 * No Swipe Effection MonthlyCalendar
 *
 * 성취 뷰에서 사용합니다.
 */
class MonthlyCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val timeZone = TimeZone.getDefault()
    private val locale = Locale.KOREA
    private val monthlyCalendarDayAdapter = MonthlyCalendarDayAdapter()
    private val calendar = Calendar.getInstance(timeZone, locale)
    private var calendarDataList: List<MonthlyCalendarDay> = listOf()
    private var currentDate = calendar.toPrettyMonthString(locale = locale)
        set(value) {
            field = value
            updateCurrentDateTextView()
        }

    private var monthlyCalendarNextMonthListener: MonthlyCalendarNextMonthListener? = null
    private var monthlyCalendarPrevMonthListener: MonthlyCalendarPrevMonthListener? = null

    private val currentDateTextView = TextView(context, null, R.style.M14).apply {
        id = ViewCompat.generateViewId()
        text = currentDate
        layoutParams =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        gravity = Gravity.CENTER_VERTICAL or Gravity.CENTER_HORIZONTAL
        typeface = ResourcesCompat.getFont(context,R.font.pretendard_medium)
        setTextColor(ContextCompat.getColor(context, R.color.black_2a292d))
        setTextSize(TypedValue.COMPLEX_UNIT_DIP,14f)
        setBackgroundResource(R.drawable.bg_monthly_calendar_current_month)
    }

    private val calendarHeaderLinearLayout = LinearLayout(context).apply {
        id = ViewCompat.generateViewId()
        orientation = HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        )
        setPadding(context.dpToPx(24),context.dpToPx(24),0,context.dpToPx(24))

        addView(currentDateTextView)

        addView(
            View(context).apply {
                layoutParams = LinearLayout.LayoutParams(0, 0, 1f)
            }
        )

        addView(
            ImageView(this.context).apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this.context,
                        R.drawable.ic_left_arrow_monthly_calendar
                    )
                )
                setOnClickListener {
                    calendar.add(Calendar.MONTH, -1)
                    currentDate = calendar.toPrettyMonthString(locale = locale)
                    initCalendarData()
                    monthlyCalendarPrevMonthListener?.onShowPrevMonth(this,calendar.toApiDateString())
                }

                setPadding(context.dpToPx(6),context.dpToPx(6),context.dpToPx(6),context.dpToPx(6))
                addCircleRipple()
            }
        )

        addView(
            ImageView(this.context).apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this.context,
                        R.drawable.ic_right_arrow_monthly_calendar
                    )
                )
                setOnClickListener {
                    calendar.add(Calendar.MONTH, 1)
                    currentDate = calendar.toPrettyMonthString(locale = locale)
                    initCalendarData()
                    monthlyCalendarNextMonthListener?.onShowNextMonth(this, calendar.toApiDateString())
                }

                setPadding(context.dpToPx(6),context.dpToPx(6),context.dpToPx(6),context.dpToPx(6))
                addCircleRipple()
            }
        )

        addInvisibleDivider(context.dpToPx(20))
    }

    private val calendarWeekDescriptionView = ViewCalendarWeekDescriptionBinding.inflate(
        LayoutInflater.from(context), this, false
    )

    private val monthRecyclerView = NoRippleRecyclerView(context).apply {
        id = ViewCompat.generateViewId()
        adapter = monthlyCalendarDayAdapter
        layoutManager = GridLayoutManager(context, TOTAL_COLUMN_COUNT).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return DAY_COLUMN_COUNT
                }
            }
        }
        layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    init {
        if (attrs != null) {
            getStyleableAttrs(attrs)
        }
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
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
        monthlyCalendarDayAdapter.submitList(calendarDataList)
    }

    private fun buildCalendarData(): List<MonthlyCalendarDay> {
        // 현재 달력이 보여주고 있는 Calendar instance의 복사본
        val proxyCalendar = Calendar.getInstance().apply {
            set(MONTH, calendar.get(MONTH))
            set(DAY_OF_MONTH, calendar.get(DAY_OF_MONTH))
            set(YEAR, calendar.get(YEAR))
        }

        val totalDayInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val monthlyCalendarDayList = mutableListOf<MonthlyCalendarDay>()
        (1..totalDayInMonth).forEach { day ->
            proxyCalendar.set(Calendar.DAY_OF_MONTH, day)
            val dayOfWeek = proxyCalendar.get(Calendar.DAY_OF_WEEK)
            val dateType = if (proxyCalendar.isWeekend()) {
                DateType.WEEKEND
            } else {
                DateType.WEEKDAY
            }

            when (day) {
                1 -> {
                    monthlyCalendarDayList.addAll(
                        createStartEmptyView(
                            dayOfWeek,
                            proxyCalendar
                        )
                    )
                    monthlyCalendarDayList.add(
                        MonthlyCalendarDay.DayMonthly(
                            day.toString(),
                            proxyCalendar.toPrettyDateString(),
                            proxyCalendar.time,
                            state = dateType
                        )
                    )
                }
                totalDayInMonth -> {
                    monthlyCalendarDayList.add(
                        MonthlyCalendarDay.DayMonthly(
                            day.toString(),
                            proxyCalendar.toPrettyDateString(),
                            proxyCalendar.time,
                            state = dateType
                        )
                    )
                    monthlyCalendarDayList.addAll(
                        createEndEmptyView(
                            dayOfWeek,
                            proxyCalendar
                        )
                    )
                }
                else -> {
                    monthlyCalendarDayList.add(
                        MonthlyCalendarDay.DayMonthly(
                            day.toString(),
                            proxyCalendar.toPrettyDateString(),
                            proxyCalendar.time,
                            state = dateType
                        )
                    )
                }
            }
        }
        return monthlyCalendarDayList
    }

    private fun createEndEmptyView(
        dayOfWeek: Int,
        proxyCalendar: Calendar
    ): List<MonthlyCalendarDay.Empty> {
        val nextCalendar = Calendar.getInstance().apply {
            set(MONTH, proxyCalendar.get(MONTH))
            set(DAY_OF_MONTH, proxyCalendar.get(DAY_OF_MONTH))
            set(YEAR, proxyCalendar.get(YEAR))
        }.also {
            it.add(MONTH, 1)
        }
        var totalDayInNextMonth = nextCalendar.getActualMinimum(DAY_OF_MONTH)
        val numberOfEmptyView = when (dayOfWeek) {
            Calendar.SUNDAY -> 6
            Calendar.MONDAY -> 5
            Calendar.TUESDAY -> 4
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 2
            Calendar.FRIDAY -> 1
            else -> 0
        }

        val listEmpty = mutableListOf<MonthlyCalendarDay.Empty>()
        repeat((0 until numberOfEmptyView).count()) {
            listEmpty.add(
                MonthlyCalendarDay.Empty(
                    totalDayInNextMonth++.toString()
                )
            )
        }
        return listEmpty
    }

    private fun createStartEmptyView(
        dayOfWeek: Int,
        proxyCalendar: Calendar
    ): List<MonthlyCalendarDay.Empty> {
        val previousCalendar = Calendar.getInstance().apply {
            set(MONTH, proxyCalendar.get(MONTH))
            set(DAY_OF_MONTH, proxyCalendar.get(DAY_OF_MONTH))
            set(YEAR, proxyCalendar.get(YEAR))
        }.also {
            it.add(MONTH, -1)
        }
        val numberOfEmptyView = when (dayOfWeek) {
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            else -> 0
        }
        var startDayInPreviousMonth = previousCalendar.getActualMaximum(DAY_OF_MONTH) - numberOfEmptyView + 1
        val listEmpty = mutableListOf<MonthlyCalendarDay.Empty>()
        repeat((0 until numberOfEmptyView).count()) {
            listEmpty.add(
                MonthlyCalendarDay.Empty(
                    startDayInPreviousMonth++.toString()
                )
            )
        }
        return listEmpty
    }

    private fun initBackgroundColor() {
        setBackgroundColor(Color.parseColor("#ffffff"))
    }

    private fun getStyleableAttrs(attrs: AttributeSet) {

    }

    fun setOnMonthlyCalendarNextMonthListener(monthlyCalendarNextMonthListener: MonthlyCalendarNextMonthListener) {
        this.monthlyCalendarNextMonthListener = monthlyCalendarNextMonthListener
    }

    fun setOnMonthlyCalendarNextMonthListener(block: (view: View, dateString: String) -> Unit) {
        this.monthlyCalendarNextMonthListener = MonthlyCalendarNextMonthListener(block)
    }

    fun setOnMonthlyCalendarPrevMonthListener(monthlyCalendarPrevMonthListener: MonthlyCalendarPrevMonthListener) {
        this.monthlyCalendarPrevMonthListener = monthlyCalendarPrevMonthListener
    }

    fun setOnMonthlyCalendarPrevMonthListener(block: (view: View, dateString: String) -> Unit) {
        this.monthlyCalendarPrevMonthListener = MonthlyCalendarPrevMonthListener(block)
    }
}

fun LinearLayout.addInvisibleDivider(width: Int) {
    addView(
        View(this.context).apply {
            layoutParams = LinearLayout.LayoutParams(
                width,0
            )
        }
    )
}