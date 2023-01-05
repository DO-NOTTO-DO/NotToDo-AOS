package kr.co.nottodo.presentation.schedule.search.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import kr.co.nottodo.R
import kr.co.nottodo.data.remote.model.ResponseHistoryDto
import kr.co.nottodo.databinding.ActivitySearchBinding
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity.Companion.currentMissionName
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity.Companion.missionName
import kr.co.nottodo.presentation.schedule.search.adapter.SearchRecyclerViewAdapter
import kr.co.nottodo.presentation.schedule.search.viewmodel.SearchViewModel
import timber.log.Timber

class SearchActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchBinding
    private val viewModel by lazy { SearchViewModel() }
    lateinit var itemList: List<ResponseHistoryDto.History>

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

        initBinding()
        viewModel.getHistory()
        viewModel.getHistoryResult.observe(this) {
            itemList = it.data
            initRecyclerView(itemList)
        }
        viewModel.errorMessage.observe(this) {
            Timber.d(it)
        }

        observeSearchBar()

        getSearchBarText()

        onCompleteBtnClick()

        viewModel.getHistory()
        viewModel.getHistoryResult.observe(this) {
            itemList = it.data
        }
        viewModel.errorMessage.observe(this) {
            Timber.d(it)
        }
    }

    private fun onCompleteBtnClick() {
        binding.tvSearchComplete.setOnClickListener {
            val intent = Intent(this, AdditionActivity::class.java)
            intent.putExtra(missionName, viewModel.searchBarText.value)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun getSearchBarText() {
        viewModel.searchBarText.value = intent.getStringExtra(currentMissionName)
    }

    private fun observeSearchBar() {
        viewModel.isSearchBarTextFilled.observe(this) {
            if (it) {
                binding.etSearchSearchBar.setBackgroundResource(R.drawable.rectangle_border_gray2_1)
            } else {
                binding.etSearchSearchBar.setBackgroundResource(R.drawable.rectangle_border_gray4_1)
            }
        }
    }

    private fun initRecyclerView(itemList: List<ResponseHistoryDto.History>) {
        binding.rvSearchHistory.adapter = SearchRecyclerViewAdapter(
            binding.root.context,
            itemList, viewModel::setSearchBarText
        )
        binding.rvSearchHistory.layoutManager = LinearLayoutManager(binding.root.context)
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }
}