package com.xtel.ivipu.view.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.MyShopCheckin;
import com.xtel.ivipu.presenter.MyShopPresenter;
import com.xtel.ivipu.view.activity.inf.IMyShopActivity;
import com.xtel.ivipu.view.adapter.AdapterRecycleMyShop;
import com.xtel.ivipu.view.widget.ProgressView;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/7/2017.
 */

public class MyShopActivity extends BasicActivity implements IMyShopActivity {

    public RecyclerView rcl_my_shop;
    AdapterRecycleMyShop adapter;
    private ArrayList<MyShopCheckin> arr;
    private IMyShopActivity view;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private MyShopPresenter presenter;
    private ProgressView progressView;
    private int page = 1, pagesize = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_shop_activity);
        presenter = new MyShopPresenter(this);
        initToolbars();
        initProgressView();
        initView();
    }

    private void initToolbars() {
        arr = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar_my_shop);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initView() {
        rcl_my_shop = (RecyclerView) findViewById(R.id.rcl_ivip);
        rcl_my_shop.setHasFixedSize(true);
        rcl_my_shop.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdapterRecycleMyShop(this, getContext(), arr, view);
        rcl_my_shop.setAdapter(adapter);
    }

    private void setDataRecyclerView(ArrayList<MyShopCheckin> newsCheckinsArrayList) {
        Log.e("arr list", newsCheckinsArrayList.toString());
        arr.addAll(newsCheckinsArrayList);
        adapter.notifyDataSetChanged();
    }

    private void initProgressView() {
        progressView = new ProgressView(this, null);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_my_shop);

        progressView.onLayoutClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        progressView.onRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        progressView.onSwipeLayoutPost(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        });
    }

    private void getData() {
        progressView.hideData();
        progressView.setRefreshing(true);
        inirDataCheckIn();
    }

    private void inirDataCheckIn() {
        presenter.getMyShopCheckin(page, pagesize);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onNetworkDisabled() {
        progressView.setRefreshing(false);
        progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_internet), getString(R.string.try_again));
        progressView.showData();
    }

    @Override
    public void onGetMyShopData(ArrayList<MyShopCheckin> arrayList) {
        progressView.setRefreshing(false);
        Log.e("arr Shop entity", arrayList.toString());
        setDataRecyclerView(arrayList);
        if (arrayList.size() < 10) {
            adapter.onSetLoadMore(false);
        }
        checkListData();
    }

    private void checkListData() {
        progressView.disableSwipe();
        progressView.setRefreshing(false);
        if (arr.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
            rcl_my_shop.getAdapter().notifyDataSetChanged();
            progressView.hideData();
        }
    }

    @Override
    public void onItemClick(int position, MyShopCheckin myShopCheckin, View view) {

    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return null;
    }
}
