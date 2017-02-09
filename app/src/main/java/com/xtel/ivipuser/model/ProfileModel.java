package com.xtel.ivipuser.model;

import com.xtel.sdk.callback.ResponseHandle;

/**
 * Created by vivhp on 2/8/2017.
 */

public class ProfileModel extends Model {

    public static ProfileModel instance = new ProfileModel();

    public static ProfileModel getInstance() {
        return instance;
    }

    public void getUser(String url, String session, ResponseHandle responseHandle) {
        requestServer.getApi(url, session, responseHandle);
    }

}
