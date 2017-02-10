package com.xtel.ivipu.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.TestRecycle;
import com.xtel.ivipu.view.activity.ActivityInfoContent;
import com.xtel.ivipu.view.activity.inf.IFragmentFoodView;
import com.xtel.ivipu.view.adapter.AdapterRecycleFood;
import com.xtel.sdk.commons.Constants;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/13/2017.
 */

public class FragmentHomeFood extends BasicFragment implements IFragmentFoodView {

    private RecyclerView rcl_food;
    private ArrayList<TestRecycle> arraylist_food;

    private int position = -1;
    private int REQUEST_VIEW_FOOD = 92;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_food, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        arraylist_food = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            TestRecycle recycle = new TestRecycle();
            recycle.setShopName("Food " + i);
            recycle.setShopMenber(String.valueOf(i));
            recycle.setShopLocation(String.valueOf(i));
            recycle.setShopComment(String.valueOf(i));
            arraylist_food.add(i, recycle);
            Log.e("food object " + i, recycle.toString());
        }
        Log.e("arr food object ", arraylist_food.toString());

        rcl_food = (RecyclerView) view.findViewById(R.id.rcl_food);

        rcl_food.setHasFixedSize(true);

        rcl_food.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapter = new AdapterRecycleFood(getActivity(), arraylist_food, this);

        rcl_food.setAdapter(adapter);
    }

    @Override
    public void onGetFoodSuccess(ArrayList arrayList) {

    }

    @Override
    public void onGetFoodError() {

    }

    @Override
    public void onItemClick(int position, TestRecycle testRecycle, View view) {
        this.position = position;
        startActivityForResult(ActivityInfoContent.class, Constants.RECYCLER_MODEL, testRecycle, REQUEST_VIEW_FOOD);
    }

    @Override
    public void showLongToast(String message) {
        super.showLongToast(message);
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }
}
