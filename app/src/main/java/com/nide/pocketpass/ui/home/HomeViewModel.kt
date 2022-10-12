package com.nide.pocketpass.ui.home

import androidx.lifecycle.ViewModel
import com.nide.pocketpass.domain.usecase.GetAllPasswordsWithCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllPasswordUseCase: GetAllPasswordsWithCategory
) : ViewModel() {



     val passwords = getAllPasswordUseCase.execute()

    /* val grouppass = passwords.map {
         it.groupBy { it.categoryId }
     }

*/


}