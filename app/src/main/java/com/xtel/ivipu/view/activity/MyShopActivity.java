package com.xtel.ivipu.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.TestMyShop;
import com.xtel.ivipu.view.activity.inf.IMyShopActivity;
import com.xtel.ivipu.view.adapter.AdapterRecycleMyShop;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/7/2017.
 */

public class MyShopActivity extends BasicActivity implements IMyShopActivity {

    public RecyclerView rcl_my_shop;
    private ArrayList<TestMyShop> arr;
    private IMyShopActivity view;
    private Toolbar toolbar;
    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_shop_activity);
        initToolbars();
        initView();
    }

    private void initToolbars() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_my_shop);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initView() {

        arr = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            TestMyShop recycle = new TestMyShop();
            recycle.setContent_name("Content Name " + i);
            recycle.setRank("Rank " + i);
            recycle.setScore("Score " + i);
            recycle.setUrl_img_content("https://lh3.googleusercontent.com/V3sk_32vKtW-YSQJwgStRL1Vi0A1WSftt7Lgrshyp50FBBKVcE5fxSOeVCeFbStDGI36jAUNmpGUqYlPLxXZYhKNb3JJV3iAvUfFAVpaPFdBgyrxYiISQ6937rEIYfaJqYNKhi0R1wokyf25yqsJqmwkHXWBa81WeUK6l091UMYobDra7hI7CASYuoQWq7DrpeKlvgI0QJQD0EscpxxCmY8pMibNhvSuiuAhlsvns8mGyAAR64EvNDtC6gg19zPMHWqyj2w4bGN4zWusGUEhuW7zSzjXP1wLEJp_u2hPPv4j_-dmcUv80Mg40MUo8ftj6eat4u2zAzEZTS7j8deYDn1hu3PzaDXc5hEHIZ_n549TETPhuhzkRSu16ecncL8c-ewIrV1ttzcgW5lOuPXtbff_FRbZvPzUEaw12tgZFDH1w76AqyjNlGazmRivw2A5J_mwmAVQfFnt3I_HaIh_nYDuXf6J5gJ0PvHvclQJ8oLMbxmn_C3Tk5bthI0X9FklN4h1cUK0AjftnkbDMNZRXHYw7NisRAMy3AV8FXedPEpiUr45JVX6w3MLgWQhyhrP3aeOfhftWQGDjDk4q763qWuvbEPpASD_zrfdpvGUbyO8fHcWjvPi=w680-h380-no");
            recycle.setUrl_img_brand("http://www.kineo.com/m/0/qualifications-1.png");
            recycle.setUrl_img_icon("https://cdn2.iconfinder.com/data/icons/movie-icons/512/Film_Reel-512.png");
            arr.add(i, recycle);
            Log.e("object " + i, recycle.toString());
        }
        Log.e("arr object ", arr.toString());

        rcl_my_shop = (RecyclerView) findViewById(R.id.rcl_my_shop);
        rcl_my_shop.setHasFixedSize(true);
        rcl_my_shop.setLayoutManager(new LinearLayoutManager(this));

        AdapterRecycleMyShop adapterRecycleMyShop = new AdapterRecycleMyShop(this,
                getApplicationContext(),
                arr,
                view);
        rcl_my_shop.setAdapter(adapterRecycleMyShop);
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
    public void onItemClick(int position, TestMyShop testMyShop, View view) {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
