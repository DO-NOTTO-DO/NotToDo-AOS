package kr.co.nottodo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.TriangleEdgeTreatment
import kr.co.nottodo.R
import kr.co.nottodo.databinding.ActivityMainBinding

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
            .setTopEdge(TriangleEdgeTreatment(30.toFloat(), false))
            .build()

        val backgroundDrawable = MaterialShapeDrawable(shapePathModel).apply {
            fillColor = ContextCompat.getColorStateList(
                this@MainActivity,
                R.color.white
            ) // change color
        }
        binding.homeCustomBottom.background = backgroundDrawable
        binding.homeCustomBottom.itemIconTintList = null
    }
}