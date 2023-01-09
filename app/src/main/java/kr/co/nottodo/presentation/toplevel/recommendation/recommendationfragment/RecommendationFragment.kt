package kr.co.nottodo.presentation.toplevel.recommendation.recommendationfragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.nottodo.R
import kr.co.nottodo.databinding.FragmentRecommendationBinding
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity
import kr.co.nottodo.presentation.toplevel.recommendation.recommendationlist.RecommendationParentAdapter


class RecommendationFragment : Fragment() {

    private var _binding: FragmentRecommendationBinding? = null
    private val binding: FragmentRecommendationBinding
        get() = requireNotNull(_binding) { "_binding is Null ..." }
    private lateinit var recommendationAdapter: RecommendationAdapter
    private lateinit var parentAdapter: RecommendationParentAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentRecommendationBinding>(
            inflater, R.layout.fragment_recommendation, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recommendationAdapter = RecommendationAdapter()
        parentAdapter = RecommendationParentAdapter(testChildItemViewClickBlock = {view, childData ->
            Log.d("ssong-develop","hello!")
        })
        binding.rvRecommendation.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendationAdapter
        }

        binding.rvNottodoRecommendListTitle.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = parentAdapter
        }
        binding.tvWriteDirect.setOnClickListener {
            startActivity(Intent(context, AdditionActivity::class.java))
        }
    }

    private fun RecommendationParentAdapter() {

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}