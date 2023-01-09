package kr.co.nottodo.presentation.toplevel.recommendation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.co.nottodo.data.remote.api.ServicePool
import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategoryListDto
import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategorySituationDto
import retrofit2.await
import retrofit2.http.Path

class RecommendationViewModel : ViewModel() {
    private val recommendationCategoryListService by lazy { ServicePool.recommendationCategoryListService }
    private val recommendationCategorySituationService by lazy { ServicePool.recommendationCategorySituationService }

    //ResponseRecommendationCategoryListDto
    //List<ResponseRecommendationCategoryListDto.CategoryList>
//
    private val _categorySituation: MutableLiveData<ResponseRecommendationCategorySituationDto> =
        MutableLiveData()
    val categorySituation: LiveData<ResponseRecommendationCategorySituationDto>
        get() = _categorySituation

    private val _errorCategorySituation: MutableLiveData<String> = MutableLiveData()
    val errorCategorySituation: LiveData<String>
        get() = _errorCategorySituation

    private val _categoryList: MutableLiveData<ResponseRecommendationCategoryListDto> =
        MutableLiveData()
    val categoryList: LiveData<ResponseRecommendationCategoryListDto>
        get() = _categoryList

    private val _errorcategoryList: MutableLiveData<String> = MutableLiveData()
    val errorcategoryList: LiveData<String>
        get() = _errorcategoryList


    fun getRecommendationCategorySitatuationService() {
        viewModelScope.launch {
            kotlin.runCatching {
                recommendationCategorySituationService.getCategorySituation().await()
            }.fold(onSuccess = { _categorySituation.value = it },
                onFailure = { _errorCategorySituation.value = it.message })
        }
    }

    fun ResponseRecommendationCategoryListService(
        id: Int
    ) {
        viewModelScope.launch {
            kotlin.runCatching {
                recommendationCategoryListService.getCategoryList(id).await()
            }.fold(onSuccess = { _categoryList.value = it },
                onFailure = { _errorcategoryList.value = it.message })
        }
    }
}