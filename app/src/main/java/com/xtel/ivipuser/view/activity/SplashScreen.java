package com.xtel.ivipuser.view.activity;

import android.os.Bundle;
import android.os.Handler;

import com.xtel.ivipuser.R;
import com.xtel.nipservicesdk.LoginManager;

public class SplashScreen extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkSession();
            }
        }, 500);
    }

    private void checkSession() {
        String session = LoginManager.getCurrentSessiong();

        if (session == null) {
            startActivityFinish(LoginActivity.class);
        } else {
            startActivityFinish(HomeActivity.class);
        }
    }
}
