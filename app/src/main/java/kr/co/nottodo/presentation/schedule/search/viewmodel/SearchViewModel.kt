package kr.co.nottodo.presentation.schedule.search.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kr.co.nottodo.data.remote.api.ServicePool
import kr.co.nottodo.data.remote.model.ResponseHistoryDto
import kr.co.nottodo.presentation.schedule.addition.view.AdditionActivity.Companion.blank
import retrofit2.await

class SearchViewModel : ViewModel() {
    private val searchService by lazy { ServicePool.searchService }

    val searchBarText: MutableLiveData<String> = MutableLiveData<String>(blank)
    val isSearchBarTextFilled: LiveData<Boolean> = Transformations.map(searchBarText) {
        it != blank
    }


    fun setSearchBarText(text: String) {
        searchBarText.value = text
    }

    private val _getHistoryResult: MutableLiveData<ResponseHistoryDto> = MutableLiveData()
    val getHistoryResult: LiveData<ResponseHistoryDto>
        get() = _getHistoryResult

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getHistory() {
        viewModelScope.launch {
            runCatching {
                searchService.getHistory().await()
            }.fold({ _getHistoryResult.value = it }, { _errorMessage.value = it.message })
        }
    }
}