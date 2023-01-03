package kr.co.nottodo.presentation.schedule.addition.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ActivityAdditionBinding
import kr.co.nottodo.presentation.schedule.addition.viewmodel.AdditionViewModel

class AdditionActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdditionBinding
    private val viewModel by lazy {
        AdditionViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addition)
        initBinding()
        binding.btnAdditionAdd.setOnClickListener {
            // 서버 통신을 통해 낫투두 추가하는 기능
        }
        binding.layoutAdditionMoveSituationPage.setOnClickListener {
            // 상황 추가 창으로 이동
        }
        binding.ivAdditionMoveSituationPage.setOnClickListener {
            viewModel.additionSituationName.value = "출근 시간"
        }
        btnActionPlusOnClickListener()
        btnDeleteActionOnClickListener()

    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addition)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }


    private fun btnDeleteActionOnClickListener() {
        with(binding) {
            btnAdditionDeleteAction1.setOnClickListener {
                if (viewModel.additionActionName2.value != blank) {
                    viewModel.additionActionName1.value = viewModel.additionActionName2.value
                    viewModel.additionActionName2.value = blank
                    tvAdditionAction2.visibility = View.GONE
                    btnAdditionDeleteAction2.visibility = View.GONE
                } else {
                    viewModel.additionActionName1.value = blank
                    tvAdditionAction1.visibility = View.GONE
                    btnAdditionDeleteAction1.visibility = View.GONE
                }
            }
            btnAdditionDeleteAction2.setOnClickListener {
                viewModel.additionActionName2.value = blank
                tvAdditionAction2.visibility = View.GONE
                btnAdditionDeleteAction2.visibility = View.GONE
            }
        }
    }

    private fun btnActionPlusOnClickListener() {
        binding.btnAdditionActionPlus.setOnClickListener {
            if (viewModel.additionActionName.value != blank) {
                if (viewModel.additionActionName1.value == blank) {
                    viewModel.additionActionName1.value =
                        viewModel.additionActionName.value
                    binding.tvAdditionAction1.visibility = View.VISIBLE
                    binding.btnAdditionDeleteAction1.visibility = View.VISIBLE
                    viewModel.additionActionName.value = blank
                } else if (viewModel.additionActionName2.value == blank) {
                    viewModel.additionActionName2.value =
                        viewModel.additionActionName.value
                    binding.tvAdditionAction2.visibility = View.VISIBLE
                    binding.btnAdditionDeleteAction2.visibility = View.VISIBLE
                    viewModel.additionActionName.value = blank
                } else {
                    Toast.makeText(
                        this@AdditionActivity,
                        additionToastText, Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    companion object {
        const val additionRecentHeader = "낫투두 기록"
        val additionRecentSearch: List<String> = listOf(
            "침대에 다시 눕지 않기", "알람 끄고 다시 자지 않기",
            "10시 이후에 일어나지 않기", "일어났으면 다시 침대에 눕지 않기", "모바일 게임 하지 않기"
        )
        const val blank = ""
        const val additionToastText = "낫투두 액션은 2개 이상 불가능~"
    }
}