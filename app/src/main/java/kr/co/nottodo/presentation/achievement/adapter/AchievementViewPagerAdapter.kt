package kr.co.nottodo.presentation.achievement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.databinding.ItemAchievementPagerMissionBinding

class AchievementViewPagerAdapter(context: Context) :
    RecyclerView.Adapter<AchievementViewPagerAdapter.AchievePagerViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    lateinit var binding: ItemAchievementPagerMissionBinding
    private val titleList: List<String> = listOf("내가 달성한 낫투두의 순위는?", "언제 낫투두를 가장 많이 시도했을까요?")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievePagerViewHolder {
        binding = ItemAchievementPagerMissionBinding.inflate(inflater, parent, false)
        return AchievePagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AchievePagerViewHolder, position: Int) {
        holder.onBind(titleList[position])
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

    class AchievePagerViewHolder(private val binding: ItemAchievementPagerMissionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(title: String) {
            binding.tvAchievementPagerMissionTitle.text = title
            if (title == "내가 달성한 낫투두의 순위는?") {
                binding.rvAchievementPagerMission.adapter =
                    AchievementMissionAdapter(binding.root.context)
                binding.rvAchievementPagerMission.layoutManager =
                    LinearLayoutManager(binding.root.context)
            } else {
                binding.rvAchievementPagerMission.adapter =
                    AchievementSituationParentAdapter(binding.root.context)
                binding.rvAchievementPagerMission.layoutManager =
                    LinearLayoutManager(binding.root.context)
            }
        }
    }
}