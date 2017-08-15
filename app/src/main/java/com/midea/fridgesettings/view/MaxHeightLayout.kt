package com.midea.fridgesettings.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.midea.fridgesettings.R


/**
 * Created by chenjian on 17-7-12.
 */
class MaxHeightLayout(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private var maxHeight = 0f

    init {
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightLayout)
        R.styleable.ConstraintLayout_Layout_android_maxHeight
        maxHeight = a.getDimension(R.styleable.MaxHeightLayout_maxHeight, 0f)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        if (heightMode == View.MeasureSpec.EXACTLY) {
            heightSize = if (heightSize <= maxHeight)
                heightSize
            else
                maxHeight.toInt()
        }

        if (heightMode == View.MeasureSpec.UNSPECIFIED) {
            heightSize = if (heightSize <= maxHeight)
                heightSize
            else
                maxHeight.toInt()
        }
        if (heightMode == View.MeasureSpec.AT_MOST) {
            heightSize = if (heightSize <= maxHeight)
                heightSize
            else
                maxHeight.toInt()
        }
        val maxHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightSize,
                heightMode)
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec)
    }
}