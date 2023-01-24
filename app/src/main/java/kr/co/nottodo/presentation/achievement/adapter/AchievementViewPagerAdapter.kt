package kr.co.nottodo.presentation.achievement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.data.remote.model.ResponseMissionStatisticDto
import kr.co.nottodo.data.remote.model.ResponseSituationStatisticDto
import kr.co.nottodo.databinding.ItemAchievementPagerMissionBinding
import kr.co.nottodo.presentation.achievement.view.AchievementFragment.Companion.MISSION_STATISTIC_TITLE
import kr.co.nottodo.presentation.achievement.view.AchievementFragment.Companion.SITUATION_STATISTIC_TITLE

class AchievementViewPagerAdapter(
    context: Context,
    private val missionStatisticList: List<ResponseMissionStatisticDto.MissionStatistic>,
    private val situationStatisticList: List<ResponseSituationStatisticDto.SituationStatistic>
) :
    RecyclerView.Adapter<AchievementViewPagerAdapter.AchievePagerViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    lateinit var binding: ItemAchievementPagerMissionBinding
    private val titleList: List<String> = listOf(MISSION_STATISTIC_TITLE, SITUATION_STATISTIC_TITLE)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievePagerViewHolder {
        binding = ItemAchievementPagerMissionBinding.inflate(inflater, parent, false)
        return AchievePagerViewHolder(binding, missionStatisticList, situationStatisticList)
    }

    override fun onBindViewHolder(holder: AchievePagerViewHolder, position: Int) {
        holder.onBind(titleList[position])
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

    class AchievePagerViewHolder(
        private val binding: ItemAchievementPagerMissionBinding,
        private val missionStatisticList: List<ResponseMissionStatisticDto.MissionStatistic>,
        private val situationStatisticList: List<ResponseSituationStatisticDto.SituationStatistic>
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(title: String) {
            binding.tvAchievementPagerMissionTitle.text = title
            if (title == MISSION_STATISTIC_TITLE) {
                binding.rvAchievementPagerMission.adapter =
                    AchievementMissionAdapter(binding.root.context, missionStatisticList)
                binding.rvAchievementPagerMission.layoutManager =
                    LinearLayoutManager(binding.root.context)
            } else {
                binding.rvAchievementPagerMission.adapter =
                    AchievementSituationParentAdapter(binding.root.context, situationStatisticList)
                binding.rvAchievementPagerMission.layoutManager =
                    LinearLayoutManager(binding.root.context)
            }
        }
    }
}