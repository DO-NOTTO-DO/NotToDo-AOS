package kr.co.nottodo.presentation.toplevel.recommendation.recommendationfragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ItemRecommendBinding
import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategorySituationDto

class RecommendationAdapter(
    private val delegate: RecommendationViewHolder.RecommendationClickDelegate
) : RecyclerView.Adapter<RecommendationViewHolder>() {

    private var selectedPosition = -1
    private var lastItemSelectedPosition = -1

    private val recommendItemList =
        mutableListOf<ResponseRecommendationCategorySituationDto.CategorySituation>()

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
        // 로직이 그리 좋지 않습니다
        bind(holder, position)
        holder.onBind(recommendItemList[position])
        if (position == selectedPosition) {
            selectBackground(holder, recommendItemList[position])
        } else {
            normalBackground(holder, recommendItemList[position])
        }
    }

    override fun getItemCount(): Int = recommendItemList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<ResponseRecommendationCategorySituationDto.CategorySituation>) {
        recommendItemList.clear()
        recommendItemList.addAll(list)
        notifyDataSetChanged()
    }

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
                delegate.onClickRecommendation(recommendItemList[selectedPosition])
                notifyItemChanged(selectedPosition)
            }
        }
    }

    private fun normalBackground(
        holder: RecommendationViewHolder,
        data: ResponseRecommendationCategorySituationDto.CategorySituation
    ) {
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(data.image)
                .into(ivRecommend)

            root.setBackgroundColor(
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
            Glide.with(holder.itemView.context)
                .load(data.activeImage)
                .into(ivRecommend)

            root.setBackgroundColor(
                ContextCompat.getColor(holder.binding.root.context, R.color.yellow_basic_fef652)
            )
            executePendingBindings()
        }
    }

    fun notifyFirstItemIsClick() {
        selectedPosition = 0
        lastItemSelectedPosition = if (lastItemSelectedPosition == -1) {
            selectedPosition
        } else {
            notifyItemChanged(lastItemSelectedPosition)
            selectedPosition
        }
        delegate.onClickRecommendation(recommendItemList[selectedPosition])
        notifyItemChanged(selectedPosition)
    }
}