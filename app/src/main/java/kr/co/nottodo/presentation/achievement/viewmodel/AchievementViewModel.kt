package kr.co.nottodo.presentation.achievement.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.co.nottodo.data.remote.api.ServicePool
import kr.co.nottodo.data.remote.model.ResponseMissionStatisticDto
import kr.co.nottodo.data.remote.model.ResponseSituationStatisticDto
import retrofit2.await

class AchievementViewModel : ViewModel() {
    private val statisticService by lazy { ServicePool.statisticService }

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

    fun getMissionStatistic() {
        viewModelScope.launch {
            kotlin.runCatching {
                statisticService.getMissionStatistic().await()
            }.fold({ _responseMission.value = it }, { _errorMessageMission.value = it.message })
        }
    }

    fun getSituationStatistic() {
        viewModelScope.launch {
            kotlin.runCatching {
                statisticService.getSituationStatistic().await()
            }.fold({ _responseSituation.value = it }, { _errorMessageSituation.value = it.message })
        }
    }

}