package com.xtel.ivipuser.view.activity.inf;

import android.app.Activity;
import android.content.Context;

/**
 * Created by vihahb on 1/12/2017.
 */

public interface IProfileActivityView {
    void onSuccess();

    void onEror();

    void showShortToast(String mes);

    void startActivitys(Class clazz);

    void startActivityAndFinish(Class clazz);

    void finishActivityBeforeStartActivity(Class clazz);

    void finishActivity();

    Activity getActivity();

    Context getContext();
}
