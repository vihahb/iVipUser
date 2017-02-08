package com.xtel.ivipuser.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 1/28/2017.
 */

public class RESP_Profile extends RESP_Basic {

    @Expose
    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "RESP_Profile{" +
                "userInfo=" + userInfo +
                '}';
    }
}
