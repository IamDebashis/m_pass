package com.nide.pocketpass.services

import android.app.assist.AssistStructure.ViewNode
import android.content.Context
import android.service.autofill.Dataset

import android.service.autofill.FillResponse
import android.util.Log
import android.view.autofill.AutofillValue
import android.widget.RemoteViews
import coil.size.Dimension
import com.nide.pocketpass.MyApplication
import com.nide.pocketpass.R
import com.nide.pocketpass.data.module.Password
import com.nide.pocketpass.domain.di.UtilitiesEntryPoint
import com.nide.pocketpass.util.AESEncryption.decrypt
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object AutofillHelper {
    private const val TAG = "AutofillHelper"

    private val app by lazy { MyApplication.getInstance() }


    private val autofillRepository by lazy {
        EntryPointAccessors.fromApplication(
            app.applicationContext,
            UtilitiesEntryPoint::class.java
        ).autofillRepository
    }

    private val autofillPassword = mutableListOf<Password>()


    fun initializeRepository(context: ApplicationContext) {


    }


    suspend fun passwordAvailable(name: String, context: Context): Boolean =
        withContext(Dispatchers.IO) {
            val repo = EntryPointAccessors.fromApplication(
                context,
                UtilitiesEntryPoint::class.java
            ).autofillRepository
            val passwords = repo.getAutofillPassword(name)
            return@withContext if (passwords.isNotEmpty()) {
                autofillPassword.clear()
                autofillPassword.addAll(passwords)
                true
            } else {
                false
            }
        }

    private fun createDataSet(
        packageName: String,
        password: Password,
        views: List<ViewNode>
    ): Dataset {
        val datasetBuilder = Dataset.Builder(createRemoteView(packageName, password))
        views.forEach { view ->
            if (view.hint!!.contains("username", true)
                || view.hint!!.contains("email", true)
                || view.hint!!.contains("phone", true)
            ) {
                datasetBuilder.setValue(view.autofillId!!, AutofillValue.forText(password.userId))
            } else if (
                view.hint!!.contains("password", true)
            ) {
                datasetBuilder.setValue(view.autofillId!!, AutofillValue.forText(password.password?.decrypt()))
            }
        }

        return datasetBuilder.build()

    }

    private fun createRemoteView(packageName: String, password: Password): RemoteViews {

        val view = RemoteViews(packageName, R.layout.autofill_layout).apply {
            setTextViewText(R.id.tv_user_name, password.userId)
            setTextViewText(R.id.tv_name, password.name)
            setImageViewBitmap(R.id.iv_icon, password.icon)
        }

        return view
    }

    fun createFillResponse(packageName: String, nodes: List<ViewNode>): FillResponse {

        val responseBuilder = FillResponse.Builder()

        autofillPassword.forEach { password ->
            val dataset = createDataSet(packageName, password, nodes)
            dataset.let(responseBuilder::addDataset)
        }
        return responseBuilder.build()

    }


}