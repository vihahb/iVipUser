package com.xtel.ivipu.view.activity.inf;

import android.app.Activity;

import com.xtel.ivipu.model.entity.RESP_NewsObject;

/**
 * Created by vivhp on 1/24/2017.
 */

public interface IActivityInfo {
    void onShowQrCode(String url);
    void onShowBarCode(String url_bar_code);

    void showSortToast(String mes);

    void onGetNewsObjSuccess(RESP_NewsObject resp_newsObject);

    void onLikeSuccess();

    Activity getActivity();
}
