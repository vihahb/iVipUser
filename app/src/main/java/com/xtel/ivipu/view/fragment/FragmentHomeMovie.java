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
import com.xtel.ivipu.view.activity.inf.IFragmentMovieView;
import com.xtel.ivipu.view.adapter.AdapterRecycleMovie;
import com.xtel.sdk.commons.Constants;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/13/2017.
 */

public class FragmentHomeMovie extends BasicFragment implements IFragmentMovieView {

    private RecyclerView rcl_movie;
    private ArrayList<TestRecycle> arrayList_movie;

    private int position = -1;
    private int REQUEST_VIEW_MOVIE = 91;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_movie, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        arrayList_movie = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            TestRecycle recycle = new TestRecycle();
            recycle.setShopName("Movie " + i);
            recycle.setShopMenber(String.valueOf(i));
            recycle.setShopLocation(String.valueOf(i));
            recycle.setShopComment(String.valueOf(i));
            arrayList_movie.add(i, recycle);
            Log.e("object " + i, recycle.toString());
        }
        Log.e("arr object ", arrayList_movie.toString());

        rcl_movie = (RecyclerView) view.findViewById(R.id.rcl_ivip);

        rcl_movie.setHasFixedSize(true);

        rcl_movie.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapter = new AdapterRecycleMovie(getActivity(), arrayList_movie, this);

        rcl_movie.setAdapter(adapter);
    }


    @Override
    public void onGetShopSuccess(ArrayList arrayList) {

    }

    @Override
    public void onGetShopError() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void showLongToast(String message) {
        super.showLongToast(message);
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void onItemClick(int position, TestRecycle testRecycle, View view) {
        this.position = position;
        startActivityForResultObject(ActivityInfoContent.class, Constants.RECYCLER_MODEL, testRecycle, REQUEST_VIEW_MOVIE);
    }
}
