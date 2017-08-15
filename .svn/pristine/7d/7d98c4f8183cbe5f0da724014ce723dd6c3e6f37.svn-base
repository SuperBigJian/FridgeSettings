package com.midea.fridgesettings.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by chenjian on 6/1/17.
 */
class RecyclerViewHolder(val context: Context, val contentView: View, val mViews: SparseArray<View> = SparseArray()) : RecyclerView.ViewHolder(contentView) {
    private fun <T : View> findViewById(viewId: Int): T {
        var view: View? = mViews.get(viewId)
        if (view == null) {
            view = contentView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    fun getView(viewId: Int): View = findViewById(viewId)

    fun getTextView(viewId: Int): TextView = findViewById(viewId)

    fun getImageView(viewId: Int): ImageView = findViewById(viewId)

    fun getButton(viewId: Int): Button = findViewById(viewId)

    fun ImageButton(viewId: Int): Button = findViewById(viewId)

    fun EditText(viewId: Int): Button = findViewById(viewId)
}
