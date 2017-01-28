package com.xtel.ivipuser.model;

import com.xtel.ivipuser.model.entity.ProfileEntity;
import com.xtel.sdk.callback.ResponseHandle;

/**
 * Created by vivhp on 1/28/2017.
 */

public class LoginModel extends Model {

    public static LoginModel instance = new LoginModel();

    public static LoginModel getInstance() {
        return instance;
    }

    public void getUser(String url, String session, ResponseHandle<ProfileEntity> responseHandle) {
        requestServer.getApi(url, session, responseHandle);
    }

}
