package com.nide.mpass.util

import java.util.regex.Pattern


class PasswordStrength(private val password: CharSequence) {

    private var lowerCase = false
    private var upperCase = false
    private var digit = false
    private var specialChar = false


    init {
        lowerCase = password.hasLowerCase()
        upperCase = password.hasUpperCase()
        digit = password.hasDigits()
        specialChar = password.hasSpecialChar()
    }

    fun check(): Int {
        return when (password.length) {
            in 1..4 -> if (lowerCase && upperCase && digit && specialChar) {
                20
            } else {
                10
            }
            in 5..9 -> if (lowerCase && upperCase && digit && specialChar) {
                55
            } else {
                25
            }
            in 10..14 -> if (lowerCase && upperCase && digit && specialChar) {
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