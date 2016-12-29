package com.xtel.ivipuser.view.activity;

import android.os.Bundle;
import android.os.Handler;

import com.xtel.ivipuser.R;

public class SplashScreen extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityFinish(HomeActivity.class);
            }
        }, 500);
    }
}
