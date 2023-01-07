package kr.co.nottodo.view.calendar.weekly

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import kr.co.nottodo.view.calendar.monthly.model.TOTAL_COLUMN_COUNT
import kr.co.nottodo.view.calendar.weekly.listener.OnWeeklyDayClickListener
import java.time.DayOfWeek
import java.time.LocalDate

// 리사이클러뷰가 맞는거 같음
// 코드 정리합시다
@SuppressLint("ClickableViewAccessibility")
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

    private var onWeeklyDayClickListener: OnWeeklyDayClickListener? = null

    private lateinit var gestureDetector: GestureDetector

    init {
        removeDefaultItemAnimator()
        removeScrollRippleEffect()
        layoutManager = GridLayoutManager(context, TOTAL_COLUMN_COUNT)
        adapter = weeklyAdapter
        var currentDate = LocalDate.now()
        initGestureDetector(
            onSwipeLeft = {
                weeklyAdapter.submitList(daysInWeek(currentDate.plusWeeks(1)))
                currentDate = currentDate.plusWeeks(1)
            },
            onSwipeRight = {
                weeklyAdapter.submitList(daysInWeek(currentDate.minusWeeks(1)))
                currentDate = currentDate.minusWeeks(1)
            }
        )
        setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
        }
        weeklyAdapter.submitList(daysInWeek(LocalDate.now()))
    }

    private fun removeDefaultItemAnimator() {
        (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    private fun removeScrollRippleEffect() {
        overScrollMode = OVER_SCROLL_NEVER
    }

    private fun initGestureDetector(
        onSwipeLeft: () -> Unit,
        onSwipeRight: () -> Unit
    ) {
        gestureDetector = GestureDetector(context, object : GestureDetector.OnGestureListener {
            override fun onDown(e: MotionEvent): Boolean = false

            override fun onShowPress(e: MotionEvent) {
                /** no - op **/
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean = false

            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean = false

            override fun onLongPress(e: MotionEvent) {
                /** no - op **/
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                var result = false
                try {
                    val diffY = e2.y - e1.y
                    val diffX = e2.x - e1.x
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight()
                            } else {
                                onSwipeLeft()
                            }
                            result = true
                        }
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
                return result
            }
        })
    }

    private fun daysInWeek(date: LocalDate): List<LocalDate> {
        val days = mutableListOf<LocalDate>()
        var current = sundayForDate(date)
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

    private fun sundayForDate(currentDate: LocalDate): LocalDate? {
        var copy = LocalDate.from(currentDate)
        val oneWeekAgo = copy.minusWeeks(1)

        while (copy.isAfter(oneWeekAgo)) {
            if (copy.dayOfWeek == DayOfWeek.SUNDAY) return copy
            copy = copy.minusDays(1)
        }
        return null
    }

    fun setOnWeeklyDayClickListener(onWeeklyDayClickListener: OnWeeklyDayClickListener) {
        this.onWeeklyDayClickListener = onWeeklyDayClickListener
    }

    fun setOnWeeklyDayClickListener(block : (view: View, date: LocalDate) -> Unit) {
        this.onWeeklyDayClickListener = OnWeeklyDayClickListener(block)
    }

    override fun onWeeklyDayClick(view: View, date: LocalDate) {
        onWeeklyDayClickListener?.onWeeklyDayClick(view, date)
    }
}