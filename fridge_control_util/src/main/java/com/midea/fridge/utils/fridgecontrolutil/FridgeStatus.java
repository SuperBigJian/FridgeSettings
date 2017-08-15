package com.midea.fridge.utils.fridgecontrolutil;

/**
 * Created by Administrator on 2016/9/2.
 */
public class FridgeStatus {
    // 冷藏设定温度
    private int coldTemperatureStep;
    // 冷冻设定温度
    private int freezeTemperatureStep;
    // 冷藏室关闭
    private boolean isColdRoomClose;
    // 左变温关闭
    private boolean isLeftChangeRoomClose;
    // 右变温关闭
    private boolean isRightChangeRoomClose;
    // 冷冻室关闭
    private boolean isFreezeRoomClose;

    private boolean isFreezeFast;// 速冻
    private boolean isSmartMode;// 智能
    private boolean isIcemaking;// 制冰
    private boolean isColdFast;  //速冷模式
    private boolean isHighHumidty; //高保湿

    // 冷冻当前温度
    private int freezeTempreture;
    // 冷藏当前温度
    private int coldTempreture;
    // 变温室功能模式
    private int changeRoomMode;
    // 左变温室温度档位
    private int leftChangeTemperatureStep;
    // 右变温室温度档位
    private int rightChangeTemperatureStep;
    // 左变温室实际温度
    private int leftChangeTemperature;
    // 右变温室实际温度
    private int rightChangeTemperature;
    // 雷达感温
    private boolean radarMode;
    // 光波保鲜
    private boolean lightWave;
    // 假日
    private boolean holidayMode;
    // 等离子除菌
    private boolean plasmaMode;
    // 冰镇模式
    private boolean icedMode;

    // 商演模式
    private boolean isShowMode;
    // 是否湿度自动控制
    private boolean isHumidityAuto;
    // 湿度值
    private int humidity;

    public int getColdTemperatureStep() {
        return coldTemperatureStep;
    }

    public void setColdTemperatureStep(int coldTemperatureStep) {
        this.coldTemperatureStep = coldTemperatureStep;
    }

    public int getFreezeTemperatureStep() {
        return freezeTemperatureStep;
    }

    public void setFreezeTemperatureStep(int freezeTemperatureStep) {
        this.freezeTemperatureStep = freezeTemperatureStep;
    }

    public boolean isColdRoomClose() {
        return isColdRoomClose;
    }

    public void setColdRoomClose(boolean coldRoomClose) {
        isColdRoomClose = coldRoomClose;
    }

    public boolean isLeftChangeRoomClose() {
        return isLeftChangeRoomClose;
    }

    public void setLeftChangeRoomClose(boolean leftChangeRoomClose) {
        isLeftChangeRoomClose = leftChangeRoomClose;
    }

    public boolean isRightChangeRoomClose() {
        return isRightChangeRoomClose;
    }

    public void setRightChangeRoomClose(boolean rightChangeRoomClose) {
        isRightChangeRoomClose = rightChangeRoomClose;
    }

    public boolean isFreezeRoomClose() {
        return isFreezeRoomClose;
    }

    public void setFreezeRoomClose(boolean freezeRoomClose) {
        isFreezeRoomClose = freezeRoomClose;
    }

    public boolean isFreezeFast() {
        return isFreezeFast;
    }

    public void setFreezeFast(boolean freezeFast) {
        isFreezeFast = freezeFast;
    }

    public boolean isSmartMode() {
        return isSmartMode;
    }

    public void setSmartMode(boolean smartMode) {
        isSmartMode = smartMode;
    }

    public boolean isIcemaking() {
        return isIcemaking;
    }

    public void setIcemaking(boolean icemaking) {
        isIcemaking = icemaking;
    }

    /**
     * 变温室功能模式
     * 0x01；软冷冻模式
     * 0x02；零度保鲜
     * 0x03；冷饮
     * 0x04；果蔬
     * 0X05: 微冻
     * 0x06：干区
     * */
    public int getChangeRoomMode() {
        return changeRoomMode;
    }

    public void setChangeRoomMode(int changeRoomMode) {
        this.changeRoomMode = changeRoomMode;
    }

    public boolean isColdFast() {
        return isColdFast;
    }

    public void setColdFast(boolean coldFast) {
        isColdFast = coldFast;
    }

    public boolean isHighHumidty() {
        return isHighHumidty;
    }

    public void setHighHumidty(boolean highHumidty) {
        isHighHumidty = highHumidty;
    }

    public int getFreezeTempreture() {
        return freezeTempreture;
    }

    public void setFreezeTempreture(int freezeTempreture) {
        this.freezeTempreture = freezeTempreture;
    }

    public int getColdTempreture() {
        return coldTempreture;
    }

    public void setColdTempreture(int coldTempreture) {
        this.coldTempreture = coldTempreture;
    }

    public int getLeftChangeTemperatureStep() {
        return leftChangeTemperatureStep;
    }

    public void setLeftChangeTemperatureStep(int leftChangeTemperatureStep) {
        this.leftChangeTemperatureStep = leftChangeTemperatureStep;
    }

    public int getRightChangeTemperatureStep() {
        return rightChangeTemperatureStep;
    }

    public void setRightChangeTemperatureStep(int rightChangeTemperatureStep) {
        this.rightChangeTemperatureStep = rightChangeTemperatureStep;
    }

    public int getLeftChangeTemperature() {
        return leftChangeTemperature;
    }

    public void setLeftChangeTemperature(int leftChangeTemperature) {
        this.leftChangeTemperature = leftChangeTemperature;
    }

    public int getRightChangeTemperature() {
        return rightChangeTemperature;
    }

    public void setRightChangeTemperature(int rightChangeTemperature) {
        this.rightChangeTemperature = rightChangeTemperature;
    }

    public boolean isRadarMode() {
        return radarMode;
    }

    public void setRadarMode(boolean radarMode) {
        this.radarMode = radarMode;
    }

    public boolean isLightWave() {
        return lightWave;
    }

    public void setLightWave(boolean lightWave) {
        this.lightWave = lightWave;
    }

    public boolean isHolidayMode() {
        return holidayMode;
    }

    public void setHolidayMode(boolean holidayMode) {
        this.holidayMode = holidayMode;
    }

    public boolean isPlasmaMode() {
        return plasmaMode;
    }

    public void setPlasmaMode(boolean plasma) {
        this.plasmaMode = plasma;
    }

    public boolean isIcedMode() {
        return icedMode;
    }

    public void setIcedMode(boolean icedMode) {
        this.icedMode = icedMode;
    }

    public boolean isShowMode() {
        return isShowMode;
    }

    public void setShowMode(boolean showMode) {
        isShowMode = showMode;
    }

    public boolean isHumidityAuto() {
        return isHumidityAuto;
    }

    public void setHumidityAuto(boolean humidityAuto) {
        isHumidityAuto = humidityAuto;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "FridgeStatus{" +
                "coldTemperatureStep=" + coldTemperatureStep +
                ", freezeTemperatureStep=" + freezeTemperatureStep +
                ", isColdRoomClose=" + isColdRoomClose +
                ", isLeftChangeRoomClose=" + isLeftChangeRoomClose +
                ", isRightChangeRoomClose=" + isRightChangeRoomClose +
                ", isFreezeRoomClose=" + isFreezeRoomClose +
                ", isFreezeFast=" + isFreezeFast +
                ", isSmartMode=" + isSmartMode +
                ", isIcemaking=" + isIcemaking +
                ", isColdFast=" + isColdFast +
                ", isHighHumidty=" + isHighHumidty +
                ", changeRoomMode=" + changeRoomMode +
                ", freezeTempreture=" + freezeTempreture +
                ", coldTempreture=" + coldTempreture +
                ", leftChangeTemperatureStep=" + leftChangeTemperatureStep +
                ", rightChangeTemperatureStep=" + rightChangeTemperatureStep +
                ", leftChangeTemperature=" + leftChangeTemperature +
                ", rightChangeTemperature=" + rightChangeTemperature +
                ", radarMode=" + radarMode +
                ", lightWave=" + lightWave +
                ", holidayMode=" + holidayMode +
                ", plasmaMode=" + plasmaMode +
                ", icedMode=" + icedMode +
                '}';
    }
}
