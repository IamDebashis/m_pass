package com.nide.pocketpass.ui.analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nide.pocketpass.domain.usecase.GetAllPasswordUseCase
import com.nide.pocketpass.util.password_util.PasswordUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    private val getAllPasswordUseCase: GetAllPasswordUseCase
) : ViewModel() {

   private val filterRange = MutableStateFlow(0..100)
    val filterBy = filterRange.asStateFlow()
  private  val passwords = getAllPasswordUseCase.execute()

    val analysis = passwords.map { passwordList ->
        val util = PasswordUtil()
        util.checkSecurePasswords(passwordList)
        util
    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            PasswordUtil()
        )

    val sortPassword = passwords.combine(filterRange) { password, order->
      val sortPass =  password.filter { it.strength in  order}
        sortPass
    }.flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
           emptyList()
        )


    fun setFilterRange(i : IntRange) = viewModelScope.launch{
        filterRange.emit(i)
    }

}