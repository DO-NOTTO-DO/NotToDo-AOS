package kr.co.nottodo.presentation.toplevel.recommendation.recommendationlist

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.databinding.ItemNottodoRecommendationListTitleBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.recommendationlistdata.RecommendationChildData
import kr.co.nottodo.presentation.toplevel.recommendation.data.recommendationlistdata.RecommendationParentData
import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategoryListDto
import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategorySituationDto

class RecommendationParentViewHolder(
    private val binding: ItemNottodoRecommendationListTitleBinding,
    private val testChildItemViewClickBlock : (view: View, childData: ResponseRecommendationCategoryListDto.RecommendationActions) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var parentData: ResponseRecommendationCategoryListDto.CategoryList

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

    fun bind(data: ResponseRecommendationCategoryListDto.CategoryList) {
        parentData = data
        childAdapter.submitList(data.recommendationActions)
        binding.apply {
            this.tvItemNottodoRecommedationListTitle.text = data.title
            executePendingBindings()
        }
    }
}