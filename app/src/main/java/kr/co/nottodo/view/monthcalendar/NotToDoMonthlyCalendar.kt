package kr.co.nottodo.view.monthcalendar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.view.monthcalendar.adapter.NotToDoMonthlyCalendarAdapter
import kr.co.nottodo.view.monthcalendar.snaphelper.SnapPagerScrollListener
import kr.co.nottodo.view.monthcalendar.util.*
import timber.log.Timber
import java.util.*
import java.util.Calendar.*

/**
 * created by ssong-develop on 2022.12.28
 *
 * Click이 없는 성취 뷰 캘린더 입니다.
 */
class NotToDoMonthlyCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    companion object {
        private const val DEFAULT_YEAR_AMOUNT = 1
    }

    private val timeZone = TimeZone.getDefault()

    private val locale = Locale.KOREA

    private var currentPosition = 0

    private val notToDoMonthlyCalendarAdapter = NotToDoMonthlyCalendarAdapter(
        onClickPrevMonth = {
            if (currentPosition > 0) {
                scrollToPosition(currentPosition - 1)
            }
        },
        onClickNextMonth = {
            adapter?.itemCount?.let { itemSize ->
                if (currentPosition <= itemSize) {
                    scrollToPosition(currentPosition + 1)
                }
            }
        }
    )

    private val startCalendar = Calendar.getInstance(timeZone, locale)

    private val endCalendar = Calendar.getInstance(timeZone, locale)

    private var calendarDataList: List<NotToDoCalendarMonth> = listOf()

    private val snapHelper = PagerSnapHelper()

    private val snapPagerScrollListener = SnapPagerScrollListener(
        snapHelper,
        SnapPagerScrollListener.ON_SCROLL,
        true,
        object : SnapPagerScrollListener.OnChangeListener {
            override fun onSnapped(position: Int) {
                currentPosition = position
                Timber.d("ssong-develop", "$position")
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
            endCalendar.add(YEAR,100)
        }
    }

    private fun initCalendarData() {
        calendarDataList = buildCalendarData()
        notToDoMonthlyCalendarAdapter.submitList(calendarDataList)
    }

    private fun buildCalendarData(): List<NotToDoCalendarMonth> {
        val calendarMonthList = mutableListOf<NotToDoCalendarMonth>()
        val dateCalendar = Calendar.getInstance(timeZone, locale)
        val monthCalendar = Calendar.getInstance(timeZone, locale)

        dateCalendar.withTime(startCalendar.time)
        monthCalendar.withTime(startCalendar.time)

        val monthDifference = endCalendar.totalMonthDifference(startCalendar)

        dateCalendar.set(DAY_OF_MONTH, 1)
        monthCalendar.set(DAY_OF_MONTH, 1)
        (0..monthDifference).forEach { _ ->
            val totalDayInMonth = dateCalendar.getActualMaximum(DAY_OF_MONTH)
            val calendarDayList = mutableListOf<NotToDoCalendarDay>()
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

                when (day) {
                    1 -> {
                        calendarDayList.addAll(createStartEmptyView(dayOfWeek))
                        calendarDayList.add(
                            NotToDoCalendarDay.Day(
                                day.toString(),
                                dateCalendar.toPrettyDateString(),
                                dateCalendar.time,
                                state = dateType
                            )
                        )
                    }
                    totalDayInMonth -> {
                        calendarDayList.add(
                            NotToDoCalendarDay.Day(
                                day.toString(),
                                dateCalendar.toPrettyDateString(),
                                dateCalendar.time,
                                state = dateType
                            )
                        )
                        calendarDayList.addAll(createEndEmptyView(dayOfWeek))
                    }
                    else -> {
                        calendarDayList.add(
                            NotToDoCalendarDay.Day(
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
            calendarMonthList.add(
                NotToDoCalendarMonth(
                    monthCalendar.toPrettyMonthString(
                        locale = locale
                    ),
                    dayOfYear.toString(),
                    calendarDayList
                )
            )
            monthCalendar.add(MONTH, 1)
        }
        return calendarMonthList
    }

    private fun createEndEmptyView(dayOfWeek: Int): List<NotToDoCalendarDay.Empty> {
        val numberOfEmptyView = when (dayOfWeek) {
            SUNDAY -> 6
            MONDAY -> 5
            TUESDAY -> 4
            WEDNESDAY -> 3
            THURSDAY -> 2
            FRIDAY -> 1
            else -> 6
        }

        val listEmpty = mutableListOf<NotToDoCalendarDay.Empty>()
        repeat((0 until numberOfEmptyView).count()) { listEmpty.add(NotToDoCalendarDay.Empty) }
        return listEmpty
    }

    private fun createStartEmptyView(dayOfWeek: Int): List<NotToDoCalendarDay.Empty> {
        val numberOfEmptyView = when (dayOfWeek) {
            MONDAY -> 1
            TUESDAY -> 2
            WEDNESDAY -> 3
            THURSDAY -> 4
            FRIDAY -> 5
            SATURDAY -> 6
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