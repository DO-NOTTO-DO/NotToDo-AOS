package kr.co.nottodo.presentation.addsituation.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ActivityAddSituationBinding
import kr.co.nottodo.presentation.addsituation.viewmodel.AddSituationViewModel
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity.Companion.situationName
import kr.co.nottodo.util.extension.addTextview

class AddSituationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSituationBinding
    private val viewModel by viewModels<AddSituationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()

        setAnimation()
        setCompleteTvClickEvent()

        viewModel.getSituationList()

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

        viewModel.situationText.observe(this) {
            binding.tvAddSituationTextWatcher.text = it.length.toString() + "/15"
            if (it.isEmpty()) {
                binding.etAddSituationWriteDirect.setBackgroundResource(R.drawable.rectangle_border_gray4_1)
            } else {
                binding.etAddSituationWriteDirect.setBackgroundResource(R.drawable.rectangle_border_gray2_1)
            }
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
            intent.putExtra(situationName, binding.etAddSituationWriteDirect.text.toString())
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



