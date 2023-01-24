package kr.co.nottodo.presentation.addsituation.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ActivityAddSituationBinding
import kr.co.nottodo.presentation.addsituation.viewmodel.AddSituationViewModel
import kr.co.nottodo.presentation.mission.addition.view.AdditionActivity
import kr.co.nottodo.presentation.mission.addition.view.AdditionActivity.Companion.OLD_SITUATION_NAME
import kr.co.nottodo.presentation.mission.addition.view.AdditionActivity.Companion.SITUATION_NAME
import kr.co.nottodo.util.extension.KeyBoardUtil
import kr.co.nottodo.util.extension.addTextview

class AddSituationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSituationBinding
    private val viewModel by viewModels<AddSituationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        viewModel.getSituationList()

        setAnimation()
        setCompleteTvClickEvent()
        setBackIvClickEvent()

        observeResponseSituation()
        observeSituationName()
        setHideKeyboard()

        getEditTextText()
    }

    private fun setHideKeyboard() {
        binding.layoutAddSituation.setOnClickListener {
            hideKeyboard()
        }
    }

    private fun getEditTextText() {
        if (intent.getStringExtra(OLD_SITUATION_NAME) != null) {
            viewModel.situationText.value = intent.getStringExtra(OLD_SITUATION_NAME)
        }
    }

    private fun hideKeyboard() {
        KeyBoardUtil.hide(this)
    }

    private fun observeSituationName() {
        viewModel.situationText.observe(this) {
            binding.tvAddSituationTextWatcher.text = it.length.toString() + "/15"
            if (it.isEmpty()) {
                binding.etAddSituationWriteDirect.setBackgroundResource(R.drawable.rectangle_border_gray4_1)
            } else {
                binding.etAddSituationWriteDirect.setBackgroundResource(R.drawable.rectangle_border_gray2_1)
            }
        }
    }

    private fun observeResponseSituation() {
        viewModel.recommendSituationList.observe(this) {
            for (element in it) {
                binding.layoutAdditionSituationRecommendKeywordList.addTextview(
                    element.name,
                    binding.etAddSituationWriteDirect
                )
            }
        }

        viewModel.recentSituationList.observe(this) {
            for (element in it) {
                binding.layoutAddSituationRecentKeywordList.addTextview(
                    element.name,
                    binding.etAddSituationWriteDirect
                )
            }
        }
    }

    private fun setBackIvClickEvent() {
        binding.ivAddSituationBack.setOnClickListener {
            finish()
        }
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_situation)
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun setCompleteTvClickEvent() {
        binding.tvAddSituationComplete.setOnClickListener {
            val intent = Intent(this, AdditionActivity::class.java)
            intent.putExtra(SITUATION_NAME, binding.etAddSituationWriteDirect.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun setAnimation() {
        overridePendingTransition(
            R.anim.animation_enter,
            R.anim.animation_exit
        )
    }
}



