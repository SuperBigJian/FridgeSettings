package com.midea.fridge.utils.accountutil;

/**
 * Created by Administrator on 2016/4/11.
 */
public class LoginInfo {
    private UserData userData;
    private TokenData tokenData;

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public TokenData getTokenData() {
        return tokenData;
    }

    public void setTokenData(TokenData tokenData) {
        this.tokenData = tokenData;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "userData=" + userData +
                ",\n tokenData=" + tokenData +
                '}';
    }
}
