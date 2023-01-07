package kr.co.nottodo.presentation.addsituation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import kr.co.nottodo.databinding.ActivityAddSituationBinding

class AddSituationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSituationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddSituationBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


    private fun checkEdit() {
        binding.etAddSituationWriteDirect.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
}



