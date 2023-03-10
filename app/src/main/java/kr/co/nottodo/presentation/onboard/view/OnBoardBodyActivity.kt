package kr.co.nottodo.presentation.onboard.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ActivityOnBoardBodyBinding
import kr.co.nottodo.presentation.MainActivity
import kr.co.nottodo.presentation.onboard.adapter.OnBoardViewPagerAdapter
import kr.co.nottodo.presentation.mission.addition.view.AdditionActivity.Companion.BLANK

class OnBoardBodyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardBodyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnBoardBodyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()

        setBtnStartClickEvent()
        setTvSkipClickEvent()
        setArrowClickEvent()
        window.statusBarColor = ContextCompat.getColor(this, R.color.bg_f5f5f5)
    }

    private fun setArrowClickEvent() {
        binding.layoutOnBoardPrev.setOnClickListener {
            binding.viewpagerOnBoard.currentItem = binding.viewpagerOnBoard.currentItem - 1
        }
        binding.layoutOnBoardNext.setOnClickListener {
            binding.viewpagerOnBoard.currentItem = binding.viewpagerOnBoard.currentItem + 1
        }
    }

    private fun setTvSkipClickEvent() {
        binding.tvOnBoardSkip.setOnClickListener {
            if (binding.tvOnBoardSkip.text == SKIP) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    private fun setBtnStartClickEvent() {
        binding.btnOnBoardStart.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun initViewPager() {
        binding.viewpagerOnBoard.adapter = OnBoardViewPagerAdapter(this)
        binding.viewpagerOnBoard.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when (position) {
                    0 -> {
                        binding.layoutOnBoardBottom.visibility = View.VISIBLE
                        binding.ivOnBoardProgressBar.setImageResource(R.drawable.ic_prgressbar_first)
                        binding.btnOnBoardStart.visibility = View.GONE
                        binding.tvOnBoardSkip.text = SKIP
                        binding.layoutOnBoardPrev.visibility = View.GONE
                    }
                    1 -> {
                        binding.layoutOnBoardBottom.visibility = View.VISIBLE
                        binding.ivOnBoardProgressBar.setImageResource(R.drawable.ic_progeressbar_second)
                        binding.btnOnBoardStart.visibility = View.GONE
                        binding.tvOnBoardSkip.text = SKIP
                        binding.layoutOnBoardPrev.visibility = View.VISIBLE
                    }
                    2 -> {
                        binding.layoutOnBoardBottom.visibility = View.VISIBLE
                        binding.ivOnBoardProgressBar.setImageResource(R.drawable.ic_progressbar_third)
                        binding.btnOnBoardStart.visibility = View.GONE
                        binding.tvOnBoardSkip.text = SKIP
                        binding.layoutOnBoardPrev.visibility = View.VISIBLE

                    }
                    3 -> {
                        binding.layoutOnBoardBottom.visibility = View.GONE
                        binding.btnOnBoardStart.visibility = View.VISIBLE
                        binding.tvOnBoardSkip.text = BLANK
                        binding.layoutOnBoardPrev.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    companion object {
        const val SKIP = "????????????"
    }
}