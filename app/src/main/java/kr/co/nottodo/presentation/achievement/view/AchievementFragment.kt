package kr.co.nottodo.presentation.achievement.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import kr.co.nottodo.data.remote.model.ResponseMissionStatisticDto
import kr.co.nottodo.data.remote.model.ResponseSituationStatisticDto
import kr.co.nottodo.databinding.FragmentAchievementBinding
import kr.co.nottodo.presentation.achievement.adapter.AchievementViewPagerAdapter
import kr.co.nottodo.presentation.achievement.adapter.AchievementViewPagerEmptyDataAdapter
import kr.co.nottodo.presentation.achievement.viewmodel.AchievementViewModel
import timber.log.Timber

class AchievementFragment : Fragment() {
    private var _binding: FragmentAchievementBinding? = null
    private val binding: FragmentAchievementBinding
        get() = requireNotNull(_binding) { "서치 프래그먼트에서 _binding이 널임" }
    private val viewModel by lazy { AchievementViewModel() }

    lateinit var missionStatistic: List<ResponseMissionStatisticDto.MissionStatistic>
    lateinit var situationStatistic: List<ResponseSituationStatisticDto.SituationStatistic>

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

        getStatistics()
        initViewPager()
    }

    private fun getStatistics() {
        viewModel.getMissionStatistic()
        viewModel.getSituationStatistic()
    }

    private fun initViewPager() {
        viewModel.isDataCome.observe(viewLifecycleOwner){
            if(it){
                missionStatistic = viewModel.responseMission.value?.data ?: listOf()
                situationStatistic = viewModel.responseSituation.value?.data ?: listOf()

                val adapter = AchievementViewPagerAdapter(requireContext(), missionStatistic, situationStatistic)
                binding.viewpagerAchievement.adapter = adapter
                binding.viewpagerAchievement.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                        adapter.notifyDataSetChanged()
                    }
                })
            }else{
                binding.viewpagerAchievement.adapter = AchievementViewPagerEmptyDataAdapter(requireContext())
                binding.tvAchievementStatistics.visibility = View.GONE
            }
            initTabLayout()
        }
        viewModel.errorMessageMission.observe(viewLifecycleOwner){
            Timber.d(it)
        }
        viewModel.errorMessageSituation.observe(viewLifecycleOwner){
            Timber.d(it)
        }
    }

    private fun initTabLayout() {
        val tabTitles = listOf("   낫투두 통계 보기   ", "   상황별 통계 보기   ")
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