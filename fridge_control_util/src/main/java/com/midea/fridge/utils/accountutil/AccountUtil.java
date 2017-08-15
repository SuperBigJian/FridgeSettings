package com.midea.fridge.utils.accountutil;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.google.gson.Gson;
import com.midea.fridge.servant.IRequestListener;
import com.midea.fridge.servant.account.IFridgeAccount;
import com.midea.fridge.utils.BaseRequestListener;
import com.midea.fridge.utils.IServiceReady;

/**
 * Created by Administrator on 2016/11/7.
 */
public class AccountUtil {
    private static final String TAG = "AccountUtil";

    private static AccountUtil instance = new AccountUtil();
    private static Context context;
    private Handler handler;
    private Gson gson;
    private boolean isServiceConnected;
    private IFridgeAccount fridgeAccount;

    public static AccountUtil getInstance() {
        return instance;
    }

    public void init(Context thatContext) {
        Log.i(TAG, "init");
        context = thatContext;
        handler = new Handler(Looper.getMainLooper());
        gson = new Gson();
        bindService();
    }

    private AccountUtil(){
    }

    public void serviceReady(IServiceReady serviceReady) {
        if(isServiceConnected) {
            serviceReady.onServiceReady();
        } else {
            bindService(serviceReady);
        }
    }

    public LoginInfo getLoginInfo() {
        waitServiceReady();
        try {
            String json = fridgeAccount.getLoginInfo();
            LoginInfo loginInfo = gson.fromJson(json, LoginInfo.class);
            return loginInfo;
        } catch (Exception e) {
            Log.w(TAG, "getLoginInfo Exception");
            return null;
        }
    }

    public void login(String userName, String password, final BaseRequestListener requestListener) {
        waitServiceReady();
        try {
            fridgeAccount.login(userName, password, new IRequestListener.Stub(){
                @Override
                public void onRequestSuccess(String result) throws RemoteException {
                    Log.i(TAG, "login, onRequestSuccess");
                    onRequestSuccessOnUI(requestListener, result);
                }

                @Override
                public void onRequestFail(String error) throws RemoteException {
                    onRequestFailOnUI(requestListener);
                }
            });
        } catch (Exception e) {
            Log.w(TAG, "login Exception");
            onRequestFailOnUI(requestListener);
        }
    }

    public void loginByFace(byte[] data, final BaseRequestListener requestListener) {
        waitServiceReady();
        try {
            fridgeAccount.loginByFace(data, new IRequestListener.Stub() {
                @Override
                public void onRequestSuccess(String result) throws RemoteException {
                    Log.i(TAG, "login, onRequestSuccess");
                    onRequestSuccessOnUI(requestListener, result);
                }

                @Override
                public void onRequestFail(String error) throws RemoteException {
                    onRequestFailOnUI(requestListener);
                }
            });
        } catch (Exception e) {
            Log.w(TAG, "loginByFace Exception");
            onRequestFailOnUI(requestListener);
        }
    }

    public void logout() {
        Log.i(TAG, "logout");
        waitServiceReady();
        try {
            fridgeAccount.logout();
        } catch (Exception e) {
            Log.w(TAG, "logout Exception");
        }
    }

    public void checkUserName(String userName, final BaseRequestListener requestListener) {
        waitServiceReady();
        try {
            fridgeAccount.checkUserName(userName, new IRequestListener.Stub(){
                @Override
                public void onRequestSuccess(String result) throws RemoteException {
                    onRequestSuccessOnUI(requestListener, result);
                }

                @Override
                public void onRequestFail(String error) throws RemoteException {
                    onRequestFailOnUI(requestListener);
                }
            });
        } catch (Exception e) {
            Log.w(TAG, "login Exception");
            onRequestFailOnUI(requestListener);
        }
    }

    public void fetchCaptcha(String userName, int versionCode, String padModel, final BaseRequestListener requestListener) {
        waitServiceReady();
        try {
            fridgeAccount.fetchCaptcha(userName, versionCode, padModel, new IRequestListener.Stub() {
                @Override
                public void onRequestSuccess(String result) throws RemoteException {
                    onRequestSuccessOnUI(requestListener, result);
                }

                @Override
                public void onRequestFail(String error) throws RemoteException {
                    onRequestFailOnUI(requestListener);
                }
            });
        } catch (Exception e) {
            Log.w(TAG, "fetchCaptcha Exception");
            onRequestFailOnUI(requestListener);
        }
    }

    public void checkCaptcha(String userName, String captcha, final BaseRequestListener requestListener) {
        waitServiceReady();
        try {
            fridgeAccount.checkCaptcha(userName, captcha, new IRequestListener.Stub() {
                @Override
                public void onRequestSuccess(String result) throws RemoteException {
                    onRequestSuccessOnUI(requestListener, result);
                }

                @Override
                public void onRequestFail(String error) throws RemoteException {
                    onRequestFailOnUI(requestListener);
                }
            });
        } catch (Exception e) {
            Log.w(TAG, "checkCaptcha Exception");
            onRequestFailOnUI(requestListener);
        }
    }

    public void register(String userName, String password, String captcha, final BaseRequestListener requestListener) {
        waitServiceReady();
        try {
            fridgeAccount.register(userName, password, captcha, new IRequestListener.Stub() {
                @Override
                public void onRequestSuccess(String result) throws RemoteException {
                    onRequestSuccessOnUI(requestListener, result);
                }

                @Override
                public void onRequestFail(String error) throws RemoteException {
                    onRequestFailOnUI(requestListener);
                }
            });
        } catch (Exception e) {
            Log.w(TAG, "register Exception");
            onRequestFailOnUI(requestListener);
        }
    }

    public void resetPassword(String userName, String password, String captcha, final BaseRequestListener requestListener) {
        waitServiceReady();
        try {
            fridgeAccount.resetPassword(userName, password, captcha, new IRequestListener.Stub() {
                @Override
                public void onRequestSuccess(String result) throws RemoteException {
                    onRequestSuccessOnUI(requestListener, result);
                }

                @Override
                public void onRequestFail(String error) throws RemoteException {
                    onRequestFailOnUI(requestListener);
                }
            });
        } catch (Exception e) {
            Log.w(TAG, "resetPassword Exception");
            onRequestFailOnUI(requestListener);
        }
    }

    public void modifyPassword(String oldPassword, String newPassword, final BaseRequestListener requestListener) {
        waitServiceReady();
        try {
            fridgeAccount.modifyPassword(oldPassword, newPassword, new IRequestListener.Stub() {
                @Override
                public void onRequestSuccess(String result) throws RemoteException {
                    onRequestSuccessOnUI(requestListener, result);
                }

                @Override
                public void onRequestFail(String error) throws RemoteException {
                    onRequestFailOnUI(requestListener);
                }
            });
        } catch (Exception e) {
            Log.w(TAG, "modifyPassword Exception");
            onRequestFailOnUI(requestListener);
        }
    }

    public void refreshToken() {
        waitServiceReady();
        try {
            fridgeAccount.refreshToken();
        } catch (Exception e) {
            Log.w(TAG, "refreshToken Exception");
        }
    }

    public void cancelAllRequest() {
        waitServiceReady();
        try {
            fridgeAccount.cancelAllRequest();
        } catch (Exception e) {
            Log.w(TAG, "cancelAllRequest Exception");
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
        Intent intent = new Intent("com.midea.fridge.servant.account.AccountService");
        intent.setClassName("com.midea.fridge.servant", "com.midea.fridge.servant.account.AccountService");
        context.bindService(intent, new MyServiceConnection(), Context.BIND_AUTO_CREATE);
    }

    private void bindService(IServiceReady serviceReady) {
        Log.i(TAG, "bindService");
        Intent intent = new Intent("com.midea.fridge.servant.account.AccountService");
        intent.setClassName("com.midea.fridge.servant", "com.midea.fridge.servant.account.AccountService");
        context.bindService(intent, new MyServiceConnection(serviceReady), Context.BIND_AUTO_CREATE);
    }

    /** 使用锁机制等待服务连接完毕 */
    private void waitServiceReady() {
        if(!isServiceConnected) {
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
            fridgeAccount = IFridgeAccount.Stub.asInterface(service);

            if(null != serviceReady) {
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
