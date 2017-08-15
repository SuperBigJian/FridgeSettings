package com.midea.fridge.utils.fridgecontrolutil;

/**
 * Created by Administrator on 2016/11/3.
 */
public abstract class ReportListenerBase implements IReportListener{
    @Override
    public void onReportDoor(int doorId, int status) {
    }

    @Override
    public void onReport(FridgeFault fridgeFault) {
    }

    @Override
    public void onReport(FridgeStatus fridgeStatus) {
    }

    @Override
    public void onReport(String deviceId) {
    }

    @Override
    public void onReport(WeightData weightData) {
    }

    @Override
    public void onReportComplete(int completeId) {
    }
}
