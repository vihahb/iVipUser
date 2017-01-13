package com.xtel.ivipuser.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.xtel.ivipuser.R;
import com.xtel.ivipuser.view.activity.inf.IProfileActivityView;
import com.xtel.ivipuser.view.fragment.FavoriteFragment;
import com.xtel.ivipuser.view.fragment.HistoryFragment;
import com.xtel.ivipuser.view.fragment.NotifyFragment;
import com.xtel.ivipuser.view.fragment.ProfileFragment;

/**
 * Created by Vũ Hà Vi on 1/12/2016.
 */

public class ProfileActivity extends BasicActivity implements IProfileActivityView, BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    private ActionBar actionBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initToolbar();

        initBottomNavigation();
        getData();
    }

    private void replaceDefaultFragment() {
        replaceFragment(R.id.detail_frame, new ProfileFragment(), "PROFILE");
        renameToolbar(R.string.nav_Profile);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void initBottomNavigation() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.detail_bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.bringToFront();
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
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onEror() {

    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void startActivitys(Class clazz) {
        super.startActivity(clazz);
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityFinish(clazz);
    }

    @Override
    public void finishActivityBeforeStartActivity(Class clazz) {
        super.finishActivityBeforeStartActivity(clazz);
    }

    private void renameToolbar(int StringResource) {
        toolbar.setTitle(StringResource);
    }

    @Override
    public void finishActivity() {
        super.finishActivity();
    }

    @Override
    public Activity getActivity() {
        return null;
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_profile:
                replaceFragment(R.id.detail_frame, new ProfileFragment(), "PROFILE");
                renameToolbar(R.string.nav_Profile);
                showShortToast("Profile");
                break;
            case R.id.menu_history:
                replaceFragment(R.id.detail_frame, new HistoryFragment(), "HISTORY");
                renameToolbar(R.string.nav_history);
                showShortToast("History");
                break;
            case R.id.menu_favorite:
                replaceFragment(R.id.detail_frame, new FavoriteFragment(), "FAVORITE");
                renameToolbar(R.string.nav_favorite);
                showShortToast("Favorite");
                break;
            case R.id.menu_notify:
                replaceFragment(R.id.detail_frame, new NotifyFragment(), "NOTIFY");
                renameToolbar(R.string.nav_notify);
                showShortToast("Notify");
                break;
            default:
                break;
        }
        return true;
    }

    private void getData() {
        String data = null;
        try {
            data = getIntent().getStringExtra("notification");
            if (data.equals("1")) {
                replaceNotify();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (data == null)
            replaceDefaultFragment();
    }

    public void replaceNotify() {
        replaceFragment(R.id.detail_frame, new NotifyFragment(), "NOTIFY");
        renameToolbar(R.string.nav_notify);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);
//        bottomNavigationView.getMenu().getItem(3).setCheckable(true);
    }
}
