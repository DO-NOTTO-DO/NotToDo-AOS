package kr.co.nottodo.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.data.local.HomeDailyResponse.HomeDaily
import kr.co.nottodo.databinding.ItemHomeOutBinding
import kr.co.nottodo.util.DiffUtilItemCallback
import timber.log.Timber

class HomeOutterAdapter(
    private val menuItemClick: (Int) -> Unit,
    private val todoItemClick: (Int, View) -> Unit,
) :
    ListAdapter<HomeDaily, HomeOutterAdapter.OutterViewHolder>(diffUtil) {
    lateinit var binding: ItemHomeOutBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutterViewHolder {
        binding =
            ItemHomeOutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OutterViewHolder(binding, menuItemClick, todoItemClick)
    }

    override fun onBindViewHolder(holder: OutterViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class OutterViewHolder(
        private val binding: ItemHomeOutBinding,
        private val menuItemClick: (Int) -> Unit,
        private val todoItemClick: (Int, View) -> Unit,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: HomeDaily) {
            binding.ivHomeOutCheckbox.setOnClickListener {
                todoItemClick(
                    absoluteAdapterPosition,
                    it
                )
            }
//            todo completionstatus에 따라서 분기처리
            when (data.completionStatus) {
                "NOTYET" -> binding.ivHomeOutCheckbox.setImageResource(R.drawable.ic_home_checkbox)
                "AMBIGUOUS" -> binding.ivHomeOutCheckbox.setImageResource(R.drawable.ic_checkbox_fail)
                else -> binding.ivHomeOutCheckbox.setImageResource(R.drawable.ic_checkbox_circle)
            }
            binding.tvHomeItemOutTitle.text = data.title
            binding.tvHomeItemOutTitleNotodo.text = data.situation
            binding.tvHomeOutterDesciption.text = data.goal
            binding.ivHomeItemOutMeatball.setOnClickListener { menuItemClick(absoluteAdapterPosition) }
            Timber.e("out ${data.actions}")
            binding.rvHomeInnerRecycler.adapter = HomeInnerAdapter(data.actions)
        }
    }

    companion object {
        val diffUtil = DiffUtilItemCallback<HomeDaily>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}