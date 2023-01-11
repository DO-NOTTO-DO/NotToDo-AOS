package kr.co.nottodo.presentation.addsituation.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kr.co.nottodo.data.remote.api.ServicePool
import kr.co.nottodo.data.remote.model.ResponseAddSituationDto
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity.Companion.blank
import retrofit2.await

class AddSituationViewModel : ViewModel() {
    private val addSituationService by lazy { ServicePool.addSituationService }

    val situationText: MutableLiveData<String> = MutableLiveData(blank)

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
                addSituationService.getResponseAddSituationDto().await()
            }.fold(onSuccess = { situationList.value = it.data }, onFailure = {})
        }
    }
}