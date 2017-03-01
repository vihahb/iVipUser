package com.xtel.ivipu.view.fragment.inf;

import android.view.View;

import com.xtel.ivipu.model.entity.HistoryEntity;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/10/2017.
 */

public interface IFragmentHistoryView {
    void onGetHistorySuccess(ArrayList arrayList);

    void onGetHistoryError();

    void showShortToast(String mes);

    void showLongToast(String mes);

    void onItemClick(int position, HistoryEntity testRecycle, View view);
}