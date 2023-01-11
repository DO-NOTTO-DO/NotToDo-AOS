package kr.co.nottodo.presentation.home

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import kr.co.nottodo.R

class HomeBallonFactory : Balloon.Factory() {
    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        return Balloon.Builder(context)
            .setLayout(R.layout.item_home_ballon)
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
}
