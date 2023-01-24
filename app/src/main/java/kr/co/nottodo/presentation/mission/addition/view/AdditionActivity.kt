package kr.co.nottodo.presentation.mission.addition.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import kr.co.nottodo.R
import kr.co.nottodo.data.remote.model.RequestMissionDto
import kr.co.nottodo.databinding.ActivityAdditionBinding
import kr.co.nottodo.presentation.addsituation.view.AddSituationActivity
import kr.co.nottodo.presentation.mission.addition.viewmodel.AdditionViewModel
import kr.co.nottodo.presentation.mission.bottomsheet.view.CalendarBottomSheet
import kr.co.nottodo.presentation.mission.search.view.SearchActivity
import kr.co.nottodo.presentation.toplevel.recommendation.recommendationactivity.RecommendationActivity
import kr.co.nottodo.util.extension.KeyBoardUtil
import kr.co.nottodo.view.snackbar.CustomSnackBar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AdditionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdditionBinding
    private val viewModel by viewModels<AdditionViewModel>()
    private lateinit var missionNameResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var actionNameResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var situationNameResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addition)
        initBinding()
        initResultLauncher()
        initBottomSheet()
        initDate()
        initActionName()

        btnActionPlusOnClickListener()
        binding.tvAdditionMissionName.setOnClickListener {
            moveToSearchActivity()
        }
        binding.btnAdditionAdd.setOnClickListener {
            postMission()
        }

        binding.layoutAdditionMoveSituationPage.setOnClickListener {
            moveToAddSituationActivity()
        }
        binding.layoutAdditionMoveRecommendPage.setOnClickListener {
            moveToRecommendationActivity()
        }
        binding.layoutAddition.setOnClickListener {
            hideKeyboard()
        }

        observeEditText()
        observeDate()
        btnDeleteActionOnClickListener()
        ivDeletePageOnClickListener()

        observeToActivateAddBtn()
        observePlusBtn()

        observeResponse()
    }

    private fun hideKeyboard() {
        KeyBoardUtil.hide(this)
    }

    private fun initActionName() {
        viewModel.additionActionName.value = intent.getStringExtra(ACTION_NAME) ?: BLANK
    }

    private fun observeDate() {
        viewModel.additionDate.observe(this) {
            binding.tvAdditionDate.text = it
        }
    }

    private fun initDate() {
        binding.tvAdditionDate.text =
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN))
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
            RESPONSE_NO_MORE_THAN_THREE -> {
                CustomSnackBar.makeSnackBar(binding.root, SNACK_BAR_TEXT_NO_MORE_THAN_THREE).show()
            }
            RESPONSE_ALREADY_EXIST -> {
                CustomSnackBar.makeSnackBar(binding.root, SNACK_BAR_TEXT_ALREADY_EXIST).show()
            }
            else -> {
                CustomSnackBar.makeSnackBar(binding.root, text).show()
            }
        }
    }

    private fun returnActionsList(): List<String> {
        return if (binding.tvAdditionActionSecond.isVisible) {
            listOf(
                binding.tvAdditionActionFirst.text.toString(),
                binding.tvAdditionActionSecond.text.toString()
            )
        } else {
            listOf(binding.tvAdditionActionFirst.text.toString())
        }
    }

    private fun initResultLauncher() {
        missionNameResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    binding.tvAdditionMissionName.text =
                        it.data?.getStringExtra(MISSION_NAME) ?: BLANK
                }
            }

        actionNameResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    viewModel.additionActionName.value =
                        it.data?.getStringExtra(ACTION_NAME) ?: BLANK
                }
            }

        situationNameResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    if ((it.data?.getStringExtra(SITUATION_NAME) ?: BLANK) == BLANK) {
                        viewModel.additionSituationName.value = INPUT
                        viewModel.isAdditionSituationNameFilled.value = false
                    } else {
                        viewModel.additionSituationName.value =
                            it.data?.getStringExtra(SITUATION_NAME) ?: INPUT
                        viewModel.isAdditionSituationNameFilled.value = true
                    }

                }
            }

    }

    private fun moveToSearchActivity() {
        val intent = Intent(Intent(this, SearchActivity::class.java))
        intent.putExtra(CURRENT_MISSION_NAME, viewModel.additionMissionName.value)
        missionNameResultLauncher.launch(intent)
    }

    private fun moveToRecommendationActivity() {
        val intent = Intent(Intent(this, RecommendationActivity::class.java))
        actionNameResultLauncher.launch(intent)
    }

    private fun moveToAddSituationActivity() {
        if (viewModel.isAdditionSituationNameFilled.value == true) {
            val intent = Intent(Intent(this, AddSituationActivity::class.java))
                .putExtra(OLD_SITUATION_NAME, viewModel.additionSituationName.value)
            situationNameResultLauncher.launch(intent)
        } else {
            val intent = Intent(Intent(this, AddSituationActivity::class.java))
            situationNameResultLauncher.launch(intent)
        }
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
                if (viewModel.additionActionNameSecond.value != BLANK) {
                    viewModel.additionActionNameFirst.value =
                        viewModel.additionActionNameSecond.value
                    viewModel.additionActionNameSecond.value = BLANK
                    tvAdditionActionSecond.visibility = View.GONE
                    btnAdditionDeleteActionSecond.visibility = View.GONE
                } else {
                    viewModel.additionActionNameFirst.value = BLANK
                    tvAdditionActionFirst.visibility = View.GONE
                    btnAdditionDeleteActionFirst.visibility = View.GONE
                }
            }
            btnAdditionDeleteActionSecond.setOnClickListener {
                viewModel.additionActionNameSecond.value = BLANK
                tvAdditionActionSecond.visibility = View.GONE
                btnAdditionDeleteActionSecond.visibility = View.GONE
            }
        }
    }

    private fun btnActionPlusOnClickListener() {
        binding.btnAdditionActionPlus.setOnClickListener {
            if (viewModel.additionActionName.value != BLANK) {
                if (viewModel.additionActionNameFirst.value == BLANK) {
                    viewModel.additionActionNameFirst.value = viewModel.additionActionName.value
                    binding.tvAdditionActionFirst.visibility = View.VISIBLE
                    binding.btnAdditionDeleteActionFirst.visibility = View.VISIBLE
                    viewModel.additionActionName.value = BLANK
                } else if (viewModel.additionActionNameSecond.value == BLANK) {
                    viewModel.additionActionNameSecond.value = viewModel.additionActionName.value
                    binding.tvAdditionActionSecond.visibility = View.VISIBLE
                    binding.btnAdditionDeleteActionSecond.visibility = View.VISIBLE
                    viewModel.additionActionName.value = BLANK
                } else {
                    hideKeyboard()
                    CustomSnackBar.makeSnackBar(binding.root, ADDITION_TOAST_TEXT).show()
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
            val calendarBottomSheet =
                supportFragmentManager.findFragmentByTag(CalendarBottomSheet.TAG) as? CalendarBottomSheet
                    ?: CalendarBottomSheet()
            if (!calendarBottomSheet.isAdded) calendarBottomSheet.show(
                supportFragmentManager,
                CalendarBottomSheet.TAG
            )
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

    companion object {
        const val BLANK = ""
        const val ADDITION_TOAST_TEXT = "실천 방법 추가는 2개까지만 가능해요"
        const val MISSION_NAME = "missionName"
        const val ACTION_NAME = "actionName"
        const val SITUATION_NAME = "situationName"
        const val CURRENT_MISSION_NAME = "currentMissionName"
        const val INPUT = "입력하기"
        const val RESPONSE_NO_MORE_THAN_THREE = "낫투두를 3개 이상 추가할 수 없습니다."
        const val RESPONSE_ALREADY_EXIST = "이미 존재하는 낫투두 입니다."
        const val SNACK_BAR_TEXT_NO_MORE_THAN_THREE = "낫투두 추가는 하루 최대 3개까지 가능합니다"
        const val SNACK_BAR_TEXT_ALREADY_EXIST = "이미 같은 내용의 낫투두가 있어요"
        const val DATE_PATTERN = "yyyy.MM.dd"
        const val OLD_SITUATION_NAME = "oldSituationName"
    }
}