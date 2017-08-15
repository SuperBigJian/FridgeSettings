package com.midea.fridgesettings.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import com.midea.fridgesettings.R
import org.jetbrains.anko.textSizeDimen

/**
 * Created by chenjian on 17-6-13.
 */
class ProgressEntry(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    val title = TextView(context)
    val seek = SeekBar(context)
    val value = TextView(context)
    val image = ImageView(context)

    init {
        val titleParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        title.textSizeDimen = R.dimen.content_text
        val seekParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        seekParams.weight = 1f
        seekParams.marginStart = 20
        seekParams.marginEnd = 20
        seek.thumb = context.resources.getDrawable(R.drawable.seek_thumb)
        val valueParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        value.textSizeDimen = R.dimen.content_text
        val imageParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        addView(title, titleParams)
        addView(seek, seekParams)
        addView(value, valueParams)
        addView(image, imageParams)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }
}