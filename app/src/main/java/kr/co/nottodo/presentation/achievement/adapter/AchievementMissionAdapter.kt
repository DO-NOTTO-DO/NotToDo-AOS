package kr.co.nottodo.presentation.achievement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ItemAchievementRvMissionBinding

class AchievementMissionAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    val sampleList = listOf(1, 2, 3, 4, 5)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemAchievementRvMissionBinding.inflate(inflater, parent, false)
        return AchievementMissionViewHolder(binding)
    }

    class AchievementMissionViewHolder(private val binding: ItemAchievementRvMissionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val backgroundList = listOf(
            R.drawable.ic_notodo_rang_bg_first,
            R.drawable.ic_notodo_rang_bg_second,
            R.drawable.ic_notodo_rang_bg_third,
            R.drawable.ic_notodo_rang_bg_forth,
            R.drawable.ic_notodo_rang_bg_fifth
        )
        private val icRankList = listOf(
            R.drawable.ic_rank_first, R.drawable.ic_rank_second, R.drawable.ic_rank_third,
            R.drawable.ic_rank_fourth, R.drawable.ic_rank_fifth
        )

        fun onBind(position: Int) {
            binding.ivAchievementRvMission.setImageResource(R.drawable.ic_rank_first)
            binding.tvAchievementRvMissionCount.text = "10회"
            binding.tvAchievementRvMissionName.text = "낫투두명"
            binding.ivAchievementRvMission.setImageResource(icRankList[position])
            binding.layoutAchievementRvMission.setBackgroundResource(backgroundList[position])
            when (position) {
                0 -> {
                    binding.tvAchievementRvMissionCount.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.yellow_basic_fef652
                        )
                    )
                }
                1, 2 -> {
                    binding.tvAchievementRvMissionCount.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.yellow_mild_fbf8af
                        )
                    )
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AchievementMissionViewHolder).onBind(position)
    }


    override fun getItemCount(): Int {
        return sampleList.size
    }
}