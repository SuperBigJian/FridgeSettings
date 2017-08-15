package com.midea.fridge.utils.accountutil;

/**
 * Created by Administrator on 2016/4/11.
 */
public class TokenData {
    public long time;
    public String code;
    public String url;

    public TokenData() {
        time = 0;
        code = "";
        url = "";
    }

    public void setTime(long time){
        this.time = time;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setUrl(String url){
        this.url = url;
    }

    @Override
    public String toString() {
        return "TokenData{" +
                "time=" + time +
                ", code='" + code + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
