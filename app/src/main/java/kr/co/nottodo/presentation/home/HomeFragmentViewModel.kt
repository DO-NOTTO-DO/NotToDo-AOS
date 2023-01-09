package kr.co.nottodo.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.co.nottodo.data.local.HomeDaily
import kr.co.nottodo.data.remote.api.HomeService
import kr.co.nottodo.data.remote.api.ServicePool
import kr.co.nottodo.data.remote.response.ResponseWrapper
import retrofit2.await

class HomeFragmentViewModel() : ViewModel() {

//    private val _missionDailyDate: MutableLiveData<HomeDaily> = MutableLiveData()
//    val missionDailyDate: LiveData<HomeDaily> = _missionDailyDate

    //리사이클러뷰 조회
    private val _responseResult: MutableLiveData<ResponseWrapper<HomeDaily>> = MutableLiveData()
    val responseResult: LiveData<ResponseWrapper<HomeDaily>> get() = _responseResult

    private val _errorMessageSituation: MutableLiveData<String> = MutableLiveData()
    val errorMessageSituation: LiveData<String>
        get() = _errorMessageSituation

    private val postService: HomeService = ServicePool.HomeService

    fun initServer(date: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                postService.getHomeDaily(date).await()
            }.fold({ _responseResult.value = it }, { _errorMessageSituation.value = it.message })
        }
    }

    private fun initHomeDaily() {

    }
}