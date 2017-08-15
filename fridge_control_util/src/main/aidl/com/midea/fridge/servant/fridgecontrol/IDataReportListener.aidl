// IDataReportListener.aidl
package com.midea.fridge.servant.fridgecontrol;

// Declare any non-default types here with import statements

interface IDataReportListener {
    /**开关门上报*/
    void onReportDoorStatus(int msgCode);
    /**故障上报*/
    void onReportFridgeFault(String fridgeFault);
    /**冰箱ID上报*/
    void onReportFridgeId(String fridgeId);
    /**冰箱基本状态上报*/
    void onReportFridgeStatus(String fridgeStatus);
}
