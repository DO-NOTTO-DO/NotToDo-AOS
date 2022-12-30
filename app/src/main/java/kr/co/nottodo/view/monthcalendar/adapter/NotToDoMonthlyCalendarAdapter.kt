package kr.co.nottodo.view.monthcalendar.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewNotToDoCalendarMonthBinding
import kr.co.nottodo.view.monthcalendar.NotToDoCalendarMonth
import kr.co.nottodo.view.monthcalendar.viewholder.NotToDoMonthViewHolder

class NotToDoMonthlyCalendarAdapter(
    private val onClickPrevMonth: () -> Unit,
    private val onClickNextMonth: () -> Unit
) : RecyclerView.Adapter<NotToDoMonthViewHolder>() {

    private val notToDoCalendarMonthList = mutableListOf<NotToDoCalendarMonth>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotToDoMonthViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewNotToDoCalendarMonthBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.view_not_to_do_calendar_month, parent, false
        )
        return NotToDoMonthViewHolder(
            binding,
            onClickPrevMonth,
            onClickNextMonth
        )
    }

    override fun onBindViewHolder(holder: NotToDoMonthViewHolder, position: Int) {
        holder.onBind(notToDoCalendarMonthList[position])
    }

    override fun getItemCount(): Int = notToDoCalendarMonthList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<NotToDoCalendarMonth>) {
        notToDoCalendarMonthList.clear()
        notToDoCalendarMonthList.addAll(list)
        notifyDataSetChanged()
    }
}