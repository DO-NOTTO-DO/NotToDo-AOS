package kr.co.nottodo.view.monthcalendar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.view.monthcalendar.adapter.NotToDoMonthlyCalendarAdapter
import kr.co.nottodo.view.monthcalendar.snaphelper.SnapPagerScrollListener
import kr.co.nottodo.view.monthcalendar.util.*
import java.util.*
import java.util.Calendar.*

/**
 * created by ssong-develop on 2022.12.28
 */
class NotToDoMonthlyCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val timeZone = TimeZone.getDefault()

    private val locale = Locale.KOREA

    private val notToDoMonthlyCalendarAdapter = NotToDoMonthlyCalendarAdapter()

    private val startCalendar = Calendar.getInstance(timeZone, locale)

    private val endCalendar = Calendar.getInstance(timeZone, locale)

    // ? 생긱
    private var calendarDataList: List<Month> = listOf()

    private val snapHelper = PagerSnapHelper()

    private val snapPagerScrollListener = SnapPagerScrollListener(
        snapHelper,
        SnapPagerScrollListener.ON_SCROLL,
        true,
        object : SnapPagerScrollListener.OnChangeListener {
            override fun onSnapped(position: Int) {
                Log.d("ssong-develop","$position")
            }
        }
    )

    init {
        if (attrs != null) {
            getStyleableAttrs(attrs)
        }
        initBackgroundColor()
        initializeNotToDoMonthCalendar()
    }

    private fun initializeNotToDoMonthCalendar() {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = notToDoMonthlyCalendarAdapter
        snapHelper.attachToRecyclerView(this)
        addOnScrollListener(snapPagerScrollListener)
        initStartCalendar()
        initEndCalendar()
        initCalendarData()
    }

    private fun initCalendarData() {
        calendarDataList = buildCalendarData()
        notToDoMonthlyCalendarAdapter.submitList(calendarDataList)
    }

    private fun buildCalendarData(): List<Month> {
        val list = mutableListOf<Month>()
        val dateCalendar = Calendar.getInstance(timeZone, locale)
        val monthCalendar = Calendar.getInstance(timeZone, locale)

        dateCalendar.withTime(startCalendar.time)
        monthCalendar.withTime(startCalendar.time)

        val monthDifference = endCalendar.totalMonthDifference(startCalendar)

        dateCalendar.set(DAY_OF_MONTH, 1)
        dateCalendar.set(DAY_OF_MONTH, 1)
        (0..monthDifference).forEach { _ ->
            val totalDayInMonth = dateCalendar.getActualMaximum(DAY_OF_MONTH)
            val subList = mutableListOf<CalendarData>()
            var dayOfYear = -1
            (1..totalDayInMonth).forEach { _ ->
                val day = dateCalendar.get(DAY_OF_MONTH)
                val dayOfWeek = dateCalendar.get(DAY_OF_WEEK)
                dayOfYear = dateCalendar.get(DAY_OF_YEAR)
                val dateType =
                    if (dateCalendar.isBefore(startCalendar) || dateCalendar.isAfter(endCalendar)) {
                        DateType.DISABLED
                    } else if (dateCalendar.isWeekend()) {
                        DateType.WEEKEND
                    } else {
                        DateType.WEEKDAY
                    }
                // TODO renaming
                when (day) {
                    1 -> {
                        subList.addAll(createStartEmptyView(dayOfWeek))
                        subList.add(
                            CalendarData.Day(
                                day.toString(),
                                dateCalendar.toPrettyDateString(),
                                dateCalendar.time,
                                state = dateType
                            )
                        )
                    }
                    totalDayInMonth -> {
                        subList.add(
                            CalendarData.Day(
                                day.toString(),
                                dateCalendar.toPrettyDateString(),
                                dateCalendar.time,
                                state = dateType
                            )
                        )
                        subList.addAll(createEndEmptyView(dayOfWeek))
                    }
                    else -> {
                        subList.add(
                            CalendarData.Day(
                                day.toString(),
                                dateCalendar.toPrettyDateString(),
                                dateCalendar.time,
                                state = dateType
                            )
                        )
                    }
                }
                dateCalendar.add(DATE, 1)
            }
            list.add(
                Month(
                    monthCalendar.toPrettyMonthString(),
                    dayOfYear.toString(),
                    subList
                )
            )
            monthCalendar.add(MONTH,1)
        }
        return list
    }

    // no touch zone

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

    private fun initBackgroundColor() {
        setBackgroundColor(Color.parseColor("#ffffff"))
    }

    private fun getStyleableAttrs(attrs: AttributeSet) {

    }
}