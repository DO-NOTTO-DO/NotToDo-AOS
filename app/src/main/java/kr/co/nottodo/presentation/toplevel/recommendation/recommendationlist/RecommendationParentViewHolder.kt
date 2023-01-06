package kr.co.nottodo.presentation.toplevel.recommendation.recommendationlist

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.databinding.ItemNottodoRecommendationListTitleBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.RecommendationChildData
import kr.co.nottodo.presentation.toplevel.recommendation.data.RecommendationParentData

class RecommendationParentViewHolder(
    private val binding: ItemNottodoRecommendationListTitleBinding,
    private val testChildItemViewClickBlock : (view: View, childData: RecommendationChildData) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var parentData: RecommendationParentData

    private val childAdapter: ChildAdapter = ChildAdapter(
        childClickListener = { view, childData ->
            testChildItemViewClickBlock.invoke(view,childData)
        }
    )

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