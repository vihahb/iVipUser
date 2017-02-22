package com.xtel.ivipu.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.xtel.ivipu.R;
import com.xtel.ivipu.model.RESP.RESP_Short;
import com.xtel.ivipu.model.entity.UserShort;
import com.xtel.ivipu.presenter.HomePresenter;
import com.xtel.ivipu.view.activity.inf.IHome;
import com.xtel.ivipu.view.fragment.FragmentHomeFood;
import com.xtel.ivipu.view.fragment.FragmentHomeMovie;
import com.xtel.ivipu.view.fragment.FragmentHomeNews;
import com.xtel.ivipu.view.fragment.FragmentHomeNewsLocations;
import com.xtel.ivipu.view.fragment.FragmentHomeShopping;
import com.xtel.ivipu.view.fragment.FragmentHomeTechnology;
import com.xtel.ivipu.view.widget.CircleTransform;
import com.xtel.ivipu.view.widget.RoundImage;
import com.xtel.sdk.dialog.BadgeIcon;

/**
 * Created by vivhp on 12/29/2016.
 */

public class HomeActivity extends BasicActivity implements NavigationView.OnNavigationItemSelectedListener, IHome, View.OnClickListener, PopupMenu.OnDismissListener, TabLayout.OnTabSelectedListener {
    HomePresenter presenter;
    Toolbar toolbar;
    TabLayout tabLayout_home;
    UserShort userShort;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RoundImage img_avatar;
    private ActionBar actionBar;
    private PopupMenu popupMenu;
    private Menu mMenuItem;
    private LinearLayout mLinearLayout;
    private RoundImage avatar_notify;
    private PopupWindow mPopupWindow;
    private Context mContext;
    private int notifications;
    private String avatar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        presenter = new HomePresenter(this);
//        presenter.onGetUserNip();
        mContext = HomeActivity.this;
        presenter.onGetUserNip();
        presenter.onGetShortUser();
        initView();
        initNavigation();
        initNavigationWidget();
        initBottomNavigation();
    }

    private void initNavigationWidget() {
        View view = navigationView.getHeaderView(0);
    }

    public void initBottomNavigation() {
//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.home_bottom_navigation_view);
//        initBottomNavigationAction();
        tabLayout_home = (TabLayout) findViewById(R.id.home_bottom_tab_view);

        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.drawable.ic_shop), 0);
        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.drawable.ic_movie), 1);
        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.drawable.ic_food), 2);
        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.drawable.ic_health), 3);
        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.drawable.ic_list_tab), 4);
        tabLayout_home.setOnTabSelectedListener(this);
        replaceDefaultFragment();
    }

    private void initBottomNavigationAction(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                tabLayout_home.getTabAt(0).setIcon(R.drawable.ic_shop);
                replaceFragment(R.id.home_frame, new FragmentHomeShopping(), "SHOPPING");
                renameToolbar(R.string.nav_shopping);
                break;
            case 1:
                tabLayout_home.getTabAt(1).setIcon(R.drawable.ic_movie);
                replaceFragment(R.id.home_frame, new FragmentHomeMovie(), "MOVIE");
                renameToolbar(R.string.nav_movie);
                break;
            case 2:
                tabLayout_home.getTabAt(2).setIcon(R.drawable.ic_food);
                replaceFragment(R.id.home_frame, new FragmentHomeFood(), "FOOD");
                renameToolbar(R.string.nav_food);
                break;
            case 3:
                tabLayout_home.getTabAt(3).setIcon(R.drawable.ic_health);
                replaceFragment(R.id.home_frame, new FragmentHomeTechnology(), "TECHNOLOGY");
                renameToolbar(R.string.nav_technology);
                break;
            case 4:
                tabLayout_home.getTabAt(4).setIcon(R.drawable.ic_list_tab);
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
        if (avatar != null) {
            setDrawableResource(userShort.getAvatar(), mMenuItem);
        }
        return true;
    }

    private void setDrawableResource(String url, final Menu menu) {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.e("Debug ", "onBitmapLoaded");
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                menu.getItem(1).setIcon(bitmapDrawable);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                if (errorDrawable != null)
                Log.e("OnFailed", errorDrawable.toString());
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null)
                Log.e("OnPrepare", placeHolderDrawable.toString());
            }
        };
        Picasso.with(this)
                .load(url)
                .transform(new CircleTransform())
                .error(R.drawable.ic_nemu_notification)
                .into(target);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mMenuItem = menu;
        if (notifications != 0) {
            onCreteBadgeItem(notifications);
        }
        if (avatar != null) {
            setDrawableResource(userShort.getAvatar(), mMenuItem);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_user) {
            pushData();
            return true;
        } else if (id == R.id.action_qr) {
            checkInQrBar();
        }
        return super.onOptionsItemSelected(item);
    }

    private void checkInQrBar() {
        startActivty(QrCheckIn.class);
    }


    private void onCreteBadgeItem(int paramsInt) {
        if (Build.VERSION.SDK_INT <= 15) {
            return;
        }

        MenuItem user_item = this.mMenuItem.findItem(R.id.action_user);
        LayerDrawable layerDrawable = (LayerDrawable) user_item.getIcon();
        Drawable userBadgeDrawable = layerDrawable.findDrawableByLayerId(R.id.action_user);
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
        layerDrawable.setDrawableByLayerId(R.id.action_user, badgeIcon);
        user_item.setIcon(layerDrawable);
    }

    private void replaceDefaultFragment() {
        tabLayout_home.getTabAt(0).setIcon(R.drawable.ic_shop);
        replaceFragment(R.id.home_frame, new FragmentHomeShopping(), "PROFILE");
        renameToolbar(R.string.nav_Profile);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_my_shop) {
            startActivty(MyShopActivity.class);
        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_privacy) {

        } else if (id == R.id.nav_faq) {

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
    public void getShortUser(RESP_Short profile) {
        UserShort userShort = new UserShort();
        userShort.setAvatar(profile.getAvatar());
        userShort.setFullname(profile.getFullname());
        userShort.setNew_notify(profile.getNew_notify());
//        userShort.setNew_notify(5);
        notifications = userShort.getNew_notify();
        avatar = userShort.getAvatar();
        Log.e("Avatar Home", avatar);
        if (notifications != 0) {
            onCreteBadgeItem(notifications);
        }
        if (avatar != null) {
            setDrawableResource(userShort.getAvatar(), mMenuItem);
        }
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

    @Override
    protected void onResume() {
        presenter.onGetShortUser();
        super.onResume();
    }
}
