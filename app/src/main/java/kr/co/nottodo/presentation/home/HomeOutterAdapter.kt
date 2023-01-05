package kr.co.nottodo.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.data.local.HomeDaily
import kr.co.nottodo.databinding.ItemHomeOutBinding
import kr.co.nottodo.util.DiffUtilItemCallback

class HomeOutterAdapter(
    private val itemClick: (Int) -> Unit
) :
    ListAdapter<HomeDaily, HomeOutterAdapter.OutterViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutterViewHolder {
        val binding =
            ItemHomeOutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OutterViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: OutterViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class OutterViewHolder(
        private val binding: ItemHomeOutBinding,
        private val itemClick: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: HomeDaily) {
            binding.root.setOnClickListener {
                itemClick(absoluteAdapterPosition)
            }
            binding.tvHomeItemOutTitle.text = data.title
//            binding.tvHomeItemOutTitleNotodo.text = data.situation
//            binding.tvHomeOutterDesciption.text = data.situation
            binding.rvHomeInnerRecycler.adapter = HomeInnerAdapter()
        }
    }

    companion object {
        val diffUtil = DiffUtilItemCallback<HomeDaily>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}