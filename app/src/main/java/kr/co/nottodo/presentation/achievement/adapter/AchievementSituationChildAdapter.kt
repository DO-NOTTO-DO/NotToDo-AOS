package kr.co.nottodo.presentation.achievement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.databinding.ItemAchievementRvSituationChildBinding

class AchievementSituationChildAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    val sampleList = listOf<Int>(1, 2, 3)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemAchievementRvSituationChildBinding.inflate(inflater, parent, false)
        return AchievementMissionViewHolder(binding)
    }

    class AchievementMissionViewHolder(private val binding: ItemAchievementRvSituationChildBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind() {
            binding.tvAchievementRvSituationChildRank.text = "1"
            binding.tvAchievementRvSituationChildCount.text = "2회"
            binding.tvAchievementRvSituationChildMissionName.text = "낫투두명"
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AchievementMissionViewHolder).onBind()
    }

    override fun getItemCount(): Int {
        return sampleList.size
    }
}