package kr.co.nottodo.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.co.nottodo.data.local.HomeDailyResponse.HomeDaily
import kr.co.nottodo.data.remote.api.HomeService
import kr.co.nottodo.data.remote.api.ServicePool
import kr.co.nottodo.data.remote.model.RequestHomeMissionCheck
import kr.co.nottodo.data.remote.model.ResponseHomeBannerDto
import kr.co.nottodo.data.remote.model.ResponseHomeMissionCheckDto
import timber.log.Timber

class HomeFragmentViewModel() : ViewModel() {

    private val _missionDailyDate: MutableLiveData<List<HomeDaily.Action>> = MutableLiveData()
    val missionDailyDate: LiveData<List<HomeDaily.Action>> = _missionDailyDate

    //리사이클러뷰 missionId값 조회
    private val _missionId: MutableLiveData<Int> = MutableLiveData()
    val missionId: LiveData<Int> = _missionId

    //리사이클러뷰 조회
    private val _responseResult: MutableLiveData<List<HomeDaily>> = MutableLiveData()
    val responseResult: LiveData<List<HomeDaily>> get() = _responseResult

    //홈 배너 조회
    private val _responseBannerResult: MutableLiveData<ResponseHomeBannerDto.HomeBanner> =
        MutableLiveData()
    val responseBannerResult: LiveData<ResponseHomeBannerDto.HomeBanner> get() = _responseBannerResult

    //투두 체크확인
    private val _responseCheckResult: MutableLiveData<ResponseHomeMissionCheckDto.HomeMissionCheckDto> =
        MutableLiveData()
    val responseCheckResult: LiveData<ResponseHomeMissionCheckDto.HomeMissionCheckDto> get() = _responseCheckResult

    private val _errorMessageSituation: MutableLiveData<String> = MutableLiveData()
    val errorMessageSituation: LiveData<String>
        get() = _errorMessageSituation

    private val postService: HomeService = ServicePool.HomeService

    //home daily 조회하는거
    fun initServer(date: String) {
        viewModelScope.launch {
            runCatching {
                postService.getHomeDaily(date)
            }.fold({
                _responseResult.value = it.data
//                Timber.e("asdf ${_responseResult.value}")
            }, { _errorMessageSituation.value = it.message })
        }
    }

    fun homeBannerInitServer() {
        viewModelScope.launch {
            runCatching {
                postService.getBanner()
            }.fold({
                _responseBannerResult.value = it.data
            }, {
                _errorMessageSituation.value = it.message
            })

        }
    }

    fun setMissionId(id:Int){
        _missionId.value = id
    }

    //patch
    fun responseHomeMissionCheck(id: Int, completionStatus: String) {
        viewModelScope.launch {
            runCatching {
                postService.patchHomeTodoCheck(
                    id, RequestHomeMissionCheck(
                        completionStatus
                    )
                )
            }.fold({
                _responseCheckResult.value = it.data
                Timber.d("mission ${it.data}")
            }, {
                _errorMessageSituation.value = it.message
            })

        }
    }
}
