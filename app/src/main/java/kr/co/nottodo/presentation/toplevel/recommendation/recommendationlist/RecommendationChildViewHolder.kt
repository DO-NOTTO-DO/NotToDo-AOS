package com.ssong_develop.nestedrecyclerview.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.databinding.ItemNottodoRecommendationListBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.RecommendationChildData


class RecommendationChildViewHolder(
    private val binding: ItemNottodoRecommendationListBinding,
    private val childClickListener: (view: View, childData: RecommendationChildData) -> Unit
) : ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var childData: RecommendationChildData
    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }


    fun bind(data: RecommendationChildData) {
        childData = data
        binding.apply {
            tvItemNottodoRecommedationList.text = data.title
        }
    }
    override fun onClick(view: View) {
        childClickListener.invoke(view, childData)
    }

    override fun onLongClick(v: View?): Boolean = false
}
