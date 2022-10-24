package com.nide.pocketpass.util

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


private var inputMethodManager: InputMethodManager? = null
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.hideSoftKeyboard() {
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showSoftKeyboard() {
    if (requestFocus()) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }
}

// show toast
fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


fun Context.getInputMethodManager(): InputMethodManager? =
    inputMethodManager ?: ContextCompat.getSystemService(
        this,
        InputMethodManager::class.java
    ).also { inputMethodManager = it }

fun View.showKeyboard(flags: Int = InputMethodManager.SHOW_IMPLICIT): Boolean {
    requestFocus()
    return context?.getInputMethodManager()?.showSoftInput(this, flags) ?: false
}

fun Context.copyToClipBoard(label: String, text: String) {
    val clipmanager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipmanager.setPrimaryClip(clip)
}

fun EditText.validate(): Boolean {
    return if (this.text.toString().trim().isEmpty()) {
        error = "Field can't be empty"
        false
    } else {
        error = null
        true
    }
}


fun <T> AppCompatActivity.collectLatestFlow(myFlow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            myFlow.collectLatest {
                collect(it)
            }
        }
    }
}

fun <T> Fragment.collectLatestFlow(myFlow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launchWhenStarted {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            myFlow.collectLatest {
                collect(it)
            }
        }
    }
}


fun isNetworkAvailable(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val cap = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
        return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val networks: Array<Network> = cm.allNetworks
        for (n in networks) {
            val nInfo: NetworkInfo? = cm.getNetworkInfo(n)
            if (nInfo != null && nInfo.isConnected) return true
        }
    } else {
        val networks = cm.allNetworkInfo
        for (nInfo in networks) {
            if (nInfo != null && nInfo.isConnected) return true
        }
    }

    return false


}
