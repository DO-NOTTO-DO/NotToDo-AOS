package kr.co.nottodo.presentation.achievement.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kr.co.nottodo.data.remote.api.ServicePool
import kr.co.nottodo.data.remote.model.ResponseAchievementDto
import kr.co.nottodo.data.remote.model.ResponseMissionStatisticDto
import kr.co.nottodo.data.remote.model.ResponseSituationStatisticDto
import retrofit2.await

class AchievementViewModel : ViewModel() {
    private val statisticService by lazy { ServicePool.statisticService }
    private val achievementService by lazy { ServicePool.achievementService }

    private val _responseMission: MutableLiveData<ResponseMissionStatisticDto> = MutableLiveData()
    val responseMission: LiveData<ResponseMissionStatisticDto>
        get() = _responseMission

    private val _errorMessageMission: MutableLiveData<String> = MutableLiveData()
    val errorMessageMission: LiveData<String>
        get() = _errorMessageMission

    private val _responseSituation: MutableLiveData<ResponseSituationStatisticDto> =
        MutableLiveData()
    val responseSituation: LiveData<ResponseSituationStatisticDto>
        get() = _responseSituation

    private val _errorMessageSituation: MutableLiveData<String> = MutableLiveData()
    val errorMessageSituation: LiveData<String>
        get() = _errorMessageSituation

    val isDataCome: MediatorLiveData<Boolean> = MediatorLiveData()

    private val _responseAchievement: MutableLiveData<ResponseAchievementDto> = MutableLiveData()
    val responseAchievement: LiveData<ResponseAchievementDto> get() = _responseAchievement

    private val _errorMessageAchievement: MutableLiveData<String> =
        MutableLiveData()
    val errorMessageAchievement: LiveData<String> get() = _errorMessageAchievement

    init {
        isDataCome.addSource(responseMission) {
            isDataCome.value = _isDataCame()
        }
        isDataCome.addSource(responseSituation) {
            isDataCome.value = _isDataCame()
        }
    }

    private fun _isDataCame(): Boolean {
        return (responseMission.value?.data?.isNotEmpty() == true
                && responseSituation.value?.data?.isNotEmpty() == true)
    }

    fun getMissionStatistic() {
        viewModelScope.launch {
            kotlin.runCatching {
                statisticService.getMissionStatistic().await()
            }.fold(onSuccess = { _responseMission.value = it },
                onFailure = { _errorMessageMission.value = it.message })
        }
    }

    fun getSituationStatistic() {
        viewModelScope.launch {
            kotlin.runCatching {
                statisticService.getSituationStatistic().await()
            }.fold(onSuccess = { _responseSituation.value = it },
                onFailure = { _errorMessageSituation.value = it.message })
        }
    }

    fun getAchievement(month: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                achievementService.getAchievement(month).await()
            }.fold(onSuccess = { _responseAchievement.value = it },
                onFailure = { _errorMessageAchievement.value = it.message })
        }
    }
}