package kr.co.nottodo.presentation.toplevel.recommendation.recommendationfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ItemRecommendBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.recommendationlistdata.RecommendationData
import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategorySituationDto

class RecommendationAdapter(
    private val sampleList: List<ResponseRecommendationCategorySituationDto.CategorySituation>,
) : RecyclerView.Adapter<RecommendationViewHolder>() {

    private var selectedPosition = -1
    private var lastItemSelectedPosition = -1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecommendationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemRecommendBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_recommend, parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecommendationViewHolder, position: Int
    ) {
        if (position == selectedPosition) {
            selectBackground(holder, sampleList[position])
        } else {
            normalBackground(holder, sampleList[position])
        }
        bind(holder, position)
        holder.onBind(sampleList[position])
    }

    override fun getItemCount(): Int = sampleList.size

//    fun submitList(list: List<RecommendationData>) {
//        sampleList.clear()
//        sampleList.addAll(list)
//        notifyDataSetChanged()
//    }

    private fun bind(
        holder: RecommendationViewHolder,
        position: Int
    ) {
        if (!holder.itemView.hasOnClickListeners()) {
            holder.itemView.setOnClickListener {
                selectedPosition = position
                lastItemSelectedPosition = if (lastItemSelectedPosition == -1) {
                    selectedPosition
                } else {
                    notifyItemChanged(lastItemSelectedPosition)
                    selectedPosition
                }
                notifyItemChanged(selectedPosition)
            }
        }
    }

    private fun normalBackground(
        holder: RecommendationViewHolder,
        data: ResponseRecommendationCategorySituationDto.CategorySituation
    ) {
        holder.binding.apply {
            Glide.with(
                holder.itemView.context
            )
                .load("https://nottodo-bucket.s3.ap-northeast-2.amazonaws.com/%EC%B6%94%EC%B2%9C+%ED%83%AD+%EC%95%84%EC%9D%B4%EC%BD%98/Property+1%3Dic_sns_default%402x.png")
                .into(ivRecommend)
            this.root.setBackgroundColor(
                ContextCompat.getColor(holder.binding.root.context, R.color.white)
            )
            executePendingBindings()
        }
    }

    private fun selectBackground(
        holder: RecommendationViewHolder,
        data: ResponseRecommendationCategorySituationDto.CategorySituation
    ) {
        holder.binding.apply {
            Glide.with(
                holder.itemView.context
            )
                .load("https://picsum.photos/id/237/200/300")
                .into(ivRecommend)
            this.root.setBackgroundColor(
                ContextCompat.getColor(holder.binding.root.context, R.color.yellow_basic_fef652)
            )
            executePendingBindings()
        }
    }
}