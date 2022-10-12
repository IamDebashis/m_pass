package com.nide.pocketpass.ui.costomeview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.nide.pocketpass.databinding.LayoutPassGenConfigBinding

class PasswordGenConfig @JvmOverloads
constructor(
    private val ctx: Context,
    private val attr: AttributeSet? = null,
    private val defStyle: Int = 0
) : ConstraintLayout(ctx, attr, defStyle) {

    private lateinit var binding: LayoutPassGenConfigBinding

    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = LayoutPassGenConfigBinding.inflate(inflater, this)
        setLengthIndicatorWithText()
    }


    private fun setLengthIndicatorWithText() {
        binding.sbLength.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvLengthCount.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }


        })
    }

    fun getLenth(): Int = binding.sbLength.progress

    fun setNumberCheck(enable: Boolean) {
        binding.cbNumbers.isChecked = enable
    }

    fun getNumberCheck(): Boolean = binding.cbNumbers.isChecked

    fun setSymbolCheck(enable: Boolean) {
        binding.cbSymbols.isChecked = enable
    }

    fun getSymbolCheck(): Boolean = binding.cbSymbols.isChecked

    fun setLowercaseCheck(enable: Boolean) {
        binding.cbLowercase.isChecked = enable
    }

    fun getLowercaseCheck(): Boolean = binding.cbLowercase.isChecked

    fun setUppercaseCheck(enable: Boolean) {
        binding.cbUppercase.isChecked = enable
    }

    fun getUppercaseCheck(): Boolean = binding.cbUppercase.isChecked

    fun setGenerateBtnClickListener(onClick:(View)->Unit){
        binding.btnRegenerate.setOnClickListener {
            onClick(it)
        }
    }

    fun setSaveBtnClickListener(onClick:(View)->Unit){
        binding.btnSave.setOnClickListener {
            onClick(it)
        }
    }


}