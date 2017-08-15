// IUARTControl.aidl
package com.midea.fridge.servant.fridgecontrol;

// Declare any non-default types here with import statements
import com.midea.fridge.servant.fridgecontrol.IControlListener;

interface IUARTControl {
    // 执行冰箱控制命令，带参数和回调
    void exec(String cmd, in String[] params, IControlListener controlListener);
    // 执行冰箱控制命令，带回调
    void exec2(String cmd, IControlListener controlListener);
    // 执行冰箱控制命令，带参数
    void exec3(String cmd, in String[] params);
    // 执行冰箱控制命令，不带参数和回调
    void exec4(String cmd);
    /** 同步获取SN码的方法 */
    String getSN();
    /** 同步获取设备ID的方法 */
    String getDeviceId();
}
