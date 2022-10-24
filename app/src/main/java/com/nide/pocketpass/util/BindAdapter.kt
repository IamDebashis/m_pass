package com.nide.pocketpass.util

import android.text.BoringLayout
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.nide.pocketpass.R

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

fun ImageView.loadWithLatter(latter: String) {
    Glide.with(context)
        .load(
            AvaterCreator.AvatarBuilder(context).setAvatarSize(200)
                .setTextSize(50).setLabel(latter).toCircle().build()
        ).into(this)
}

fun EditText.validPhone():Boolean{
    return if (this.text.toString().trim().isEmpty()) {
        error = "Phone number is a mandatory field"
        false
    }else if(this.text.toString().length < 10){
        error = "At least 10 number should enter"
        false
    } else {
        error = null
        true
    }
}

fun EditText.validName():Boolean{
    return if (this.text.toString().trim().isEmpty()) {
        error = "please enter a name"
        false
    }else if(this.text.toString().length < 2){
        error = "Name should be at least more then 2 character "
        false
    } else {
        error = null
        true
    }
}
