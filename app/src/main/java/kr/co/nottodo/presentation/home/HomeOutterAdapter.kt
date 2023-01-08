package kr.co.nottodo.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.data.local.HomeDaily
import kr.co.nottodo.databinding.ItemHomeOutBinding
import kr.co.nottodo.util.DiffUtilItemCallback

class HomeOutterAdapter(
    private val menuItemClick: (Int) -> Unit,
    private val todoItemClick: (Int, View) -> Unit
) :
    ListAdapter<HomeDaily, HomeOutterAdapter.OutterViewHolder>(diffUtil) {
    lateinit var binding: ItemHomeOutBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutterViewHolder {
        binding =
            ItemHomeOutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OutterViewHolder(binding, menuItemClick, todoItemClick)
    }

    var clickedPosition = 0
    var getEmoji = -1

    override fun onBindViewHolder(holder: OutterViewHolder, position: Int) {

        holder.onBind(currentList[position])
//        if(clickedPosition == position && getEmoji !=-1){
//            holder.set
//        }
    }

    class OutterViewHolder(
        private val binding: ItemHomeOutBinding,
        private val menuItemClick: (Int) -> Unit,
        private val todoItemClick: (Int, View) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: HomeDaily) {
            binding.ivHomeItemOutMeatball.setOnClickListener { menuItemClick(absoluteAdapterPosition) }
            binding.ivHomeOutCheckbox.setOnClickListener {
                todoItemClick(
                    absoluteAdapterPosition,
                    it
                )
            }
            when(data.successstatus) {
                "success" -> {
                    binding.ivHomeOutCheckbox.setImageResource()
                }
                "failed" -> {}
            }
            binding.tvHomeItemOutTitle.text = data.title
//            binding.tvHomeItemOutTitleNotodo.text = data.situation
//            binding.tvHomeOutterDesciption.text = data.situation
            binding.rvHomeInnerRecycler.adapter = HomeInnerAdapter()
        }
    }

    fun setComplete(checkStatus: String) {
        binding.ivHomeOutCheckbox.setImageResource(R.drawable.ic_home_checkbox)
        binding.tvHomeItemOutTitle.setText("김준서").toString()
        binding.ivHomeOutCheckbox.requestLayout()
        Log.d("adapter", "setComplete: ")
    }

    fun setImage(pos: String) {
        when (pos) {
            "NOTYET" -> binding.ivHomeOutCheckbox.setImageResource(R.drawable.ic_home_checkbox)
            "AMBIGUOUS" -> binding.ivHomeOutCheckbox.setImageResource(R.drawable.ic_checkbox_fail)
            else -> binding.ivHomeOutCheckbox.setImageResource(R.drawable.ic_checkbox_circle)
        }
    }

    fun checkBox(checkStatus: String): Int {
        //NOTYET, AMBIGUOUS, FINISH
        val status = when (checkStatus) {
            "NOTYET" -> R.drawable.ic_home_checkbox
            "AMBIGUOUS" -> R.drawable.ic_checkbox_fail
            else -> R.drawable.ic_checkbox_circle
        }
        return status
    }

    companion object {
        val diffUtil = DiffUtilItemCallback<HomeDaily>(
            onItemsTheSame = { old, new -> old.id == new.id },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}