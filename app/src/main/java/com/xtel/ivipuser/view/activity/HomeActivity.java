package com.xtel.ivipuser.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.squareup.picasso.Picasso;
import com.xtel.ivipuser.R;
import com.xtel.ivipuser.presenter.HomePresenter;
import com.xtel.ivipuser.view.activity.inf.IHome;
import com.xtel.ivipuser.view.fragment.FragmentHomeFood;
import com.xtel.ivipuser.view.fragment.FragmentHomeMovie;
import com.xtel.ivipuser.view.fragment.FragmentHomeNews;
import com.xtel.ivipuser.view.fragment.FragmentHomeNewsLocations;
import com.xtel.ivipuser.view.fragment.FragmentHomeShopping;
import com.xtel.ivipuser.view.fragment.FragmentHomeTechnology;
import com.xtel.ivipuser.view.widget.RoundImage;
import com.xtel.sdk.dialog.BadgeIcon;

/**
 * Created by vivhp on 12/29/2016.
 */

public class HomeActivity extends BasicActivity implements NavigationView.OnNavigationItemSelectedListener, IHome, View.OnClickListener, PopupMenu.OnDismissListener, TabLayout.OnTabSelectedListener {
    HomePresenter presenter;
    Toolbar toolbar;
    TabLayout tabLayout_home;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RoundImage img_avatar;
    private ActionBar actionBar;
    private BottomNavigationView bottomNavigationView;
    private PopupMenu popupMenu;
    private Menu mMenuItem;
    private LinearLayout mLinearLayout;
    private PopupWindow mPopupWindow;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter = new HomePresenter(this);
        mContext = HomeActivity.this;
        initView();
        initNavigation();
        initNavigationWidget();
        initBottomNavigation();
    }

    private void initNavigationWidget() {
        View view = navigationView.getHeaderView(0);
        img_avatar = (RoundImage) view.findViewById(R.id.img_avatar);
        img_avatar.setOnClickListener(this);
        String url = "https://unige.ch/mcr/application/files/5614/7220/3533/avatar_square_512.png";
        setAvatar(url, img_avatar);
    }

    public void initBottomNavigation() {
//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.home_bottom_navigation_view);
//        initBottomNavigationAction();
        tabLayout_home = (TabLayout) findViewById(R.id.home_bottom_tab_view);

        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.mipmap.ic_home_selected_shop), 0);
        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.mipmap.ic_home_selected_movie), 1);
        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.mipmap.ic_home_selected_food), 2);
        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.mipmap.ic_home_selected_airplane), 3);
        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.mipmap.ic_home_selected_more), 4);
        tabLayout_home.setOnTabSelectedListener(this);
        replaceDefaultFragment();
    }

    private void initBottomNavigationAction(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                tabLayout_home.getTabAt(0).setIcon(R.mipmap.ic_home_selected_shop);
                replaceFragment(R.id.home_frame, new FragmentHomeShopping(), "SHOPPING");
                renameToolbar(R.string.nav_shopping);
                break;
            case 1:
                tabLayout_home.getTabAt(1).setIcon(R.mipmap.ic_home_selected_movie);
                replaceFragment(R.id.home_frame, new FragmentHomeMovie(), "MOVIE");
                renameToolbar(R.string.nav_movie);
                break;
            case 2:
                tabLayout_home.getTabAt(2).setIcon(R.mipmap.ic_home_selected_food);
                replaceFragment(R.id.home_frame, new FragmentHomeFood(), "FOOD");
                renameToolbar(R.string.nav_food);
                break;
            case 3:
                tabLayout_home.getTabAt(3).setIcon(R.mipmap.ic_home_selected_airplane);
                replaceFragment(R.id.home_frame, new FragmentHomeTechnology(), "TECHNOLOGY");
                renameToolbar(R.string.nav_technology);
                break;
            case 4:
                tabLayout_home.getTabAt(4).setIcon(R.mipmap.ic_home_selected_more);
                View mainTab = ((ViewGroup) tabLayout_home.getChildAt(0)).getChildAt(tab.getPosition());
                showPopupLayout(mainTab);
                break;
        }
    }

    public void showPopupLayout(View view) {
        popupMenu = new PopupMenu(HomeActivity.this, view);
        popupMenu.inflate(R.menu.popup_nemu);
        popupMenu.setOnDismissListener(this);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id_menu = item.getItemId();
                switch (id_menu) {
                    case R.id.menu_news:
                        showShortToast("News");
                        replaceFragment(R.id.home_frame, new FragmentHomeNews(), "NEWS");
                        popupMenu.dismiss();
                        break;
                    case R.id.menu_news_location:
                        showShortToast("News for location");
                        replaceFragment(R.id.home_frame, new FragmentHomeNewsLocations(), "NEWS FOR LOCATION");
                        popupMenu.dismiss();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        popupMenu.show();
    }

    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        btnPopup = (Button) findViewById(R.id.btnPopupMenu);
//        btnPopup.setOnClickListener(this);
        mLinearLayout = (LinearLayout) findViewById(R.id.ln_popup);
    }

//    private void showPopupWindows() {
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//        final View custom_view = inflater.inflate(R.layout.custom_popup_layout, null);
//
//        mPopupWindow = new PopupWindow(custom_view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        if (Build.VERSION.SDK_INT >= 21) {
//            mPopupWindow.setElevation(5.0f);
//        }
//        mPopupWindow.setOutsideTouchable(true);
//
//        new Handler().postDelayed(new Runnable() {
//
//            public void run() {
//                mPopupWindow.showAtLocation(custom_view, Gravity.BOTTOM | Gravity.RIGHT, 0, 0);
//            }
//
//        }, 100L);
//
//    }

    @SuppressWarnings("deprecation")
    private void initNavigation() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            showConfirmExitApp();
        }
    }


    private void setAvatar(String url, RoundImage img_avatar) {
        Picasso.with(getApplicationContext())
                .load(url)
                .placeholder(R.drawable.ic_action_name)
                .error(R.drawable.ic_action_name)
                .fit()
                .centerCrop()
                .into(img_avatar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
//        MenuItem item = menu.findItem(R.id.action_user);
//        LayerDrawable icon = (LayerDrawable) item.getIcon();
//        //Update layoutDrawable badge drawable
//        BadgeUtils.setBadgeCount(getApplicationContext(), icon, mNotificationsCount);
//        new FeatchCountTask().execute();
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mMenuItem = menu;
        onCreteBadgeItem(10);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_user) {
            pushData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void onCreteBadgeItem(int paramsInt) {
        if (Build.VERSION.SDK_INT <= 15) {
            return;
        }

        MenuItem user_item = this.mMenuItem.findItem(R.id.action_user);
        LayerDrawable layerDrawable = (LayerDrawable) user_item.getIcon();
        Drawable userBadgeDrawable = layerDrawable.findDrawableByLayerId(R.id.ic_badge);
        BadgeIcon badgeIcon;
        if ((userBadgeDrawable != null)
                && ((userBadgeDrawable instanceof BadgeIcon))
                && (paramsInt < 0)) {
            badgeIcon = (BadgeIcon) userBadgeDrawable;
        } else {
            badgeIcon = new BadgeIcon(this);
        }
        badgeIcon.setCount(paramsInt);
        layerDrawable.mutate();
        layerDrawable.setDrawableByLayerId(R.id.ic_badge, badgeIcon);
        user_item.setIcon(layerDrawable);
    }

    private void replaceDefaultFragment() {
        tabLayout_home.getTabAt(0).setIcon(R.mipmap.ic_home_selected_shop);
        replaceFragment(R.id.home_frame, new FragmentHomeShopping(), "PROFILE");
        renameToolbar(R.string.nav_Profile);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_exit) {
            exitApp();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void startActivty(Class clazz) {
        super.startActivity(clazz);
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
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_avatar) {
            startActivity(ProfileActivity.class);
            drawer.closeDrawer(GravityCompat.START);
        }
//        else if (id == R.id.btnPopupMenu){
//            showPopupLayout(v);
//        }
    }

     /*
    Sample AsyncTask to fetch the notifications count
    */

    private void pushData() {
        Intent intent = new Intent(this, ProfileActivity.class);
        String push_data = "1";
        intent.putExtra("notification", push_data);
        startActivity(intent);
    }

    @Override
    public void onDismiss(PopupMenu menu) {
        showShortToast("Popup On Dismissed");
        tabLayout_home.getChildAt(0).setSelected(false);
    }

    private void renameToolbar(int StringResource) {
        toolbar.setTitle(StringResource);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        initBottomNavigationAction(tab);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
//        setIconTabUnSelected(tab);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void setIconTabUnSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                tabLayout_home.getTabAt(0).setIcon(R.mipmap.ic_home_shop);
                break;
            case 1:
                tabLayout_home.getTabAt(1).setIcon(R.mipmap.ic_home_movie);
                break;
            case 2:
                tabLayout_home.getTabAt(2).setIcon(R.mipmap.ic_home_food);
                break;
            case 3:
                tabLayout_home.getTabAt(3).setIcon(R.mipmap.ic_home_airplane);
                break;
            case 4:
                tabLayout_home.getTabAt(4).setIcon(R.mipmap.ic_home_more);
                break;
            default:
                break;
        }
    }
}
