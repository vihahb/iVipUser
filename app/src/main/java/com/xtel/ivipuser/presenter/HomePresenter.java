package com.xtel.ivipuser.presenter;

import android.util.Log;

import com.xtel.ivipuser.model.LoginModel;
import com.xtel.ivipuser.model.entity.ProfileEntity;
import com.xtel.ivipuser.view.activity.inf.IHome;
import com.xtel.nipservicesdk.commons.Constants;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.nipservicesdk.utils.SharedUtils;
import com.xtel.sdk.callback.ResponseHandle;

/**
 * Created by vivhp on 12/29/2016.
 */

public class HomePresenter {

    private IHome view;

    private String TAG = "Home presenter";

    public HomePresenter(IHome iHome) {
        this.view = iHome;
    }

    public void onGetUserNip() {
        String session = SharedUtils.getInstance().getStringValue(Constants.SESSION);
        String url = "http://124.158.5.112:9180/nipum/v1.0/g/user/info/";
        Log.e(TAG + "url", url);
        LoginModel.getInstance().getUser(url, session, new ResponseHandle<ProfileEntity>(ProfileEntity.class) {
            @Override
            public void onSuccess(ProfileEntity obj) {
                String name = obj.getFirst_name() + " " + obj.getLast_name();
                Log.e(TAG + "name", name);
            }

            @Override
            public void onError(com.xtel.ivipuser.model.entity.Error error) {
                Log.e(TAG + "err mes", error.getMessage());
            }
        });
    }

    private String parseMessage(int code) {
        String mess = JsonParse.getCodeMessage(view.getActivity(), code, "");
        return mess;
    }
}
