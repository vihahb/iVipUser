package com.xtel.ivipuser.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xtel.ivipuser.R;
import com.xtel.ivipuser.view.activity.LoginActivity;
import com.xtel.ivipuser.view.activity.inf.IProfileActivityView;
import com.xtel.nipservicesdk.utils.SharedUtils;

/**
 * Created by vihahb on 1/13/2017.
 */

public class ProfileFragment extends BasicFragment implements View.OnClickListener, IProfileActivityView {

    private Button btn_Logout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        btn_Logout = (Button) view.findViewById(R.id.btn_Logout);
        btn_Logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_Logout) {
            SharedUtils.getInstance().clearData();
            finishActivityBeforeStartActivity(LoginActivity.class);
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onEror() {

    }

    @Override
    public void showLongToast(String message) {
        super.showLongToast(message);
    }

    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void startActivitys(Class clazz) {
        super.startActivity(clazz);
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityAndFinish(clazz);
    }

    @Override
    public void finishActivity() {
        super.finishActivity();
    }

    @Override
    public void finishActivityBeforeStartActivity(Class clazz) {
        super.finishActivityBeforeStartActivity(clazz);
    }
}

