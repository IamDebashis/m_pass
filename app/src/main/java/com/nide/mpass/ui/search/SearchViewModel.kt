package com.nide.mpass.ui.search

import androidx.lifecycle.*
import com.nide.mpass.domain.usecase.GetAllCategoryUseCase
import com.nide.mpass.domain.usecase.SearchInPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchInPasswordUseCase: SearchInPasswordUseCase,
    private val getAllCategoryUseCase: GetAllCategoryUseCase
) : ViewModel() {
    private val TAG = javaClass.simpleName

    val allCategories = getAllCategoryUseCase.execute()

    private val _query = MutableStateFlow("")
    private val _filter = MutableStateFlow(emptyList<Int>())

    @OptIn(ExperimentalCoroutinesApi::class)
    val filterPassword = _query.flatMapLatest { value ->
        searchInPasswordUseCase.execute(value)
    }.combine(_filter) { passwords, filter ->
        if (filter.isNotEmpty()) {
            passwords.filter { filter.contains(it.categoryId) }
        } else {
            passwords
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000),
        initialValue = emptyList()
    )


    fun filterBy(fields: List<Int>) = viewModelScope.launch {
        _filter.emit(fields)
    }

    fun search(query: String) = viewModelScope.launch {
        _query.emit(query)
    }




}