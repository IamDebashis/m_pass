package com.nide.pocketpass.ui.home

import android.content.Context
import android.widget.FrameLayout
import android.widget.TextView
import com.nide.pocketpass.R

class SectionHeaderView(
    private val mContext: Context
) : FrameLayout(mContext) {

private lateinit var titleText : TextView

init {
    inflate(mContext, R.layout.item_section_header,this)

    findView()

}

private fun findView(){
    titleText = findViewById(R.id.tv_title)
}

fun setTitle(title: String){
    titleText.text = title
}


}