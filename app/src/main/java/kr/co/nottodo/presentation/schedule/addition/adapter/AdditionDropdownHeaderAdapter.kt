package kr.co.nottodo.presentation.schedule.addition.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.databinding.ItemAdditionDropdownHeaderBinding
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity.Companion.additionRecentHeader

class AdditionDropdownHeaderAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemAdditionDropdownHeaderBinding.inflate(inflater, parent, false)
        return DropdownHeaderViewHolder(binding)
    }

    class DropdownHeaderViewHolder(private val binding: ItemAdditionDropdownHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            binding.tvAdditionDropdownHeader.text = additionRecentHeader
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DropdownHeaderViewHolder).onBind()
    }

    override fun getItemCount(): Int {
        return 1
    }
}
