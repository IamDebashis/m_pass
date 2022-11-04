package com.nide.pocketpass.ui.start.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.safetynet.SafetyNet
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.nide.pocketpass.R
import okio.IOException
import java.io.ByteArrayOutputStream
import java.security.SecureRandom

    const val API_KEY ="AIzaSyAXwA9HSp6vg0JmJkhnervYyGIQo9pAUfI"

class LogInActivity : AppCompatActivity() {

    private lateinit var analytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        analytics = Firebase.analytics
        callSafetyNet()

    }


    private fun callSafetyNet(){
        val nonceData = "Safety Net Sample: " + System.currentTimeMillis()
        val nonce: ByteArray? = getRequestNonce(nonceData)
        if (nonce != null) {
            SafetyNet.getClient(this).attest(nonce, API_KEY)
                .addOnSuccessListener(this) {
                    // Indicates communication with the service was successful.
                    // Use response.getJwsResult() to get the result data.
                }
                .addOnFailureListener(this) { e ->
                    // An error occurred while communicating with the service.
                    if (e is ApiException) {
                        // An error with the Google Play services API contains some
                        // additional details.
                        val apiException = e as ApiException

                        // You can retrieve the status code using the
                        // apiException.statusCode property.
                    } else {
                        // A different, unknown type of error occurred.
                        Log.d("app", "Error: " + e.message)
                    }
                }
        }
    }

    private fun getRequestNonce(data: String): ByteArray? {
        val byteStream = ByteArrayOutputStream()
        val bytes = ByteArray(24)
        SecureRandom().nextBytes(bytes)
        try {
            byteStream.write(bytes)
            byteStream.write(data.toByteArray())
        } catch (e: IOException) {
            return null
        }
        return byteStream.toByteArray()
    }


}