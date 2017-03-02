package com.zxw.data.bean;

/**
 * author：CangJie on 2016/12/7 16:39
 * email：cangjie2016@gmail.com
 */
public class Login {

    /**
     * code : 902130
     * keyCode : 720e2c3426bda880a4fcf83253785792
     * name : 奥水军
     */

    private String userId;
    private String keyCode;
    private String name;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Login{" +
                "userId='" + userId + '\'' +
                ", keyCode='" + keyCode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
