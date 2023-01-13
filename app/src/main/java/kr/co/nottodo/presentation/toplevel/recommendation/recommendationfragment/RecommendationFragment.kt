package kr.co.nottodo.presentation.toplevel.recommendation.recommendationfragment

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.nottodo.R
import kr.co.nottodo.databinding.FragmentRecommendationBinding
import kr.co.nottodo.presentation.MainActivity
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity.Companion.actionName
import kr.co.nottodo.presentation.toplevel.recommendation.recommendationlist.RecommendationParentAdapter
import kr.co.nottodo.presentation.toplevel.recommendation.viewmodel.RecommendationViewModel
import kr.co.nottodo.util.extension.dpToPx
import kr.co.nottodo.util.extension.setStatusBarColor

class RecommendationFragment : Fragment() {

    private var _binding: FragmentRecommendationBinding? = null
    private val binding: FragmentRecommendationBinding
        get() = requireNotNull(_binding) { "_binding is Null ..." }
    private lateinit var recommendationAdapter: RecommendationAdapter
    private lateinit var parentAdapter: RecommendationParentAdapter
    private val viewModel by viewModels<RecommendationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecommendationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
        initRecyclerView()
        initListener()
        setStatusBarColor(activity as MainActivity, R.color.white)

        viewModel.categorySituation.observe(viewLifecycleOwner) { categorySituationList ->
            if (categorySituationList.isNotEmpty()) {
                // 어댑터한테 데이터를 넣어줬어요
                recommendationAdapter.submitList(categorySituationList)
                recommendationAdapter.notifyFirstItemIsClick()
            }
        }

        viewModel.categoryId.observe(viewLifecycleOwner) { categoryId ->
            viewModel.getRecommendList(categoryId)
        }

        viewModel.categoryList.observe(viewLifecycleOwner) { categoryList ->
            parentAdapter.submitList(categoryList)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initAdapter() {
        recommendationAdapter = RecommendationAdapter(
            delegate = { recommendItem ->
                viewModel.setCategoryId(recommendItem.id)
            }
        )
        parentAdapter = RecommendationParentAdapter { view, childData ->
            startActivity(
                Intent(context, AdditionActivity::class.java).putExtra(
                    actionName,
                    childData.name
                )
            )
        }
    }

    private fun initRecyclerView() {
        with(binding) {
            rvRecommendation.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = recommendationAdapter
                addItemDecoration(RecommendationCategoryItemDecoration())
                recycledViewPool.run {
                    setMaxRecycledViews(0,0)
                }
            }

            rvNottodoRecommendListTitle.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = parentAdapter
            }
        }
    }

    private fun initListener() {
        binding.tvWriteDirect.setOnClickListener {
            startActivity(Intent(context, AdditionActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            })
        }

    }

    class RecommendationCategoryItemDecoration() : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
                outRect.right = parent.context.dpToPx(20)
            }
        }
    }
}