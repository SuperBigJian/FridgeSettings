package com.midea.fridge.utils.fridgecontrolutil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/10/20.
 */
public class FridgeControlReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String data = intent.getStringExtra("data");
        if(null != FridgeControlUtil.getInstance()) {
            FridgeControlUtil.getInstance().onReceive(action, data);
        }
    }
}
