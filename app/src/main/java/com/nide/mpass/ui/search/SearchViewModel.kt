package com.nide.mpass.ui.search

import androidx.lifecycle.*
import com.nide.mpass.data.module.Password
import com.nide.mpass.domain.usecase.GetAllCategoryUseCase
import com.nide.mpass.domain.usecase.SearchInPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchInPasswordUseCase: SearchInPasswordUseCase,
    private val getAllCategoryUseCase: GetAllCategoryUseCase
) : ViewModel() {

    val allCategories = getAllCategoryUseCase.execute()

    private val _query = MutableLiveData<String>()
    private val _filterBy = MutableLiveData<List<Int>>()
    private val filterPasswordResult = MutableLiveData<List<Password>>()

    val searchPassword = _query.switchMap {
      searchInPasswordUseCase.execute(it).asLiveData()
    }



    val filterPasswords  = searchPassword.switchMap { passwords ->
      val filtered =  passwords.filter { _filterBy.value?.contains(it.categoryId) ?: true   }
        filterPasswordResult.postValue(filtered)
        filterPasswordResult
    }

    fun fiterBy(fields: List<Int>){
        _filterBy.postValue(fields)
    }

    fun search(query: String){
        _query.postValue(query)
    }


}