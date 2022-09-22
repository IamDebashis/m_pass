package com.nide.mpass.password_util

import com.nide.mpass.R
import com.nide.mpass.data.module.Password
import com.nide.mpass.util.AESEncryption.decrypt

class PasswordUtil {
    var totalWeek: Int = 0
        private set
    var totalSafe: Int = 0
        private set
    var totalRisk: Int = 0
        private set

    suspend fun checkSequrePasswords(passwordList: List<Password>): Int {

        passwordList.forEach { password ->
            val strength = PasswordStrength(password.password?.decrypt() ?: "").check()
            when (strength) {
                in 1..30 -> totalRisk++
                in 31..60 -> totalWeek++
                in 61..100 -> totalSafe++
            }
        }
        return totalWeek + totalSafe
    }


}