package kr.co.nottodo.presentation.toplevel.recommendation

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.nottodo.databinding.ItemRecommendBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.RecommendationData

class RecommendationViewHolder(
    val binding: ItemRecommendBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: RecommendationData) {
        Glide.with(binding.root.context)
            .load("https://nottodo-bucket.s3.ap-northeast-2.amazonaws.com/%EC%B6%94%EC%B2%9C+%ED%83%AD+%EC%95%84%EC%9D%B4%EC%BD%98/Property+1%3Dic_sns_default%402x.png")
            .into(binding.ivRecommend)
    }
}
