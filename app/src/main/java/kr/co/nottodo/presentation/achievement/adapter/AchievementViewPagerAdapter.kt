package kr.co.nottodo.presentation.achievement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.data.remote.model.ResponseMissionStatisticDto
import kr.co.nottodo.data.remote.model.ResponseSituationStatisticDto
import kr.co.nottodo.databinding.ItemAchievementPagerMissionBinding
import kr.co.nottodo.presentation.achievement.view.AchievementFragment.Companion.missionStatisticTitle
import kr.co.nottodo.presentation.achievement.view.AchievementFragment.Companion.situationStatisticTitle

class AchievementViewPagerAdapter(
    context: Context,
    private val missionStatisticList: List<ResponseMissionStatisticDto.MissionStatistic>,
    private val situationStatisticList: List<ResponseSituationStatisticDto.SituationStatistic>
) :
    RecyclerView.Adapter<AchievementViewPagerAdapter.AchievePagerViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    lateinit var binding: ItemAchievementPagerMissionBinding
    private val titleList: List<String> = listOf(missionStatisticTitle, situationStatisticTitle)


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
            if (title == missionStatisticTitle) {
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