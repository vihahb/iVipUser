package com.xtel.ivipuser.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.xtel.ivipuser.R;
import com.xtel.ivipuser.model.entity.Profile;
import com.xtel.ivipuser.model.entity.UserInfo;
import com.xtel.ivipuser.view.activity.inf.IProfileActivityView;
import com.xtel.ivipuser.view.fragment.FavoriteFragment;
import com.xtel.ivipuser.view.fragment.HistoryFragment;
import com.xtel.ivipuser.view.fragment.NotifyFragment;
import com.xtel.ivipuser.view.fragment.ProfileFragment;

/**
 * Created by Vũ Hà Vi on 1/12/2016.
 */

public class ProfileActivity extends BasicActivity implements IProfileActivityView, TabLayout.OnTabSelectedListener {

    BottomNavigationView bottomNavigationView;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initToolbar();

//        initBottomNavigation();
        initTablayout();
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

    private void initTablayout() {
        tabLayout = (TabLayout) findViewById(R.id.detail_bottom_tab_view);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_action_profile), 0);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_action_history), 1);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_action_favorite), 2);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_action_notify), 3);

        tabLayout.setOnTabSelectedListener(this);
    }

//    public void initBottomNavigation() {
//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.detail_bottom_navigation_view);
//        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//    }

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
    public void onGetProfileSuccess(Profile profile) {

    }

    @Override
    public void onGetProfileEror() {

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
        getSupportActionBar().setTitle(StringResource);
    }

    @Override
    public void finishActivity() {
        super.finishActivity();
    }

    @Override
    public void setProfileSuccess(UserInfo profile) {

    }

//    @Override
//    public void setProfileSuccess(RESP_Profile profile) {
//
//    }

//    @Override
//    public void setProfileSuccess(Profile profile) {
//
//    }

    @Override
    public Activity getActivity() {
        return null;
    }

    @Override
    public Context getContext() {
        return null;
    }

    public void OnSelectedTab(int tab_position) {
        switch (tab_position) {
            case 0:
                replaceFragment(R.id.detail_frame, new ProfileFragment(), "PROFILE");
                renameToolbar(R.string.nav_Profile);
                showShortToast("Profile");
                break;
            case 1:
                replaceFragment(R.id.detail_frame, new HistoryFragment(), "HISTORY");
                renameToolbar(R.string.nav_history);
                showShortToast("History");
                break;
            case 2:
                replaceFragment(R.id.detail_frame, new FavoriteFragment(), "FAVORITE");
                renameToolbar(R.string.nav_favorite);
                showShortToast("Favorite");
                break;
            case 3:
                replaceFragment(R.id.detail_frame, new NotifyFragment(), "NOTIFY");
                renameToolbar(R.string.nav_notify);
                showShortToast("Notify");
                break;
            default:
                break;
        }
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
//        replaceFragment(R.id.detail_frame, new NotifyFragment(), "NOTIFY");
//        renameToolbar(R.string.nav_notify);
        tabLayout.getTabAt(3).select();
//        bottomNavigationView.getMenu().getItem(3).setCheckable(true);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        OnSelectedTab(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
