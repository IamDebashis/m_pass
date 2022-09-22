package com.nide.mpass.ui.update

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nide.mpass.data.module.Category
import com.nide.mpass.data.module.Password
import com.nide.mpass.data.truple.PasswordTuple
import com.nide.mpass.domain.usecase.GetAllCategoryUseCase
import com.nide.mpass.domain.usecase.GetPasswordByIdWithCategory
import com.nide.mpass.domain.usecase.SaveNewCategoryUseCase
import com.nide.mpass.domain.usecase.UpdatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val getPasswordByIdWithCategory: GetPasswordByIdWithCategory,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val saveNewCategoryUseCase: SaveNewCategoryUseCase,
) : ViewModel() {

    val fieldPosition = MutableLiveData<Category>()

    fun updatePassword(password: Password) {
        updatePasswordUseCase.execute(password)
    }

    fun getPassword(id: Int) = getPasswordByIdWithCategory.execute(id)

    fun getAllCategory() = getAllCategoryUseCase.execute()

    fun saveNewCategory(category: Category) {
        saveNewCategoryUseCase.execute(category)
    }


}