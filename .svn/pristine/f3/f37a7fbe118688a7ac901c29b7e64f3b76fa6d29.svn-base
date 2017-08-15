package com.midea.fridgesettings.utils;

import android.util.Log;

import com.midea.fridgesettings.model.AES128;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/6.
 */
public class Utils {

    public static String getLogTime() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
        String t = format.format(new Date());
        return t;
    }


    public static String getLogFileTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddHH-mm-ss");
        String t = format.format(new Date());
        return t;
    }


    public static String MD5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes("utf-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString().toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }

    public static String MD5_small(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes("utf-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成签名
     *
     * @param params
     * @return
     */
    public static String getSign(Map<String, String> params) {
        String result;
        StringBuffer r = new StringBuffer();
        Iterator<String> iter = params.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            r.append(name).append(params.get(name));
        }
        try {
            result = AES128.encrypt(MD5_small(r.toString()), "20160613646aBcDW");
        } catch (Exception e) {
            throw new java.lang.RuntimeException("sign error !");
        }
        return result;
    }

    public static String getSixRandom() {
        String strRand = "";
        for (int i = 0; i < 6; i++) {
            strRand += String.valueOf((int) (Math.random() * 10));
        }
        Log.v("sstang", "random=====" + strRand);
        return strRand;
    }
}
