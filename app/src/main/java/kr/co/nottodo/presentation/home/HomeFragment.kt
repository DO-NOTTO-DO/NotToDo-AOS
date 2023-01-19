package kr.co.nottodo.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import android.os.Bundle
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
import kr.co.nottodo.presentation.MainActivity
import kr.co.nottodo.presentation.mission.addition.view.AdditionActivity
import kr.co.nottodo.presentation.mission.addition.view.AdditionActivity.Companion.BLANK
import kr.co.nottodo.view.calendar.monthly.util.convertToLocalDate
import kr.co.nottodo.view.calendar.weekly.listener.OnWeeklyCalendarSwipeListener
import kr.co.nottodo.util.extension.setStatusBarColor
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: error("binding not init")
    private lateinit var outterAdapter: HomeOutterAdapter
    private var todayData = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    private var weeklyData = todayData

    private var typingJob: Job? = null
    private lateinit var balloon: Balloon

    private val viewModel by viewModels<HomeFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRecyclerView()
        initListener()
        observerData()

        initServer(weeklyData)
        initMonth()
        setStatusBarColor(activity as MainActivity, R.color.white)
    }

    private fun initAdapter() {
        outterAdapter = HomeOutterAdapter(::menuClick, ::todoClick)
    }

    private fun initRecyclerView() {
        with(binding) {
            rvHomeShowTodo.apply {
                adapter = outterAdapter
            }
        }
    }

    private fun initListener() {
        binding.fbtnHomeFloating.setOnClickListener {
            startActivity(Intent(context, AdditionActivity::class.java).apply {
                addFlags(FLAG_ACTIVITY_SINGLE_TOP)
            })
        }

        binding.weekelyCalendar.setOnWeeklyDayClickListener { view, date ->
            weeklyData = date.format(DateTimeFormatter.ofPattern(YEAR_PATTERN))
            viewModel.initServer(weeklyData)
        }

        binding.homeSwipeRefreshLayout.setOnRefreshListener {
            binding.weekelyCalendar.refresh()
            initServer(todayData)
            binding.homeSwipeRefreshLayout.isRefreshing = false
        }

        binding.weekelyCalendar.setOnWeeklyCalendarSwipeListener(object :
            OnWeeklyCalendarSwipeListener {
            override fun onSwipe(mondayDate: LocalDate?) {
                if (mondayDate != null) {
                    // Monday 에 따라서 주간 캘린더에 보여줄 낫투두 리스트 값 갱신
                    viewModel.homeWeeklyInitServer(mondayDate.toString())
                    initMonth()
                }
            }
        })
    }

    private fun observerData() {
        viewModel.responseCheckResult.observe(viewLifecycleOwner) {
            viewModel.initServer(weeklyData)
            viewModel.homeWeeklyInitServer(
                binding.weekelyCalendar.mondayDate.toString())
        }

        viewModel.responseWeeklyResult.observe(viewLifecycleOwner) { resultList ->
            val notToDoCountList = resultList?.map {
                it.actionDate.convertToLocalDate() to it.percentage
            } ?: emptyList()
            binding.weekelyCalendar.setNotToDoCount(notToDoCountList)
        }

        viewModel.responseResult.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.clHomeNotodoRecyler.visibility = View.VISIBLE
                binding.clHomeNotodo.visibility = View.INVISIBLE
                outterAdapter.submitList(it)
            } else {
                binding.clHomeNotodoRecyler.visibility = View.INVISIBLE
                binding.clHomeNotodo.visibility = View.VISIBLE
            }
        }

        viewModel.responseBannerResult.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.image)
                .into(binding.ivHomeNottoGraphic)
            animateTypingTitle(it.title).toString()
        }
    }

    private fun initServer(day: String) {
        viewModel.initServer(day)
        viewModel.homeBannerInitServer()
        viewModel.homeWeeklyInitServer(binding.weekelyCalendar.mondayDate.toString())
    }

    private fun initMonth() {
        binding.tvHomeDate.text =
            binding.weekelyCalendar.mondayDate?.format(DateTimeFormatter.ofPattern(MONTH_PATTERN))
    }

    private fun menuClick(indexId: Int, title: String, situation: String) {
        val bottomSheetDialogFragment = HomeBottomFragment(title, situation, indexId)
        bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
    }

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
            viewModel.responseHomeMissionCheck(itemId, AMBIGUOUS)
            balloon.dismiss()
        }
        complete.setOnClickListener {
            viewModel.responseHomeMissionCheck(itemId, FINISH)
            balloon.dismiss()
        }
        balloon.dismiss()
    }

    @SuppressLint("SetTextI18n")
    private fun animateTypingTitle(title: String) {
        typingJob = lifecycleScope.launch {
            typingJob?.cancel()
            var isThreadRun = false
            var position = 0
            binding.tvHomeMotiveDescription.text = BLANK
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val MONTH_PATTERN = "yyyy.MM"
        const val YEAR_PATTERN = "yyyy-MM-dd"
        const val AMBIGUOUS = "AMBIGUOUS"
        const val FINISH = "FINISH"
    }
}
