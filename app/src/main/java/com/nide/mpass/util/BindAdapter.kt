package com.nide.mpass.util

import androidx.databinding.BindingAdapter
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.nide.mpass.R

@BindingAdapter(value = ["progressWithColor"])
fun LinearProgressIndicator.setProgressWithColor(mProgress: Int) {
    progress = mProgress
    when (mProgress) {
        in 1..30 -> {
            setIndicatorColor(resources.getColor(R.color.red, null))
            trackColor = resources.getColor(R.color.gray, null)
        }
        in 31..60 -> {
            setIndicatorColor(resources.getColor(R.color.yellow, null))
            trackColor = resources.getColor(R.color.gray, null)
        }
        in 61..100 -> {
            setIndicatorColor(resources.getColor(R.color.green, null))
            trackColor = resources.getColor(R.color.gray, null)
        }
        else -> {
            setBackgroundColor(resources.getColor(R.color.white, null))
            trackColor = resources.getColor(R.color.white, null)
        }
    }
}