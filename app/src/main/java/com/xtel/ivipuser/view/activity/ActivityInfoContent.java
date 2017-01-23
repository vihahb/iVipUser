package com.xtel.ivipuser.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.xtel.ivipuser.R;
import com.xtel.ivipuser.view.activity.inf.IInfoContentView;
import com.xtel.ivipuser.view.fragment.FragmentInfoAddress;
import com.xtel.ivipuser.view.fragment.FragmentInfoInfomation;
import com.xtel.ivipuser.view.fragment.FragmentInfoProperties;
import com.xtel.ivipuser.view.fragment.FragmentInfoSuggestion;

/**
 * Created by vihahb on 1/17/2017.
 */

public class ActivityInfoContent extends BasicActivity implements View.OnClickListener, IInfoContentView {

//    private TestRecycle testRecycle;
//    private TextView txt_info_shop_name, txt_info_shop_member, txt_info_shop_location, txt_info_shop_comment;
//    private Button btn_getGiftCode;

    private TabLayout tabLayout;
    private FrameLayout info_frame;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initTransition();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_content);
        initToolbars();
//        initView();
        initControl();
    }

    private void initTransition() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // inside your activity (if you did not enable transitions in your theme)
            getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
// set an enter transition
            getWindow().setEnterTransition(new Explode());
            // set an exit transition
            getWindow().setExitTransition(new Explode());
        }
    }

//    private void initView() {
//        txt_info_shop_name = (TextView) findViewById(R.id.tv_info_shop_name);
//        txt_info_shop_member = (TextView) findViewById(R.id.tv_info_shop_member);
//        txt_info_shop_location = (TextView) findViewById(R.id.tv_info_shop_location);
//        txt_info_shop_comment = (TextView) findViewById(R.id.tv_info_shop_comment);
//
//        btn_getGiftCode = (Button) findViewById(R.id.btnGetGiftCode);
//
//        getDataFromFragmentShop();
//    }

    private void initToolbars() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_info_content);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initControl() {
        tabLayout = (TabLayout) findViewById(R.id.tabs_info);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_action_info), 0);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_action_location), 1);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_action_search), 2);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_action_suggestion), 3);
        replaceDefaultFragment();
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tab_position = tab.getPosition();
                if (tab_position == 0) {
                    renameToolbar(R.string.tab_properties);
                    replaceFragment(R.id.info_frame, new FragmentInfoProperties(), "Properties");
                } else if (tab_position == 1) {
                    renameToolbar(R.string.tab_address);
                    replaceFragment(R.id.info_frame, new FragmentInfoAddress(), "Address");
                } else if (tab_position == 2) {
                    renameToolbar(R.string.tab_info);
                    replaceFragment(R.id.info_frame, new FragmentInfoInfomation(), "Infomation");
                } else if (tab_position == 3) {
                    renameToolbar(R.string.tab_suggestion);
                    replaceFragment(R.id.info_frame, new FragmentInfoSuggestion(), "Suggestion");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

//    private boolean validData(){
//        try {
//            testRecycle = (TestRecycle) getIntent().getSerializableExtra(Constants.RECYCLER_MODEL);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return testRecycle != null;
//    }
//
//    private void getDataFromFragmentShop(){
//        if (validData()){
//            String object = testRecycle.toString();
//            Log.d("Object Info", object);
//            String shopName = testRecycle.getShopName();
//            String shopMember = testRecycle.getShopMenber();
//            String shopLocation = testRecycle.getShopLocation();
//            String shopComment = testRecycle.getShopComment();
//
//            txt_info_shop_name.setText(shopName);
//            txt_info_shop_member.setText(shopMember);
//            txt_info_shop_location.setText(shopLocation);
//            txt_info_shop_comment.setText(shopComment);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            supportFinishAfterTransition();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }

    @Override
    public void showShortToast(String mes) {
        super.showShortToast(mes);
    }

    @Override
    public void showLongToast(String mes) {
        super.showLongToast(mes);
    }

    @Override
    protected void showProgressBar(boolean isTouchOutside, boolean isCancel, String title, String message) {
        super.showProgressBar(isTouchOutside, isCancel, title, message);
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public void closeProgressBar() {
        super.closeProgressBar();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    private void renameToolbar(int StringResource) {
        getSupportActionBar().setTitle(StringResource);
    }

    private void replaceDefaultFragment() {
        renameToolbar(R.string.tab_properties);
        replaceFragment(R.id.info_frame, new FragmentInfoProperties(), "Properties");
    }
}
