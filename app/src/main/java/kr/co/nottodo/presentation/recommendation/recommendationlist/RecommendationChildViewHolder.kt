package kr.co.nottodo.presentation.toplevel.recommendation.recommendationlist

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kr.co.nottodo.databinding.ItemNottodoRecommendationListBinding
import kr.co.nottodo.data.remote.model.ResponseRecommendationCategoryListDto

class RecommendationChildViewHolder(
    private val binding: ItemNottodoRecommendationListBinding,
    private val childClickListener: (view: View, childData: ResponseRecommendationCategoryListDto.RecommendationActions) -> Unit
) : ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

    private lateinit var childData: ResponseRecommendationCategoryListDto.RecommendationActions

    init {
        binding.root.setOnClickListener(this)
        binding.root.setOnLongClickListener(this)
    }


    fun bind(data: ResponseRecommendationCategoryListDto.RecommendationActions) {
        childData = data
        binding.apply {
            tvItemNottodoRecommedationList.text = data.name
        }
    }
    override fun onClick(view: View) {
        childClickListener.invoke(view, childData)
    }

    override fun onLongClick(v: View?): Boolean = false
}
