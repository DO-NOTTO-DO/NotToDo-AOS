package kr.co.nottodo.view.calendar.weekly

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import kr.co.nottodo.R
import kr.co.nottodo.view.calendar.monthly.model.TOTAL_COLUMN_COUNT
import kr.co.nottodo.view.calendar.weekly.listener.OnWeeklyCalendarSwipeListener
import kr.co.nottodo.view.calendar.weekly.listener.OnWeeklyDayClickListener
import java.time.DayOfWeek
import java.time.LocalDate

class WeeklyCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr), OnWeeklyDayClickListener {

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    private val weeklyAdapter = WeeklyAdapter(this)
    private var currentDate = LocalDate.now()
    var selectedDate = LocalDate.now()
    var mondayDate: LocalDate? = null
    private lateinit var gestureDetector: GestureDetector
    private var onWeeklyDayClickListener: OnWeeklyDayClickListener? = null
    private var onWeeklyCalendarSwipeListener: OnWeeklyCalendarSwipeListener? = null

    init {
        removeDefaultItemAnimator()
        removeScrollRippleEffect()
        setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        layoutManager = GridLayoutManager(context, TOTAL_COLUMN_COUNT)
        adapter = weeklyAdapter
        gestureDetector = GestureDetector(context, object : GestureDetector.OnGestureListener {
            override fun onDown(e: MotionEvent): Boolean = false

            override fun onShowPress(e: MotionEvent) {
                /** no - op **/
                /** no - op **/
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean = false

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean = true

            override fun onLongPress(e: MotionEvent) {
                /** no - op **/
                /** no - op **/
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                val result = false
                try {
                    val diffY = e2.y - e1.y
                    val diffX = e2.x - e1.x
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                weeklyAdapter.submitList(daysInWeek(currentDate.minusWeeks(1)))
                                currentDate = currentDate.minusWeeks(1)
                                mondayDate = mondayForDate(currentDate)
                                onWeeklyCalendarSwipeListener?.onSwipe(mondayDate)
                            } else {
                                weeklyAdapter.submitList(daysInWeek(currentDate.plusWeeks(1)))
                                currentDate = currentDate.plusWeeks(1)
                                mondayDate = mondayForDate(currentDate)
                                onWeeklyCalendarSwipeListener?.onSwipe(mondayDate)
                            }
                        }
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
                return result
            }
        })
        addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                gestureDetector.onTouchEvent(e)
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
        weeklyAdapter.submitList(daysInWeek(LocalDate.now()))
    }

    private fun removeDefaultItemAnimator() {
        (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    private fun removeScrollRippleEffect() {
        overScrollMode = OVER_SCROLL_NEVER
    }

    private fun daysInWeek(date: LocalDate): List<LocalDate> {
        val days = mutableListOf<LocalDate>()
        var current = mondayForDate(date)
        return if (current == null) {
            emptyList()
        } else {
            val end = current.plusWeeks(1)

            while (current!!.isBefore(end)) {
                days.add(current)
                current = current.plusDays(1)
            }
            days
        }
    }

    private fun mondayForDate(currentDate: LocalDate): LocalDate? {
        var copy = LocalDate.from(currentDate)
        val oneWeekAgo = copy.minusWeeks(1)

        while (copy.isAfter(oneWeekAgo)) {
            if (copy.dayOfWeek == DayOfWeek.MONDAY) {
                mondayDate = copy
                return copy
            }
            copy = copy.minusDays(1)
        }
        return null
    }

    fun refresh() {
        currentDate = LocalDate.now()
        selectedDate = LocalDate.now()
        weeklyAdapter.setSelectedDay(selectedDate)
        weeklyAdapter.submitList(daysInWeek(currentDate))
    }

    fun setNotToDoCount(list: List<Pair<LocalDate?, Double>>) {
        weeklyAdapter.submitNotTodoCountList(list)
    }

    fun setOnWeeklyDayClickListener(onWeeklyDayClickListener: OnWeeklyDayClickListener) {
        this.onWeeklyDayClickListener = onWeeklyDayClickListener
    }

    fun setOnWeeklyDayClickListener(block: (view: View, date: LocalDate) -> Unit) {
        this.onWeeklyDayClickListener = OnWeeklyDayClickListener(block)
    }

    fun setOnWeeklyCalendarSwipeListener(onWeeklyCalendarSwipeListener: OnWeeklyCalendarSwipeListener) {
        this.onWeeklyCalendarSwipeListener = onWeeklyCalendarSwipeListener
    }

    override fun onWeeklyDayClick(view: View, date: LocalDate) {
        selectedDate = date
        weeklyAdapter.setSelectedDay(date)
        onWeeklyDayClickListener?.onWeeklyDayClick(view, date)
    }
}