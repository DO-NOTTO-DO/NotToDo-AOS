package kr.co.nottodo.presentation.addsituation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ActivityAddSituationBinding
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity.Companion.situationName

class AddSituationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSituationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddSituationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAnimation()
        setCompleteTvClickEvent()
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


    private fun checkEdit() {
        binding.etAddSituationWriteDirect.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                p0?.length
            }
        })
    }
}



