package com.midea.fridgesettings

import android.animation.ValueAnimator
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.PowerManager
import android.os.SystemClock
import android.view.View
import com.midea.fridge.utils.config.FridgeConfigUtil
import com.midea.fridge.utils.fridgecontrolutil.FridgeControlUtil
import com.midea.fridgesettings.utils.LogUtils
import com.midea.fridgesettings.view.CustomDialog
import com.midea.fridgesettings.view.SwitchEntry
import com.midea.fridgesettings.wifi.WifiControl
import kotlinx.android.synthetic.main.fragment_about.*
import org.jetbrains.anko.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by chenjian on 6/1/17.
 */
class AboutFragment(type: FragmentTag = BaseFragment.FragmentTag.FRAGMENT_TAB_ABOUT) : BaseFragment(type) {

    val arrowAnim: ValueAnimator = ValueAnimator.ofFloat(0f, 180f)
    var isRotated = false

    override fun getContentViewLayoutId(): Int = R.layout.fragment_about

    override fun initViewsAndEvents() {
        super.initViewsAndEvents()
        tvFriModel.text = FridgeConfigUtil.getInstance().fullName
        rowFriModel.visibility = View.GONE //屏蔽冰箱型号
        tvDeviceId.text = FridgeControlUtil.getInstance().deviceId
        tvSysVersion.text = android.os.Build.DISPLAY

        idTable.setOnClickListener {
            openHiddenFunction(mADBHints, idTable)
        }

        verTable.setOnClickListener {
            openHiddenFunction(mDevHints, verTable)
        }

        initSwitch(kerLog, R.string.kernel_log)
        initSwitch(sysLog, R.string.system_log)

        arrowAnim.duration = 300
        arrowAnim.addUpdateListener { animation ->
            val currentValue = animation.animatedValue as Float
            ivArrow.rotationX = if (isRotated) 180 - currentValue else currentValue
        }

        expandItem.collapse()
        expandItem.setDuration(300)
        llLog.setOnClickListener {
            expandItem.toggle()
            isRotated = ivArrow.rotationX > 90
            arrowAnim.start()

        }

        recovery.setOnClickListener {
            val builder = CustomDialog.Builder(activity)
            builder.setMessage(R.string.recovery_message)
            builder.setPositiveButton(null, object : CustomDialog.OnClickListener {
                override fun onClick(dialog: Dialog) {
                    activity.sendBroadcast(Intent("android.intent.action.MASTER_CLEAR"))
                }
            }).setNegativeButton(null, null)

            builder.createTwoButtonDialog().show()
        }

        reStart.setOnClickListener {
            val builder = CustomDialog.Builder(activity)
            builder.setMessage(R.string.restart_message)
            builder.setPositiveButton(null, object : CustomDialog.OnClickListener {
                override fun onClick(dialog: Dialog) {
                    val pm = activity.getSystemService(Context.POWER_SERVICE) as PowerManager
                    pm.reboot("")
                }
            }).setNegativeButton(null, null)
            builder.createTwoButtonDialog().show()
        }
    }

    private fun initSwitch(view: SwitchEntry, res: Int) {
        with(view) {
            tvTitle.textSize = 24f
            tvTitle.textColor = Color.rgb(103, 107, 108)
            tvTitle.textResource = res
            switch.setOnCheckedChangeListener { _, isChecked -> onSwitchChange(view, isChecked) }
        }
    }

    private fun onSwitchChange(view: SwitchEntry, isChecked: Boolean) {
        when (view.id) {

            R.id.kerLog -> {
                if (isChecked) {
                    LogUtils.getInstance(context).startKernelLog()
                } else {
                    LogUtils.getInstance(context).stopKernelLog()
                }
            }

            R.id.sysLog -> {
                if (isChecked) {
                    LogUtils.getInstance(context).startAppLog()
                } else {
                    LogUtils.getInstance(context).stopAppLog()
                }
            }
        }
    }

    var mDevHints = Array<Long>(10, { _ -> 0 })
    var mADBHints = Array<Long>(10, { _ -> 0 })

    private fun openHiddenFunction(hints: Array<Long>, view: View) {
        System.arraycopy(hints, 1, hints, 0, hints.size - 1)
        hints[hints.size - 1] = SystemClock.uptimeMillis()
        if (SystemClock.uptimeMillis() - hints[0] <= 3000) {
            when (view) {
                idTable -> openRemoteDebug()

                verTable -> openDeveloperMode()
            }
        }
    }

    private fun openRemoteDebug() {
        val ipAddress = WifiControl.getConnectionInfo()?.ipAddress ?: 0
        val ip = WifiControl.intToIp(ipAddress)

        for (i in mADBHints.indices) {
            mADBHints[i] = 0
        }

        Thread {
            val str1 = exec("setprop service.adb.tcp.port 5555")
            val str2 = exec("stop adbd")
            val str3 = exec("start adbd")
            LogUtils.Loge("chenjian", "$str1 \n $str2 \n $str3")
            activity.runOnUiThread {
                toast("IP is $ip")
            }
        }.start()
    }

    private fun exec(command: String): String {
        try {
            val process = Runtime.getRuntime().exec(command)
            val reader = BufferedReader(
                    InputStreamReader(process.inputStream))
            val buffer = CharArray(4096)
            val output = StringBuffer()
            while (reader.read(buffer) > 0) {
                output.append(buffer)
            }
            reader.close()
            process.waitFor()
            return output.toString()
        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: InterruptedException) {
            throw RuntimeException(e)
        }
    }

    private fun openDeveloperMode() {
        val intent = intentFor<DeveloperActivity>()
        intent.singleTop()
        startActivity(intent)
    }

    override fun onUserVisible() {
        super.onUserVisible()
        ivArrow.imageResource = if (expandItem.isExpanded) R.mipmap.ic_arrow_up else R.mipmap.ic_arrow_down

        kerLog.switch.isChecked = LogUtils.getInstance(context).isKernelLogRunning
        sysLog.switch.isChecked = LogUtils.getInstance(context).isAppLogRunning
    }

    override fun onUserInvisible() {
        super.onUserInvisible()
    }

    override fun destroyViewAndThing() {
        super.destroyViewAndThing()
    }
}

