package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;

/**
 * Created by vivhp on 12/29/2016.
 */

public interface IHome {

    void showShortToast(String mes);

    void showLongToast(String mes);

    void startActivty(Class clazz);

    Activity getActivity();
}
