package kr.co.nottodo.presentation.achievement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.data.remote.model.ResponseMissionStatisticDto
import kr.co.nottodo.databinding.ItemAchievementRvSituationChildBinding

class AchievementSituationChildAdapter(
    context: Context,
    private val missionStatisticList: List<ResponseMissionStatisticDto.MissionStatistic>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemAchievementRvSituationChildBinding.inflate(inflater, parent, false)
        return AchievementMissionViewHolder(binding)
    }

    class AchievementMissionViewHolder(private val binding: ItemAchievementRvSituationChildBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: ResponseMissionStatisticDto.MissionStatistic, position: Int) {
            binding.tvAchievementRvSituationChildRank.text = (position + 1).toString()
            binding.tvAchievementRvSituationChildCount.text = item.count.toString()
            binding.tvAchievementRvSituationChildMissionName.text = item.title
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AchievementMissionViewHolder).onBind(missionStatisticList[position], position)
    }

    override fun getItemCount(): Int {
        return missionStatisticList.size
    }
}