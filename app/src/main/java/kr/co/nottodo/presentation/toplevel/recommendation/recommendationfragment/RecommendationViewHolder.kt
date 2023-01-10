package kr.co.nottodo.presentation.toplevel.recommendation.recommendationfragment

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.nottodo.databinding.ItemRecommendBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategorySituationDto

class RecommendationViewHolder(
    val binding: ItemRecommendBinding
) : RecyclerView.ViewHolder(binding.root){

    private lateinit var recommendationItem: ResponseRecommendationCategorySituationDto.CategorySituation

    fun interface RecommendationClickDelegate {
        fun onClickRecommendation(
            recommendItem: ResponseRecommendationCategorySituationDto.CategorySituation
        )
    }

    fun onBind(data: ResponseRecommendationCategorySituationDto.CategorySituation) {
        recommendationItem = data
        binding.tvRecommendTitle.text = data.name
        Glide.with(binding.root.context)
            .load(data.image)
            .into(binding.ivRecommend)
    }
}
