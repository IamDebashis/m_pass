package com.nide.pocketpass.util.password_util

import android.util.Log
import com.nide.pocketpass.data.module.Password
import com.nide.pocketpass.util.AESEncryption.decrypt

class PasswordUtil {
    var totalWeek: Int = 0
        get
        private set
    var totalSafe: Int = 0
        get
        private set
    var totalRisk: Int = 0
        get
        private set

    var totalSecure: Int = 0

    fun getTotalPassword() = totalWeek + totalSafe + totalRisk



    fun getSecurePass(): Int {
        return try {
          val percent =  ( totalSecure.toFloat() / getTotalPassword()) * 100
            percent.toInt()
        } catch (e: ArithmeticException) {
            0
        }
    }

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
        totalSecure = totalWeek + totalSafe
        return totalSecure
    }


}