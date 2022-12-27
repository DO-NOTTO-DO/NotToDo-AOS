package kr.co.nottodo.view.monthcalendar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kr.co.nottodo.view.monthcalendar.adapter.NotToDoMonthCalendarAdapter
import kr.co.nottodo.view.monthcalendar.util.*
import java.util.*
import java.util.Calendar.*

/**
 * created by ssong-develop on 2022.12.28
 *
 * 큰 구상
 * ConstraintLayout에 viewPager2가 들어가 있는 형태로 만든다.
 *
 * 세부 사항
 * 1. viewPager2 어댑터를 recyclerView로 만들 수 있음
 * 1-1. 그렇다면 recyclerView의 itemView를 각 페이지 단위로 만든다.
 * 2. 현재 recyclerView vertical로 된 로직의 일부를 각 페이지를 구현하는데 사용
 * 3. 그럼 될 거 같은데?? 대충 메모만 해놓자
 */
class NotToDoMonthlyCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val timeZone = TimeZone.getDefault()

    private val locale = Locale.KOREA

    private val notToDoMonthCalendarAdapter = NotToDoMonthCalendarAdapter()

    private val startCalendar = Calendar.getInstance(timeZone, locale)

    private val endCalendar = Calendar.getInstance(timeZone, locale)

    private var calendarDataList: List<CalendarData> = listOf()

    // TODO 아직 구현 안함, 나중에 구현 할 에정
    private var onSelectedListener: () -> Unit = {}

    init {
        if (attrs != null) {
            getStyleableAttrs(attrs)
        }
        initBackgroundColor()
        initializeNotToDoMonthCalendar()
    }

    private fun initBackgroundColor() {
        setBackgroundColor(Color.parseColor("#ffffff"))
    }

    private fun initializeNotToDoMonthCalendar() {
        initStartCalendar()
        initEndCalendar()
        layoutManager = GridLayoutManager(context, TOTAL_COLUMN_COUNT).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return calendarDataList[position].columnCount
                }
            }
        }
        adapter = notToDoMonthCalendarAdapter

        initCalendarData()
    }

    private fun initStartCalendar() {
        startCalendar.apply {
            set(HOUR_OF_DAY, 0)
            set(MINUTE, 0)
            set(SECOND, 0)
            set(MILLISECOND, 0)
        }
    }

    private fun initEndCalendar() {
        endCalendar.apply {
            time = startCalendar.time
            // TODO 일단 DEFAULT 1년짜리로 만들었는데, xml 상에서 더 받아내도록 할 수도 있다!
            endCalendar.add(YEAR, 1)
        }
    }

    private fun initCalendarData() {
        calendarDataList = buildCalendarData()
        notToDoMonthCalendarAdapter.submitList(calendarDataList)
    }

    private fun buildCalendarData(): List<CalendarData> {
        val list = mutableListOf<CalendarData>()
        val calendar = Calendar.getInstance(timeZone, locale)
        calendar.withTime(startCalendar.time)

        val monthDifference = endCalendar.totalMonthDifference(startCalendar)

        calendar.set(DAY_OF_MONTH, 1)
        (0..monthDifference).forEach { _ ->
            val totalDayInMonth = calendar.getActualMaximum(DAY_OF_MONTH)
            (1..totalDayInMonth).forEach { _ ->
                val day = calendar.get(DAY_OF_MONTH)
                val dayOfYear = calendar.get(DAY_OF_YEAR)
                val dayOfWeek = calendar.get(DAY_OF_WEEK)
                val dateType =
                    if (calendar.isBefore(startCalendar) || calendar.isAfter(endCalendar)) {
                        DateType.DISABLED
                    } else if (calendar.isWeekend()) {
                        DateType.WEEKEND
                    } else {
                        DateType.WEEKDAY
                    }
                when (day) {
                    1 -> {
                        list.add(
                            CalendarData.Month(
                                calendar.toPrettyDateString(),
                                dayOfYear.toString()
                            )
                        )
                        list.addAll(createStartEmptyView(dayOfWeek))
                        list.add(
                            CalendarData.Day(
                                day.toString(),
                                calendar.toPrettyDateString(),
                                calendar.time,
                                state = dateType
                            )
                        )
                    }
                    totalDayInMonth -> {
                        list.add(
                            CalendarData.Day(
                                day.toString(),
                                calendar.toPrettyDateString(),
                                calendar.time,
                                state = dateType
                            )
                        )
                        list.addAll(createEndEmptyView(dayOfWeek))
                    }
                    else -> {
                        list.add(
                            CalendarData.Day(
                                day.toString(),
                                calendar.toPrettyDateString(),
                                calendar.time,
                                state = dateType
                            )
                        )
                    }
                }
                calendar.add(DATE, 1)
            }
        }
        return list
    }

    private fun createEndEmptyView(dayOfWeek: Int): List<CalendarData.Empty> {
        val numberOfEmptyView = when (dayOfWeek) {
            SUNDAY -> 6
            MONDAY -> 5
            TUESDAY -> 4
            WEDNESDAY -> 3
            THURSDAY -> 2
            FRIDAY -> 1
            else -> 6
        }

        val listEmpty = mutableListOf<CalendarData.Empty>()
        repeat((0 until numberOfEmptyView).count()) { listEmpty.add(CalendarData.Empty) }
        return listEmpty
    }


    private fun createStartEmptyView(dayOfWeek: Int): List<CalendarData.Empty> {
        val numberOfEmptyView = when (dayOfWeek) {
            MONDAY -> 1
            TUESDAY -> 2
            WEDNESDAY -> 3
            THURSDAY -> 4
            FRIDAY -> 5
            SATURDAY -> 6
            else -> 0
        }

        val listEmpty = mutableListOf<CalendarData.Empty>()
        repeat((0 until numberOfEmptyView).count()) { listEmpty.add(CalendarData.Empty) }
        return listEmpty
    }

    // TODO 딱히 styleable을 지정할 거 같지는 않습니다..아마?
    private fun getStyleableAttrs(attrs: AttributeSet) {
    }
}