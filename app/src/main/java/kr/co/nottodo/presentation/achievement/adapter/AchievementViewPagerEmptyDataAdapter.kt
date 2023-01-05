package kr.co.nottodo.presentation.achievement.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.databinding.ItemAchievementPagerEmptyBinding

class AchievementViewPagerEmptyDataAdapter(context: Context) :
    RecyclerView.Adapter<AchievementViewPagerEmptyDataAdapter.AchievementEmptyViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    lateinit var binding: ItemAchievementPagerEmptyBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementEmptyViewHolder {
        binding = ItemAchievementPagerEmptyBinding.inflate(inflater, parent, false)
        return AchievementEmptyViewHolder(binding)
    }

    class AchievementEmptyViewHolder(binding: ItemAchievementPagerEmptyBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onBindViewHolder(holder: AchievementEmptyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 2
    }
}