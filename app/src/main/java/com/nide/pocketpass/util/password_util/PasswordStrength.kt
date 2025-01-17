package com.nide.pocketpass.util.password_util

import java.util.regex.Pattern


class PasswordStrength(private val password: CharSequence) {

    private var lowerCase = false
    private var upperCase = false
    private var digit = false
    private var specialChar = false
    var strength = 0
        private set


    companion object{
        fun mapStrength(s: Int): String {
            return when (s) {
                in 1..30 -> "Risk"
                in 31..60 -> "Week"
                in 61..100 -> "Safe"
                else -> ""
            }
        }


    }

    fun mapStrength(): String {
        return when (strength) {
            in 1..30 -> "Risk"
            in 31..60 -> "Week"
            in 61..100 -> "Safe"
            else -> ""
        }
    }

    init {
        lowerCase = password.hasLowerCase()
        upperCase = password.hasUpperCase()
        digit = password.hasDigits()
        specialChar = password.hasSpecialChar()
    }

    fun check(): Int {
        strength = when (password.length) {
            in 1..4 -> if (lowerCase && upperCase && digit && specialChar) {
                20
            } else {
                10
            }
            in 5..8 -> if (lowerCase && upperCase && digit && specialChar) {
                55
            } else {
                25
            }
            in 9..14 -> if (lowerCase && upperCase && digit && specialChar) {
                65
            } else {
                35
            }
            in 15..30 -> if (lowerCase && upperCase && digit && specialChar) {
                80
            } else {
                40
            }
            in 31..64 -> if (lowerCase && upperCase && digit && specialChar) {
                100
            } else {
                50
            }
            else -> {
                0
            }
        }
        return strength
    }



    private fun CharSequence.hasUpperCase(): Boolean {
        val pattern = Pattern.compile("[A-Z]")
        val hasUpperCase = pattern.matcher(this)
        return hasUpperCase.find()
    }

    private fun CharSequence.hasLowerCase(): Boolean {
        val pattern = Pattern.compile("[a-z]")
        val hasLowerCase = pattern.matcher(this)
        return hasLowerCase.find()
    }

    private fun CharSequence.hasDigits(): Boolean {
        val pattern = Pattern.compile("[0-9]")
        val hasDigits = pattern.matcher(this)
        return hasDigits.find()
    }


    private fun CharSequence.hasSpecialChar(): Boolean {
        val pattern = Pattern.compile("[@#=+!£/|/$%&?]")
        val hasUpperCase = pattern.matcher(this)
        return hasUpperCase.find()
    }


}