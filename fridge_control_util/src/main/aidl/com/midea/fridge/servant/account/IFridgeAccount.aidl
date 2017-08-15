// IFridgeAccount.aidl
package com.midea.fridge.servant.account;

import com.midea.fridge.servant.IRequestListener;

// Declare any non-default types here with import statements

interface IFridgeAccount {
    /** 校验用户名 */
    void checkUserName(String userName, IRequestListener requestListener);
    /** 获取验证码 */
    void fetchCaptcha(String userName, int versionCode, String padModel, IRequestListener requestListener);
    /** 校验验证码 */
    void checkCaptcha(String userName, String captcha, IRequestListener requestListener);
    /** 注册用户 */
    void register(String userName, String password, String captcha, IRequestListener requestListener);
    /** 重设密码 */
    void resetPassword(String userName, String password, String captcha, IRequestListener requestListener);
    /** 修改密码 */
    void modifyPassword(String oldPassword, String newPassword, IRequestListener requestListener);
    void login(String userName, String password, IRequestListener requestListener);
    /** 与login相比，增加SLK登录的回调 */
    void login2(String userName, String password, IRequestListener loginListener, IRequestListener slkListener);
    /** 登录SLK，只能在登录i+账号成功后调用 */
    void loginSLK(IRequestListener slkListener);
    void logout();
    /** 刷新登录信息 */
    void refreshToken();
    /** 获取登录信息，获取为空时说明未登录 */
    String getLoginInfo();
    /** SLK是否登录成功 */
    boolean isSLKLogin();
    /** 人脸登录 */
    void loginByFace(in byte[] faceData, IRequestListener requestListener);
    void cancelAllRequest();
}
