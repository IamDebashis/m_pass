package com.nide.pocketpass.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nide.pocketpass.data.truple.PasswordTuple
import com.nide.pocketpass.ui.home.SectionHeaderView

class ItemSectionDecoration(
    private val context: Context,
    private val getItemList: () -> MutableList<PasswordTuple>
) : RecyclerView.ItemDecoration() {


    private val sectionHeight by lazy {
        dipToPx(context, 30f)
    }
    private val sectionWidth by lazy {
        getScreenWidth(context)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val layoutManager = parent.layoutManager

        // allow only linear layout manager
        if (layoutManager !is LinearLayoutManager) {
            return
        }
        // allow only vertical orientation
        if (LinearLayoutManager.VERTICAL != layoutManager.orientation) {
            return
        }
        val list = getItemList()

        if (list.isEmpty()) {
            return
        }
        val position = parent.getChildAdapterPosition(view)

        if (position == 0) {
            outRect.top = sectionHeight
            return
        }

        val currentItem = getItemList()[position]
        val previousItem = getItemList()[position - 1]

        if (currentItem.categoryId != previousItem.categoryId) {
            outRect.top = sectionHeight
        }
    }

  /*  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val item = getItemList()
        if (item.isEmpty()) {
            return
        }

        val childCount = parent.childCount
        if (childCount == 0) {
            return
        }

        val firstView = parent.getChildAt(0)
        val position = parent.getChildAdapterPosition(firstView)
        val password = item[position]

        val condition = password.categoryId != item[position + 1].categoryId
        drawSectionView(
            c, password.categoryName, if (firstView.bottom <= sectionHeight && condition) {
                firstView.bottom - sectionHeight
            } else {
                0
            }
        )


    }*/

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(childView)
            val item = getItemList()[position]

            if (getItemList().isNotEmpty() &&
                (position == 0 || item.categoryId != getItemList()[position - 1].categoryId)
            ) {
                val top = childView.top - sectionHeight
                drawSectionView(c, item.categoryName, top)
            }
        }

    }


    private fun drawSectionView(canvas: Canvas, text: String, top: Int) {
        val view = SectionHeaderView(context)
        view.setTitle(text)
        val bitmap = getViewGroupBitmap(view)
        val bitmapCanvas = Canvas(bitmap)
        view.draw(bitmapCanvas)
        canvas.drawBitmap(bitmap, 0f, top.toFloat(), null)
    }

    private fun getViewGroupBitmap(viewGroup: ViewGroup): Bitmap {
        val layoutParams = ViewGroup.LayoutParams(sectionWidth, sectionHeight)
        viewGroup.layoutParams = layoutParams
        viewGroup.measure(
            View.MeasureSpec.makeMeasureSpec(sectionWidth, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(sectionHeight, View.MeasureSpec.EXACTLY)
        )

        viewGroup.layout(0, 0, sectionWidth, sectionHeight)

        val bitmap = Bitmap.createBitmap(viewGroup.width, viewGroup.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        viewGroup.draw(canvas)
        return bitmap
    }


    fun dipToPx(context: Context, dipValue: Float): Int {
        return (dipValue * context.resources.displayMetrics.density).toInt()
    }


    private fun getScreenWidth(context: Context): Int {
        val outMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val display = context.display
            display?.getRealMetrics(outMetrics)
        } else {
            val display =
                (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            display.getMetrics(outMetrics)
        }
        return outMetrics.widthPixels
    }


}