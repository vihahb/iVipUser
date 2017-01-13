package com.xtel.ivipuser.view.activity.inf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.xtel.nipservicesdk.model.entity.Error;

/**
 * Created by vihahb on 1/10/2017.
 */

public interface IRegisterPhoneView {

    void onSuccess();

    void onError(Error error);

    void showShortToast(String mes);

    void showLongToast(String mes);

    void startActivitys(Intent intent);

    void finishActivitys();

    void startActivityForResults(Intent intent, int requestCode);

    Activity getActivity();

    Context getContext();

}
