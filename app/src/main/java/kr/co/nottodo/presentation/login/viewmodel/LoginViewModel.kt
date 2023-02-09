package kr.co.nottodo.presentation.login.viewmodel

import LoginState
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kr.co.nottodo.Application

class LoginViewModel : ViewModel() {
    val isLogged: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)

    private var _loginStateLiveData = MutableLiveData<LoginState>(LoginState.UnInitialized)
    val loginStateLiveData: LiveData<LoginState> = _loginStateLiveData

    fun fetchData(tokenId: String?): Job = viewModelScope.launch {
        setState(LoginState.Loading)
        tokenId?.let {
            setState(
                LoginState.Login(it)
            )
            Log.d("viemodel", "fetchData: $tokenId null이 아닐경우")
        } ?: kotlin.run {
            setState(
                LoginState.Success.NotRegistered
            )
            Log.d("viemodel", "fetchData: $tokenId null일 경우")
        }
    }

    /* 로그인 성공 result 받았을 떄 호출 */
    fun saveToken(idToken: String, account: GoogleSignInAccount) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            fetchData(idToken)
        }
        Application.email = account.email
    }

    /* 로그인 성공 후 정보 설정 */
    fun setUserInfo(firebaseUser: FirebaseUser?) = viewModelScope.launch {
        firebaseUser?.let { user ->
            setState(
                LoginState.Success.Registered(
                    user.displayName ?: "익명",
                    user.photoUrl!!,
                    user.email?:"email"
                )
            )
            Log.d("viemodel", "setUserInfo: auth로그인 성공")
        } ?: kotlin.run {
            setState(LoginState.Success.NotRegistered)
            Log.d("viemodel", "setUserInfo: auth로그인 실패")
        }
    }

    /* 로그아웃 버튼 클릭 시 호출 */
    fun signOut() = viewModelScope.launch {
        fetchData(null)
        Log.d("signOut", "signOut: 로그아웃 버튼 클릭 시 호출")
    }

    private fun setState(state: LoginState) {
        _loginStateLiveData.postValue(state)
        Log.d("viemodel", "setState: ${loginStateLiveData}")
    }
}