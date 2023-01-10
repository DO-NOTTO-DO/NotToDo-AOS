package kr.co.nottodo.presentation.toplevel.recommendation.recommendationlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ItemNottodoRecommendationListBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategoryListDto

class ChildAdapter(
    private val childClickListener: (view: View, childData: ResponseRecommendationCategoryListDto.RecommendationActions) -> Unit
) : RecyclerView.Adapter<RecommendationChildViewHolder>() {

    private val childItems = mutableListOf<ResponseRecommendationCategoryListDto.RecommendationActions>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendationChildViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemNottodoRecommendationListBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_nottodo_recommendation_list, parent, false
            )
        return RecommendationChildViewHolder(binding, childClickListener)
    }

    override fun onBindViewHolder(holder: RecommendationChildViewHolder, position: Int) {
        holder.bind(childItems[position])
    }

    override fun getItemCount(): Int = childItems.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ResponseRecommendationCategoryListDto.RecommendationActions>) {
        childItems.clear()
        childItems.addAll(list)
        notifyDataSetChanged()
    }
}
