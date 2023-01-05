package kr.co.nottodo.presentation.toplevel.recommendation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ItemRecommendBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.RecommendationData

class RecommendationAdapter : RecyclerView.Adapter<RecommendationViewHolder>() {

    private val sampleItems = mutableListOf<RecommendationData>(RecommendationData("",""), RecommendationData("",""),RecommendationData("",""),
        RecommendationData("",""),RecommendationData("",""),RecommendationData("",""))
    private var selectedPosition = -1
    private var lastItemSelectedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecommendationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemRecommendBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_recommend, parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecommendationViewHolder, position: Int
    ) {
        if (position == selectedPosition) {
            selectBackground(holder, sampleItems[position])
        } else {
            normalBackground(holder, sampleItems[position])
        }
        bind(holder, position)
        holder.onBind(sampleItems[position])
    }

    override fun getItemCount(): Int = sampleItems.size

    fun submitList(list: List<RecommendationData>) {
        sampleItems.clear()
        sampleItems.addAll(list)
        notifyDataSetChanged()
    }

    private fun bind(
        holder: RecommendationViewHolder,
        position: Int
    ) {
        if (!holder.itemView.hasOnClickListeners()) {
            holder.itemView.setOnClickListener {
                selectedPosition = position
                lastItemSelectedPosition = if (lastItemSelectedPosition == -1) {
                    selectedPosition
                } else {
                    notifyItemChanged(lastItemSelectedPosition)
                    selectedPosition
                }
                notifyItemChanged(selectedPosition)
            }
        }
    }

    private fun normalBackground(
        holder: RecommendationViewHolder,
        data: RecommendationData
    ) {
        holder.binding.apply {
            this.root.setBackgroundColor(Color.parseColor("#334455"))
            executePendingBindings()
        }
    }

    private fun selectBackground(
        holder: RecommendationViewHolder,
        data: RecommendationData
    ) {
        holder.binding.apply {
            this.root.setBackgroundColor(Color.parseColor("#112233"))
            executePendingBindings()
        }
    }
}