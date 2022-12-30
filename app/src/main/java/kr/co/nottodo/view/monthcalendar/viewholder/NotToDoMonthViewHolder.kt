package kr.co.nottodo.view.monthcalendar.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.databinding.ViewNotToDoCalendarMonthBinding
import kr.co.nottodo.view.monthcalendar.DAY_COLUMN_COUNT
import kr.co.nottodo.view.monthcalendar.NotToDoCalendarMonth
import kr.co.nottodo.view.monthcalendar.TOTAL_COLUMN_COUNT
import kr.co.nottodo.view.monthcalendar.adapter.NotToDoDayAdapter

class NotToDoMonthViewHolder(
    private val binding: ViewNotToDoCalendarMonthBinding
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var notToDoCalendarMonthData: NotToDoCalendarMonth

    private val dayAdapter: NotToDoDayAdapter = NotToDoDayAdapter()

    init {
        initRecyclerView()
    }

    fun onBind(data: NotToDoCalendarMonth) {
        notToDoCalendarMonthData = data
        binding.apply {
            monthItem = notToDoCalendarMonthData
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
