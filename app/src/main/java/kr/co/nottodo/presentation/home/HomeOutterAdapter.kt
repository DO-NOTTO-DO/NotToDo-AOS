package kr.co.nottodo.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.data.local.HomeDaily
import kr.co.nottodo.databinding.ItemHomeOutBinding
import kr.co.nottodo.util.DiffUtilItemCallback

class HomeOutterAdapter(
    private val menuItemClick: (Int) -> Unit,
    private val todoItemClick: (Int, View) -> Unit
) :
    ListAdapter<HomeDaily, HomeOutterAdapter.OutterViewHolder>(diffUtil) {
    private lateinit var itemCheckBoxClickListener: ItemCheckBoxClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutterViewHolder {
        val binding =
            ItemHomeOutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OutterViewHolder(binding, menuItemClick, todoItemClick)
    }

    override fun onBindViewHolder(holder: OutterViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class OutterViewHolder(
        private val binding: ItemHomeOutBinding,
        private val menuItemClick: (Int) -> Unit,
        private val todoItemClick: (Int, View) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: HomeDaily) {
            binding.ivHomeItemOutMeatball.setOnClickListener { menuItemClick(absoluteAdapterPosition) }
            binding.ivHomeOutCheckbox.setOnClickListener { todoItemClick(absoluteAdapterPosition, binding.ivHomeOutCheckbox) }
            binding.tvHomeItemOutTitle.text = data.title
//            binding.tvHomeItemOutTitleNotodo.text = data.situation
//            binding.tvHomeOutterDesciption.text = data.situation
            binding.rvHomeInnerRecycler.adapter = HomeInnerAdapter()
        }
    }

    interface ItemCheckBoxClickListener {
        fun onClick(view: View, position: Int, itemId: Long)
    }

    fun setItemCheckBoxClickListener(itemCheckBoxClickListener: ItemCheckBoxClickListener) {
        this.itemCheckBoxClickListener = itemCheckBoxClickListener
    }

    companion object {
        val diffUtil = DiffUtilItemCallback<HomeDaily>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}