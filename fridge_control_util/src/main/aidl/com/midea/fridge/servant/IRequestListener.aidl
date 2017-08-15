// IRequestListener.aidl
package com.midea.fridge.servant;

// Declare any non-default types here with import statements

interface IRequestListener {
    /**请求有返回结果*/
    void onRequestSuccess(String result);
    /**请求失败*/
    void onRequestFail(String error);
}
