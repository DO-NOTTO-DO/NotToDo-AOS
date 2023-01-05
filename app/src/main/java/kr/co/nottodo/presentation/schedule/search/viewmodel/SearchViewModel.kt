package kr.co.nottodo.presentation.schedule.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class SearchViewModel: ViewModel() {
    val searchBarText: MutableLiveData<String> = MutableLiveData<String>("")
    val isSearchBarTextFilled: LiveData<Boolean> = Transformations.map(searchBarText) {
        it != ""
    }
}