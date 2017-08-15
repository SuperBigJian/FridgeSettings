package com.midea.fridgesettings.wifi

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.NetworkInfo
import android.net.wifi.SupplicantState
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.midea.fridgesettings.R
import com.midea.fridgesettings.WifiFragment
import com.midea.fridgesettings.model.AccessPoint
import kotlinx.android.synthetic.main.wifi_connect_dialog.*
import org.jetbrains.anko.textResource


/**
 * Created by chenjian on 17-6-7.
 */
class WifiConnectDialog(context: Context, val adapter: WifiFragment.WifiAdapter) : Dialog(context) {
    private val TAG = "WifiConnectDialog"

    private val CONNECT_UI = 2000
    private val CONNECTED_UI = 3000
    private val CONNECTING_UI = 4000
    private val CUSTOM_UI = 5000
    private val FAILED_UI = 6000

    var postion: Int = 0
    var accessPoint: AccessPoint = AccessPoint()

    private val mEncryptType = arrayOf("开放", "WPA/WPA2 PSK", "WEP")
    private val mEncryptSpinnerAdapter: ArrayAdapter<String> = ArrayAdapter(context, R.layout.wifi_custom_spinner_item, mEncryptType)

    private val cbShowPsd by lazy { dialogPsd.findViewById(R.id.cb_wifi_dialog_showpd) as CheckBox }
    private val psdInput by lazy { dialogPsd.findViewById(R.id.et_wifi_dialog_password) as EditText }

    private val tvStrength by lazy { dialogInfo.findViewById(R.id.tl_tv_wifiStrength) as TextView }
    private val tvSpeed by lazy { dialogInfo.findViewById(R.id.tl_tv_wifiSpeed) as TextView }
    private val tvAddress by lazy { dialogInfo.findViewById(R.id.tl_tv_ipAddress) as TextView }
    private val tvSecurity by lazy { dialogInfo.findViewById(R.id.tl_tv_security) as TextView }

    private val ivConnecting by lazy { dialogCon.findViewById(R.id.iv_wifiConnecting) as ImageView }
    private val tvState by lazy { dialogCon.findViewById(R.id.tv_wifiConnectedState) as TextView }

    private val ssidInput by lazy { dialogCus.findViewById(R.id.ssidInput) as EditText }
    private val encSpinner by lazy { dialogCus.findViewById(R.id.encryptSpinner) as Spinner }

    private var wifiInfo: WifiInfo? = null
    private val handler = Handler()

    private var isConnecting = false
    private var lengthLimit = 0
    private var preWifi: WifiInfo? = null

    private var connectStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            if (intent.action == WifiManager.SUPPLICANT_STATE_CHANGED_ACTION) {
                /**
                 * when some certain ap connects fail because of wrong password,here comes a broadcast
                 */
                val error = intent.getIntExtra(WifiManager.EXTRA_SUPPLICANT_ERROR, 0)
                if (WifiManager.ERROR_AUTHENTICATING == error) {
                    Log.d(TAG, "ERROR_AUTHENTICATING: 密码错误")
                    tvState.textSize = 30f
                    tvState.textResource = R.string.password_error
                    isConnecting = false
                    clearSavedPsd()
                    conFailUI()//密码错误
                    return
                }
            }

            if (intent.action == WifiManager.NETWORK_STATE_CHANGED_ACTION) {
                val info = intent.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)
                if (null != info) {
                    val state = info.detailedState
                    Log.d(TAG, "NetworkInfo: \t $state \n \t $info")
                    val wifiInfo = intent.getParcelableExtra<WifiInfo>(WifiManager.EXTRA_WIFI_INFO)
                    Log.d(TAG, "WifiInfo \t $wifiInfo \n \t $accessPoint")
                    when (state) {
                        NetworkInfo.DetailedState.CONNECTED -> {
                            if (wifiInfo != null && (wifiInfo.supplicantState == SupplicantState.COMPLETED) && isConnecting) {
                                dismiss() //连接成功
                            }
                        }

                        NetworkInfo.DetailedState.FAILED -> {
                            if (!isConnecting) return
                            tvState.textSize = 30f
                            tvState.textResource = R.string.connect_failed
                            conFailUI() //连接失败
                        }

                        else -> {
                            Log.d(TAG, "Unknow State $state")
                        }
                    }
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.wifi_connect_dialog)
        setCanceledOnTouchOutside(true)
    }

    override fun show() {
        super.show()
        initEvent()
        initView()
        Log.d(TAG, "dialog-------show")
    }

    private fun initEvent() {
        registerReceiver()
        setPasswordListener()

        isConnecting = false
        lengthLimit = if (!WifiControl.isLock(accessPoint)) 0 else
            if (accessPoint.encryptionType.toLowerCase().contains("wpa")) 8 else 5

        preWifi = WifiControl.getConnectionInfo()

        //设置下拉列表风格
        mEncryptSpinnerAdapter.setDropDownViewResource(R.layout.wifi_custom_spinner_dropdown_item)
        encSpinner.adapter = mEncryptSpinnerAdapter
        encSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                showPasswordInput(position)
            }
        }

        cbShowPsd.isChecked = false
        cbShowPsd.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                psdInput.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                psdInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            psdInput.setSelection(psdInput.length())//set cursor to the end
        }

        bt_wifi_dialog_remove.setOnClickListener {
            clearSavedPsd()
            dismiss()
        }

        bt_wifi_dialog_cancel.setOnClickListener {
            dismiss()
        }
    }

    private fun initView() {
        setCanceledOnTouchOutside(true)
        tv_wifi_dialog_title.text = accessPoint.ssid
        if (accessPoint.apType == 10086) {
            cusConnectUI()
        } else if (WifiControl.isConnected(accessPoint)) {
            connectedUI()
        } else {
            connectUI()
        }
    }

    override fun dismiss() {
        Log.d(TAG, "dialog-------dismiss")
        WifiControl.startScan()
        unRegisterReceiver()
        ivConnecting.clearAnimation()
        adapter.notifyDataSetChanged()
        if (WifiControl.getConnectionInfo() == null && preWifi != null) {
            WifiControl.connect(preWifi?.networkId ?: -1)
        }
        handler.removeCallbacksAndMessages(null)
        super.dismiss()
    }

    private fun cusConnectUI() {
        refreshDialogContent(CUSTOM_UI)
        ssidInput.setText("")
        bt_wifi_dialog_join.setOnClickListener {
            accessPoint.ssid = ssidInput.text.toString()
            accessPoint.password = psdInput.text.toString()
            accessPoint.encryptionType = encSpinner.selectedItem.toString()
            accessPoint.isHideSSID = true
            clearSavedPsd()
            tryToConnect()
        }
    }

    private fun connectedUI() {
        refreshDialogContent(CONNECTED_UI)

        wifiInfo = WifiControl.getConnectionInfo()

        tvStrength.text = when (accessPoint.signalStrength) {
            in 0..30 -> "弱"
            in 30..70 -> "中"
            else -> "强"
        }

        tvSecurity.text = when (WifiControl.getSecurity(accessPoint)) {
            WifiControl.SECURITY_PSK -> {
                when (WifiControl.getPskType(accessPoint)) {
                    WifiControl.PskType.WPA_WPA2 -> "WPA/WPA2 PSK"
                    WifiControl.PskType.WPA2 -> "WPA2 PSK"
                    WifiControl.PskType.WPA -> "WPA PSK"
                    else -> "UNKNOWN PSK"
                }
            }
            WifiControl.SECURITY_WEP -> "WEP"
            WifiControl.SECURITY_EAP -> "EAP"
            else -> "UNKNOWN SECURITY"
        }

        tvSpeed.text = "${wifiInfo?.linkSpeed}Mbps"

        tvAddress.text = WifiControl.intToIp(wifiInfo?.ipAddress ?: 0)

        bt_wifi_dialog_connect.isEnabled = true
        bt_wifi_dialog_connect.textResource = R.string.finish
        bt_wifi_dialog_connect.setOnClickListener {
            dismiss()
        }
    }

    private fun connectUI() {
        refreshDialogContent(CONNECT_UI)
        if (!WifiControl.isLock(accessPoint)) {
            dialogPsd.visibility = View.GONE
            if (WifiControl.isConfigured(accessPoint) != -1) {
                bt_wifi_dialog_remove.visibility = View.VISIBLE
                bt_wifi_dialog_cancel.visibility = View.GONE
            }
        } else {
            val str = WifiControl.getSavedPsd(accessPoint.ssid)
            psdInput.setText(str)
            psdInput.setSelection(psdInput.length())
        }

        bt_wifi_dialog_connect.isEnabled = psdInput.length() >= lengthLimit
        bt_wifi_dialog_connect.textResource = R.string.connect
        bt_wifi_dialog_connect.setOnClickListener {
            accessPoint.password = psdInput.text.toString()
            tryToConnect()
        }
    }

    private fun tryToConnect() {
        tv_wifi_dialog_title.text = accessPoint.ssid
        isConnecting = true
        if (WifiControl.connect(accessPoint)) {
            if (WifiControl.isLock(accessPoint)) {
                WifiControl.savePsd(accessPoint.ssid,psdInput.text.toString())
            }
            connectingUI()
        } else {
            tvState.textSize = 30f
            tvState.textResource = R.string.connect_refuse
            conFailUI()
        }
    }

    private fun connectingUI() {
        refreshDialogContent(CONNECTING_UI)
        hideKeyboard(psdInput)

        tvState.textSize = 24f
        tvState.textResource = R.string.on_connect

        bt_wifi_dialog_connect.isEnabled = false
        bt_wifi_dialog_connect.textResource = R.string.connect

        handler.postDelayed({
            tvState.textSize = 30f
            tvState.textResource = R.string.connect_timeout
            conFailUI()
        }, 20000)
    }

    private fun conFailUI() {
        isConnecting = false
        refreshDialogContent(FAILED_UI)

        bt_wifi_dialog_connect.isEnabled = true
        bt_wifi_dialog_connect.textResource = R.string.finish
        bt_wifi_dialog_connect.setOnClickListener {
            dismiss()
        }

        handler.postDelayed({ dismiss() }, 3000)
    }

    private fun refreshDialogContent(state: Int) {
        when (state) {
            CUSTOM_UI -> {
                dialogInfo.visibility = View.GONE
                dialogPsd.visibility = View.GONE
                dialogCon.visibility = View.GONE
                dialogCus.visibility = View.VISIBLE

                bt_wifi_dialog_remove.visibility = View.GONE
                bt_wifi_dialog_cancel.visibility = View.VISIBLE
                bt_wifi_dialog_connect.visibility = View.GONE
                bt_wifi_dialog_join.visibility = View.VISIBLE
            }

            CONNECTED_UI -> {
                dialogInfo.visibility = View.VISIBLE
                dialogPsd.visibility = View.GONE
                dialogCus.visibility = View.GONE
                dialogCon.visibility = View.GONE

                bt_wifi_dialog_remove.visibility = View.VISIBLE
                bt_wifi_dialog_cancel.visibility = View.GONE
                bt_wifi_dialog_connect.visibility = View.VISIBLE
                bt_wifi_dialog_join.visibility = View.GONE
            }

            CONNECT_UI -> {
                dialogInfo.visibility = View.GONE
                dialogPsd.visibility = View.VISIBLE
                dialogCon.visibility = View.GONE
                dialogCus.visibility = View.GONE

                bt_wifi_dialog_remove.visibility = View.GONE
                bt_wifi_dialog_cancel.visibility = View.VISIBLE
                bt_wifi_dialog_connect.visibility = View.VISIBLE
                bt_wifi_dialog_join.visibility = View.GONE
            }

            CONNECTING_UI -> {
                setCanceledOnTouchOutside(false)

                dialogInfo.visibility = View.GONE
                dialogPsd.visibility = View.GONE
                dialogCon.visibility = View.VISIBLE
                dialogCus.visibility = View.GONE

                ivConnecting.visibility = View.VISIBLE
                ivConnecting.startAnimation(getLoadingAnimation())

                bt_wifi_dialog_remove.visibility = View.GONE
                bt_wifi_dialog_cancel.visibility = View.VISIBLE
                bt_wifi_dialog_connect.visibility = View.VISIBLE
                bt_wifi_dialog_join.visibility = View.GONE
            }

            FAILED_UI -> {
                setCanceledOnTouchOutside(true)

                dialogInfo.visibility = View.GONE
                dialogPsd.visibility = View.GONE
                dialogCon.visibility = View.VISIBLE
                dialogCus.visibility = View.GONE

                ivConnecting.visibility = View.GONE
                ivConnecting.clearAnimation()

                bt_wifi_dialog_remove.visibility = View.GONE
                bt_wifi_dialog_cancel.visibility = View.VISIBLE
                bt_wifi_dialog_connect.visibility = View.VISIBLE
                bt_wifi_dialog_join.visibility = View.GONE
            }
        }
    }

    private fun clearSavedPsd() {
        WifiControl.remove(WifiControl.isConfigured(accessPoint))
        WifiControl.removeSavedPsd(accessPoint.ssid)
    }

    private fun setPasswordListener() {

        psdInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (null != s && s.toString().length >= lengthLimit) {
                    bt_wifi_dialog_connect.isEnabled = true
                }

                if (s.isNullOrBlank() || s.isNullOrEmpty()) {
                    clearSavedPsd()
                }
                accessPoint.passwordChange = true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (null == s || s.toString().length < lengthLimit) {
                    with(bt_wifi_dialog_connect) {
                        isEnabled = false
                    }
                }
            }
        })
    }

    private fun getLoadingAnimation(): Animation {
        val loadingAnim = AnimationUtils.loadAnimation(context, R.anim.am_wifi_laoding)
        loadingAnim.interpolator = LinearInterpolator()
        return loadingAnim
    }

    private fun registerReceiver() {
        val filter = IntentFilter()
        filter.addAction(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION)
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        context.registerReceiver(connectStateReceiver, filter)
    }

    private fun unRegisterReceiver() {
        try {
            context.unregisterReceiver(connectStateReceiver)
        } catch (e: IllegalArgumentException) {
            Log.w(TAG, "The broadcast has been canceled")
        }
    }

    //隐藏虚拟键盘
    fun hideKeyboard(v: View) {
        val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(v.applicationWindowToken, 0)
        }
    }

    /**显示或隐藏密码输入 */
    private fun showPasswordInput(position: Int) {
        if (0 == position) {
            psdInput.setText("")
            dialogPsd.visibility = View.GONE
        } else {
            dialogPsd.visibility = View.VISIBLE
        }
    }
}