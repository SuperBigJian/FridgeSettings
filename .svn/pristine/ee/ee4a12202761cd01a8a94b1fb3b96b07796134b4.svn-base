package com.midea.fridge.utils.fridgecontrolutil;

/**
 * Created by Administrator on 2016/10/20.
 */
public interface IReportListener {
    /**开关门上报*/
    void onReportDoor(int doorId, int status);
    /**故障上报*/
    void onReport(FridgeFault fridgeFault);
    /**冰箱状态上报*/
    void onReport(FridgeStatus fridgeStatus);
    /**冰箱id上报*/
    void onReport(String deviceId);
    /**称重数据上报*/
    void onReport(WeightData weightData);
    /**功能完成上报（速冷或速冻）*/
    void onReportComplete(int completeId);
}
