package kr.co.nottodo.presentation.login.view

import LoginState
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Job
import kr.co.nottodo.Application
import kr.co.nottodo.BuildConfig
import kr.co.nottodo.databinding.ActivityLoginBinding
import kr.co.nottodo.presentation.login.viewmodel.LoginViewModel
import timber.log.Timber

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var auth: FirebaseAuth
    private var tokenId: String? = null  //Google Auth 인증에 성공하면 token 값으로 설정된다
    private lateinit var fetchJob: Job

    /* FirebaseAuth */
    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    private val googleSignInOptions: GoogleSignInOptions by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(GOOGLE_CLIENT_ID)
            .requestEmail()
            .requestProfile()
            .build()
    }

    /* GoogleSignIn */
    private val googleSignIn by lazy {
        GoogleSignIn.getClient(this, googleSignInOptions)
    }

    /* Google Auth 로그인 결과 수신 */
    private val loginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("login", "loginLauncher - result : $result")
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    task.getResult(ApiException::class.java)?.let { account ->
                        tokenId = account.idToken
                        Application.auth.signInWithCredential(
                            GoogleAuthProvider.getCredential(
                                tokenId,
                                null
                            )
                        )
                        viewModel.saveToken(
                            tokenId ?: throw java.lang.Exception(), account
                        )  //Loading 상태 이후 Login 상태로 변경
                    } ?: throw Exception()
                } catch (e: Exception) {
                    e.printStackTrace()
                    handleErrorState()  //Error 상태
                }
            } else {
                handleErrorState()  //Error 상태
            }
        }

    private fun revokeAccess() {
        googleSignIn.revokeAccess().addOnCompleteListener(this) { }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeIsLoggedIn()
        setKakaoLogin()
        setKakaoLogout()
//        auth = Firebase.auth

        fetchJob = viewModel.fetchData(tokenId)
        initViews()
        observeData()
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

    /* viewModel 을  관찰하여 상태 변화에 따라 처리 */
    private fun observeData() = viewModel.loginStateLiveData.observe(this) {
        Log.d("LoginViewModel", "observeData() - it : $it")
        when (it) {
            is LoginState.UnInitialized -> initViews()
//            is LoginState.Loading -> handleLoadingState()
            is LoginState.Login -> handleLoginState(it)
            is LoginState.Success -> handleSuccessState(it)
            else -> handleLoadingState()
        }
    }

    /* view 기본 설정 */
    private fun initViews() = with(binding) {
        tokenId?.let {  //로그인 된 상태
//            groupLoginRequired.isGone = true
//            groupLogoutRequired.isVisible = true
        } ?: kotlin.run {  //로그인 안된 상태
//            groupLoginRequired.isVisible = true
//            groupLogoutRequired.isGone = true
        }

        binding.btnGoogleLogin.setOnClickListener {   //로그인 버튼 클릭 시
            val signInIntent: Intent = googleSignIn.signInIntent
            loginLauncher.launch(signInIntent)  //loginLauncher로 결과 수신하여 처리
        }
        binding.btnGoogleLogout.setOnClickListener {  //로그아웃 버튼 클릭 시
            Application.auth.signOut()
            Log.d("TAG", "initViews: ${Application.auth}")
            revokeAccess()
            Application.email = null
            viewModel.signOut()
        }
    }

    /* Loading 상태인 경우 */
    private fun handleLoadingState() = with(binding) {
//        progressBar.isVisible = true
//        groupLoginRequired.isGone = true
//        groupLogoutRequired.isGone = true
    }

    /* Google Auth Login 상태인 경우 */
    private fun handleLoginState(state: LoginState.Login) = with(binding) {
        val credential = GoogleAuthProvider.getCredential(state.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this@LoginActivity) { task ->
                if (task.isSuccessful) {  //Login 성공
                    viewModel.setUserInfo(firebaseAuth.currentUser)  //Login 상태 이후 Success 상태로 변경, 정보 설정
                    Log.d("handleLoginState1", "handleLoginState: ")
                } else { //Login 실패
                    viewModel.setUserInfo(null)
                    Application.auth.signOut()
                    Log.d("handleLoginState", "handleLoginState: ")
                }
            }
    }

    /* Google Auth Login Success 상태인 경우 */
    private fun handleSuccessState(state: LoginState.Success) = with(binding) {
        when (state) {
            is LoginState.Success.Registered -> {  //Google Auth 등록된 상태
                handleRegisteredState(state)  //Success.Registered 상태로 변경
                Log.d("handleSuccessState", "handleSuccessState: $state")
            }
            is LoginState.Success.NotRegistered -> {  //Google Auth 미등록된 상태
                Toast.makeText(this@LoginActivity, "NotRegistered", Toast.LENGTH_SHORT).show()
//                groupLoginRequired.isVisible = true
//                groupLogoutRequired.isGone = true
                Log.d("handleSuccessState", "NotRegisteredx: $state")
                binding.tvGoogleNickname.text = ""
                binding.ivProfileImage.setImageDrawable(null)
            }
            else -> Toast.makeText(this@LoginActivity, "Nothing", Toast.LENGTH_SHORT).show()
        }

    }

    /* Google Auth Login Registered 상태인 경우 */
    private fun handleRegisteredState(state: LoginState.Success.Registered) = with(binding) {
        Glide.with(this@LoginActivity)
            .load(state.profileImgeUri.toString())
            .transition(
                DrawableTransitionOptions.withCrossFade(
                    DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
                )
            )
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(ivProfileImage)
        tvGoogleNickname.text = state.userName
    }

    /* Error 상태인 경우 */
    private fun handleErrorState() = with(binding) {
        Toast.makeText(this@LoginActivity, "Error State", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val GOOGLE_CLIENT_ID = BuildConfig.GOOGLE_CLIENT_ID
    }
}
