package kr.co.nottodo.presentation.addsituation.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kr.co.nottodo.data.remote.api.ServicePool
import kr.co.nottodo.data.remote.model.ResponseAddSituationDto
import kr.co.nottodo.presentation.mission.addition.view.AdditionActivity.Companion.BLANK

class AddSituationViewModel : ViewModel() {
    private val addSituationService by lazy { ServicePool.addSituationService }

    val situationText: MutableLiveData<String> = MutableLiveData(BLANK)

    private val situationList: MutableLiveData<ResponseAddSituationDto.ResponseAddSituationDetailDto> =
        MutableLiveData()
    val recentSituationList: LiveData<List<ResponseAddSituationDto.Recent>> =
        Transformations.map(situationList) {
            it.recents
        }
    val recommendSituationList: LiveData<List<ResponseAddSituationDto.Recommend>> =
        Transformations.map(situationList) {
            it.recommends
        }

    fun getSituationList() {
        viewModelScope.launch {
            kotlin.runCatching {
                addSituationService.getResponseAddSituationDto()
            }.fold(onSuccess = { situationList.value = it.data }, onFailure = {})
        }
    }
}