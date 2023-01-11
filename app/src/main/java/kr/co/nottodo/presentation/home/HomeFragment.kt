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
    private var lable = ""
    private lateinit var dataId: HomeDailyResponse.HomeDaily
    lateinit var mainActivity: MainActivity

    private val viewModel by viewModels<HomeFragmentViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // 2. Context를 Activity로 형변환하여 할당
        mainActivity = context as MainActivity
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        title()
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun title() {
        lifecycleScope.launch {
            val isThreadRun = false
            var position = -1
            while (!isThreadRun) {
                delay(100)
                mainActivity.runOnUiThread {
                    Timber.e("homeFragment")
                    if (position < lable.length - 1) {
                        position += 1
                        binding.tvHomeMotiveDescription.text =
                            binding.tvHomeMotiveDescription.text.toString() + lable[position].toString()
                        Timber.e("asdfasdf")
                    }
                }
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        clickFbtn()
        initStatus()
        observerData()
        refreshHomeBanner()
    }

    @SuppressLint("SetTextI18n")
    override fun onStart() {
        super.onStart()
//        var isThreadRun = true
//        var position = -1
//        Thread {
//            while (isThreadRun) {
//                Thread.sleep(300)
//                mainActivity.runOnUiThread {
//                    if (position < lable.length - 1) {
//                        position += 1
//                        binding.tvHomeMotiveDescription.setText(binding.tvHomeMotiveDescription.text.toString() + lable[position].toString())
////                        Timber.e(binding.tvHomeMotiveDescription.text.toString())
//                    } else {
//                        isThreadRun = false
//                    }
//                }
//            }
//        }.start()
    }

    private fun observerData() {
        viewModel.responseCheckResult.observe(viewLifecycleOwner) {
            viewModel.initServer("2023-01-07")
            Timber.e("home2 $it")
        }
    }

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
        Timber.e(index.toString())
        val bottomSheetDialogFragment = HomeBottomFragment()
        bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
    }

    private lateinit var balloon: Balloon

    private fun todoClick(index: Int, view: View) {
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
            viewModel.responseHomeMissionCheck(28, "AMBIGUOUS")
            Timber.e("home1 $it")

            balloon.dismiss()
        }
        complete.setOnClickListener {
            Toast.makeText(context, "complete", Toast.LENGTH_SHORT).show()
            viewModel.responseHomeMissionCheck(28, "FINISH")

            balloon.dismiss()
        }
        balloon.dismiss()
    }

    private fun initStatus() {
        viewModel.initServer("2023-01-07")
    }

    private fun refreshHomeBanner() {
        viewModel.homeBannerInitServer()
        showBanner()
        binding.homeSwipeRefreshLayout.setOnRefreshListener {
            viewModel.homeBannerInitServer()
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
            lable = it.title
//            binding.tvHomeMotiveDescription.text = it.title
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
