package kr.co.nottodo.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.skydoves.balloon.Balloon
import kr.co.nottodo.R
import kr.co.nottodo.databinding.FragmentHomeBinding
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity
import timber.log.Timber

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: error("binding not init")
    private lateinit var outterAdapter: HomeOutterAdapter
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
        initStatus()
        showBanner()
    }

    private fun initAdapter() {
        outterAdapter = HomeOutterAdapter(::menuClick, ::todoClick)
        viewModel.responseResult.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.rvHomeShowTodo.adapter = outterAdapter
                outterAdapter.submitList(it)
            }
        }
        Timber.e("home ${viewModel.responseResult.value}")
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

            balloon.dismiss()
        }
        complete.setOnClickListener {
            Toast.makeText(context, "complete", Toast.LENGTH_SHORT).show()

            balloon.dismiss()
        }
        balloon.dismiss()
    }

    private fun initStatus() {
        viewModel.initServer("2023-01-07")
        viewModel.homeBannerInitServer()
    }

    private fun showBanner() {
        viewModel.responseBannerResult.observe(viewLifecycleOwner) {
            when (it.image) {
                "이미지1" -> binding.ivHomeNottoGraphic.setImageResource(R.drawable.img_home_graphic1)
                "이미지2" -> binding.ivHomeNottoGraphic.setImageResource(R.drawable.img_home_graphic2)
                "이미지3" -> binding.ivHomeNottoGraphic.setImageResource(R.drawable.img_home_graphic3)
                "이미지4" -> binding.ivHomeNottoGraphic.setImageResource(R.drawable.img_home_graphic4)
            }

            it.image
            Timber.e("home${it.image}")
            binding.tvHomeMotiveDescription.text = it.title
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
