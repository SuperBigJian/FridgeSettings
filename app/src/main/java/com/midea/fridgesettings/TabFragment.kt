package com.midea.fridgesettings

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.midea.fridgesettings.BaseFragment.FragmentTag
import com.midea.fridgesettings.adapter.CommonAdapter
import com.midea.fridgesettings.adapter.RecyclerViewHolder
import com.midea.fridgesettings.adapter.TabDividerItemDecoration
import com.midea.fridgesettings.model.FragmentTabInfo
import com.midea.fridgesettings.model.getTabItemInfo
import org.jetbrains.anko.toast

/**
 * Created by chenjian on 6/1/17.
 */
class TabFragment : Fragment() {
    var hasUpdate = false
        set(value) {
            field = value
            getRootView().adapter.notifyDataSetChanged()
        }

    private val mSettingList = getTabItemInfo()
    private val adapter by lazy { FragmentTabAdapter(context, mSettingList) }
    var selectTabPos = 1

    override fun getContext(): Context {
        return activity.applicationContext
    }

    fun getRootView(): RecyclerView = view as RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return RecyclerView(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewsAndEvents()
    }

    private fun initViewsAndEvents() {

        if (activity is OnTabClickListener) {
            setTabClickListener(activity as OnTabClickListener)
        }
        getRootView().overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        getRootView().itemAnimator = DefaultItemAnimator()

        getRootView().adapter = adapter

        getRootView().layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        getRootView().addItemDecoration(TabDividerItemDecoration(resources.getDrawable(R.drawable.divider_fgtab, null), 1))

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun getTabPosition(tag: FragmentTag): Int {
        mSettingList.forEachIndexed { index, fragmentTabInfo ->
            if (fragmentTabInfo.tabType == tag) {
                return index
            }
        }
        return 1
    }

    fun setSelectTab(pos: Int) {
        if (selectTabPos != pos) {
            mSettingList[pos].isSelect = true
            adapter.notifyItemChanged(pos)
            mSettingList[selectTabPos].isSelect = false
            adapter.notifyItemChanged(selectTabPos)
            selectTabPos = pos
        }
    }

    fun setSelectTabByTag(tag: FragmentTag) {
        setSelectTab(getTabPosition(tag))
    }

    fun goWIFIFragment() {
        val ryc = getRootView()
        ryc.getChildAt(1).performClick()
    }

    inner class FragmentTabAdapter(context: Context, data: ArrayList<FragmentTabInfo>) : CommonAdapter<FragmentTabInfo>(context, data) {

        override fun onCreateLayoutHelper(): LayoutHelper {
            return LinearLayoutHelper()
        }

        override fun onItemLongClick(itemView: View, pos: Int) {
            if (itemView.tag == FragmentTag.FRAGMENT_TAB_HEAD) return
            toast("长按没有功能（>_<）${itemView.tag}")
        }

        override fun onItemClick(itemView: View, pos: Int) {
            if (itemView.tag == FragmentTag.FRAGMENT_TAB_HEAD) return
            setSelectTab(pos)
            tabClickListener?.onTabClick(itemView.tag as FragmentTag)
        }

        override fun getItemViewType(position: Int): Int {
            return mSettingList[position].tabType.type
        }

        override fun getItemLayoutId(viewType: Int): Int {

            when (viewType) {
                FragmentTag.FRAGMENT_TAB_HEAD.type -> {
                    return R.layout.fragment_tab_head
                }
                else -> {
                    return R.layout.fragment_tab_entry
                }
            }
        }

        override fun bindData(holder: RecyclerViewHolder, position: Int, item: FragmentTabInfo?) {
            when (item?.tabType) {
                FragmentTag.FRAGMENT_TAB_HEAD -> {
                    holder.itemView?.tag = FragmentTag.FRAGMENT_TAB_HEAD
                    holder.getTextView(R.id.tv_head_title).text = item.title
                }
                else -> {
                    holder.itemView?.tag = item?.tabType
                    holder.getTextView(R.id.tv_entry_title).text = item?.title
                    holder.getImageView(R.id.tv_entry_icon).setImageResource(item?.icon ?: -1)
                    holder.getImageView(R.id.iv_hint).visibility = if (item?.tabType == FragmentTag.FRAGMENT_TAB_UPDATE && hasUpdate) View.VISIBLE else View.GONE
                }
            }
            holder.itemView?.isSelected = item?.isSelect ?: false
        }
    }

    private var tabClickListener: OnTabClickListener? = null

    fun setTabClickListener(listener: OnTabClickListener) {
        tabClickListener = listener
    }

    interface OnTabClickListener {
        fun onTabClick(tag: FragmentTag)
    }
}