package com.midea.fridge.utils.fridgecontrolutil;

/**
 * 查询或控制的返回监听
 * Created by Administrator on 2016/9/7.
 */
public interface OnControlListener<T> {
    void onControlResponse(T t);
    void onControlTimeout();
    void onControlException();
}
