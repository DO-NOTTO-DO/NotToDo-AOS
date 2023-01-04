package kr.co.nottodo.view.nouse

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.databinding.ViewNoUseCalendarMonthBinding
import kr.co.nottodo.view.monthlycalendar.adapter.MonthlyCalendarDayAdapter
import kr.co.nottodo.view.monthlycalendar.model.CalendarMonth
import kr.co.nottodo.view.monthlycalendar.model.DAY_COLUMN_COUNT
import kr.co.nottodo.view.monthlycalendar.model.TOTAL_COLUMN_COUNT

class NotToDoMonthViewHolder(
    private val binding: ViewNoUseCalendarMonthBinding,
    private val onClickPrevMonth: () -> Unit,
    private val onClickNextMonth: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var calendarMonthData: CalendarMonth

    private val dayAdapter: MonthlyCalendarDayAdapter = MonthlyCalendarDayAdapter()

    init {
        initClickListener()
        initRecyclerView()
    }

    fun onBind(data: CalendarMonth) {
        calendarMonthData = data
        binding.apply {
            monthItem = calendarMonthData
            executePendingBindings()
        }
        dayAdapter.submitList(data.dayList)
    }

    private fun initClickListener() {
        binding.apply {
            ivBack.setOnClickListener {
                onClickPrevMonth.invoke()
            }
            ivNext.setOnClickListener {
                onClickNextMonth.invoke()
            }
        }
    }

    private fun initRecyclerView() {
        binding.rvMonth.apply {
            layoutManager = GridLayoutManager(context, TOTAL_COLUMN_COUNT).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return DAY_COLUMN_COUNT
                    }
                }
            }
            adapter = dayAdapter
        }
    }
}
