package kr.co.nottodo.presentation.home

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import kr.co.nottodo.R

class HomeBallonFactory : Balloon.Factory() {
    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        return Balloon.Builder(context)
            .setLayout(R.layout.item_home_ballon)
//            .setWidthRatio(0.8f)
            .setHeight(74)
            .setWidth(144)
            .setArrowSize(10)
            .setArrowOrientation(ArrowOrientation.BOTTOM)
            .setArrowPosition(0.3f)
            .setCornerRadius(100f)
            .setBackgroundColorResource(R.color.white)
            .setBalloonAnimation(BalloonAnimation.CIRCULAR)
            .setLifecycleOwner(lifecycle)
            .build()

    }

    /*
    .setArrowSize(10)
            .setArrowOrientation(ArrowOrientation.TOP)
            .setArrowPosition(0.5f)
//            .setWidthRatio(0.55f)
            .setHeight(250)
            .setCornerRadius(4f)
            .setBackgroundColorResource(R.color.white)
            .setBalloonAnimation(BalloonAnimation.CIRCULAR)
            .setLifecycleOwner(lifecycle)
            .build()
     */
}