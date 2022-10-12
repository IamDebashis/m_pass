package com.nide.pocketpass.ui.update

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nide.pocketpass.data.module.Category
import com.nide.pocketpass.data.module.Password
import com.nide.pocketpass.data.truple.PasswordTuple
import com.nide.pocketpass.domain.usecase.GetAllCategoryUseCase
import com.nide.pocketpass.domain.usecase.GetPasswordByIdWithCategory
import com.nide.pocketpass.domain.usecase.SaveNewCategoryUseCase
import com.nide.pocketpass.domain.usecase.UpdatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(
    private val getPasswordByIdWithCategory: GetPasswordByIdWithCategory,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val saveNewCategoryUseCase: SaveNewCategoryUseCase,
) : ViewModel() {

    var password : PasswordTuple? = null
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