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

object ParentDiffUtilItemCallback : DiffUtil.ItemCallback<RecommendationParentData>() {
    override fun areItemsTheSame(
        oldItem: RecommendationParentData,
        newItem: RecommendationParentData
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: RecommendationParentData,
        newItem: RecommendationParentData
    ): Boolean {
        return oldItem == newItem
    }
}

class RecommendationParentAdapter(
    private val testChildItemViewClickBlock: (view: View, childData: RecommendationChildData) -> Unit
) : ListAdapter<RecommendationParentData, RecommendationParentViewHolder>(ParentDiffUtilItemCallback) {

    private val parentItem = mutableListOf<RecommendationParentData>(
        RecommendationParentData(
            0, "유튜브 보지 않기", listOf(
                RecommendationChildData(0, "스마트폰 전원 꺼두기"),
                RecommendationChildData(1, "유튜브 프리미엄 해지")
            )
        ),
        RecommendationParentData(
            1,
            "인스타그램 보지 않기",
            listOf(
                RecommendationChildData(0, "계정 비활성화 하기"),
                RecommendationChildData(1, "인스타 어플리케이션 삭제")
            )
        )
    )

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
            testChildItemViewClickBlock(view, childData)
        }
    }

    override fun onBindViewHolder(holder: RecommendationParentViewHolder, position: Int) {
        holder.bind(parentItem(position))
    }

    private fun parentItem(position: Int): RecommendationParentData {
        return parentItem[position]
    }
}