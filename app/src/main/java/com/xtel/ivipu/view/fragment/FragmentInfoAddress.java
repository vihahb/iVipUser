package com.xtel.ivipu.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.xtel.ivipu.R;
import com.xtel.ivipu.model.RESP.RESP_NewEntity;
import com.xtel.ivipu.model.RESP.RESP_NewsObject;
import com.xtel.ivipu.model.entity.NewsObj;
import com.xtel.ivipu.model.entity.Shop_Address;
import com.xtel.ivipu.presenter.FragmentInfoAddressPresenter;
import com.xtel.ivipu.view.fragment.inf.IFragmentAddressView;
import com.xtel.ivipu.view.widget.WidgetHelper;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.PermissionHelper;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.commons.NetWorkInfo;
import com.xtel.sdk.utils.GPSTracker;

import java.util.ArrayList;
import java.util.HashMap;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


/**
 * Created by vihahb on 1/17/2017.
 */

public class FragmentInfoAddress extends BasicFragment implements IFragmentAddressView, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapLongClickListener, LocationListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnMapClickListener {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    public static GoogleApiClient mGoogleApiClient;
    private static boolean isFindMyLocation;
    String type;
    private GoogleMap mMap;
    private LocationRequest mLocationRequest;
    private Marker pickMarker;
    private boolean isCanLoadMap = true;
    private GPSTracker tracker;
    private RESP_NewEntity newEntity;
    private FragmentInfoAddressPresenter presenter;
    private int id_news, id;
    private NewsObj newsObject;
    private String[] permission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private int REQUEST_PERMISSION_LOCATION = 100;
    private HashMap<Marker, Shop_Address> hashMap_Maker;
    private Marker getPickMarker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_address, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tracker = new GPSTracker(getContext());
        presenter = new FragmentInfoAddressPresenter(this);
        initPermission();
    }

    private void initPermission() {
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkBooleanPermission()) {
                Toast.makeText(getContext(), "Permission already granted", Toast.LENGTH_SHORT).show();
                createLocationRequest();
                initGoogleMaps();
                getDataFromFragmentShop();
            } else {
                requestPermission();
            }
        }
    }

    private void requestPermission() {
        PermissionHelper.checkListPermission(permission, getActivity(), REQUEST_PERMISSION_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            boolean fineLocationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            boolean coasLocationAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
            if (fineLocationAccepted && coasLocationAccepted) {
                Toast.makeText(getContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermission();
                }
            }
        }
    }

    private boolean checkBooleanPermission() {
        boolean check_done = true;
        if (!(ContextCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            check_done = false;
        } else if (!(ContextCompat.checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            check_done = false;
        }
        return check_done;
    }

    private void createLocationRequest() {
        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
    }

    private void initGoogleMaps() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_ivip);
        mapFragment.getMapAsync(this);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        hashMap_Maker = new HashMap<>();

    }

//    private boolean checkPermission() {
//        try {
//            return !(ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    private boolean validData() {
        try {
            newEntity = (RESP_NewEntity) getActivity().getIntent().getSerializableExtra(Constants.RECYCLER_MODEL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newEntity != null;
    }

    private void getDataFromFragmentShop() {
        if (validData()) {
            id_news = newEntity.getId();
            presenter.getNewsInfo(id_news);
        }
    }

    private void checkNetWork(int type_optional) {
        final Context context = getContext();
        if (!NetWorkInfo.isOnline(context)) {
            WidgetHelper.getInstance().showAlertNetwork(context);
        } else {
            if (type_optional == 1) {
                Log.e("Id options", String.valueOf(id));
                Log.e("Type options", type);
                presenter.getAddress(id, type);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (!isFindMyLocation) {
            if (location != null) {
                isFindMyLocation = true;
                mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (!isFindMyLocation) {
        }
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onCameraIdle() {
        double lat = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter().latitude;
        double lng = mMap.getProjection().getVisibleRegion().latLngBounds.getCenter().longitude;

        if (isCanLoadMap) {
            isCanLoadMap = false;

        }

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(tracker.getLatitude(), tracker.getLatitude()), 15));
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnCameraIdleListener(this);
        setMapSetting();
    }

    @Override
    public void onStart() {
        try {
            mGoogleApiClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        try {
            mGoogleApiClient.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected())
            try {
                startLocationUpdates();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void onStop() {
        try {
            stopLocationUpdates();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityAndFinish(clazz);
    }

    public void setMapSetting() {
        if (mMap != null) {
            mMap.getUiSettings().setMapToolbarEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            if (checkBooleanPermission()) {
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    protected void startLocationUpdates() {
        if (checkBooleanPermission())
            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    protected void stopLocationUpdates() {
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetAddressSuccess(ArrayList<Shop_Address> arrayList) {
        Log.e("Arr shop Adress", JsonHelper.toJson(arrayList));
    }

    @Override
    public void onGetNewsObjSuccess(RESP_NewsObject object) {
        newsObject = new NewsObj();
        Log.e("news obj", JsonHelper.toJson(newsObject));
        int store_id = object.getStore_id();
        int chain_id = object.getChain_store_id();
        if (!String.valueOf(store_id).equals("0")) {
            type = "store";
            id = store_id;
        } else {
            type = "chain";
            id = chain_id;
        }
        checkNetWork(1);
    }

    @Override
    public void onGetAddressError() {

    }
}
