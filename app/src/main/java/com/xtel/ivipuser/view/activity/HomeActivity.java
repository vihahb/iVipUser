package com.xtel.ivipuser.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.xtel.ivipuser.R;
import com.xtel.ivipuser.presenter.HomePresenter;
import com.xtel.ivipuser.view.activity.inf.IHome;
import com.xtel.sdk.utils.BadgeUtils;

/**
 * Created by vivhp on 12/29/2016.
 */

public class HomeActivity extends BasicActivity implements NavigationView.OnNavigationItemSelectedListener, IHome, View.OnClickListener {
    HomePresenter presenter;

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ImageView img_avatar;
    private ActionBar actionBar;
    private int mNotificationsCount = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        presenter = new HomePresenter(this);
        initView();
        initNavigation();
        initNavigationWidget();
    }

    private void initNavigationWidget() {
        View view = navigationView.getHeaderView(0);
        img_avatar = (ImageView) view.findViewById(R.id.img_avatar);
        img_avatar.setOnClickListener(this);
    }

    private void initView() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = FirebaseInstanceId.getInstance().getToken();
                showSnackBarShort(v, "Token " + token);
                Log.d("Token ", token);
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void initNavigation() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_user);
        LayerDrawable icon = (LayerDrawable) item.getIcon();
        //Update layoutDrawable badge drawable
        BadgeUtils.setBadgeCount(getApplicationContext(), icon, mNotificationsCount);
        new FeatchCountTask().execute();
        return true;
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

    private void updateNotificationsBadge(int count) {
        mNotificationsCount = count;
        invalidateOptionsMenu();
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

    class FeatchCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
// example count. This is where you'd
            // query your data store for the actual count.
            return mNotificationsCount;
        }

        @Override
        protected void onPostExecute(Integer count) {
            updateNotificationsBadge(count);
        }
    }

}
