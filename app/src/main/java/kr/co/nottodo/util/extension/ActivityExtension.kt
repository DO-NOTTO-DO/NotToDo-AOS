package kr.co.nottodo.util.extension

import android.app.Activity
import androidx.core.content.ContextCompat
import kr.co.nottodo.presentation.MainActivity

// 나중에 리팩토링 할게요...ㅎ TODO by Giovanni Junseo Kim
fun setStatusBarColor(activity: Activity, resId: Int) {
    activity.window.statusBarColor =
        ContextCompat.getColor(activity, resId)
}