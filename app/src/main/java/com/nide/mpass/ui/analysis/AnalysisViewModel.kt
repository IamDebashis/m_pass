package com.nide.mpass.ui.analysis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nide.mpass.domain.usecase.GetAllPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    private val getAllPasswordUseCase: GetAllPasswordUseCase
) : ViewModel() {

    val passwords = getAllPasswordUseCase.execute()


}