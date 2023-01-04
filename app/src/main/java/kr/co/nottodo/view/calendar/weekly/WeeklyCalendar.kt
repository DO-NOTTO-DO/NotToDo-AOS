package kr.co.nottodo.view.calendar.weekly

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.view.calendar.weekly.snaphelper.SnapPagerScrollListener

class WeeklyCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): RecyclerView(context,attrs,defStyleAttr) {

    private val weeklyAdapter = WeeklyAdapter()

    private val snapHelper = PagerSnapHelper()

    private val snapPagerScrollListener = SnapPagerScrollListener(
        snapHelper,
        SnapPagerScrollListener.ON_SCROLL,
        true,
        object : SnapPagerScrollListener.OnChangeListener {
            override fun onSnapped(position: Int) {

            }
        }
    )

    init {
        adapter = weeklyAdapter
        layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        weeklyAdapter.submitList(listOf("1","2","3","4","5","6"))
        snapHelper.attachToRecyclerView(this)
        addOnScrollListener(snapPagerScrollListener)
    }
}