package kr.co.nottodo.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.data.local.HomeDailyResponse.HomeDaily
import kr.co.nottodo.databinding.ItemHomeInnerBinding
import kr.co.nottodo.util.DiffUtilItemCallback
import timber.log.Timber

class HomeInnerAdapter(private val actionList: List<HomeDaily.Action>) :
    ListAdapter<HomeDaily.Action, HomeInnerAdapter.InnerViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
        val binding =
            ItemHomeInnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Timber.e("inner ${actionList.size}")
        return InnerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
        holder.onBind(actionList[position])
//        (holder as InnerViewHolder).onBind(actionList[position])
    }

    class InnerViewHolder(private val binding: ItemHomeInnerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: HomeDaily.Action) {
            binding.tvHomeItemInnerTitle.text = data.name
            Timber.e("inneradapter $data")
        }
    }

    companion object {
        val diffUtil = DiffUtilItemCallback<HomeDaily.Action>(
            onItemsTheSame = { old, new -> old.hashCode() == new.hashCode() },
            onContentsTheSame = { old, new -> old == new }
        )
    }
}