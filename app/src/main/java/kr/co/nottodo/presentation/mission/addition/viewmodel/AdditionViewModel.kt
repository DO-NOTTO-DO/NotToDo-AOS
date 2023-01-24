package kr.co.nottodo.presentation.mission.addition.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kr.co.nottodo.data.remote.api.ServicePool
import kr.co.nottodo.data.remote.model.RequestMissionDto
import kr.co.nottodo.data.remote.model.ResponseMissionDto
import kr.co.nottodo.presentation.mission.addition.view.AdditionActivity.Companion.BLANK
import kr.co.nottodo.presentation.mission.addition.view.AdditionActivity.Companion.INPUT
import retrofit2.HttpException
import timber.log.Timber

class AdditionViewModel : ViewModel() {

    private val missionService by lazy { ServicePool.missionService }

    val additionMissionName: MutableLiveData<String> = MutableLiveData(BLANK)
    val isAdditionMissionNameFilled: LiveData<Boolean> = Transformations.map(additionMissionName) {
        it.isNotEmpty()
    }
    val additionActionName: MutableLiveData<String> = MutableLiveData(BLANK)
    val isAdditionActionNameFilled: LiveData<Boolean> = Transformations.map(additionActionName) {
        it.isNotEmpty()
    }

    val additionActionNameFirst: MutableLiveData<String> = MutableLiveData(BLANK)
    private val isAdditionActionNameFirstFilled: LiveData<Boolean> =
        Transformations.map(additionActionNameFirst) {
            it.isNotEmpty()
        }
    val additionActionNameSecond: MutableLiveData<String> = MutableLiveData(BLANK)
    val isAdditionActionNameSecondFilled: LiveData<Boolean> =
        Transformations.map(additionActionNameSecond) {
            it.isNotEmpty()
        }

    val additionSituationName: MutableLiveData<String> = MutableLiveData(INPUT)
    val isAdditionSituationNameFilled: MutableLiveData<Boolean> = MutableLiveData(false)

    val additionGoalName: MutableLiveData<String> = MutableLiveData(BLANK)
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

    //    fun postMission(request: RequestMissionDto) {
//        viewModelScope.launch {
//            kotlin.runCatching {
//                missionService.postMission(request).await()
//            }.fold(
//                onSuccess = {
//                    _responsePostMission.value = it
//                },
//                onFailure = {
//                    _errorMessageMission.value = it.message
//                }
//            )
//        }
//    }
    fun postMission(request: RequestMissionDto) {
        viewModelScope.launch {

            runCatching {
                missionService.postMission(request)
            }
                .onSuccess {
                    Timber.tag("AdditionViewModel").d(it.toString())
                    _responsePostMission.value = it
                }
                .recover { e ->
                    if (e is HttpException) {
                        val raw = e.response()?.errorBody()?.string()
                        Timber.tag("AdditionViewModel").e(raw)
                        raw?.let { errorJson ->
                            val dto = Json.decodeFromString<ErrorResponse>(errorJson)
                            Timber.tag("AdditionViewModel").e(dto.toString())
                            _errorMessageMission.value = dto.message
                        }
                    }
                }.onFailure {
                    Timber.e(it)
                }
        }
    }
}

@Serializable
data class ErrorResponse(
    val status: Int,
    val success: Boolean,
    val message: String
)