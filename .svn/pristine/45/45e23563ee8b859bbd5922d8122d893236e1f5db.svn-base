package com.midea.fridge.utils.log;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.midea.fridge.servant.log.ILogServant;

/**
 * Created by Administrator on 2017/5/23.
 */

public class LogManager {
    private static final String TAG = "LogManager";

    private static Context context;
    private ILogServant logServant;
    private boolean isServiceConnected;

    private static class SingletonHolder {
        private static final LogManager INSTANCE = new LogManager();
    }

    private LogManager(){}

    public static final LogManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public void init(Context thatContext) {
        Log.i(TAG, "init");
        context = thatContext;
        bindService();
    }

    public void addScreenLog(String screenStatus, int type) {
        if(!isServiceConnected) {
            bindService();
            return;
        }
        try {
            logServant.addScreenLog(screenStatus, type);
        } catch (Exception e) {
            Log.w(TAG, "addScreenLog Exception");
        }
    }

    private void bindService() {
        Log.i(TAG, "bindService");
        Intent intent = new Intent("com.midea.fridge.servant.LOG");
        intent.setClassName("com.midea.fridge.servant", "com.midea.fridge.servant.log.LogService");
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "onServiceConnected");
                isServiceConnected = true;
                logServant = ILogServant.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG, "onServiceDisconnected");
                isServiceConnected = false;
            }
        }, Context.BIND_AUTO_CREATE);
    }
}
