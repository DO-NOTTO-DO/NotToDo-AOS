package kr.co.nottodo.presentation.schedule.addition.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import kr.co.nottodo.R
import kr.co.nottodo.data.remote.model.RequestMissionDto
import kr.co.nottodo.databinding.ActivityAdditionBinding
import kr.co.nottodo.presentation.schedule.addition.viewmodel.AdditionViewModel
import kr.co.nottodo.presentation.schedule.bottomsheet.view.CalendarBottomSheet
import kr.co.nottodo.presentation.schedule.search.view.SearchActivity

class AdditionActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdditionBinding
    private val viewModel by lazy {
        AdditionViewModel()
    }
    lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_addition)
        initResultLauncher()
        initBinding()
        initBottomSheet()
        btnActionPlusOnClickListener()
        binding.tvAdditionMissionName.setOnClickListener {
            moveToSearchActivity()
        }
        binding.btnAdditionAdd.setOnClickListener {
            postMission()
        }

        observeResponse()

        binding.layoutAdditionMoveSituationPage.setOnClickListener {
            // TODO by 김준서 : 상황 추가 화면으로 이동 - 상황 추가 화면 구현시 개발
        }
        binding.ivAdditionMoveSituationPage.setOnClickListener {
            viewModel.additionSituationName.value = "입력하기"
            viewModel.isAdditionSituationNameFilled.value = true
        } // TODO by 김준서 : 추후 상황 추가 화면 구현시 개발
        binding.layoutAdditionMoveRecommendPage.setOnClickListener {
            // TODO by 김준서 : 추후 행동 추천 화면 구현시 개발
        }
        observeEditText()
        btnDeleteActionOnClickListener()
        ivDeletePageOnClickListener()

        observeToActivateAddBtn()
        observePlusBtn()
    }

    private fun postMission() {
        viewModel.postMission(
            RequestMissionDto(
                binding.tvAdditionMissionName.text.toString(),
                binding.tvAdditionSituationName.text.toString(),
                returnActionsList(),
                binding.etAdditionGoalTitle.text.toString(),
                binding.tvAdditionDate.text.toString()
            )
        )
    }

    private fun observeResponse() {
        viewModel.responsePostMission.observe(this) {
            finish()
        }

        viewModel.errorMessageMission.observe(this) {
            makeErrorToast(it)
        }
    }

    private fun makeErrorToast(text: String) {
        when (text) {
            "낫투두를 3개 이상 추가할 수 없습니다." -> {
                Toast.makeText(this, "낫투두 추가는 하루 최대 3개까지 가능합니다.", Toast.LENGTH_SHORT).show()
            }
            "이미 존재하는 낫투두 입니다." -> {
                Toast.makeText(this, "이미 같은 내용의 낫투두가 있어요", Toast.LENGTH_SHORT).show()
            }
            "올바르지 않은 날짜 형식입니다." -> {
                Toast.makeText(this, "올바르지 않은 날짜입니다.", Toast.LENGTH_SHORT).show()
            }
            "액션은 최대 2개까지만 추가할 수 있습니다." -> {
                Toast.makeText(this, "액션은 최대 2개까지만 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun returnActionsList(): List<String> {
        if (binding.tvAdditionActionSecond.isVisible) {
            return listOf(
                binding.tvAdditionActionFirst.text.toString(),
                binding.tvAdditionActionSecond.text.toString()
            )
        } else {
            return listOf(binding.tvAdditionActionFirst.text.toString())
        }
    }

    private fun initResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    binding.tvAdditionMissionName.text = result.data?.getStringExtra(missionName)
                }
            }
    }

    private fun moveToSearchActivity() {
        val intent = Intent(Intent(this, SearchActivity::class.java))
        intent.putExtra(currentMissionName, viewModel.additionMissionName.value)
        resultLauncher.launch(intent)
    }

    private fun observePlusBtn() {
        viewModel.isAdditionActionNameSecondFilled.observe(this) {
            if (it) {
                binding.btnAdditionActionPlus.setImageResource(R.drawable.ic_btn_plus_disabled)
            } else {
                binding.btnAdditionActionPlus.setImageResource(R.drawable.ic_btn_plus_enabled)
            }
        }
    }

    private fun ivDeletePageOnClickListener() {
        binding.ivAdditionDeletePage.setOnClickListener {
            finish()
        }
    }

    private fun btnDeleteActionOnClickListener() {
        with(binding) {
            btnAdditionDeleteActionFirst.setOnClickListener {
                if (viewModel.additionActionNameSecond.value != blank) {
                    viewModel.additionActionNameFirst.value =
                        viewModel.additionActionNameSecond.value
                    viewModel.additionActionNameSecond.value = blank
                    tvAdditionActionSecond.visibility = View.GONE
                    btnAdditionDeleteActionSecond.visibility = View.GONE
                } else {
                    viewModel.additionActionNameFirst.value = blank
                    tvAdditionActionFirst.visibility = View.GONE
                    btnAdditionDeleteActionFirst.visibility = View.GONE
                }
            }
            btnAdditionDeleteActionSecond.setOnClickListener {
                viewModel.additionActionNameSecond.value = blank
                tvAdditionActionSecond.visibility = View.GONE
                btnAdditionDeleteActionSecond.visibility = View.GONE
            }
        }
    }

    private fun btnActionPlusOnClickListener() {
        binding.btnAdditionActionPlus.setOnClickListener {
            if (viewModel.additionActionName.value != blank) {
                if (viewModel.additionActionNameFirst.value == blank) {
                    viewModel.additionActionNameFirst.value = viewModel.additionActionName.value
                    binding.tvAdditionActionFirst.visibility = View.VISIBLE
                    binding.btnAdditionDeleteActionFirst.visibility = View.VISIBLE
                    viewModel.additionActionName.value = blank
                } else if (viewModel.additionActionNameSecond.value == blank) {
                    viewModel.additionActionNameSecond.value = viewModel.additionActionName.value
                    binding.tvAdditionActionSecond.visibility = View.VISIBLE
                    binding.btnAdditionDeleteActionSecond.visibility = View.VISIBLE
                    viewModel.additionActionName.value = blank
                } else {
                    Toast.makeText(
                        this@AdditionActivity, additionToastText, Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addition)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun initBottomSheet() {
        binding.layoutAdditionCalendar.setOnClickListener {
            CalendarBottomSheet().show(supportFragmentManager, CalendarBottomSheet().tag)
        }
    }

    private fun observeEditText() {
        viewModel.isAdditionMissionNameFilled.observe(this) {
            if (it) {
                binding.tvAdditionMissionName.setBackgroundResource(R.drawable.rectangle_border_gray2_1)
            } else {
                binding.tvAdditionMissionName.setBackgroundResource(R.drawable.rectangle_border_gray4_1)
            }
        }
        viewModel.isAdditionGoalNameFilled.observe(this) {
            if (it) {
                binding.etAdditionGoalTitle.setBackgroundResource(R.drawable.rectangle_border_gray2_1)
            } else {
                binding.etAdditionGoalTitle.setBackgroundResource(R.drawable.rectangle_border_gray4_1)
            }
        }
        viewModel.isAdditionActionNameFilled.observe(this) {
            if (it) {
                binding.etAdditionActionName.setBackgroundResource(R.drawable.rectangle_border_gray2_1)
            } else {
                binding.etAdditionActionName.setBackgroundResource(R.drawable.rectangle_border_gray4_1)
            }
        }
    }

    private fun observeToActivateAddBtn() {
        viewModel.isBtnSuitConditions.observe(this) {
            if (it) {
                binding.btnAdditionAdd.setBackgroundColor(getColor(R.color.black_2a292d))
                binding.btnAdditionAdd.isEnabled = true
            } else {
                binding.btnAdditionAdd.setBackgroundColor(getColor(R.color.gray_2_8e8e93))
                binding.btnAdditionAdd.isEnabled = false
            }
        }
    }

//    private fun setDropdownMenu() {
//        binding.etAdditionMissionName.setOnClickListener {
//            AdditionDropdownFragment().show(supportFragmentManager, AdditionDropdownFragment().tag)
//        }
//    }

    companion object {
        const val additionRecentHeader = "낫투두 기록"
        val additionRecentSearch: List<String> = listOf(
            "침대에 다시 눕지 않기",
            "알람 끄고 다시 자지 않기",
            "10시 이후에 일어나지 않기",
            "일어났으면 다시 침대에 눕지 않기",
            "모바일 게임 하지 않기"
        )
        const val blank = ""
        const val additionToastText = "낫투두 액션은 2개 이상 불가능~"
        const val missionName = "missionName"
        const val currentMissionName = "currentMissionName"
    }
}