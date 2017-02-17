package com.xtel.ivipu.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;
import com.xtel.ivipu.R;
import com.xtel.ivipu.presenter.ScannerQrPresenter;
import com.xtel.ivipu.view.activity.inf.IScannerView;
import com.xtel.nipservicesdk.utils.PermissionHelper;
import com.xtel.sdk.callback.DialogListener;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

/**
 * Created by vivhp on 2/14/2017.
 */

public class QrCheckIn extends BasicActivity implements ZXingScannerView.ResultHandler, IScannerView {

    private static final int REQUEST_PERMISSION_ALL = 2;
    private ZXingScannerView mZXingScannerView;
    private ScannerQrPresenter presenter;

    private String[] permission = new String[]{Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private ViewGroup frameContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate", "onCreate QrCodeScanner");
        presenter = new ScannerQrPresenter(this);
        setContentView(R.layout.layout_scanner_qr_bar);
        initToolBar(R.id.scanqr_toolbar, getString(R.string.activity_checkin_content));
        initView();
        initScannerView();

        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getApplicationContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
            } else {
                requestPermission();
            }
        }
    }

    private void initScannerView() {
        mZXingScannerView = new ZXingScannerView(getApplicationContext()) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                return super.createViewFinderView(context);
            }
        };
        frameContent.addView(mZXingScannerView);
    }

    private void initView() {
        frameContent = (ViewGroup) findViewById(R.id.scanqr_content);
    }

    private void initToolBar(int id, String title) {
        Toolbar toolbar = (Toolbar) findViewById(id);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (title != null)
            actionBar.setTitle(title);
    }


    private void requestPermission() {
        PermissionHelper.checkListPermission(permission, this, REQUEST_PERMISSION_ALL);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_ALL:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean fineLocationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean coasLocationAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && fineLocationAccepted && coasLocationAccepted) {
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                    requestPermissions(new String[]{CAMERA},
//                                                            REQUEST_CAMERA);
                                                    requestPermission();
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(QrCheckIn.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean checkPermission() {
        boolean check_done = true;
        if (!(ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            check_done = false;
        } else if (!(ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            check_done = false;
        } else if (!(ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            check_done = false;
        }
        return check_done;
    }

    @Override
    public void handleResult(Result rawResult) {
        final String result = rawResult.getText();
        Log.d("QRCodeScanner", rawResult.getText());
        Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());

        String content = getString(R.string.action_checkin_success_content);
        String reward = getString(R.string.action_checkin_success_score_reward);
        onCheckinSuccess(result, content, reward);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if (mZXingScannerView == null) {
                    mZXingScannerView = new ZXingScannerView(this);
                    setContentView(mZXingScannerView);
                }
                mZXingScannerView.setResultHandler(this);
                mZXingScannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStartChecking() {
    }

    @Override
    public void onCheckinSuccess(String mes, String content, String reward) {
        showDialogCheckinNotification(getString(R.string.action_checkin_success), content, reward, new DialogListener() {
            @Override
            public void onClicked(Object object) {
                finish();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public void onCheckinError(Error error) {

    }

    @Override
    public Activity getActivity() {
        return null;
    }
}
