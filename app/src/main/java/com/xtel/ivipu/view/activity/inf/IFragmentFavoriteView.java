package com.xtel.ivipu.view.activity.inf;

import android.view.View;

import com.xtel.ivipu.model.entity.HistoryEntity;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/10/2017.
 */

public interface IFragmentFavoriteView {
    void onGetFavoriteSuccess(ArrayList arrayList);

    void onGetFavoriteError();

    void showShortToast(String mes);

    void showLongToast(String mes);

    void onItemClick(int position, HistoryEntity testRecycle, View view);
}
