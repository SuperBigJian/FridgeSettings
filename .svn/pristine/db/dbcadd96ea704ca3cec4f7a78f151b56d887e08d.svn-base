package com.midea.fridge.utils.config;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.midea.fridge.servant.config.IFridgeConfig;
import com.midea.fridge.utils.BaseRequestListener;
import com.midea.fridge.utils.IServiceReady;

/**
 * Created by chenjian on 17-6-20.
 */

public class FridgeConfigUtil {
    private static final String TAG = "FridgeConfigUtil";

    private static FridgeConfigUtil instance = new FridgeConfigUtil();
    private static Context context;
    private Handler handler;
    private Gson gson;
    private boolean isServiceConnected;
    private IFridgeConfig fridgeConfig;
    private DeviceInfo info = new DeviceInfo();

    public static FridgeConfigUtil getInstance() {
        return instance;
    }

    public void init(Context thatContext) {
        Log.i(TAG, "init");
        context = thatContext;
        handler = new Handler(Looper.getMainLooper());
        gson = new Gson();
        bindService();
    }

    private FridgeConfigUtil() {
    }

    public void serviceReady(IServiceReady serviceReady) {
        if (isServiceConnected) {
            serviceReady.onServiceReady();
        } else {
            bindService(serviceReady);
        }
    }

    private void onRequestSuccessOnUI(final BaseRequestListener requestListener, final String result) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    requestListener.onRequestSuccess(result);
                } catch (Exception e) {
                }
            }
        });
    }

    private void onRequestFailOnUI(final BaseRequestListener requestListener) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    requestListener.onRequestFail();
                } catch (Exception e) {
                }
            }
        });
    }

    private void bindService() {
        Log.i(TAG, "bindService");
        Intent intent = new Intent("com.midea.fridge.servant.config.ConfigService");
        intent.setClassName("com.midea.fridge.servant", "com.midea.fridge.servant.config.ConfigService");
        context.bindService(intent, new MyServiceConnection(), Context.BIND_AUTO_CREATE);
    }

    private void bindService(IServiceReady serviceReady) {
        Log.i(TAG, "bindService");
        Intent intent = new Intent("com.midea.fridge.servant.config.ConfigService");
        intent.setClassName("com.midea.fridge.servant", "com.midea.fridge.servant.config.ConfigService");
        context.bindService(intent, new MyServiceConnection(serviceReady), Context.BIND_AUTO_CREATE);
    }

    public DeviceInfo getDeviceInfo() {
        waitServiceReady();
        try {
            String json = fridgeConfig.getFridgeConfig();
            info = gson.fromJson(json, DeviceInfo.class);
        } catch (Exception e) {
            info.setCameraNum(-1);
            info.setDesignCode("error");
            info.setManufacturer("error");
            info.setName("error");
            info.setPadModel("error");
            info.setVaryRoomNum(-1);
            info.setWeightAreaNum(-1);
            e.printStackTrace();
        }
        return info;
    }

    public String getFridgePadModel() {
        if (info.getName() == null || "error".equals(info.getName())) {
            return getDeviceInfo().getPadModel();
        } else {
            return info.getPadModel();
        }
    }

    public int getVaryRoomNum() {
        if (info.getName() == null || "error".equals(info.getName())) {
            return getDeviceInfo().getVaryRoomNum();
        } else {
            return info.getVaryRoomNum();
        }
    }

    public String getFullName() {
        if (info.getName() == null || "error".equals(info.getName())) {
            return getDeviceInfo().getName();
        } else {
            return info.getName();
        }
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
            fridgeConfig = IFridgeConfig.Stub.asInterface(service);

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
