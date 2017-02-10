package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;
import android.content.Context;

import com.xtel.ivipu.model.entity.RESP_Profile;
import com.xtel.ivipu.model.entity.UserInfo;

/**
 * Created by vihahb on 1/12/2017.
 */

public interface IProfileActivityView {

    void showShortToast(String mes);

    void startActivitys(Class clazz);

    void startActivityAndFinish(Class clazz);

    void finishActivityBeforeStartActivity(Class clazz);

    void finishActivity();

    void setProfileSuccess(UserInfo profile);

    void reloadProfile(RESP_Profile profile);

    void updateProfileSucc();

    void onPostPictureSuccess(String url);

    void onPostPictureError(String mes);

    Activity getActivity();

    Context getContext();
}
