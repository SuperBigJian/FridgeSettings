package com.midea.fridgesettings

import android.app.KeyguardManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.PowerManager
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import com.midea.fridge.utils.config.FridgeConfigUtil
import com.midea.fridge.utils.update.FridgeUpdateUtil
import com.midea.fridgesettings.utils.LogUtils
import com.midea.fridgesettings.wifi.WifiControl
import kotlinx.android.synthetic.main.fragment_update.*
import org.jetbrains.anko.*
import java.util.*


/**
 * Created by chenjian on 6/1/17.
 */
class UpdateFragment(type: FragmentTag = BaseFragment.FragmentTag.FRAGMENT_TAB_UPDATE) : BaseFragment(type) {
    private val TAG = "UpdateFragment"

    private val update = FridgeUpdateUtil.getInstance()
    private var downTimer: Timer? = null
    var isUpdating = false

    private var receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            tv_curState.visibility = View.VISIBLE
            iv_check.visibility = View.INVISIBLE
            iv_check.clearAnimation()
            btUpdate.isEnabled = true
            when (intent.action) {
                FridgeUpdateUtil.UPDATE_ACTION_CHECK_DOWNLOADED -> {
                    val data = intent.getStringExtra(FridgeUpdateUtil.UPDATE_ACTION_CHECK_DOWNLOADED_EXTRA)
                    Log.d(TAG, "sstang ----> downloaded $data")
                    tv_curState.textResource = R.string.you_can_update_now
                    btUpdate.textResource = R.string.update
                    btUpdate.setOnClickListener {
                        val intent = intentFor<OtaActivity>()
                        intent.putExtra("json", data)
                        intent.singleTop()
                        startActivity(intent)
                    }

                    val tab = fragmentManager.findFragmentById(R.id.tabFragment)
                    if (tab is TabFragment) {
                        tab.hasUpdate = true
                    }
                }

                FridgeUpdateUtil.UPDATE_ACTION_CHECK_HAVE_NEW -> {
                    Log.d(TAG, "sstang ----> havenewversion")

                    tv_curState.textResource = R.string.has_update_need_download
                    btUpdate.textResource = R.string.download
                    btUpdate.setOnClickListener {
                        iv_check.visibility = View.VISIBLE
                        iv_check.animation = getLoadingAnimation()
                        btUpdate.isEnabled = false
                        progress.progress = 0
                        update.download()
                    }
                }

                FridgeUpdateUtil.UPDATE_ACTION_CHECK_NEWEST -> {
                    Log.d(TAG, "sstang ----> nonewversion ")

                    tv_curState.textResource = R.string.no_update
                    btUpdate.textResource = R.string.check_update
                    btUpdate.setOnClickListener { checkForUpdate() }
                }

                FridgeUpdateUtil.UPDATE_ACTION_CHECK_FAILED -> {
                    val data = intent.getStringExtra(FridgeUpdateUtil.UPDATE_ACTION_CHECK_FAILED_EXTRA)
                    Log.d(TAG, "sstang ----> failed $data")
                    if (WifiControl.getConnectionInfo() == null) {
                        tv_curState.textResource = R.string.no_net
                    } else {
                        tv_curState.textResource = R.string.no_update
                    }
                    btUpdate.textResource = R.string.check_update
                    btUpdate.setOnClickListener { checkForUpdate() }
                }

                FridgeUpdateUtil.UPDATE_ACTION_DOWNLOAD_START -> {
                    val fileSize = intent.getIntExtra(FridgeUpdateUtil.UPDATE_ACTION_DOWNLOAD_START_EXTRA, 0)
                    Log.d(TAG, "sstang ----> onStart $fileSize")

                    tv_curState.textResource = R.string.downloading
                    btUpdate.textResource = R.string.cancelDownload
                    btUpdate.setOnClickListener {
                        iv_check.visibility = View.VISIBLE
                        iv_check.animation = getLoadingAnimation()
                        btUpdate.isEnabled = false
                        update.stopUpdate()
                    }

                    isUpdating = true
                    wakeUpAndUnlock()
                    startTimer()
                    progress.visibility = View.VISIBLE
                    progress.progress = 0
                }

                FridgeUpdateUtil.UPDATE_ACTION_DOWNLOAD_FINISH -> {
                    Log.d(TAG, "sstang ----> onFinish ")

                    tv_curState.textResource = R.string.downloadFinish
                    iv_check.visibility = View.VISIBLE
                    iv_check.animation = getLoadingAnimation()
                    btUpdate.isEnabled = false
                    update.checkState()

                    isUpdating = false
                    stopWakeUp()
                    stopTimer()
                    progress.progress = 100
                    progress.visibility = View.INVISIBLE
                }

                FridgeUpdateUtil.UPDATE_ACTION_DOWNLOAD_ERROR -> {
                    Log.d(TAG, "sstang ----> onError ")

                    progress.visibility = View.INVISIBLE
                    if (WifiControl.getConnectionInfo() == null) {
                        tv_curState.textResource = R.string.no_net
                        btUpdate.textResource = R.string.check_update
                        btUpdate.setOnClickListener {
                            iv_check.visibility = View.VISIBLE
                            iv_check.animation = getLoadingAnimation()
                            btUpdate.isEnabled = false
                            update.checkState()
                        }
                    } else {
                        tv_curState.textResource = R.string.download_fail
                        btUpdate.textResource = R.string.reDownload
                        btUpdate.setOnClickListener {
                            iv_check.visibility = View.VISIBLE
                            iv_check.animation = getLoadingAnimation()
                            btUpdate.isEnabled = false
                            progress.progress = 0
                            update.download()
                        }
                    }

                    isUpdating = false
                    stopWakeUp()
                    stopTimer()
                }

                FridgeUpdateUtil.UPDATE_ACTION_DOWNLOAD_STOP -> {
                    Log.d(TAG, "sstang ----> onStop ")

                    progress.visibility = View.INVISIBLE
                    if (WifiControl.getConnectionInfo() == null) {
                        tv_curState.textResource = R.string.no_net
                        btUpdate.textResource = R.string.check_update
                        btUpdate.setOnClickListener {
                            iv_check.visibility = View.VISIBLE
                            iv_check.animation = getLoadingAnimation()
                            btUpdate.isEnabled = false
                            update.checkState()
                        }
                    } else {
                        tv_curState.textResource = R.string.download_fail
                        btUpdate.textResource = R.string.reDownload
                        btUpdate.setOnClickListener {
                            iv_check.visibility = View.VISIBLE
                            iv_check.animation = getLoadingAnimation()
                            btUpdate.isEnabled = false
                            progress.progress = 0
                            update.download()
                        }
                    }

                    isUpdating = false
                    stopWakeUp()
                    stopTimer()
                }

            }

        }
    }

    override fun getContentViewLayoutId(): Int = R.layout.fragment_update

    override fun initViewsAndEvents() {
        super.initViewsAndEvents()
        initView()
        checkForUpdate()
    }

    private fun initView() {
        pic_fridge.imageResource = BuildConfig.FRIDGE_PIC
        device.text = FridgeConfigUtil.getInstance().fullName
        version.text = android.os.Build.DISPLAY
        progress.max = 100
        btUpdate.setOnClickListener { checkForUpdate() }
    }

    fun checkForUpdate() {
        tv_curState.visibility = View.VISIBLE
        progress.visibility = View.INVISIBLE
        if (WifiControl.getConnectionInfo() == null) {
            tv_curState.textResource = R.string.no_net
            btUpdate.isEnabled = true
            btUpdate.textResource = R.string.check_update
        } else {
            tv_curState.textResource = R.string.checking
            iv_check.visibility = View.VISIBLE
            iv_check.animation = getLoadingAnimation()
            btUpdate.isEnabled = false
            update.checkState()
        }
    }

    override fun onUserVisible() {
        super.onUserVisible()
        registerReceiver()
        if (isUpdating) {
            tv_curState.textResource = R.string.downloading
            btUpdate.textResource = R.string.cancelDownload
            btUpdate.setOnClickListener {
                iv_check.visibility = View.VISIBLE
                iv_check.animation = getLoadingAnimation()
                btUpdate.isEnabled = false
                update.stopUpdate()
            }
            startTimer()
        }
    }

    override fun onUserInvisible() {
        super.onUserInvisible()
        stopTimer()
    }

    override fun destroyViewAndThing() {
        unRegisterReceiver()
        stopTimer()
        update.stopUpdate()
        super.destroyViewAndThing()
    }

    private fun startTimer() {
        downTimer = Timer()
        downTimer?.schedule(object : java.util.TimerTask() {

            override fun run() {
                runOnUiThread {
                    progress.progress = update.progress
                }
            }
        }, 5000, 20000)
    }

    private fun stopTimer() {
        if (downTimer != null) {
            downTimer?.purge()
            downTimer?.cancel()
            downTimer = null
        }
    }

    private fun getLoadingAnimation(): Animation {
        val loadingAnim = AnimationUtils.loadAnimation(context, R.anim.am_wifi_laoding)
        loadingAnim.interpolator = LinearInterpolator()
        return loadingAnim
    }

    private fun registerReceiver() {
        val filter = IntentFilter()
        filter.addAction(FridgeUpdateUtil.UPDATE_ACTION_CHECK_DOWNLOADED)
        filter.addAction(FridgeUpdateUtil.UPDATE_ACTION_CHECK_HAVE_NEW)
        filter.addAction(FridgeUpdateUtil.UPDATE_ACTION_CHECK_NEWEST)
        filter.addAction(FridgeUpdateUtil.UPDATE_ACTION_CHECK_FAILED)
        filter.addAction(FridgeUpdateUtil.UPDATE_ACTION_DOWNLOAD_FINISH)
        filter.addAction(FridgeUpdateUtil.UPDATE_ACTION_DOWNLOAD_ERROR)
        filter.addAction(FridgeUpdateUtil.UPDATE_ACTION_DOWNLOAD_STOP)
        filter.addAction(FridgeUpdateUtil.UPDATE_ACTION_DOWNLOAD_START)
        context.registerReceiver(receiver, filter)
    }

    private fun unRegisterReceiver() {
        try {
            context.unregisterReceiver(receiver)
        } catch (e: IllegalArgumentException) {
            Log.w("WIFIX", "The broadcast has been canceled")
        }
    }

    private val km by lazy { activity.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager }
    private val pm by lazy { activity.getSystemService(Context.POWER_SERVICE) as PowerManager }
    //获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
    private val mWl: PowerManager.WakeLock by lazy { pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright") }

    /**
     * 点亮屏幕，并且常亮
     */
    private fun wakeUpAndUnlock() {
        LogUtils.Logi(TAG, "wakeUpAndUnlock")
        val kl = km.newKeyguardLock("unLock")
        //解锁
        kl.disableKeyguard()
        mWl.setReferenceCounted(false)
        mWl.acquire()
    }

    /**
     * 取消常亮
     */
    private fun stopWakeUp() {
        LogUtils.Logi(TAG, "stopWakeUp")
        if (mWl.isHeld) {
            LogUtils.Logi(TAG, "stopWakeUp, release")
            mWl.release()
        }
    }
}