// ICommunication.aidl
package com.midea.fridge.servant.fridgecontrol;

// Declare any non-default types here with import statements
import com.midea.fridge.servant.fridgecontrol.IDataReportListener;
import com.midea.fridge.servant.fridgecontrol.IControlListener;

/**
* UART设备串口通信相关操作
* */
interface ICommunication {
    /** 异步查询SN */
    void querySN(IControlListener controlListener);
    /** 查询冰箱状态 */
    void queryFridgeStatus(IControlListener controlListener);
    /** 查询冰箱扩展状态 */
    void queryFridgeExtendStatus(IControlListener controlListener);
    /** 开启强制化霜 */
    void forceDefrosting(IControlListener controlListener);
    /** 开启强制运行 */
    void forceRun(IControlListener controlListener);
    /** 关闭强制化霜和强制运行 */
    void disableDefrostingAndRun(IControlListener controlListener);
    /** 查询冰箱故障 */
    void queryFridgeFault(IControlListener controlListener);
    /** 设置温度 */
    void setRoomTemperature(int roomId, int temperature, IControlListener controlListener);
    /** 启动功能模式 */
    void enableMode(int modeType, IControlListener controlListener);
    /** 关闭功能模式 */
    void disableMode(int modeType, IControlListener controlListener);
    /** 开启箱室 */
    void switchOn(int roomId, IControlListener controlListener);
    /** 关闭箱室 */
    void shutDown(int roomId, IControlListener controlListener);
    /** 开启或关闭手把灯 */
    void setHandleLight(int value);
    /** 称重去皮功能，返回值为负代表操作失败 */
    int zeroWeigh(int value);
    /** 同步获取SN码的方法 */
    String getSN();
    /** 同步获取设备ID的方法 */
    String getDeviceId();
    /** 设置变温室功能模式 */
    void setVtMode(int mode, IControlListener controlListener);
    /** 检查称重模块 */
    void weight(IControlListener controlListener);
    /** 开启或关闭商演模式 */
    void setShow(int value, IControlListener controlListener);
    /** 开启数据上报 */
    int setReportMode(int mode, int interval);
}
