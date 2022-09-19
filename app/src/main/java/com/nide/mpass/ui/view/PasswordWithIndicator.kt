package com.nide.mpass.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import com.nide.mpass.R
import com.nide.mpass.databinding.PasswordInputLayoutBinding
import com.nide.mpass.util.PasswordStrength
import com.nide.mpass.util.setProgressWithColor

class PasswordWithIndicator @JvmOverloads
constructor(
    private val mContext: Context,
    private val attrs: AttributeSet? = null,
    private val defStyle: Int = 0
) : ConstraintLayout(mContext, attrs, defStyle) {

  private  lateinit var binding: PasswordInputLayoutBinding

    init {
        val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = PasswordInputLayoutBinding.inflate(inflater, this)
        onTextChage()
        startIconVisible(false)
    }

    private fun onTextChage() {
        binding.etPasswordField.editText?.doOnTextChanged { text, start, before, count ->
            if(!text.isNullOrBlank()){
                binding.passwordStrengthIndicator.setProgressWithColor(PasswordStrength(text).check())
            }
        }
    }

    fun enableEditText(enable : Boolean){
        binding.etPasswordField.editText?.isEnabled = enable
    }


    fun startIconVisible(enable: Boolean){
        binding.etPasswordField.isStartIconVisible = enable
    }

    fun setText(text: CharSequence){
        binding.etPasswordField.editText?.setText(text)
    }

    fun getText() : String{
      return  binding.etPasswordField.editText?.text.toString()
    }

    fun setHintText(hint: String){
        binding.etPasswordField.editText?.hint = hint
    }

}