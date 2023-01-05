package kr.co.nottodo.presentation.toplevel.recommendation.recommendationlist

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.databinding.ItemNottodoRecommendationListTitleBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.RecommendationParentData

class RecommendationParentViewHolder(
    private val binding: ItemNottodoRecommendationListTitleBinding
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var parentData: RecommendationParentData

    private val childAdapter: RecommendationChildAdapter = RecommendationChildAdapter()

    init {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvNottodoRecommendList.apply {
            adapter = childAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun bind(data: RecommendationParentData) {
        parentData = data
        childAdapter.submitList(data.childDatas)
        binding.apply {
            this.tvItemNottodoRecommedationListTitle.text = data.title
            executePendingBindings()
        }
    }
}