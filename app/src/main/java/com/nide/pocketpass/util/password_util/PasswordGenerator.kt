package com.nide.pocketpass.util.password_util


class PasswordGenerator constructor(private val builder: PasswordBuilder, private val length: Int) {

    private val charList = ArrayList<Int>()
    private val password = StringBuilder()

    init {
        if (builder.isUppercase) charList.add(0)
        if (builder.isLowercase) charList.add(1)
        if (builder.isDigit) charList.add(2)
        if (builder.isSpecialChar) charList.add(3)
    }

    fun generatePassword() : String {
        if(charList.isNotEmpty()) {
            for (i in 1..length) {
                when (charList.random()) {
                    0 -> password.append(('A'..'Z').random())
                    1 -> password.append(('a'..'z').random())
                    2 -> password.append(('0'..'9').random())
                    3 -> password.append(
                        listOf(
                            '!', '@', '#', '$', '%', '&', '*', '+', '=', '-', '~', '?', '/', '_'
                        ).random()
                    )
                }

            }
        }
        return password.toString()

    }


}

class PasswordBuilder(private val length: Int) {

    var isLowercase = false
        private set
    var isUppercase = false
        private set
    var isDigit = false
        private set
    var isSpecialChar = false
        private set


    fun isLowerCase(enable: Boolean) = apply {
        this.isLowercase = enable
    }

    fun isUpperCase(enable: Boolean) = apply {
        this.isUppercase = enable
    }

    fun isDigit(enable: Boolean) = apply {
        this.isDigit = enable
    }

    fun isSpecialChar(enable: Boolean) = apply {
        this.isSpecialChar = enable
    }

    fun build(): PasswordGenerator {
        return PasswordGenerator(this, length)
    }

}