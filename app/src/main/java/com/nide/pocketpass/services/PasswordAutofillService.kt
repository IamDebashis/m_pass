package com.nide.pocketpass.services

import android.os.CancellationSignal
import android.service.autofill.*
import android.util.Log
import kotlinx.coroutines.runBlocking


class PasswordAutofillService : AutofillService() {
    private val TAG = javaClass.simpleName
    override fun onFillRequest(
        request: FillRequest, cancellationSignal: CancellationSignal, callback: FillCallback
    ) {

        val context = request.fillContexts
        val structure = context[context.size - 1].structure
        val packageName = structure.activityComponent.packageName


        val name = packageName.split(".")[1]
        var passPresent: Boolean
        runBlocking {
            passPresent = AutofillHelper.passwordAvailable(
                name,
                this@PasswordAutofillService.applicationContext
            )
        }
        try {
            if (passPresent) {
                val parser = StructureParser(structure)
                parser.parseForFill()
                val fillResponse =
                    AutofillHelper.createFillResponse(this.packageName, parser.autofillFields)
                callback.onSuccess(fillResponse)
            } else {
                callback.onFailure("No Password present")
            }

        } catch (e: Exception) {
            callback.onFailure("something went wrong!")
        }


    }


    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
        
    }



}

