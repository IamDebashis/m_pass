package com.nide.mpass.data.local.datasource

import com.nide.mpass.data.local.dao.PasswordDao
import com.nide.mpass.data.module.Password
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class PasswordLocalDataSource(private val passwordDao: PasswordDao) {


    fun getAllPassword() = passwordDao.getAllPassword()


    fun getPasswordById(id: Int) = passwordDao.getPasswordById(id)


    fun insertPassword(password: Password) = CoroutineScope(Dispatchers.IO).launch {
        passwordDao.insertPassword(password)
    }

    fun updatePassword(password: Password) = CoroutineScope(Dispatchers.IO).launch {
        passwordDao.updatePassword(password)
    }

    fun deletePassword(password: Password) = CoroutineScope(Dispatchers.IO).launch {
        passwordDao.deletePassword(password)
    }

    fun deleteAllPasswords() = CoroutineScope(Dispatchers.IO).launch {
        passwordDao.deleteAllPassword()
    }

    fun getPasswordsByCategory(categoryId: Int) = passwordDao.getPasswordByCategory(categoryId)


}