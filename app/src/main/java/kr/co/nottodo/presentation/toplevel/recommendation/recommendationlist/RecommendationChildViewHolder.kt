package com.ssong_develop.nestedrecyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.databinding.ItemNottodoRecommendationListBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.RecommendationChildData


class RecommendationChildViewHolder(
    private val binding: ItemNottodoRecommendationListBinding
) : ViewHolder(binding.root) {

    private lateinit var childData: RecommendationChildData

    fun bind(data: RecommendationChildData) {
        childData = data
        binding.apply {
            tvItemNottodoRecommedationList.text = data.title
        }
    }
}