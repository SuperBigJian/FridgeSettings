package com.midea.fridgesettings

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Message
import android.support.v7.widget.DefaultItemAnimator
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.midea.fridgesettings.adapter.CommonAdapter
import com.midea.fridgesettings.adapter.RecyclerViewHolder
import com.midea.fridgesettings.adapter.WifiDividerItemDecoration
import com.midea.fridgesettings.model.AccessPoint
import com.midea.fridgesettings.wifi.WifiConnectDialog
import com.midea.fridgesettings.wifi.WifiControl
import kotlinx.android.synthetic.main.fragment_wifi.*
import kotlinx.android.synthetic.main.wifi_state_area.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textResource


/**
 * Created by chenjian on 6/1/17.
 */
class WifiFragment(type: FragmentTag = BaseFragment.FragmentTag.FRAGMENT_TAB_WIFI) : BaseFragment(type) {
    private val TAG = "WifiFragment"

    private val apAdapter by lazy { WifiAdapter(context, currentAps) }
    private val currentAps by lazy { ArrayList<AccessPoint>() }

    private val conDialog: WifiConnectDialog by lazy { WifiConnectDialog(activity, apAdapter) }

    val WIFI_STATE_CHANGED = 100
    val SCAN_RESULTS_AVAILABLE = 200
    val NETWORK_STATE_CHANGED = 300

    private var wiFiReceiver = object : BroadcastReceiver() {
        private var disSSID = ""
        private var errorPsd = false
        override fun onReceive(context: Context, intent: Intent) {
            val message = Message.obtain()
            when (intent.action) {

                WifiManager.WIFI_STATE_CHANGED_ACTION -> {
                    Log.d("WIFIX", "WIFI_STATE_CHANGED_ACTION: ${intent.extras}")
                    val state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                            WifiManager.WIFI_STATE_DISABLED)
                    message.what = WIFI_STATE_CHANGED
                    message.arg1 = state
                }

                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION -> {
                    Log.d("WIFIX", "SCAN_RESULTS_AVAILABLE_ACTION: ${intent.extras}")
                    val isScanned = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, true)
                    if (isScanned) {
                        message.what = SCAN_RESULTS_AVAILABLE
                    }
                }

                WifiManager.SUPPLICANT_STATE_CHANGED_ACTION, WifiManager.NETWORK_STATE_CHANGED_ACTION -> {
                    message.what = NETWORK_STATE_CHANGED

                    if (!isConDialogShow()) {
                        val info = intent.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)
                        val error = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, 0)
                        if (null != info) {
                            val state = info.detailedState
                            Log.d(TAG, "NetworkInfo: \t $state \n \t ${info.extraInfo}")
                            if (state == NetworkInfo.DetailedState.DISCONNECTED) {
                                disSSID = info.extraInfo.replace("\"", "").trim()
                                if (errorPsd) {
                                    WifiControl.remove(WifiControl.isConfigured(AccessPoint(ssid = disSSID)))
                                    WifiControl.removeSavedPsd(disSSID)
                                    disSSID = ""
                                    errorPsd = false
                                }
                            }
                        }

                        if (WifiManager.ERROR_AUTHENTICATING == error && !disSSID.isNullOrEmpty()) {
                            if (disSSID.isNullOrEmpty()) {
                                errorPsd = true
                            } else {
                                Log.d(TAG, "EXTRA_SUPPLICANT: ${intent.extras}")
                                WifiControl.remove(WifiControl.isConfigured(AccessPoint(ssid = disSSID)))
                                WifiControl.removeSavedPsd(disSSID)
                                disSSID = ""
                            }
                        }
                    }
                }
            }
            mainHandler.sendMessage(message)
        }
    }

    private var mainHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            Log.d("WIFIX", "message : $msg")
            when (msg?.what) {
                WIFI_STATE_CHANGED -> {
                    when (msg.arg1) {
                        WifiManager.WIFI_STATE_DISABLING -> {
                            Log.d("WIFIX", "WIFI_DISABLING")
                        }

                        WifiManager.WIFI_STATE_DISABLED -> {
                            WifiControl.openWifi()
                        }

                        WifiManager.WIFI_STATE_ENABLING -> {
                            Log.d("WIFIX", "WIFI_ENABLEING")
                        }

                        WifiManager.WIFI_STATE_ENABLED -> {
                            WifiControl.startScan()
                            refreshUI(if (currentAps.isEmpty()) 2 else 1)
                        }
                    }
                }

                SCAN_RESULTS_AVAILABLE -> {
                    currentAps.clear()
                    val aps = WifiControl.getScanAp()
                    if (aps != null) {
                        val data = WifiControl.mergeRelativeAPs(aps)
                        currentAps.addAll(data)
                        refreshUI(if (currentAps.isEmpty()) 0 else 3)
                        if (!isConDialogShow()) {
                            apAdapter.notifyDataSetChanged()
                        }
                    }

                }

                NETWORK_STATE_CHANGED -> {
                    WifiControl.startScan()
                }

                else -> Log.d("WIFIX", "unknown message: " + msg?.what)
            }
        }
    }

    override fun getContentViewLayoutId(): Int = R.layout.fragment_wifi

    override fun initViewsAndEvents() {
        super.initViewsAndEvents()
        initView()
    }

    private fun registerWIFIReceiver() {
        val filter = IntentFilter()
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION)
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        context.registerReceiver(wiFiReceiver, filter)
    }

    private fun unRegisterWIFIReceiver() {
        try {
            context.unregisterReceiver(wiFiReceiver)
        } catch (e: IllegalArgumentException) {
            Log.w("WIFIX", "The broadcast has been canceled")
        }
    }

    /**
     * 0->No wifi
     * 1->Scanning with list
     * 2->Scanning no list
     * 3->Wifi list
     */
    private fun refreshUI(state: Int) {

        when (state) {

            0 -> {
                tv_noWifi.visibility = View.VISIBLE
                rv_wifi.visibility = View.GONE
                iv_wifiLoading.visibility = View.GONE
                iv_wifiLoading.clearAnimation()
                tv_wifi_state.text = resources.getString(R.string.please_refresh_wifi)
            }

            1 -> {
                tv_noWifi.visibility = View.GONE
                rv_wifi.visibility = View.VISIBLE
                iv_wifiLoading.visibility = View.VISIBLE
                iv_wifiLoading.startAnimation(getLoadingAnimation())
                tv_wifi_state.text = resources.getString(R.string.scanning_again)
            }

            2 -> {
                tv_noWifi.visibility = View.VISIBLE
                rv_wifi.visibility = View.GONE
                iv_wifiLoading.visibility = View.VISIBLE
                iv_wifiLoading.startAnimation(getLoadingAnimation())
                tv_wifi_state.text = resources.getString(R.string.scanning_again)
            }

            3 -> {
                tv_noWifi.visibility = View.GONE
                rv_wifi.visibility = View.VISIBLE
                iv_wifiLoading.visibility = View.GONE
                iv_wifiLoading.clearAnimation()
                tv_wifi_state.text = resources.getString(R.string.choose_network)
            }
        }
    }

    private fun initView() {
        rv_wifi.itemAnimator = DefaultItemAnimator()
        rv_wifi.layoutManager = VirtualLayoutManager(activity, VirtualLayoutManager.VERTICAL)
        rv_wifi.adapter = apAdapter
        rv_wifi.addItemDecoration(WifiDividerItemDecoration(resources.getDrawable(R.drawable.divider_fgtab, null), 1))

        tv_refresh.setOnClickListener {
            if (WifiControl.isWifEnabled()) {
                WifiControl.startScan()
                refreshUI(1)
            }
        }

        conDialog.setOnDismissListener { refreshUI(1) }

        extraWIFI.setOnClickListener {
            showConnectDialog(AccessPoint(ssid = "添加网络", apType = 10086))
        }

        WifiControl.startScan()
    }

    override fun onUserVisible() {
        super.onUserVisible()
        registerWIFIReceiver()
    }

    override fun onUserInvisible() {
        super.onUserInvisible()
        unRegisterWIFIReceiver()
        mainHandler.removeCallbacksAndMessages(null)
    }

    override fun destroyViewAndThing() {
        super.destroyViewAndThing()
        unRegisterWIFIReceiver()
        mainHandler.removeCallbacksAndMessages(null)
    }

    private fun getLoadingAnimation(): Animation {
        val loadingAnim = AnimationUtils.loadAnimation(context, R.anim.am_wifi_laoding)
        loadingAnim.interpolator = LinearInterpolator()
        return loadingAnim
    }

    private fun showConnectDialog(ap: AccessPoint, pos: Int = -1) {
        conDialog.accessPoint = ap
        conDialog.postion = pos
        conDialog.show()
    }

    fun isConDialogShow(): Boolean = conDialog.isShowing

    inner class WifiAdapter(context: Context, var data: ArrayList<AccessPoint>?) : CommonAdapter<AccessPoint>(context, data) {

        override fun onItemClick(itemView: View, pos: Int) {
            val ap = data?.get(pos) ?: return
            showConnectDialog(ap, pos)
        }

        override fun onItemLongClick(itemView: View, pos: Int) {

        }

        override fun getItemLayoutId(viewType: Int): Int {
            return R.layout.wifi_ap_item
        }

        override fun onCreateLayoutHelper(): LayoutHelper {
            return LinearLayoutHelper()
        }

        override fun bindData(holder: RecyclerViewHolder, position: Int, item: AccessPoint?) {
            val accessPoint = data?.get(position) ?: return
            holder.getTextView(R.id.ssid).text = accessPoint.ssid
            holder.getImageView(R.id.iv_wifiConnected).visibility = View.INVISIBLE
            holder.getTextView(R.id.state).text = ""

            holder.getImageView(R.id.iv_wifiStrength).imageResource =
                    when (accessPoint.signalStrength) {
                        in 0..30 -> R.mipmap.wifi_state_low
                        in 30..70 -> R.mipmap.wifi_state_normal
                        else -> R.mipmap.wifi_state_good
                    }
            holder.getImageView(R.id.iv_wifiLock).visibility =
                    if (WifiControl.isLock(accessPoint))
                        View.VISIBLE
                    else
                        View.INVISIBLE

            if (WifiControl.isConfigured(accessPoint) != -1) {
                //holder.getTextView(R.id.state).textResource = R.string.connect_saved
                if (WifiControl.isConnected(accessPoint)) {
                    holder.getImageView(R.id.iv_wifiConnected).visibility = View.VISIBLE
                    holder.getTextView(R.id.state).textResource = R.string.connected
                }
            }
        }
    }

}