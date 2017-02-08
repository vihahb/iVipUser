package com.xtel.ivipuser.view.activity.inf;

import android.content.Context;
import android.view.View;

import com.xtel.ivipuser.model.entity.TestMyShop;

/**
 * Created by vivhp on 2/7/2017.
 */

public interface IMyShopActivity {

    void onSuccess();

    void onError();

    void onItemClick(int position, TestMyShop testMyShop, View view);

    Context getContext();
}
