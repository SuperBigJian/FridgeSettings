package com.midea.fridgesettings

import android.app.Application
import com.midea.fridge.utils.accountutil.AccountUtil
import com.midea.fridge.utils.config.FridgeConfigUtil
import com.midea.fridge.utils.fridgecontrolutil.FridgeControlUtil
import com.midea.fridge.utils.fridgecontrolutil.PadControlUtil
import com.midea.fridge.utils.update.FridgeUpdateUtil
import com.midea.fridgesettings.wifi.WifiControl

/**
 * Created by chenjian on 17-6-20.
 */
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        WifiControl.initialize(this)
        FridgeUpdateUtil.getInstance().init(this)
        FridgeConfigUtil.getInstance().init(this)
        FridgeControlUtil.getInstance().init(this)
        PadControlUtil.getInstance().init(this)
        AccountUtil.getInstance().init(this)
    }
}