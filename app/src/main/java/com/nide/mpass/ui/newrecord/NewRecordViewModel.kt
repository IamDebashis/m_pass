package com.nide.mpass.ui.newrecord

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nide.mpass.data.module.Category
import com.nide.mpass.data.module.Password
import com.nide.mpass.domain.usecase.GetAllCategoryUseCase
import com.nide.mpass.domain.usecase.SaveNewCategoryUseCase
import com.nide.mpass.domain.usecase.SaveNewPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewRecordViewModel @Inject constructor(
  private val saveNewPasswordUseCase: SaveNewPasswordUseCase,
  private val saveNewCategoryUseCase: SaveNewCategoryUseCase,
  private val getAllCategoryUseCase: GetAllCategoryUseCase
) : ViewModel() {

    val fieldPosition = MutableLiveData<Category>().apply {

    }

    fun saveNewPassword(password: Password) {
        saveNewPasswordUseCase.execute(password)
    }

    fun getAllCategory() = getAllCategoryUseCase.execute()

    fun saveNewCategory(category: Category) {
        saveNewCategoryUseCase.execute(category)
    }


}