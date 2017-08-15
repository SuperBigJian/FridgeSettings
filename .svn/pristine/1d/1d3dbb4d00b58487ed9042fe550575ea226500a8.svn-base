package com.midea.fridgesettings.adapter

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper


/**
 * Created by chenjian on 6/1/17.
 */

abstract class CommonAdapter<T>(val context: Context,
                                val datas: ArrayList<T>?) : DelegateAdapter.Adapter<RecyclerViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerViewHolder {
        val holder: RecyclerViewHolder = RecyclerViewHolder(context,
                inflater.inflate(getItemLayoutId(viewType), parent, false))
        holder.contentView.setOnClickListener {
            mClickListener?.onItemClick(holder.contentView, holder.layoutPosition)
            onItemClick(holder.contentView, holder.layoutPosition)
        }
        holder.contentView.setOnLongClickListener {
            mLongClickListener?.onItemLongClick(holder.contentView, holder.layoutPosition)
            onItemLongClick(holder.contentView, holder.layoutPosition)
            return@setOnLongClickListener true
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        bindData(holder, position, datas?.get(position))
    }

    override fun getItemCount(): Int = datas?.size ?: 0

    abstract fun getItemLayoutId(viewType: Int): Int

    abstract fun bindData(holder: RecyclerViewHolder, position: Int, item: T?)

    abstract fun onItemClick(itemView: View, pos: Int)
    abstract fun onItemLongClick(itemView: View, pos: Int)

    fun addData(pos: Int, t: T) {
        datas?.add(pos, t)
        notifyItemInserted(pos)
    }

    fun addData(t: T) {
        datas?.add(t)
        notifyItemInserted(itemCount)
    }

    fun removeData(pos: Int) {
        datas?.removeAt(pos)
        notifyItemRemoved(pos)
    }

    interface OnItemClickListener {
        fun onItemClick(itemView: View, pos: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(itemView: View, pos: Int)
    }

    private var mClickListener: OnItemClickListener? = null

    fun setItemClickListener(clickListener: OnItemClickListener?) {
        mClickListener = clickListener
    }

    private var mLongClickListener: OnItemLongClickListener? = null

    fun setItemLongClickListener(clickListener: OnItemLongClickListener?) {
        mLongClickListener = clickListener
    }
}

