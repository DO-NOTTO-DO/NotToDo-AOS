package kr.co.nottodo.presentation.login.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kr.co.nottodo.databinding.ActivityLoginBinding
import kr.co.nottodo.presentation.login.viewmodel.LoginViewModel
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeIsLoggedIn()
        setKakaoLogin()
        setKakaoLogout()
    }

    private fun observeIsLoggedIn() {
        viewModel.isLogged.observe(this) {
            setUserProfile()
        }
    }

    private fun setKakaoLogout() {
        binding.btnLoginKakaoLogout.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Timber.e("로그아웃 실패. SDK에서 토큰 삭제됨", error)
                } else {
                    Timber.i("로그아웃 성공. SDK에서 토큰 삭제됨")
                    viewModel.isLogged.value = false
                }
            }
        }
    }

    private fun setUserProfile() {
        Timber.i("setUserProfile 함수 실행됨")
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Timber.e("사용자 정보 요청 실패", error)
                } else if (user != null) {
                    Timber.i(
                        "사용자 정보 요청 성공" +
                                "\n회원번호: ${user.id}" +
                                "\n이메일: ${user.kakaoAccount?.email}" +
                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                    )
                    binding.tvLoginNickname.text = user.kakaoAccount?.profile?.nickname
                    Glide.with(this)
                        .load(user.kakaoAccount?.profile?.thumbnailImageUrl)
                        .centerCrop()
                        .into(binding.ivProfileImage)
                }
            }
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                if (error != null) {
                    Timber.e("토큰 정보 보기 실패", error)
                } else if (tokenInfo != null) {
                    Timber.i(
                        "토큰 정보 보기 성공" +
                                "\n회원번호: ${tokenInfo.id}" +
                                "\n만료시간: ${tokenInfo.expiresIn} 초"
                    )
                }
            }
        } else {
            binding.tvLoginNickname.text = "로그인이 필요합니다."
            binding.ivProfileImage.setImageDrawable(null)
        }
    }

    private fun setKakaoLogin() {
        val kakaoLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Timber.e("로그인 실패", error)
            } else if (token != null) {
                Timber.i("로그인 성공 ${token.accessToken}")
                viewModel.isLogged.value = true
            }
        }
        binding.btnLoginKakaoLogin.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                // 카카오톡 로그인
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    // 로그인 실패 부분
                    if (error != null) {
                        Timber.e("로그인 실패 $error")
                        // 사용자 취소
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }
                        // 다른 오류
                        else {
                            UserApiClient.instance.loginWithKakaoAccount(
                                this,
                                callback = kakaoLoginCallback
                            ) // 카카오 이메일 로그인
                        }
                    }
                    // 로그인 성공 부분
                    else if (token != null) {
                        Timber.e("로그인 성공 ${token.accessToken}")
                    }
                }
            } else {
                Timber.d("카카오톡이 설치되어 있지 않습니다.")
                // 카카오 이메일 로그인
                UserApiClient.instance.loginWithKakaoAccount(
                    this,
                    callback = kakaoLoginCallback
                )
            }
        }
    }
}