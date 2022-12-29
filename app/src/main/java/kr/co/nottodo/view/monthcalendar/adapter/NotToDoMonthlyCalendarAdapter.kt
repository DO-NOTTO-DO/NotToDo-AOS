package kr.co.nottodo.view.monthcalendar.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ViewNotToDoCalendarMonthBinding
import kr.co.nottodo.view.monthcalendar.Month
import kr.co.nottodo.view.monthcalendar.viewholder.NotToDoMonthViewHolder

class NotToDoMonthlyCalendarAdapter : RecyclerView.Adapter<NotToDoMonthViewHolder>() {

    private val monthList = mutableListOf<Month>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotToDoMonthViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ViewNotToDoCalendarMonthBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.view_not_to_do_calendar_month, parent, false
        )
        return NotToDoMonthViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotToDoMonthViewHolder, position: Int) {
        holder.onBind(monthList[position])
    }

    override fun getItemCount(): Int = monthList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Month>) {
        monthList.clear()
        monthList.addAll(list)
        notifyDataSetChanged()
    }
}