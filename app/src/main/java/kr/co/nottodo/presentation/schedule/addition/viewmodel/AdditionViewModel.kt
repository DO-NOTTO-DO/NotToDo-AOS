package kr.co.nottodo.presentation.schedule.addition.viewmodel

import androidx.lifecycle.*

class AdditionViewModel : ViewModel() {
    val additionMissionName: MutableLiveData<String> = MutableLiveData("")
    val isAdditionMissionNameFilled: LiveData<Boolean> = Transformations.map(additionMissionName) {
        it.isNotEmpty()
    }
    val additionActionName: MutableLiveData<String> = MutableLiveData("")
    val isAdditionActionNameFilled: LiveData<Boolean> = Transformations.map(additionActionName) {
        it.isNotEmpty()
    }

    val additionActionName1: MutableLiveData<String> = MutableLiveData("")
    val isAdditionActionName1Filled: LiveData<Boolean> = Transformations.map(additionActionName1) {
        it.isNotEmpty()
    }
    val additionActionName2: MutableLiveData<String> = MutableLiveData("")

    val additionSituationName: MutableLiveData<String> = MutableLiveData("입력하기")
    val isAdditionSituationNameSuit: LiveData<Boolean> =
        Transformations.map(additionSituationName) {
            it != "입력하기"
        }

    val additionGoalName: MutableLiveData<String> = MutableLiveData("")
    val isAdditionGoalNameFilled: LiveData<Boolean> = Transformations.map(additionGoalName) {
        it.isNotEmpty()
    }

    val isBtnSuitConditions: MediatorLiveData<Boolean> = MediatorLiveData()

    init {
        isBtnSuitConditions.addSource(isAdditionMissionNameFilled) {
            isBtnSuitConditions.value = _isBtnSuitConditions()
        }
        isBtnSuitConditions.addSource(isAdditionActionName1Filled) {
            isBtnSuitConditions.value = _isBtnSuitConditions()
        }
        isBtnSuitConditions.addSource(isAdditionSituationNameSuit) {
            isBtnSuitConditions.value = _isBtnSuitConditions()
        }
        isBtnSuitConditions.addSource(isAdditionGoalNameFilled) {
            isBtnSuitConditions.value = _isBtnSuitConditions()
        }
    }

    private fun _isBtnSuitConditions(): Boolean {
        return (isAdditionMissionNameFilled.value == true
                && isAdditionActionName1Filled.value == true
                && isAdditionSituationNameSuit.value == true
                && isAdditionGoalNameFilled.value == true)
    }
}