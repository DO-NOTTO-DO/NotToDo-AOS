package kr.co.nottodo.presentation.home

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.animations.BalloonRotateDirection
import kr.co.nottodo.R

class HomeBallonFactory : Balloon.Factory() {
    override fun create(context: Context, lifecycle: LifecycleOwner?): Balloon {
        return Balloon.Builder(context)
            .setWidthRatio(0.8f)
            .setHeight(50)
            .setWidth(400)
            .setIsVisibleArrow(false)
            .setCornerRadius(8f)
            .setAlpha(0.9f)
            .setTextIsHtml(true)
            .setLayout(R.layout.item_home_ballon)
            .setBackgroundColorResource(R.color.white)
            .setBalloonAnimation(BalloonAnimation.CIRCULAR)
            .setLifecycleOwner(lifecycle)
            .build()

    }
}