package kr.co.nottodo.presentation.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.skydoves.balloon.Balloon
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.co.nottodo.R
import kr.co.nottodo.data.local.HomeDailyResponse
import kr.co.nottodo.databinding.FragmentHomeBinding
import kr.co.nottodo.presentation.MainActivity
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity
import timber.log.Timber

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: error("binding not init")
    private lateinit var outterAdapter: HomeOutterAdapter
    private val stringBuilder = StringBuilder()
    private lateinit var dataId: HomeDailyResponse.HomeDaily
    lateinit var mainActivity: MainActivity

    private val viewModel by viewModels<HomeFragmentViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 2. Context를 Activity로 형변환하여 할당
        mainActivity = context as MainActivity
    }

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
        initServer()
        observerData()
        showBanner()
    }

    private fun observerData() {
        viewModel.responseCheckResult.observe(viewLifecycleOwner) {
            viewModel.initServer("2023-01-07")
            //체크박스 값이 바뀌면 서버통신 다시
        }
        viewModel.missionId.observe(viewLifecycleOwner) { missionId ->
            viewModel.setMissionId(missionId)

        }
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

    private fun menuClick(index: Int) {
        Timber.e("index ${index}}")
        val bottomSheetDialogFragment = HomeBottomFragment()
        bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
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
            Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show()
            //todo dataId
            viewModel.responseHomeMissionCheck(itemId, "AMBIGUOUS")
            Timber.e("home1 $it")

            balloon.dismiss()
        }
        complete.setOnClickListener {
            Toast.makeText(context, "complete", Toast.LENGTH_SHORT).show()
            viewModel.responseHomeMissionCheck(itemId, "FINISH")
            balloon.dismiss()
        }
        balloon.dismiss()
    }

    private fun initServer() {
        viewModel.initServer("2023-01-07")
        viewModel.homeBannerInitServer()
    }

    private fun refreshHomeBanner() {
        binding.homeSwipeRefreshLayout.setOnRefreshListener {
            viewModel.homeBannerInitServer()
            binding.tvHomeMotiveDescription.text = ""
            binding.homeSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun showBanner() {
        viewModel.responseBannerResult.observe(viewLifecycleOwner) {
            when (it.image) {
                "이미지1" -> binding.ivHomeNottoGraphic.setImageResource(R.drawable.img_home_graphic1)
                "이미지2" -> binding.ivHomeNottoGraphic.setImageResource(R.drawable.img_home_graphic2)
                "이미지3" -> binding.ivHomeNottoGraphic.setImageResource(R.drawable.img_home_graphic3)
                "이미지4" -> binding.ivHomeNottoGraphic.setImageResource(R.drawable.img_home_graphic4)
            }
            typingTitle(it.title).toString()
        }
        refreshHomeBanner()
    }

    @SuppressLint("SetTextI18n")
    private fun typingTitle(title: String) {
        lifecycleScope.launch {
            val isThreadRun = false
            var position = 0
            while (!isThreadRun) {
                delay(100)
                mainActivity.runOnUiThread {
                    if (position < title.length) {
                        binding.tvHomeMotiveDescription.text =
                            binding.tvHomeMotiveDescription.text.toString() + title[position].toString()
                        position += 1
                    }
                }
            }
        }
    }

    private fun clickFbtn() {
        binding.fbtnHomeFloating.setOnClickListener {
            startActivity(Intent(context, AdditionActivity::class.java))
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
