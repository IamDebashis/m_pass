package com.nide.pocketpass.domain.repository

import com.nide.pocketpass.data.module.Password

interface AutofillRepository {

    fun getAutofillPassword(name: String): List<Password>

}