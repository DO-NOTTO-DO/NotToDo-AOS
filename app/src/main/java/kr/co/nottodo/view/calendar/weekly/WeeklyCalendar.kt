package kr.co.nottodo.view.calendar.weekly

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import kr.co.nottodo.R
import kr.co.nottodo.view.calendar.monthly.model.TOTAL_COLUMN_COUNT
import kr.co.nottodo.view.calendar.weekly.listener.OnSwipeTouchListener
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
    private lateinit var gestureDetectorCompat: GestureDetectorCompat

    private var onWeeklyDayClickListener: OnWeeklyDayClickListener? = null

    init {
        removeDefaultItemAnimator()
        removeScrollRippleEffect()
        setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        layoutManager = GridLayoutManager(context, TOTAL_COLUMN_COUNT)
        adapter = weeklyAdapter
        gestureDetectorCompat = GestureDetectorCompat(context, object: GestureDetector.OnGestureListener {
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
            ): Boolean = true

            override fun onLongPress(e: MotionEvent) {
                /** no - op **/
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                Log.d("ssong-develop1","invoke!")
                val result = false

                if(e1 != null && e2 != null){
                    try {
                        val diffY = e2.y - e1.y
                        val diffX = e2.x - e1.x
                        if (Math.abs(diffX) > Math.abs(diffY)) {
                            if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                                if (diffX > 0) {
                                    weeklyAdapter.submitList(daysInWeek(currentDate.minusWeeks(1)))
                                    currentDate = currentDate.minusWeeks(1)
                                } else {
                                    weeklyAdapter.submitList(daysInWeek(currentDate.plusWeeks(1)))
                                    currentDate = currentDate.plusWeeks(1)
                                }
                            }
                        }
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                }
                return result
            }
        })
        weeklyAdapter.submitList(daysInWeek(LocalDate.now()))
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return ev?.let {
            if (gestureDetectorCompat != null) {
                Log.d("ssong-develop1","invoke")
                gestureDetectorCompat.onTouchEvent(it)
            } else {
                Log.d("ssong-develop2","invoke")
                super.dispatchTouchEvent(ev)
                true
            }
        } ?: super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return event?.let {
            if (it.action == MotionEvent.ACTION_MOVE) {
                gestureDetectorCompat.onTouchEvent(event)
                true
            } else {
                super.onTouchEvent(event)
            }
        } ?: super.onTouchEvent(event)
    }

    private fun removeDefaultItemAnimator() {
        (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    private fun removeScrollRippleEffect() {
        overScrollMode = OVER_SCROLL_NEVER
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