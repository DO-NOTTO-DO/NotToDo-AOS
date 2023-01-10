package kr.co.nottodo.presentation.toplevel.recommendation.recommendationlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ItemNottodoRecommendationListTitleBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.recommendationlistdata.RecommendationChildData
import kr.co.nottodo.presentation.toplevel.recommendation.data.recommendationlistdata.RecommendationParentData
import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategoryListDto
import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategorySituationDto

object ParentDiffUtilItemCallback : DiffUtil.ItemCallback<ResponseRecommendationCategoryListDto.CategoryList>() {
    override fun areItemsTheSame(
        oldItem: ResponseRecommendationCategoryListDto.CategoryList,
        newItem: ResponseRecommendationCategoryListDto.CategoryList
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ResponseRecommendationCategoryListDto.CategoryList,
        newItem: ResponseRecommendationCategoryListDto.CategoryList
    ): Boolean {
        return oldItem == newItem
    }
}

class RecommendationParentAdapter(
    private val childItemClick: (view: View, childData: ResponseRecommendationCategoryListDto.RecommendationActions) -> Unit
) : ListAdapter<ResponseRecommendationCategoryListDto.CategoryList, RecommendationParentViewHolder>(ParentDiffUtilItemCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendationParentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemNottodoRecommendationListTitleBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_nottodo_recommendation_list_title,
                parent,
                false
            )
        return RecommendationParentViewHolder(
            binding
        ) { view, childData ->
            childItemClick(view, childData)
        }
    }

    override fun onBindViewHolder(holder: RecommendationParentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}