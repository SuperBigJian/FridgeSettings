package com.midea.fridgesettings.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView

/**
 * Created by chenjian on 17-6-12.
 */
class SwitchEntry(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    val tvTitle: TextView = TextView(context)
    val switch: Switch = Switch(context)
    private val titleParams: LayoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

    init {
        setGravity(Gravity.CENTER_VERTICAL)
        titleParams.weight = 1.0F
        addView(tvTitle, titleParams)
        addView(switch)
    }
}