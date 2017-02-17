package com.xtel.ivipu.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.RESP_Profile;
import com.xtel.ivipu.model.entity.UserInfo;
import com.xtel.ivipu.view.activity.inf.IProfileActivityView;
import com.xtel.ivipu.view.fragment.FavoriteFragment;
import com.xtel.ivipu.view.fragment.HistoryFragment;
import com.xtel.ivipu.view.fragment.NotifyFragment;
import com.xtel.ivipu.view.fragment.ProfileFragment;

/**
 * Created by Vũ Hà Vi on 1/12/2016.
 */

public class ProfileActivity extends BasicActivity implements IProfileActivityView, TabLayout.OnTabSelectedListener {

    BottomNavigationView bottomNavigationView;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private Menu menu;

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
        } else if (id == R.id.action_update) {
            ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("PROFILE");
            if (fragment != null) {
                fragment.onEnableView();
            }
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
        } else if (id == R.id.action_update_done) {
            ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("PROFILE");
            if (fragment != null) {
                fragment.onDisableView();
                fragment.checkNetwork(this, 1);
            }
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
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

    @Override
    public void reloadProfile(RESP_Profile profile) {

    }

    @Override
    public void updateProfileSucc() {
    }

    @Override
    public void onPostPictureSuccess(String url, String server_path) {

    }

    @Override
    public void onPostPictureError(String mes) {
    }

    @Override
    public void onEnableView() {
    }

    @Override
    public void onDisableView() {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

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
                menu.getItem(0).setVisible(true);
                break;
            case 1:
                replaceFragment(R.id.detail_frame, new HistoryFragment(), "HISTORY");
                renameToolbar(R.string.nav_history);
                showShortToast("History");
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(false);
                break;
            case 2:
                replaceFragment(R.id.detail_frame, new FavoriteFragment(), "FAVORITE");
                renameToolbar(R.string.nav_favorite);
                showShortToast("Favorite");
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(false);
                break;
            case 3:
                replaceFragment(R.id.detail_frame, new NotifyFragment(), "NOTIFY");
                renameToolbar(R.string.nav_notify);
                showShortToast("Notify");
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(false);
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
//                replaceNotify();
                replaceDefaultFragment();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (data == null)
            replaceDefaultFragment();
    }

//    public void replaceNotify() {
////        replaceFragment(R.id.detail_frame, new NotifyFragment(), "NOTIFY");
////        renameToolbar(R.string.nav_notify);
//        tabLayout.getTabAt(3).select();
////        bottomNavigationView.getMenu().getItem(3).setCheckable(true);
//    }

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
