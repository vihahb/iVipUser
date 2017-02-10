package com.xtel.ivipu.view.activity.inf;

import android.view.View;

import com.xtel.ivipu.model.entity.TestRecycle;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/17/2017.
 */

public interface IFragmentShopView {

    void onGetShopSuccess(ArrayList arrayList);

    void onGetShopError();

    void onLoadMore();

    void showShortToast(String mes);

    void showLongToast(String mes);

    void onItemClick(int position, TestRecycle testRecycle, View view);

}
