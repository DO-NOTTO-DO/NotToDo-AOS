package kr.co.nottodo

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import com.kakao.sdk.common.KakaoSdk
import timber.log.Timber

class Application : Application() {
    override fun onCreate() {
        super.onCreate()

        setupKakaoSdk()
        setupTimber()
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
    }

    private fun setupKakaoSdk() {
        KakaoSdk.init(this, BuildConfig.KAKAO_APP_KEY)
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}
