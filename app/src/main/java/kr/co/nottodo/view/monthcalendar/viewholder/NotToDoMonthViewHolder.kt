package kr.co.nottodo.view.monthcalendar.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.databinding.ViewNotToDoCalendarMonthBinding
import kr.co.nottodo.view.monthcalendar.DAY_COLUMN_COUNT
import kr.co.nottodo.view.monthcalendar.NotToDoCalendarMonth
import kr.co.nottodo.view.monthcalendar.TOTAL_COLUMN_COUNT
import kr.co.nottodo.view.monthcalendar.adapter.NotToDoDayAdapter

class NotToDoMonthViewHolder(
    private val binding: ViewNotToDoCalendarMonthBinding,
    private val onClickPrevMonth: () -> Unit,
    private val onClickNextMonth: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var notToDoCalendarMonthData: NotToDoCalendarMonth

    private val dayAdapter: NotToDoDayAdapter = NotToDoDayAdapter()

    init {
        initClickListener()
        initRecyclerView()
    }

    fun onBind(data: NotToDoCalendarMonth) {
        notToDoCalendarMonthData = data
        binding.apply {
            monthItem = notToDoCalendarMonthData
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
