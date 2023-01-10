package kr.co.nottodo.presentation.toplevel.recommendation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kr.co.nottodo.data.remote.api.ServicePool
import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategoryListDto
import kr.co.nottodo.presentation.toplevel.recommendation.data.responsedto.ResponseRecommendationCategorySituationDto
import timber.log.Timber

class RecommendationViewModel : ViewModel() {
    private val recommendationCategoryListService by lazy { ServicePool.recommendationCategoryListService }
    private val recommendationCategorySituationService by lazy { ServicePool.recommendationCategorySituationService }

    private val _categorySituation: MutableLiveData<List<ResponseRecommendationCategorySituationDto.CategorySituation>> =
        MutableLiveData()
    val categorySituation: LiveData<List<ResponseRecommendationCategorySituationDto.CategorySituation>>
        get() = _categorySituation

    private val _categoryList: MutableLiveData<List<ResponseRecommendationCategoryListDto.CategoryList>> =
        MutableLiveData()
    val categoryList: LiveData<List<ResponseRecommendationCategoryListDto.CategoryList>>
        get() = _categoryList

    private val _categoryId: MutableLiveData<Int> = MutableLiveData()
    val categoryId: LiveData<Int> = _categoryId

    init {
        viewModelScope.launch {
            runCatching {
                recommendationCategorySituationService.getCategorySituation()
            }.onSuccess { response ->
                when (response.status) {
                    200 -> {
                        _categorySituation.value = response.data
                    }
                    else -> {
                        _categorySituation.value = emptyList()
                    }
                }
            }.onFailure { throwable ->
                // 아예 실패
                Timber.e(throwable)
                _categorySituation.value = emptyList()
            }
        }
    }

    fun setCategoryId(id: Int) {
        _categoryId.value = id
    }

    fun getRecommendList(id: Int) {
        viewModelScope.launch {
            runCatching {
                recommendationCategoryListService.getCategoryList(id)
            }.onSuccess { response ->
                when (response.status) {
                    200 -> {
                        _categoryList.value = response.data
                    }
                    else -> {
                        _categoryList.value = emptyList()
                    }
                }
            }.onFailure { throwable ->
                Timber.e(throwable)
                _categoryList.value = emptyList()
            }
        }
    }
}