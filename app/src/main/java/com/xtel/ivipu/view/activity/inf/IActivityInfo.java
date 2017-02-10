package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;

/**
 * Created by vivhp on 1/24/2017.
 */

public interface IActivityInfo {
    void onShowQrCode(String url);
    void onShowBarCode(String url_bar_code);

    Activity getActivity();
}
