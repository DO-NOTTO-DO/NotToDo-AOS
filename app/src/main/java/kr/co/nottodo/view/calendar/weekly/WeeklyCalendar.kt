package kr.co.nottodo.view.calendar.weekly

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import kr.co.nottodo.view.calendar.monthly.model.TOTAL_COLUMN_COUNT
import kr.co.nottodo.view.calendar.weekly.listener.OnSwipeTouchListener
import kr.co.nottodo.view.calendar.weekly.listener.OnWeeklyDayClickListener
import java.time.DayOfWeek
import java.time.LocalDate

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
        // 이 touchListener가 recyclerview 본인 자체를 터치하게 되면 e1좌표가 나오지 않는 것이 문제이다
        // 이 시...그럼 어떻게 해야하는건데
        this.setOnTouchListener(object: OnSwipeTouchListener(context) {
            override fun onSwipeRight() {
                super.onSwipeRight()
                weeklyAdapter.submitList(daysInWeek(currentDate.minusWeeks(1)))
                currentDate = currentDate.minusWeeks(1)
            }

            override fun onSwipeLeft() {
                super.onSwipeLeft()
                weeklyAdapter.submitList(daysInWeek(currentDate.plusWeeks(1)))
                currentDate = currentDate.plusWeeks(1)
            }

            override fun onSwipeUp() {
                super.onSwipeUp()
            }

            override fun onSwipeDown() {
                super.onSwipeDown()
            }
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