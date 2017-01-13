package com.xtel.sdk.fcm;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by vihahb on 1/10/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {


    private static final String TAG = "MyFirebaseInstanceIDService";

    @SuppressLint("LongLogTag")
    @Override
    public void onTokenRefresh() {
        //Get update token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "new token " + refreshedToken);


    }
}
