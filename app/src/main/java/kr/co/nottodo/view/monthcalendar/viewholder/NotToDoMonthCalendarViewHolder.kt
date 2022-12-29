package kr.co.nottodo.view.monthcalendar.viewholder

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.databinding.ViewNotToDoCalendarDayBinding
import kr.co.nottodo.databinding.ViewNotToDoCalendarEmptyBinding
import kr.co.nottodo.databinding.ViewNotToDoCalendarMonthBinding
import kr.co.nottodo.view.monthcalendar.CalendarData
import kr.co.nottodo.view.monthcalendar.DAY_COLUMN_COUNT
import kr.co.nottodo.view.monthcalendar.Month
import kr.co.nottodo.view.monthcalendar.TOTAL_COLUMN_COUNT
import kr.co.nottodo.view.monthcalendar.adapter.NotToDoDayAdapter

abstract class NotToDoCalendarViewHolder(view: View) : ViewHolder(view) {
    abstract fun onBind(data: CalendarData)
}

class NotToDoMonthViewHolder(
    private val binding: ViewNotToDoCalendarMonthBinding
) : ViewHolder(binding.root) {

    private lateinit var monthData: Month

    private val dayAdapter: NotToDoDayAdapter = NotToDoDayAdapter()

    init {
        initRecyclerView()
    }

    fun onBind(data : Month) {
        monthData = data
        binding.apply {
            monthItem = monthData
            dayAdapter.submitList(data.dayList)
            executePendingBindings()
        }
    }

    private fun initRecyclerView() {
        binding.rvMonth.apply {
            layoutManager = GridLayoutManager(context, TOTAL_COLUMN_COUNT).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        // TODO 여기 어떻게 해야할 지 생각좀?
                        return DAY_COLUMN_COUNT
                    }
                }
            }
            adapter = dayAdapter
        }
    }
}

// TODO 클릭 시 어떻게 처리해야할 지에 대한 로직이 들어가야 합니다
class NotToDoDayViewHolder(
    private val binding: ViewNotToDoCalendarDayBinding
) : NotToDoCalendarViewHolder(binding.root) {

    private lateinit var dayData: CalendarData.Day

    override fun onBind(data: CalendarData) {
        if (data is CalendarData.Day) {
            dayData = data
            binding.apply {
                dayItem = data
                executePendingBindings()
            }
        }

        if (!binding.root.hasOnClickListeners()) {
            // TODO ClickListener를 달아주면 됩니다.
        }
    }
}

// TODO Empty가 아니라 DISABLE이 되도록 보여져야 합니다
class NotToDoEmptyViewHolder(
    private val binding: ViewNotToDoCalendarEmptyBinding
) : NotToDoCalendarViewHolder(binding.root) {
    override fun onBind(data: CalendarData) {}
}
