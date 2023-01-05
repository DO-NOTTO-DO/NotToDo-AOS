package kr.co.nottodo.presentation.achievement.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kr.co.nottodo.databinding.FragmentAchievementBinding
import kr.co.nottodo.presentation.achievement.adapter.AchievementViewPagerAdapter

class AchievementFragment : Fragment() {
    private var _binding: FragmentAchievementBinding? = null
    private val binding: FragmentAchievementBinding
        get() = requireNotNull(_binding) { "서치 프래그먼트에서 _binding이 널임" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAchievementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 데이터가 없는 경우
        // val emptyDataAdapter = AchievementViewPagerEmptyDataAdapter(requireContext())
        // binding.viewpagerAchievement.adapter = emptyDataAdapter
        // binding.tvAchievementStatistics.visibility = View.GONE

        // 데이터 있는 경우
        val adapter = AchievementViewPagerAdapter(requireContext())
        binding.viewpagerAchievement.adapter = adapter
        binding.viewpagerAchievement.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                adapter.notifyDataSetChanged()
            }
        })
        val tabTitles = listOf<String>("   낫투두 통계 보기   ", "   상황별 통계 보기   ")
        TabLayoutMediator(
            binding.tablayoutAchievementStatistics,
            binding.viewpagerAchievement
        ) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}