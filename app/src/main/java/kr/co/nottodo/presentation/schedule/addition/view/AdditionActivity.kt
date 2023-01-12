package kr.co.nottodo.presentation.schedule.addition.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import kr.co.nottodo.presentation.schedule.addition.viewmodel.AdditionViewModel
import kr.co.nottodo.presentation.schedule.bottomsheet.view.CalendarBottomSheet
import kr.co.nottodo.presentation.schedule.search.view.SearchActivity
import kr.co.nottodo.presentation.toplevel.recommendation.recommendationactivity.RecommendationActivity
import kr.co.nottodo.presentation.toplevel.recommendation.viewmodel.RecommendationViewModel
import kr.co.nottodo.view.snackbar.CustomSnackBar
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AdditionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdditionBinding
    private val viewModel by viewModels<AdditionViewModel>()
    private val viewModel1 by viewModels<RecommendationViewModel>()
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

        observeEditText()
        observeDate()
        btnDeleteActionOnClickListener()
        ivDeletePageOnClickListener()

        observeToActivateAddBtn()
        observePlusBtn()

        observeResponse()
    }

    private fun initActionName() {
        viewModel.additionActionName.value = intent.getStringExtra(actionName) ?: blank
    }

    private fun observeDate() {
        viewModel.additionDate.observe(this) {
            binding.tvAdditionDate.text = it
        }
    }

    private fun initDate() {
        binding.tvAdditionDate.text =
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(datePattern))
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
            responseNoMoreThanThree -> {
                CustomSnackBar.makeSnackBar(binding.root, snackBarTextNoMoreThanThree).show()
            }
            responseAlreadyExist -> {
                CustomSnackBar.makeSnackBar(binding.root, snackBarTextAlreadyExist).show()
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
                        it.data?.getStringExtra(missionName) ?: blank
                }
            }

        actionNameResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    viewModel.additionActionName.value =
                        it.data?.getStringExtra(actionName) ?: blank
                }
            }

        situationNameResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    viewModel.additionSituationName.value =
                        it.data?.getStringExtra(situationName) ?: blank
                    viewModel.isAdditionSituationNameFilled.value =
                        it.data?.getStringExtra(situationName) != blank
                }
            }

    }

    private fun moveToSearchActivity() {
        val intent = Intent(Intent(this, SearchActivity::class.java))
        intent.putExtra(currentMissionName, viewModel.additionMissionName.value)
        missionNameResultLauncher.launch(intent)
    }

    private fun moveToRecommendationActivity() {
        val intent = Intent(Intent(this, RecommendationActivity::class.java))
        actionNameResultLauncher.launch(intent)
    }

    private fun moveToAddSituationActivity() {
        val intent = Intent(Intent(this, AddSituationActivity::class.java))
        situationNameResultLauncher.launch(intent)
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

    companion object {
        const val blank = ""
        const val additionToastText = "낫투두 액션은 2개 이상 불가능~"
        const val missionName = "missionName"
        const val actionName = "actionName"
        const val situationName = "situationName"
        const val currentMissionName = "currentMissionName"
        const val input = "입력하기"
        const val responseNoMoreThanThree = "낫투두를 3개 이상 추가할 수 없습니다."
        const val responseAlreadyExist = "이미 존재하는 낫투두 입니다."
        const val snackBarTextNoMoreThanThree = "낫투두 추가는 하루 최대 3개까지 가능합니다"
        const val snackBarTextAlreadyExist = "이미 같은 내용의 낫투두가 있어요"
        const val datePattern = "yyyy.MM.dd"

    }
}