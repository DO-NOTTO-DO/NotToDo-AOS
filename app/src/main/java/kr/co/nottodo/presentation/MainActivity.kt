package kr.co.nottodo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ActivityMainBinding
import kr.co.nottodo.presentation.achievement.view.AchievementFragment
import kr.co.nottodo.presentation.home.HomeFragment
import kr.co.nottodo.presentation.mypage.view.MyPageFragment

import kr.co.nottodo.presentation.toplevel.recommendation.recommendationfragment.RecommendationFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val radius = resources.getDimension(R.dimen.bottomNaviTopCorner)
        val shapePathModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(CornerFamily.CUT, radius)
            .setTopRightCorner(CornerFamily.CUT, radius)
            .build()

        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
            fillColor = ContextCompat.getColorStateList(
                this@MainActivity,
                R.color.white
            ) // change color
        }
        binding.homeCustomBottom.background = backgroundDrawable
        binding.homeCustomBottom.itemIconTintList = null
        initTransaction()
        if (savedInstanceState == null) {
            changeFragment(HomeFragment())
        }
    }

    private fun initTransaction() {
        binding.homeCustomBottom.setOnItemSelectedListener { item ->
            changeFragment(
                //todo 이거 자기꺼에 맞게 바꿔주세요
                when (item.itemId) {
                    R.id.menu_home -> HomeFragment()
                    R.id.menu_recommend -> RecommendationFragment()
                    R.id.menu_result -> AchievementFragment()
                    else -> MyPageFragment()
                }
            )
            true
        }
        binding.homeCustomBottom.setOnItemReselectedListener {

        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view_home, fragment)
            .commit()
    }
}