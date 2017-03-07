package com.xtel.ivipu.view.activity;

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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.xtel.ivipu.R;
import com.xtel.ivipu.model.RESP.RESP_Short;
import com.xtel.ivipu.model.entity.UserShort;
import com.xtel.ivipu.presenter.HomePresenter;
import com.xtel.ivipu.view.activity.inf.IHome;
import com.xtel.ivipu.view.fragment.FragmentHomeFashionMakeUp;
import com.xtel.ivipu.view.fragment.FragmentHomeFood;
import com.xtel.ivipu.view.fragment.FragmentHomeHealth;
import com.xtel.ivipu.view.fragment.FragmentHomeNewsForMe;
import com.xtel.ivipu.view.fragment.FragmentHomeNewsList;
import com.xtel.ivipu.view.fragment.FragmentHomeOtherService;
import com.xtel.ivipu.view.fragment.FragmentHomeTechnology;
import com.xtel.ivipu.view.fragment.FragmentMyShop;
import com.xtel.ivipu.view.widget.CircleTransform;
import com.xtel.ivipu.view.widget.LinearLayoutAnimationSlideBottom;
import com.xtel.ivipu.view.widget.RoundImage;
import com.xtel.sdk.dialog.BadgeIcon;

/**
 * Created by vivhp on 12/29/2016.
 */

public class HomeActivity extends BasicActivity implements NavigationView.OnNavigationItemSelectedListener, IHome, View.OnClickListener {
    HomePresenter presenter;
    Toolbar toolbar;
    //    TabLayout tabLayout_home;
    LinearLayout ln_layout_transparent, ln_layout_nav_item;
    LinearLayoutAnimationSlideBottom ln_popup_item;
    BottomNavigationView nav_bottom_home;
    UserShort userShort;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private RoundImage img_avatar;
    private ActionBar actionBar;
    private PopupMenu popupMenu;
    private MenuItem mMenuItem;
    private LinearLayout mLinearLayout;
    private RoundImage avatar_notify;
    private PopupWindow mPopupWindow;
    private Context mContext;
    private int notifications;
    private String avatar;
    FrameLayout fr_home_overlay;
    private Button btn_health, btn_service, btn_news_for_location;

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
        nav_bottom_home = (BottomNavigationView) findViewById(R.id.bottom_navigation_item);
        initMenuSelected();
//        initBottomNavigationAction();
//        tabLayout_home = (TabLayout) findViewById(R.id.home_bottom_tab_view);
//
//        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.mipmap.ic_news_list), 0);
//        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.mipmap.ic_fashion), 1);
//        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.mipmap.ic_food), 2);
//        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.mipmap.ic_technology), 3);
//        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.mipmap.ic_list_tab), 4);
////        tabLayout_home.addTab(tabLayout_home.newTab().setIcon(R.mipmap.ic_news_for_me), 5);
//        tabLayout_home.setOnTabSelectedListener(this);
        replaceDefaultFragment();
    }


    private void initMenuSelected() {
        nav_bottom_home.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.nav_news:
//                                tabLayout_home.getTabAt(0).setIcon(R.mipmap.ic_news_list);
                                disableItem();
                                replaceFragment(R.id.home_frame, new FragmentHomeNewsList(), "NEWS");
                                renameToolbar(R.string.nav_new_list);
                                break;
                            case R.id.nav_fashion:
//                                tabLayout_home.getTabAt(1).setIcon(R.mipmap.ic_fashion);
                                disableItem();
                                replaceFragment(R.id.home_frame, new FragmentHomeFashionMakeUp(), "FASHION");
                                renameToolbar(R.string.nav_fashion);
                                break;
                            case R.id.nav_food:
//                                tabLayout_home.getTabAt(2).setIcon(R.mipmap.ic_food);
                                disableItem();
                                replaceFragment(R.id.home_frame, new FragmentHomeFood(), "FOOD");
                                renameToolbar(R.string.nav_food);
                                break;
                            case R.id.nav_technology:
//                                tabLayout_home.getTabAt(3).setIcon(R.mipmap.ic_technology);
                                disableItem();
                                replaceFragment(R.id.home_frame, new FragmentHomeTechnology(), "TECHNOLOGY");
                                renameToolbar(R.string.nav_technology);
                                break;
                            case R.id.nav_list_item:
//                                tabLayout_home.getTabAt(4).setIcon(R.mipmap.ic_list_tab);
                                ln_popup_item.setVisibility(View.VISIBLE);
                                fr_home_overlay.setVisibility(View.VISIBLE);
                                break;
                        }
                        return true;
                    }
                });
    }

//    private void initBottomNavigationAction(TabLayout.Tab tab) {
//        switch (tab.getPosition()) {
//            case 0:
//                tabLayout_home.getTabAt(0).setIcon(R.mipmap.ic_news_list);
//                ln_popup_item.setVisibility(View.GONE);
//                replaceFragment(R.id.home_frame, new FragmentHomeNewsList(), "NEWS");
//                renameToolbar(R.string.nav_new_list);
//                break;
//            case 1:
//                tabLayout_home.getTabAt(1).setIcon(R.mipmap.ic_fashion);
//                ln_popup_item.setVisibility(View.GONE);
//                replaceFragment(R.id.home_frame, new FragmentHomeFashionMakeUp(), "FASHION");
//                renameToolbar(R.string.nav_fashion);
//                break;
//            case 2:
//                tabLayout_home.getTabAt(2).setIcon(R.mipmap.ic_food);
//                ln_popup_item.setVisibility(View.GONE);
//                replaceFragment(R.id.home_frame, new FragmentHomeFood(), "FOOD");
//                renameToolbar(R.string.nav_food);
//                break;
//            case 3:
//                tabLayout_home.getTabAt(3).setIcon(R.mipmap.ic_technology);
//                ln_popup_item.setVisibility(View.GONE);
//                replaceFragment(R.id.home_frame, new FragmentHomeTechnology(), "TECHNOLOGY");
//                renameToolbar(R.string.nav_technology);
//                break;
//            case 4:
//                tabLayout_home.getTabAt(4).setIcon(R.mipmap.ic_list_tab);
//                ln_popup_item.setVisibility(View.VISIBLE);
//                break;
////            case 4:
////                tabLayout_home.getTabAt(4).setIcon(R.mipmap.ic_another_service);
////                replaceFragment(R.id.home_frame, new FragmentHomeOtherService(), "SERVICE");
////                renameToolbar(R.string.nav_services);
////                break;
////            case 5:
////                tabLayout_home.getTabAt(5).setIcon(R.mipmap.ic_news_for_me);
////                replaceFragment(R.id.home_frame, new FragmentHomeNewsForMe(), "NEWS_FOR_ME");
////                renameToolbar(R.string.nav_news_for_me);
////                break;
//        }
//    }

//    public void showPopupLayout(View view) {
//        popupMenu = new PopupMenu(HomeActivity.this, view);
//        popupMenu.inflate(R.menu.popup_nemu);
//        popupMenu.setOnDismissListener(this);
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int id_menu = item.getItemId();
//                switch (id_menu) {
//                    case R.id.menu_news:
//                        showShortToast("News");
//                        replaceFragment(R.id.home_frame, new FragmentHomeHealth(), "NEWS");
//                        popupMenu.dismiss();
//                        break;
//                    case R.id.menu_news_location:
//                        showShortToast("News for location");
//                        replaceFragment(R.id.home_frame, new FragmentHomeNewsForMe(), "NEWS FOR LOCATION");
//                        popupMenu.dismiss();
//                        break;
//                    default:
//                        break;
//                }
//                return true;
//            }
//        });
//        popupMenu.show();
//    }

    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        btnPopup = (Button) findViewById(R.id.btnPopupMenu);
//        btnPopup.setOnClickListener(this);
        mLinearLayout = (LinearLayout) findViewById(R.id.ln_popup);
        ln_layout_transparent = (LinearLayout) findViewById(R.id.ln_layout_transparent);
        ln_layout_nav_item = (LinearLayout) findViewById(R.id.ln_layout_nav_item);
        ln_popup_item = (LinearLayoutAnimationSlideBottom) findViewById(R.id.ln_popup_item);
        ln_popup_item.setVisibility(View.GONE);
        ln_layout_transparent.setOnClickListener(this);
        btn_health = (Button) findViewById(R.id.btn_health);
        btn_health.setOnClickListener(this);
        btn_service = (Button) findViewById(R.id.btn_service);
        btn_service.setOnClickListener(this);
        btn_news_for_location = (Button) findViewById(R.id.btn_news_for_locations);
        btn_news_for_location.setOnClickListener(this);
        fr_home_overlay = (FrameLayout) findViewById(R.id.fr_home_overlay);
        fr_home_overlay.setOnClickListener(this);
    }

    private void disableItem() {
        ln_popup_item.setVisibility(View.GONE);
        fr_home_overlay.setVisibility(View.GONE);
    }

    private void replaceHealth() {
        disableItem();
        replaceFragment(R.id.home_frame, new FragmentHomeHealth(), "HEALTH");
        renameToolbar(R.string.nav_health);
    }

    private void replaceService() {
        disableItem();
        replaceFragment(R.id.home_frame, new FragmentHomeOtherService(), "SERVICE");
        renameToolbar(R.string.nav_services);
    }

    private void replaceNewsForLocation() {
        disableItem();
        replaceFragment(R.id.home_frame, new FragmentHomeNewsForMe(), "NEWS_FOR_LOCATION");
        renameToolbar(R.string.nav_news_for_me);
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
        mMenuItem = menu.findItem(R.id.action_user);
        if (avatar != null) {
//            setDrawableResource(userShort.getAvatar(), mMenuItem);
        }
        return true;
    }

    private void setImgUser2Toolbar(String avatar_url) {
        final ImageView imageView = new ImageView(this);
        imageView.setVisibility(View.GONE);

        Picasso.with(getApplicationContext())
                .load(avatar_url)
                .noPlaceholder()
                .transform(new CircleTransform())
                .error(R.mipmap.ic_launcher)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        mMenuItem.setIcon(imageView.getDrawable());
                        imageView.destroyDrawingCache();
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

//    private void setDrawableResource(String url, final Menu menu) {
//        Target target = new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                Log.e("Debug ", "onBitmapLoaded");
//                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
//                menu.getItem(1).setIcon(bitmapDrawable);
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//                if (errorDrawable != null)
//                Log.e("OnFailed", errorDrawable.toString());
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//                if (placeHolderDrawable != null)
//                Log.e("OnPrepare", placeHolderDrawable.toString());
//            }
//        };
//        Picasso.with(this)
//                .load(url)
//                .transform(new CircleTransform())
//                .error(R.drawable.ic_nemu_notification)
//                .into(target);
//    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        if (notifications != 0) {
//            onCreteBadgeItem(notifications);
//        }
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
        disableItem();
        startActivty(QrCheckIn.class);
    }


    private void onCreteBadgeItem(int paramsInt) {
        if (Build.VERSION.SDK_INT <= 15) {
            return;
        }
        LayerDrawable layerDrawable = (LayerDrawable) mMenuItem.getIcon();
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
        mMenuItem.setIcon(layerDrawable);
    }

    private void replaceDefaultFragment() {
//        tabLayout_home.getTabAt(0).setIcon(R.mipmap.ic_news_list);
        replaceFragment(R.id.home_frame, new FragmentHomeNewsList(), "NEWS");
        renameToolbar(R.string.nav_new_list);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            nav_bottom_home.setVisibility(View.VISIBLE);
            replaceDefaultFragment();
        } else if (id == R.id.nav_my_shop) {
            replaceMyShopFragment();
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

    private void replaceMyShopFragment() {
        disableItem();
        nav_bottom_home.setVisibility(View.GONE);
        replaceFragment(R.id.home_frame, new FragmentMyShop(), "MYSHOP");
        renameToolbar(R.string.fragment_myshop_content);
    }

    @Override
    public void startActivty(Class clazz) {
        super.startActivity(clazz);
    }

    @Override
    public void startActivityFinish(Class clazz) {
        super.startActivityFinish(clazz);
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
//            setDrawableResource(userShort.getAvatar(), mMenuItem);
        setImgUser2Toolbar(avatar);
        if (notifications != 0) {
            onCreteBadgeItem(notifications);
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
        } else if (id == R.id.ln_layout_transparent) {
            disableItem();
        } else if (id == R.id.btn_health) {
            replaceHealth();
        } else if (id == R.id.btn_service) {
            replaceService();
        } else if (id == R.id.btn_news_for_locations) {
            replaceNewsForLocation();
        } else if (id == R.id.fr_home_overlay){
            disableItem();
        }
//        else if (id == R.id.btnPopupMenu){
//            showPopupLayout(v);
//        }
    }

    private void pushData() {
        disableItem();
        Intent intent = new Intent(this, ProfileActivity.class);
        String push_data = "1";
        intent.putExtra("notification", push_data);
        startActivity(intent);
    }

    private void renameToolbar(int StringResource) {
        toolbar.setTitle(StringResource);
    }

    @Override
    protected void onResume() {
        presenter.onGetShortUser();
        super.onResume();
    }
}
