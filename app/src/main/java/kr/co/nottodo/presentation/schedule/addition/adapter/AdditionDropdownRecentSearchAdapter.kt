package kr.co.nottodo.presentation.schedule.addition.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.databinding.ItemAdditionDropdownRecentSearchBinding
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity.Companion.additionRecentSearch

class AdditionDropdownRecentSearchAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemAdditionDropdownRecentSearchBinding.inflate(inflater, parent, false)
        return DropdownSearchViewHolder(binding)
    }

    class DropdownSearchViewHolder(private val binding: ItemAdditionDropdownRecentSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: String) {
            binding.tvAdditionDropdownList.text = data
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DropdownSearchViewHolder).onBind(additionRecentSearch[position])
    }

    override fun getItemCount(): Int {
        return additionRecentSearch.size
    }
}
