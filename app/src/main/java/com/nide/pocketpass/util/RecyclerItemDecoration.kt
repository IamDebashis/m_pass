package com.nide.pocketpass.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.nide.pocketpass.R
import com.nide.pocketpass.data.truple.PasswordTuple


class RecyclerItemDecoration(
    con: Context,
    headerHeight: Int,
    isSticky: Boolean,
    callback: SectionCallback,
    private val itemList: () -> MutableList<PasswordTuple>
) : ItemDecoration() {

    var context: Context
    private var headerOffset: Int
    private var sticky: Boolean
    private var sectionCallback: SectionCallback
    private var headerView: View? = null
    private var tvTitle: TextView? = null


    init {
        context = con
        headerOffset = headerHeight
        sticky = isSticky
        sectionCallback = callback
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val pos = parent.getChildAdapterPosition(view)
        if (sectionCallback.isSection(pos, itemList())) {
            outRect.top = headerOffset
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (headerView == null) {
            headerView = inflateHeader(parent)
            tvTitle = headerView!!.findViewById(R.id.tv_title)
            fixLayoutSize(headerView!!, parent)
        }
        var prevTitle = ""
        for (i in 0 until parent.childCount) {
            val child: View = parent.getChildAt(i)
            val childPos = parent.getChildAdapterPosition(child)
            val title = sectionCallback.getSectionHeaderName(childPos, itemList())
            tvTitle!!.text = title
            if (!prevTitle.equals(
                    title,
                    ignoreCase = true
                ) || sectionCallback.isSection(childPos, itemList())
            ) {
                drawHeader(c, child, headerView!!)
                prevTitle = title
            }
        }
    }

    private fun drawHeader(c: Canvas, child: View, headerView: View) {
        c.save()
        if (sticky) {
            c.translate(0F, 0.coerceAtLeast(child.top - headerView.height).toFloat())
        } else {
            c.translate(0F, (child.top - headerView.height).toFloat())
        }
        headerView.draw(c)
        c.restore()
    }

    private fun fixLayoutSize(view: View, viewGroup: ViewGroup) {
        val widthSpec: Int =
            View.MeasureSpec.makeMeasureSpec(viewGroup.width, View.MeasureSpec.EXACTLY)
        val heightSpec: Int =
            View.MeasureSpec.makeMeasureSpec(viewGroup.height, View.MeasureSpec.UNSPECIFIED)
        val childWidth = ViewGroup.getChildMeasureSpec(
            widthSpec,
            viewGroup.paddingLeft + viewGroup.paddingRight,
            view.layoutParams.width
        )
        val childHeight = ViewGroup.getChildMeasureSpec(
            heightSpec,
            viewGroup.paddingTop + viewGroup.paddingBottom,
            view.layoutParams.height
        )
        view.measure(childWidth, childHeight)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    private fun inflateHeader(recyclerView: RecyclerView): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.item_section_header, recyclerView, false)
    }

    interface SectionCallback {
        fun isSection(pos: Int, list: List<PasswordTuple>): Boolean
        fun getSectionHeaderName(pos: Int, list: List<PasswordTuple>): String
    }
}