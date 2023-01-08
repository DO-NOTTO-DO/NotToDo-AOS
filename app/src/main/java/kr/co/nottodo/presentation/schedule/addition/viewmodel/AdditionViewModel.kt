package kr.co.nottodo.presentation.schedule.addition.viewmodel

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kr.co.nottodo.data.remote.api.ServicePool
import kr.co.nottodo.data.remote.model.RequestMissionDto
import kr.co.nottodo.data.remote.model.ResponseMissionDto
import retrofit2.await

class AdditionViewModel : ViewModel() {

    private val missionService by lazy { ServicePool.missionService }

    val additionMissionName: MutableLiveData<String> = MutableLiveData("")
    val isAdditionMissionNameFilled: LiveData<Boolean> = Transformations.map(additionMissionName) {
        it.isNotEmpty()
    }
    val additionActionName: MutableLiveData<String> = MutableLiveData("")
    val isAdditionActionNameFilled: LiveData<Boolean> = Transformations.map(additionActionName) {
        it.isNotEmpty()
    }

    val additionActionNameFirst: MutableLiveData<String> = MutableLiveData("")
    private val isAdditionActionNameFirstFilled: LiveData<Boolean> =
        Transformations.map(additionActionNameFirst) {
            it.isNotEmpty()
        }
    val additionActionNameSecond: MutableLiveData<String> = MutableLiveData("")
    val isAdditionActionNameSecondFilled: LiveData<Boolean> =
        Transformations.map(additionActionNameSecond) {
            it.isNotEmpty()
        }

    val additionSituationName: MutableLiveData<String> = MutableLiveData("입력하기")
    private val isAdditionSituationNameSuit: LiveData<Boolean> =
        Transformations.map(additionSituationName) {
            it != "입력하기"
        }
    val isAdditionSituationNameFilled: MutableLiveData<Boolean> = MutableLiveData(false)

    val additionGoalName: MutableLiveData<String> = MutableLiveData("")
    val isAdditionGoalNameFilled: LiveData<Boolean> = Transformations.map(additionGoalName) {
        it.isNotEmpty()
    }

    val isBtnSuitConditions: MediatorLiveData<Boolean> = MediatorLiveData()

    init {
        isBtnSuitConditions.addSource(isAdditionMissionNameFilled) {
            isBtnSuitConditions.value = _isBtnSuitConditions()
        }
        isBtnSuitConditions.addSource(isAdditionActionNameFirstFilled) {
            isBtnSuitConditions.value = _isBtnSuitConditions()
        }
        isBtnSuitConditions.addSource(isAdditionSituationNameFilled) {
            isBtnSuitConditions.value = _isBtnSuitConditions()
        }
        isBtnSuitConditions.addSource(isAdditionGoalNameFilled) {
            isBtnSuitConditions.value = _isBtnSuitConditions()
        }
    }

    val additionDate: MutableLiveData<String> = MutableLiveData()

    private fun _isBtnSuitConditions(): Boolean {
        return (isAdditionMissionNameFilled.value == true
                && isAdditionActionNameFirstFilled.value == true
                && isAdditionSituationNameFilled.value == true
                && isAdditionGoalNameFilled.value == true)
    }

    private val _responsePostMission: MutableLiveData<ResponseMissionDto> = MutableLiveData()
    val responsePostMission: LiveData<ResponseMissionDto> get() = _responsePostMission

    private val _errorMessageMission: MutableLiveData<String> = MutableLiveData()
    val errorMessageMission: LiveData<String>
        get() = _errorMessageMission

    fun postMission(request: RequestMissionDto) {
        viewModelScope.launch {
            kotlin.runCatching {
                missionService.postMission(request).await()
            }.fold(
                onSuccess = {
                    _responsePostMission.value = it
                },
                onFailure = {
                    _errorMessageMission.value = it.message
                }
            )
        }
    }

}