package kr.co.nottodo.presentation.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    val isLogged: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
}