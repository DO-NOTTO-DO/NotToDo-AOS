package kr.co.nottodo.presentation.toplevel.recommendation.recommendationfragment

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.nottodo.databinding.ItemRecommendBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.recommendationlistdata.RecommendationData
import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategorySituationDto

class RecommendationViewHolder(
    val binding: ItemRecommendBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: ResponseRecommendationCategorySituationDto.CategorySituation) {
        binding.tvRecommendTitle.text = data.name
        Glide.with(binding.root.context)
            .load(data.image)
            .into(binding.ivRecommend)
    }
}
