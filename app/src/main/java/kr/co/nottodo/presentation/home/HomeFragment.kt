package kr.co.nottodo.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.skydoves.balloon.Balloon
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.co.nottodo.R
import kr.co.nottodo.databinding.FragmentHomeBinding
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity.Companion.blank
import kr.co.nottodo.view.calendar.weekly.listener.OnWeeklyCalendarSwipeListener
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: error("binding not init")
    private lateinit var outterAdapter: HomeOutterAdapter
    private val stringBuilder = StringBuilder()
    private var todayData = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    private var weeklyData = todayData
    private var typingJob: Job? = null

    private val viewModel by viewModels<HomeFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        clickFbtn()
        initServer(weeklyData)
        clickWeekly()
        observerData()
        showBanner()
    }

    private fun observerData() {
        viewModel.responseCheckResult.observe(viewLifecycleOwner) {
            viewModel.initServer(weeklyData)
            //체크박스 값이 바뀌면 서버통신 다시
        }
//        viewModel.missionId.observe(viewLifecycleOwner) { missionId ->
//            viewModel.setMissionId(missionId)
//
//        }
    }

    //todo adapter초기화에서 observer빼기
    private fun initAdapter() {
        viewModel.responseResult.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.rvHomeShowTodo.adapter = outterAdapter
                outterAdapter.submitList(it.toMutableList())
                Timber.e("submitList $it")
            }
        }
        outterAdapter = HomeOutterAdapter(::menuClick, ::todoClick)
    }

    private fun menuClick(indexId: Int, title: String, situation: String) {
//        Timber.e("index $index")
        val bottomSheetDialogFragment = HomeBottomFragment(title, situation)
        bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)

    }

    private fun clickWeekly() {
        binding.weekelyCalendar.setOnWeeklyCalendarDayClickListener { view, date ->
            binding.tvHomeMotiveDescription.text = ""
            weeklyData = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            Timber.d("date$weeklyData")
            initServer(weeklyData)
        }
    }

    private lateinit var balloon: Balloon

    private fun todoClick(index: Int, view: View, itemId: Int) {
        Timber.e(index.toString())

        balloon = HomeBallonFactory().create(requireContext(), viewLifecycleOwner)

        val fail: ImageView =
            balloon.getContentView().findViewById(R.id.iv_balloon_fail)
        val complete: ImageView =
            balloon.getContentView().findViewById(R.id.iv_balloon_complete)

        if (balloon.isShowing) {
            balloon.dismiss()
        } else balloon.showAlignTop(view)

        fail.setOnClickListener {
            viewModel.responseHomeMissionCheck(itemId, "AMBIGUOUS")
            Timber.e("home1 $it")

            balloon.dismiss()
        }
        complete.setOnClickListener {
            viewModel.responseHomeMissionCheck(itemId, "FINISH")
            balloon.dismiss()
        }
        balloon.dismiss()
    }

    private fun initServer(day: String) {
        viewModel.initServer(day)
        viewModel.homeBannerInitServer()
    }

    private fun refreshHomeBanner() {
        binding.homeSwipeRefreshLayout.setOnRefreshListener {
            initServer(todayData)
            binding.tvHomeMotiveDescription.text = blank
            binding.homeSwipeRefreshLayout.isRefreshing = false
            binding.weekelyCalendar.adapter?.notifyDataSetChanged()
        }
    }

    private fun showBanner() {
        viewModel.responseBannerResult.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.image)
                .into(binding.ivHomeNottoGraphic)
            typingTitle(it.title).toString()
        }
        refreshHomeBanner()
    }

    @SuppressLint("SetTextI18n")
    private fun typingTitle(title: String) {
        typingJob = lifecycleScope.launch {
            typingJob?.cancel()
            var isThreadRun = false
            var position = 0
            binding.tvHomeMotiveDescription.text = blank
            while (!isThreadRun) {
                delay(100)
                if (position < title.length) {
                    binding.tvHomeMotiveDescription.text =
                        binding.tvHomeMotiveDescription.text.toString() + title[position].toString()
                    position += 1
                } else {
                    isThreadRun = true
                }
            }
        }
    }

    private fun clickFbtn() {
        binding.fbtnHomeFloating.setOnClickListener {
            startActivity(Intent(context, AdditionActivity::class.java).apply {
                addFlags(FLAG_ACTIVITY_SINGLE_TOP)
            })
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
