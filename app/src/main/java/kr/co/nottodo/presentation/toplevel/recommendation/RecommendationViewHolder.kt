package kr.co.nottodo.presentation.toplevel.recommendation

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import kr.co.nottodo.databinding.ItemRecommendBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.Recommendationdata

class RecommendationViewHolder(
    val binding: ItemRecommendBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(data: Recommendationdata) {
        Glide.with(binding.root.context)
            .load("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory&fname=https://k.kakaocdn.net/dn/EShJF/btquPLT192D/SRxSvXqcWjHRTju3kHcOQK/img.png")
            .into(binding.ivRecommend)
    }
}