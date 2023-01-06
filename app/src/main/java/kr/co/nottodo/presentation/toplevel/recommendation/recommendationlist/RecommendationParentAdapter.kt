package kr.co.nottodo.presentation.toplevel.recommendation.recommendationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ItemNottodoRecommendationListTitleBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.RecommendationChildData
import kr.co.nottodo.presentation.toplevel.recommendation.data.RecommendationParentData




class RecommendationParentAdapter : RecyclerView.Adapter<RecommendationParentViewHolder>() {
    private val parentItem = mutableListOf<RecommendationParentData>(RecommendationParentData(0,"유튜브 보지 않기", listOf(
        RecommendationChildData(0,"스마트폰 전원 꺼두기"), RecommendationChildData(1,"유튜브 프리미엄 해지")
    )),
            RecommendationParentData(1,"인스타그램 보지 않기", listOf(RecommendationChildData(0,"계정 비활성화 하기"), RecommendationChildData(1,"인스타 어플리케이션 삭제"))))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationParentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemNottodoRecommendationListTitleBinding =
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_nottodo_recommendation_list_title,
                parent,
                false
            )
        return RecommendationParentViewHolder(binding)

    }

    override fun onBindViewHolder(holder: RecommendationParentViewHolder, position: Int) {
        holder.bind(parentItem(position))
    }

    private fun parentItem(position: Int): RecommendationParentData {
        return parentItem[position]
    }

    override fun getItemCount(): Int {
        return parentItem.size

    }
}