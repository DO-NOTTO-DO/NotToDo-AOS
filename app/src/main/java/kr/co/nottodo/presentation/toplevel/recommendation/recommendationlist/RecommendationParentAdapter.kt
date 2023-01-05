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
    private val parentItem = mutableListOf<RecommendationParentData>(RecommendationParentData(0,"폰 끄기", listOf(
        RecommendationChildData(0,"유튜브 프리미엄 해지"), RecommendationChildData(1,"테스트1")
    )),
            RecommendationParentData(1,"폰 끄기2", listOf(RecommendationChildData(0,"프리미엄 해지2"), RecommendationChildData(1,"테스트2"))))

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