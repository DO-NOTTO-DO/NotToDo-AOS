package kr.co.nottodo.presentation.achievement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ItemAchievementRvSituationParentBinding

class AchievementSituationParentAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    val sampleList = listOf<Int>(1, 2, 3)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemAchievementRvSituationParentBinding.inflate(inflater, parent, false)
        return AchievementMissionViewHolder(binding)
    }

    class AchievementMissionViewHolder(private val binding: ItemAchievementRvSituationParentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val backgroundToggleOffList = listOf(
            R.drawable.ic_rank_toggleoff_first, R.drawable.ic_rank_toggleoff_second,
            R.drawable.ic_rank_toggleoff_third
        )
        private val backgroundToggleOnList = listOf(
            R.drawable.ic_rank_toggleon_first, R.drawable.ic_rank_toggleon_second,
            R.drawable.ic_rank_toggleon_third
        )
        private val icRankList =
            listOf(R.drawable.ic_rank_first, R.drawable.ic_rank_second, R.drawable.ic_rank_third)

        private val textYellowColorList =
            listOf(
                R.color.yellow_basic_fef652,
                R.color.yellow_mild_fbf8af,
                R.color.yellow_mild_fbf8af
            )

        fun onBind(position: Int) {
            binding.ivAchievementRvSituationParentRank.setImageResource(R.drawable.ic_rank_first)
            binding.tvAchievementRvSituationParentCount.text = "10회"
            binding.tvAchievementRvSituationParentName.text = "상황명"
            binding.rvAchievementPagerSituationChild.adapter =
                AchievementSituationChildAdapter(binding.root.context)
            binding.rvAchievementPagerSituationChild.layoutManager =
                LinearLayoutManager(binding.root.context)
            binding.ivAchievementRvSituationParentRank.setImageResource(icRankList[position])
            binding.layoutAchievementRvSituationParent.setBackgroundResource(backgroundToggleOffList[position])
        }

        fun onClickListener(position: Int) {
            binding.layoutAchievementRvSituationParent.setOnClickListener {
                if (binding.rvAchievementPagerSituationChild.visibility == View.GONE) {
                    binding.rvAchievementPagerSituationChild.visibility = View.VISIBLE
                    binding.layoutAchievementRvSituationParent.setBackgroundResource(
                        backgroundToggleOnList[position]
                    )
                    binding.tvAchievementRvSituationParentCount.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            textYellowColorList[position]
                        )
                    )

                } else {
                    binding.rvAchievementPagerSituationChild.visibility = View.GONE
                    binding.layoutAchievementRvSituationParent.setBackgroundResource(
                        backgroundToggleOffList[position]
                    )
                    binding.tvAchievementRvSituationParentCount.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.gray_1_626068
                        )
                    )
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AchievementMissionViewHolder).onBind(position)
        holder.onClickListener(position)
    }

    override fun getItemCount(): Int {
        return sampleList.size
    }
}