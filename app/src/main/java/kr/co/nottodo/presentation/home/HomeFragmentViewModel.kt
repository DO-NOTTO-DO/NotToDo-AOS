package kr.co.nottodo.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.co.nottodo.data.local.HomeDailyResponse.HomeDaily
import kr.co.nottodo.data.remote.api.HomeService
import kr.co.nottodo.data.remote.api.ServicePool
import timber.log.Timber

class HomeFragmentViewModel() : ViewModel() {

//    private val _missionDailyDate: MutableLiveData<HomeDaily> = MutableLiveData()
//    val missionDailyDate: LiveData<HomeDaily> = _missionDailyDate

    //리사이클러뷰 조회
    private val _responseResult: MutableLiveData<List<HomeDaily>> =
        MutableLiveData()
    val responseResult: LiveData<List<HomeDaily>> get() = _responseResult

    private val _errorMessageSituation: MutableLiveData<String> = MutableLiveData()
    val errorMessageSituation: LiveData<String>
        get() = _errorMessageSituation

    private val postService: HomeService = ServicePool.HomeService

    fun initServer(date: String) {
        viewModelScope.launch {
            Timber.e("ViewModel asdfasdf")
            runCatching {
                postService.getHomeDaily(date)
            }.fold({
                _responseResult.value = it.data
                Timber.e("asdf ${_responseResult.value}")
            }, { _errorMessageSituation.value = it.message })

        }
    }
}