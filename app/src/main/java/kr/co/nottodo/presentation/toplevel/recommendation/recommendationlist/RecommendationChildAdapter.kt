package kr.co.nottodo.presentation.toplevel.recommendation.recommendationlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssong_develop.nestedrecyclerview.viewholder.RecommendationChildViewHolder
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ItemNottodoRecommendationListBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.recommendationlistdata.RecommendationChildData

    class ChildAdapter(
        private val childClickListener: (view: View, childData: RecommendationChildData) -> Unit
    ) : RecyclerView.Adapter<RecommendationChildViewHolder>() {

        private val childItems = mutableListOf<RecommendationChildData>()

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
            return RecommendationChildViewHolder(binding,childClickListener)
        }

        override fun onBindViewHolder(holder: RecommendationChildViewHolder, position: Int) {
            holder.bind(childItems[position])
        }

        override fun getItemCount(): Int = childItems.size

        @SuppressLint("NotifyDataSetChanged")
        fun submitList(list: List<RecommendationChildData>) {
            childItems.clear()
            childItems.addAll(list)
            notifyDataSetChanged()
        }
    }
