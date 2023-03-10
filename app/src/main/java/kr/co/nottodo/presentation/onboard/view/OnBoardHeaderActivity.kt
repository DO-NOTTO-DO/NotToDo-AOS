package kr.co.nottodo.presentation.onboard.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ActivityOnBoardHeaderBinding

class OnBoardHeaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardHeaderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardHeaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOnBoardStart.setOnClickListener {
            startActivity(Intent(this, OnBoardBodyActivity::class.java))
            finish()
        }

        window.statusBarColor = ContextCompat.getColor(this, R.color.bg_f5f5f5)
    }
}