package com.midea.fridgesettings.adapter

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import com.midea.fridgesettings.BaseFragment
import org.jetbrains.anko.forEachChild


/**
 * Created by chenjian on 6/2/17.
 */
class TabDividerItemDecoration(val mDivider: Drawable, val mDividerSize: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect?.set(0, 0, 0, mDividerSize)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        parent.forEachChild {
            if (it.tag == BaseFragment.FragmentTag.FRAGMENT_TAB_HEAD) {
                val params = it.layoutParams as RecyclerView.LayoutParams
                val top = it.bottom + params.bottomMargin
                val bottom = top + mDividerSize
                mDivider.setBounds(left, top, right, bottom)
                mDivider.draw(c)
            }
        }
        mDivider.setBounds(parent.width - mDividerSize, 0, parent.width, parent.height)
        mDivider.draw(c)
    }
}