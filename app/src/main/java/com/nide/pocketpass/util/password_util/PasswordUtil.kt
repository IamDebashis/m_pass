package com.nide.pocketpass.util.password_util

import com.nide.pocketpass.data.module.Password
import com.nide.pocketpass.util.AESEncryption.decrypt

class PasswordUtil {
    var totalWeek: Int = 0
        private set
    var totalSafe: Int = 0
        private set
    var totalRisk: Int = 0
        private set

    var totalSequre: Int = 0

    fun checkSecurePasswords(passwordList: List<Password>): Int {

        passwordList.forEach { password ->
            var strength = 0
            if (password.password?.isNotBlank() == true) {
                strength = PasswordStrength(password.password.decrypt() ?: "").check()
            }
            when (strength) {
                in 1..30 -> totalRisk++
                in 31..60 -> totalWeek++
                in 61..100 -> totalSafe++
            }
        }
        totalSequre = totalWeek + totalSafe
        return totalSequre
    }


}