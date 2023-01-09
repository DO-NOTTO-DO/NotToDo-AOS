package kr.co.nottodo.presentation.toplevel.recommendation.recommendationfragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.nottodo.R
import kr.co.nottodo.databinding.FragmentRecommendationBinding
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity
import kr.co.nottodo.presentation.toplevel.recommendation.recommendationlist.RecommendationParentAdapter
import kr.co.nottodo.presentation.toplevel.recommendation.viewmodel.RecommendationViewModel


class RecommendationFragment : Fragment() {

    private var _binding: FragmentRecommendationBinding? = null
    private val binding: FragmentRecommendationBinding
        get() = requireNotNull(_binding) { "_binding is Null ..." }
    private lateinit var recommendationAdapter: RecommendationAdapter
    private lateinit var parentAdapter: RecommendationParentAdapter
    lateinit var recommendationViewModel: RecommendationViewModel

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

    private fun getRecommendationCategorySitatuationService() {
        getRecommendationCategorySitatuationService()
    }

//    private val viewModel.getRecommendationCategorySitatuationService()
//    private val viewModel.getCategorySituation.observe(owner:)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recommendationAdapter = RecommendationAdapter()
        parentAdapter =
            RecommendationParentAdapter(testChildItemViewClickBlock = { view, childData ->
                Log.d("ssong-develop", "hello!")
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}