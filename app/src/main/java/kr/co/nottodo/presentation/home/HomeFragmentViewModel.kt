package kr.co.nottodo.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.co.nottodo.data.local.HomeDailyResponse.HomeDaily
import kr.co.nottodo.data.remote.api.HomeService
import kr.co.nottodo.data.remote.api.ServicePool
import kr.co.nottodo.data.remote.model.*
import timber.log.Timber

class HomeFragmentViewModel() : ViewModel() {

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

    //위클리 투두 개수 확인
    private val _responseWeeklyResult: MutableLiveData<List<HomeWeeklyDto.WeeklyPercent>> =
        MutableLiveData()
    val responseWeeklyResult: LiveData<List<HomeWeeklyDto.WeeklyPercent>> get() = _responseWeeklyResult

    //바텀시트 다른날짜에도 하기
    private val _missionAddDate: MutableLiveData<List<ResponseHomeBottomMissionDto.BottomCalender>> =
        MutableLiveData()
    val missionDailyDate: LiveData<List<ResponseHomeBottomMissionDto.BottomCalender>> = _missionAddDate

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
            }, { _errorMessageSituation.value = it.message })
        }
    }

    //홈배너 조회
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

    //홈 위클리 성공조회
    fun homeWeeklyInitServer(startDate: String) {
        viewModelScope.launch {
            runCatching {
                postService.getWeekCount(startDate)
            }.fold({
                _responseWeeklyResult.value = it.data
                Timber.e("weekly$it")
            }, { throwable ->
            })
        }
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
            }, { throwable ->
                _errorMessageSituation.value = throwable.message
            })

        }
    }

    // TODO 윤정
    // 여기가 값이 이상해 ㅇㅇ???
    fun postMissionAnotherDay(id: Int, listDate: List<String> ) {
        viewModelScope.launch {
            runCatching {
                postService.postHomeBottomCalender(
                    id,
                    RequestHomeBottomMissionDto(listDate)
                )
            }.fold({
                Timber.tag("ssong-develop").e("mission ${it.data}")
                _missionAddDate.value = listOf(ResponseHomeBottomMissionDto.BottomCalender(it.data.dates))
            }, {
                Timber.tag("ssong-develop").e("mission $it")
                _errorMessageSituation.value = it.message
            })

        }
    }
}
