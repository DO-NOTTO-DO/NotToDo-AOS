package kr.co.nottodo.presentation.toplevel.recommendation.recommendationactivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ActivityRecommendationBinding
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity.Companion.actionName
import kr.co.nottodo.presentation.toplevel.recommendation.recommendationfragment.RecommendationAdapter
import kr.co.nottodo.presentation.toplevel.recommendation.recommendationfragment.RecommendationFragment
import kr.co.nottodo.presentation.toplevel.recommendation.recommendationlist.RecommendationParentAdapter
import kr.co.nottodo.presentation.toplevel.recommendation.viewmodel.RecommendationViewModel

class RecommendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendationBinding
    private lateinit var recommendationAdapter: RecommendationAdapter
    private lateinit var parentAdapter: RecommendationParentAdapter
    private val viewModel by viewModels<RecommendationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initRecyclerView()
        setAnimation()


        viewModel.categorySituation.observe(this) { categorySituationList ->
            if (categorySituationList.isNotEmpty()) {
                recommendationAdapter.submitList(categorySituationList)
                recommendationAdapter.notifyFirstItemIsClick()
            }
        }

        viewModel.categoryId.observe(this) { categoryId ->
            viewModel.getRecommendList(categoryId)
        }

        viewModel.categoryList.observe(this) { categoryList ->
            parentAdapter.submitList(categoryList)
        }
        binding.ivBtnBack.setOnClickListener {
            finish()
        }
    }


    private fun initAdapter() {
        recommendationAdapter = RecommendationAdapter(
            delegate = { recommendItem ->
                viewModel.setCategoryId(recommendItem.id)
            }
        )
        parentAdapter = RecommendationParentAdapter { view, childData ->
            val intent = Intent(this, AdditionActivity::class.java).putExtra(
                actionName, childData.name
            )
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun initRecyclerView() {
        with(binding) {
            rvRecommendation.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = recommendationAdapter
                addItemDecoration(RecommendationFragment.RecommendationCategoryItemDecoration())
            }

            rvNottodoRecommendListTitle.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = parentAdapter
            }
        }
    }

    private fun setAnimation() {
        overridePendingTransition(
            R.anim.animation_enter,
            R.anim.animation_exit
        )
    }
}

