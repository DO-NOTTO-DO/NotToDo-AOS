package kr.co.nottodo.presentation.schedule.search.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ActivitySearchBinding
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity.Companion.blank
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity.Companion.currentMissionName
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity.Companion.missionName
import kr.co.nottodo.presentation.schedule.search.adapter.SearchRecyclerViewAdapter
import kr.co.nottodo.presentation.schedule.search.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    private val viewModel by lazy { SearchViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        val sampleList = listOf(
            "침대에 다시 눕지 않기",
            "10시 이후에 일어나지 않기",
            "일어났으면 다시 침대에 눕지 않기",
            "10시 이후에 일어나지 않기",
            "일어났으면 다시 침대에 눕지 않기",
            "10시 이후에 일어나지 않기",
            "일어났으면 다시 침대에 눕지 않기",
            "10시 이후에 일어나지 않기",
            "일어났으면 다시 침대에 눕지 않기",
            "10시 이후에 일어나지 않기",
            "일어났으면 다시 침대에 눕지 않기"
        )

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.vm = viewModel
        binding.lifecycleOwner = this

        binding.rvSearchHistory.adapter = SearchRecyclerViewAdapter(
            binding.root.context,
            sampleList, viewModel::setSearchBarText
        )
        binding.rvSearchHistory.layoutManager = LinearLayoutManager(binding.root.context)

        viewModel.isSearchBarTextFilled.observe(this) {
            if (it) {
                binding.etSearchSearchBar.setBackgroundResource(R.drawable.rectangle_border_gray2_1)
                binding.tvSearchComplete.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.gray_1_626068
                    )
                )
            } else {
                binding.etSearchSearchBar.setBackgroundResource(R.drawable.rectangle_border_gray4_1)
                binding.tvSearchComplete.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.gray_3_c4c4c4
                    )
                )
            }
        }

        if(intent.getStringExtra(currentMissionName) != blank){
            viewModel.searchBarText.value = intent.getStringExtra(currentMissionName)
        }

        binding.tvSearchComplete.setOnClickListener {
            if (viewModel.isSearchBarTextFilled.value == true) {
                val intent = Intent(this, AdditionActivity::class.java)
                intent.putExtra(missionName, viewModel.searchBarText.value)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}