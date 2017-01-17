package com.xtel.ivipuser.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtel.ivipuser.R;
import com.xtel.ivipuser.model.entity.TestRecycle;
import com.xtel.ivipuser.view.activity.ActivityInfoContent;
import com.xtel.ivipuser.view.activity.inf.IFragmentShopView;
import com.xtel.ivipuser.view.adapter.AdapterRecycleShop;
import com.xtel.sdk.commons.Constants;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/13/2017.
 */

public class FragmentHomeShopping extends BasicFragment implements IFragmentShopView {

    private RecyclerView rcl_shop;
    private ArrayList<TestRecycle> arrayList;

    private int position = -1;
    private int REQUEST_VIEW_SHOP = 99;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_shopping, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        initArrayList();
        initRecylerView(view);
    }


    private void initRecylerView(View view) {

        arrayList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            TestRecycle recycle = new TestRecycle();
            recycle.setShopName("Shop " + i);
            recycle.setShopMenber(String.valueOf(i));
            recycle.setShopLocation(String.valueOf(i));
            recycle.setShopComment(String.valueOf(i));
            arrayList.add(i, recycle);
            Log.e("object " + i, recycle.toString());
        }
        Log.e("arr object ", arrayList.toString());

        rcl_shop = (RecyclerView) view.findViewById(R.id.rcl_shop);
        rcl_shop.setHasFixedSize(true);

        rcl_shop.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapter = new AdapterRecycleShop(getActivity(), arrayList, this);

        rcl_shop.setAdapter(adapter);
    }


    @Override
    public void onGetShopSuccess(ArrayList arrayList) {

    }

    @Override
    public void onGetShopError() {

    }


    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void showLongToast(String message) {
        super.showLongToast(message);
    }

    @Override
    public void onItemClick(int position, TestRecycle testRecycle) {
        this.position = position;
        startActivityForResult(ActivityInfoContent.class, Constants.RECYCLER_MODEL, testRecycle, REQUEST_VIEW_SHOP);
    }
}
