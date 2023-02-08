package kr.co.nottodo.presentation.login.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kr.co.nottodo.Application
import kr.co.nottodo.BuildConfig
import kr.co.nottodo.databinding.ActivityLoginBinding
import kr.co.nottodo.presentation.login.viewmodel.LoginViewModel
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var startGoogleLoginForResult: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeIsLoggedIn()
        setKakaoLogin()
        setKakaoLogout()
//        googleInit()
        auth = Firebase.auth
//        binding.btnGoogleLogin.setOnClickListener {
//            val signInIntent = mGoogleSignInClient.signInIntent
//            startGoogleLoginForResult.launch(signInIntent)
//        }
        val requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            //구글 로그인 결과 처리...........................
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                Application.auth.signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Application.email = account.email
                            Log.d("Login1", "signInWithCredential:success")
                            val user = auth.currentUser
                            updateUI(user)
//                        changeVisibility("login")
                        } else {
                            Log.w("Login2", "signInWithCredential:failure", task.exception)
//                        changeVisibility("logout")
                        }
                    }
            } catch (e: ApiException) {
//            changeVisibility("logout")
                Log.e("Login3", "login E$e")
            }
        }
        binding.btnGoogleLogin.setOnClickListener {
            //구글 로그인....................
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(GOOGLE_CLIENT_ID)
                .requestEmail()
                .build()
            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            requestLauncher.launch(signInIntent)
        }

        binding.btnGoogleLogout.setOnClickListener {
            //로그아웃...........
            Application.auth.signOut()
            Application.email = null
            binding.tvGoogleNickname.text = ""
//            changeVisibility("logout")
        }
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

    private fun googleInit() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(GOOGLE_CLIENT_ID)
            .requestEmail()
            .requestProfile()
            .requestId()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

//        startGoogleLoginForResult =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
//                if (result.resultCode == RESULT_OK) {
//                    result.data?.let { data ->
//
//                        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//
//                        try {
//                            // Google Sign In was successful, authenticate with Firebase
//                            val account = task.getResult(ApiException::class.java)!!
//                            Log.d("LoginActivity", "firebaseAuthWithGoogle:" + account.id)
//                            firebaseAuthWithGoogle(account.idToken!!)
//                        } catch (e: ApiException) {
//                            // Google Sign In failed, update UI appropriately
//                            Log.w("Login", "Google sign in failed", e)
//                        }
//                    }
//                    // Google Login Success
//                } else {
//                    Log.e("Login", "Google Result Error ${result}")
//                }
//            }
    }

    // [START auth_with_google]
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Login", "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Login", "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        // FirebaseUser 데이터에 따른 UI 작업
        binding.tvGoogleNickname.text = user?.displayName
    }

    companion object {
        private const val GOOGLE_CLIENT_ID = BuildConfig.GOOGLE_CLIENT_ID
    }

}