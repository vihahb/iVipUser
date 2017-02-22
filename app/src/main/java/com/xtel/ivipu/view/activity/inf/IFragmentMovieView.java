package com.xtel.ivipu.view.activity.inf;

import android.view.View;

import com.xtel.ivipu.model.entity.TestRecycle;

import java.util.ArrayList;

/**
 * Created by vivhp on 1/23/2017.
 */

public interface IFragmentMovieView {
    void onGetShopSuccess(ArrayList arrayList);

    void onGetShopError();

    void onLoadMore();

    void showShortToast(String mes);

    void showLongToast(String mes);

    void onItemClick(int position, TestRecycle testRecycle, View view);
}