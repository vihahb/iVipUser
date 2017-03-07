package com.xtel.ivipu.view.fragment.inf;

import android.app.Activity;
import android.content.Context;

import com.xtel.ivipu.model.entity.MemberObj;

import java.util.ArrayList;

/**
 * Created by vuhavi on 07/03/2017.
 */

public interface IFragmentMemberCard {

    void onGetMemberCardSuccess(ArrayList<MemberObj> arrayList);
    void onGetMemberCardError();
    void showShortToast(String mes);
    void startActivityAndFinish(Class clazz);
    void onLoadMore();

    Activity getActivity();
    Context getContext();
}
