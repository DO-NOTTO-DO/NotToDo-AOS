package kr.co.nottodo.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.data.local.HomeDaily
import kr.co.nottodo.databinding.ItemHomeInnerBinding
import kr.co.nottodo.util.DiffUtilItemCallback

class HomeInnerAdapter :
    ListAdapter<HomeDaily.Action, HomeInnerAdapter.InnerViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val binding =
            ItemHomeInnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InnerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }


    class InnerViewHolder(private val binding: ItemHomeInnerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: HomeDaily.Action) {
            binding.tvHomeItemInnerTitle.text = data.name
        }
    }

    companion object {
        val diffUtil = DiffUtilItemCallback<HomeDaily.Action>(
            onItemsTheSame = { old, new -> old.hashCode() == new.hashCode() },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}