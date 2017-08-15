package com.midea.fridgesettings

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.util.Log
import com.midea.fridge.utils.config.FridgeConfigUtil
import kotlinx.android.synthetic.main.fragment_bluetooth.*
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textResource


/**
 * Created by chenjian on 6/1/17.
 */
class BluetoothFragment(type: FragmentTag = BaseFragment.FragmentTag.FRAGMENT_TAB_BLUETOOTH) : BaseFragment(type) {
    private val MODEL = FridgeConfigUtil.getInstance().fullName
    private var mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private val bluetoothStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val state = intent?.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR)
            when (state) {

                BluetoothAdapter.STATE_OFF -> {
                    tv_bluetoothState.text = getText(R.string.bluetoothOff)
                    sw_bluetooth.switch.isEnabled = true
                    sw_bluetooth.switch.isChecked = false
                }

                BluetoothAdapter.STATE_TURNING_OFF -> {
                    sw_bluetooth.switch.isEnabled = false
                    tv_bluetoothState.text = getText(R.string.bluetoothTurningOff)
                }

                BluetoothAdapter.STATE_ON -> {
                    if (mBluetoothAdapter.name != MODEL) {
                        mBluetoothAdapter.name = MODEL
                    }
                    tv_bluetoothState.text = "${getText(R.string.bluetoothDeviceName)}${mBluetoothAdapter.name}"
                    sw_bluetooth.switch.isEnabled = true
                    sw_bluetooth.switch.isChecked = true
                }

                BluetoothAdapter.STATE_TURNING_ON -> {
                    sw_bluetooth.switch.isEnabled = false
                    tv_bluetoothState.text = getText(R.string.bluetoothTurningOn)
                }
            }
        }
    }

    override fun getContentViewLayoutId(): Int = R.layout.fragment_bluetooth

    override fun initViewsAndEvents() {
        super.initViewsAndEvents()
        mBluetoothAdapter.name = MODEL
        with(sw_bluetooth) {
            tvTitle.textSize = 30f
            tvTitle.textColor = Color.rgb(103, 107, 108)
            tvTitle.textResource = R.string.bluetooth
            switch.setOnCheckedChangeListener { _, isChecked ->
                val isEnabled = mBluetoothAdapter.isEnabled
                if (isChecked && !isEnabled) {
                    mBluetoothAdapter.enable()
                    val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
                    intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0)
                    activity.sendBroadcast(intent)
                } else if (!isChecked && isEnabled) {
                    mBluetoothAdapter.disable()
                }
            }
        }
    }

    private fun initUI() {
        sw_bluetooth.switch.isChecked = mBluetoothAdapter.isEnabled
        if (mBluetoothAdapter.isEnabled) {
            tv_bluetoothState.text = "${getText(R.string.bluetoothDeviceName)}${mBluetoothAdapter.name}。"
        } else {
            tv_bluetoothState.text = getText(R.string.bluetoothOff)
        }
    }

    override fun onUserVisible() {
        super.onUserVisible()
        initUI()
        registerReceiver()
    }

    override fun onUserInvisible() {
        super.onUserInvisible()
        unRegisterReceiver()
    }

    override fun destroyViewAndThing() {
        super.destroyViewAndThing()
        unRegisterReceiver()
    }

    private fun registerReceiver() {
        val filter = IntentFilter()
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        context.registerReceiver(bluetoothStateReceiver, filter)
    }

    private fun unRegisterReceiver() {
        try {
            context.unregisterReceiver(bluetoothStateReceiver)
        } catch (e: IllegalArgumentException) {
            Log.w("Bluetooth", "The broadcast has been canceled")
        }
    }
}