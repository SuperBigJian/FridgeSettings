package com.midea.fridge.utils.fridgecontrolutil;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.midea.fridge.servant.fridgecontrol.IPadControl;
import com.midea.fridge.utils.IServiceReady;

/**
 * Created by chenjian on 17-6-20.
 */

public class PadControlUtil {
    private static final String TAG = "PadControlUtil";

    private static PadControlUtil instance = new PadControlUtil();
    private static Context context;
    private Handler handler;
    private Gson gson;
    private boolean isServiceConnected;
    private IPadControl padControl;

    public static PadControlUtil getInstance() {
        return instance;
    }

    public void init(Context thatContext) {
        Log.i(TAG, "init");
        context = thatContext;
        handler = new Handler(Looper.getMainLooper());
        gson = new Gson();
        bindService();
    }

    private PadControlUtil() {
    }
    public void disableIR() {
        waitServiceReady();
        try {
            padControl.disableIR();
        } catch (Exception e) {
            Log.w(TAG, "disableIR Exception");
        }
    }

    public void enableIR() {
        waitServiceReady();
        try {
            padControl.enableIR();
        } catch (Exception e) {
            Log.w(TAG, "enableIR Exception");
        }
    }

    public void setIR(int value) {
        waitServiceReady();
        try {
            padControl.setIR(value);
        } catch (Exception e) {
            Log.w(TAG, "setIR Exception");
        }
    }

    public int getIR() throws Exception {
        waitServiceReady();
        try {
            return padControl.getIR();
        } catch (Exception e) {
            Log.w(TAG, "getIR Exception");
            return -1;
        }
    }

    private void bindService() {
        Log.i(TAG, "bindService");
        Intent intent = new Intent("com.midea.fridge.servant.fridgecontrol.PadControlService");
        intent.setClassName("com.midea.fridge.servant", "com.midea.fridge.servant.fridgecontrol.PadControlService");
        context.bindService(intent, new MyServiceConnection(), Context.BIND_AUTO_CREATE);
    }

    private void bindService(IServiceReady serviceReady) {
        Log.i(TAG, "bindService");
        Intent intent = new Intent("com.midea.fridge.servant.fridgecontrol.PadControlService");
        intent.setClassName("com.midea.fridge.servant", "com.midea.fridge.servant.fridgecontrol.PadControlService");
        context.bindService(intent, new MyServiceConnection(serviceReady), Context.BIND_AUTO_CREATE);
    }

    /**
     * 使用锁机制等待服务连接完毕
     */
    private void waitServiceReady() {
        if (!isServiceConnected) {
            bindService();
        }
    }

    private class MyServiceConnection implements ServiceConnection {
        private IServiceReady serviceReady;

        public MyServiceConnection() {
        }

        public MyServiceConnection(IServiceReady serviceReady) {
            this.serviceReady = serviceReady;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected");
            isServiceConnected = true;
            padControl = IPadControl.Stub.asInterface(service);

            if (null != serviceReady) {
                serviceReady.onServiceReady();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected");
            isServiceConnected = false;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bindService();
                }
            }, 60000);
        }
    }
}
