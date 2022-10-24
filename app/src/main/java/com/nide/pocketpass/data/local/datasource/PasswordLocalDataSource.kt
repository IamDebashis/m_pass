package com.nide.pocketpass.data.local.datasource

import com.nide.pocketpass.data.local.dao.PasswordDao
import com.nide.pocketpass.data.module.Password
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    fun deltePasswordById(id: Int) = CoroutineScope(Dispatchers.IO).launch {
        passwordDao.deltePaawordById(id)
    }

    fun getPasswordsByCategory(categoryId: Int) = passwordDao.getPasswordByCategory(categoryId)

    fun getPasswordByIdWithCategory(id: Int) = passwordDao.getPasswordWithCategory(id)

    fun getPasswordBySearch(query: String) = passwordDao.getPasswordBySearch("%$query%")

    fun getAllPasswordWithCategory() = passwordDao.getAllPasswordWithCategory()

    fun getAutofillPassword(name : String) = passwordDao.getAutofillPassword("%$name%")

}