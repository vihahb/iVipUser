package com.xtel.ivipuser.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;
import com.xtel.ivipuser.R;
import com.xtel.ivipuser.view.activity.LoginActivity;
import com.xtel.ivipuser.view.activity.inf.IProfileActivityView;
import com.xtel.ivipuser.view.widget.RoundImage;
import com.xtel.nipservicesdk.utils.SharedUtils;

/**
 * Created by vihahb on 1/13/2017.
 */

public class ProfileFragment extends BasicFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, IProfileActivityView {

    public static String[] gender_spinner = {"Nam", "Nữ", "Khác"};
    private Button btn_Logout;
    private RoundImage img_avatar;
    private Spinner sp_gender;
    private ArrayAdapter<String> arr_gender;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_profile, container, false);
    }

    private void setResource(String url, RoundImage imageView) {
        String avatar = url;
        Picasso.with(getContext())
                .load(avatar)
                .placeholder(R.drawable.ic_action_name)
                .error(R.drawable.ic_action_name)
                .fit()
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    private void initView(View view) {
        img_avatar = (RoundImage) view.findViewById(R.id.img_avatar_profile);
        btn_Logout = (Button) view.findViewById(R.id.btn_Logout);

        sp_gender = (Spinner) view.findViewById(R.id.sp_gender);

        btn_Logout.setOnClickListener(this);

        String url = "https://unige.ch/mcr/application/files/5614/7220/3533/avatar_square_512.png";
        setResource(url, img_avatar);

        initSpinner();
    }

    private void initSpinner() {
        arr_gender = new ArrayAdapter<String>(getContext(), R.layout.spinner_custom_view_simple, gender_spinner);
        arr_gender.setDropDownViewResource(R.layout.spinner_custom_drop_down_simple);
        sp_gender.setAdapter(arr_gender);
        sp_gender.setOnItemSelectedListener(this);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

