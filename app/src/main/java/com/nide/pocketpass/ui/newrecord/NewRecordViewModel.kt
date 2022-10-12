package com.nide.pocketpass.ui.newrecord

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nide.pocketpass.data.module.Category
import com.nide.pocketpass.data.module.Password
import com.nide.pocketpass.domain.usecase.GetAllCategoryUseCase
import com.nide.pocketpass.domain.usecase.SaveNewCategoryUseCase
import com.nide.pocketpass.domain.usecase.SaveNewPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewRecordViewModel @Inject constructor(
  private val saveNewPasswordUseCase: SaveNewPasswordUseCase,
  private val saveNewCategoryUseCase: SaveNewCategoryUseCase,
  private val getAllCategoryUseCase: GetAllCategoryUseCase
) : ViewModel() {

    val fieldPosition = MutableLiveData<Category>()

    fun saveNewPassword(password: Password) {
        saveNewPasswordUseCase.execute(password)
    }

    fun getAllCategory() = getAllCategoryUseCase.execute()

    fun saveNewCategory(category: Category) {
        saveNewCategoryUseCase.execute(category)
    }


}