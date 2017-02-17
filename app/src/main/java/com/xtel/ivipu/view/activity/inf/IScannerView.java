package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;

/**
 * Created by vivhp on 2/15/2017.
 */

public interface IScannerView {
    void onStartChecking();

    void onCheckinSuccess(String mes, String content, String reward);

    void onCheckinError(Error error);

    Activity getActivity();
}
