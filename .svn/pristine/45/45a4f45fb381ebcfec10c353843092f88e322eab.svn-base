package com.midea.fridge.utils.update;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.midea.update.UpGradeManager;

/**
 * Created by chenjian on 17-6-20.
 */

public class FridgeUpdateUtil {
    private static final String TAG = "FridgeUpdateUtil";

    public static final String UPDATE_ACTION_CHECK_DOWNLOADED = "com.midea.update.downloaded";
    public static final String UPDATE_ACTION_CHECK_DOWNLOADED_EXTRA = "upGradeInfo";
    public static final String UPDATE_ACTION_CHECK_HAVE_NEW = "com.midea.update.havenewversion";
    public static final String UPDATE_ACTION_CHECK_NEWEST = "com.midea.update.nonewversion";
    public static final String UPDATE_ACTION_CHECK_FAILED = "com.midea.update.failed";
    public static final String UPDATE_ACTION_CHECK_FAILED_EXTRA = "failed_info";
    public static final String UPDATE_ACTION_DOWNLOAD_FINISH = "com.midea.update.download.finish";
    public static final String UPDATE_ACTION_DOWNLOAD_ERROR = "com.midea.update.download.error";
    public static final String UPDATE_ACTION_DOWNLOAD_STOP = "com.midea.update.download.stop";
    public static final String UPDATE_ACTION_DOWNLOAD_START = "com.midea.update.download.start";
    public static final String UPDATE_ACTION_DOWNLOAD_START_EXTRA = "fileSize";

    private static FridgeUpdateUtil instance = new FridgeUpdateUtil();
    private static Context context;
    private Handler handler;
    private boolean isServiceConnected;
    private UpGradeManager upGradeManager;

    public static FridgeUpdateUtil getInstance() {
        return instance;
    }

    public void init(Context thatContext) {
        Log.i(TAG, "init");
        context = thatContext;
        handler = new Handler(Looper.getMainLooper());
        bindService();
    }

    private FridgeUpdateUtil() {
    }


    private void bindService() {
        Log.i(TAG, "bindService");
        Intent intent = new Intent("com.midea.upgrade");
        intent.setClassName("com.midea.update", "com.midea.update.UpGradeService");
        context.bindService(intent, new MyServiceConnection(), Context.BIND_AUTO_CREATE);
    }

    /**
     * 使用锁机制等待服务连接完毕
     */
    private void waitServiceReady() {
        if (!isServiceConnected) {
            bindService();
        }
    }

    public void checkState() {
        waitServiceReady();
        try {
            upGradeManager.checkUpGrade();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void download() {
        waitServiceReady();
        try {
            upGradeManager.downLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startUpdate() {
        waitServiceReady();
        try {
            upGradeManager.install();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopUpdate() {
        waitServiceReady();
        try {
            upGradeManager.stopDownload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseUpdate() {
        waitServiceReady();
        try {
            upGradeManager.pauseDownload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getProgress() {
        waitServiceReady();
        try {
            return upGradeManager.getProgress();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private class MyServiceConnection implements ServiceConnection {

        public MyServiceConnection() {
        }


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected");
            isServiceConnected = true;
            upGradeManager = UpGradeManager.Stub.asInterface(service);
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
