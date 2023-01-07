package kr.co.nottodo.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skydoves.balloon.Balloon
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
        outterAdapter = HomeOutterAdapter(::click)
        addRepoList()
        binding.rvHomeShowTodo.adapter = outterAdapter
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

    private fun click(index: Int) {
        Timber.e(index.toString())
        val bottomSheetDialogFragment = HomeBottomFragment()
        bottomSheetDialogFragment.show(childFragmentManager, bottomSheetDialogFragment.tag)
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