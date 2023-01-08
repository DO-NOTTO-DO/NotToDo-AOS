package kr.co.nottodo.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.skydoves.balloon.Balloon
import kr.co.nottodo.R
import kr.co.nottodo.data.local.HomeDaily
import kr.co.nottodo.databinding.FragmentHomeBinding
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity
import timber.log.Timber

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: error("binding not init")
    private lateinit var outterAdapter: HomeOutterAdapter

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
    }

    private fun initAdapter() {
        outterAdapter = HomeOutterAdapter(::menuClick, ::todoClick)
        addRepoList()
        binding.rvHomeShowTodo.adapter = outterAdapter
        outterAdapter.checkBox("AMBIGUOUS")

    }

    private fun menuClick(index: Int) {
        Timber.e(index.toString())
        val bottomSheetDialogFragment = HomeBottomFragment()
        bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
    }

    private lateinit var balloon: Balloon

//    val emojiList = listOf<ImageView>(
//        balloon.getContentView().findViewById(R.id.iv_balloon_fail),
//        balloon.getContentView().findViewById(R.id.iv_balloon_complete)
//    )

    private fun todoClick(index: Int, view: View) {
        Timber.e(index.toString())

        balloon = HomeBallonFactory().create(requireContext(), viewLifecycleOwner)
        if (balloon.isShowing) {
            balloon.dismiss()
        } else balloon.showAlignTop(view)

        val fail: ImageView =
            balloon.getContentView().findViewById(R.id.iv_balloon_fail)
        val complete: ImageView =
            balloon.getContentView().findViewById(R.id.iv_balloon_complete)
        fail.setOnClickListener {
            Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show()
//            outterAdapter.checkBox("AMBIGUOUS")
//            outterAdapter.notifyItemChanged(outterAdapter.checkBox("AMBIGUOUS"))

            balloon.dismiss()
        }
        complete.setOnClickListener {
            Toast.makeText(context, "complete", Toast.LENGTH_SHORT).show()

//            outterAdapter.notifyDataSetChanged()
            outterAdapter.setComplete("NOTYET")
            balloon.dismiss()
        }
        balloon.dismiss()
    }

    private fun addRepoList() {
        outterAdapter.submitList(
            listOf(
                HomeDaily(
                    1, "김수빈"
                ),
                HomeDaily(
                    2, "김수빈"
                ),
                HomeDaily(
                    3, "김수빈"
                ),
                HomeDaily(
                    4, "김수빈"
                ),
            )
        )
    }

    private fun clickFbtn() {
        binding.fbtnHomeFloating.setOnClickListener {
            startActivity(Intent(context, AdditionActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
