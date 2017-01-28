package com.xtel.ivipuser.view.activity.inf;

import android.app.Activity;

/**
 * Created by vivhp on 1/24/2017.
 */

public interface IActivityInfo {
    void onShowQrCode(String url);

    Activity getActivity();
}
