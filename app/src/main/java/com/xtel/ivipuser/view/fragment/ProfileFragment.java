package com.xtel.ivipuser.view.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xtel.ivipuser.R;
import com.xtel.ivipuser.model.entity.Profile;
import com.xtel.ivipuser.model.entity.RESP_Profile;
import com.xtel.ivipuser.model.entity.UserInfo;
import com.xtel.ivipuser.presenter.ProfilePresenter;
import com.xtel.ivipuser.view.activity.LoginActivity;
import com.xtel.ivipuser.view.activity.inf.IProfileActivityView;
import com.xtel.ivipuser.view.widget.RoundImage;
import com.xtel.nipservicesdk.utils.SharedUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by vihahb on 1/13/2017.
 */

public class ProfileFragment extends BasicFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, IProfileActivityView {

    public static final String TAG = "Profile frag ";
    public static String[] gender_spinner = {"Nam", "Nữ", "Khác"};
    public UserInfo userInfo;
    ProfilePresenter presenter;
    private TextView tv_name, tv_date_reg, tv_score, tv_rank;
    private EditText edt_name, edt_phone, edt_address, edt_birth;
    private Button btn_Logout;
    private RoundImage img_avatar;
    private Spinner sp_gender;
    private ArrayAdapter<String> arr_gender;
    private DatePickerDialog pickerDialog;

    public static String convertLong2Time(long time) {
        Date date = new Timestamp(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC+7"));
        String formatTime = dateFormat.format(date);
        return formatTime;
    }

    //    Profile profile;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_profile, container, false);
    }

    private void setResource(String url, RoundImage imageView) {
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.drawable.ic_action_name)
                .error(R.drawable.ic_action_name)
                .fit()
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ProfilePresenter(this);
        initView(view);
    }

    private void initView(View view) {

        tv_name = (TextView) view.findViewById(R.id.tv_username);
        tv_date_reg = (TextView) view.findViewById(R.id.tv_date_reg);
        tv_score = (TextView) view.findViewById(R.id.tv_score);
        tv_rank = (TextView) view.findViewById(R.id.tv_ranking);

        edt_name = (EditText) view.findViewById(R.id.edt_fullname);
        edt_address = (EditText) view.findViewById(R.id.edt_address_profile);
        edt_birth = (EditText) view.findViewById(R.id.edt_birthday_profile);
        edt_phone = (EditText) view.findViewById(R.id.edt_phone);

        img_avatar = (RoundImage) view.findViewById(R.id.img_avatar_profile);
        btn_Logout = (Button) view.findViewById(R.id.btn_Logout);

        sp_gender = (Spinner) view.findViewById(R.id.sp_gender);

        btn_Logout.setOnClickListener(this);
        initSpinner();
//        presenter.setProfile();
        presenter.getProfileData();

        initOnclick();
    }

    private void initOnclick() {
        edt_birth.setOnClickListener(this);
    }

    private void initSpinner() {
        arr_gender = new ArrayAdapter<String>(getContext(), R.layout.spinner_custom_view_simple, gender_spinner);
        arr_gender.setDropDownViewResource(R.layout.spinner_custom_drop_down_simple);
        sp_gender.setAdapter(arr_gender);
        sp_gender.setOnItemSelectedListener(this);
        userInfo = new UserInfo();
    }

    private void setSpinnerGender(int gender) {
        if (gender == 1) {
            sp_gender.setSelection(0);
        } else if (gender == 2) {
            sp_gender.setSelection(1);
        } else {
            sp_gender.setSelection(2);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_Logout) {
            SharedUtils.getInstance().clearData();
            finishActivityBeforeStartActivity(LoginActivity.class);
        } else if (id == R.id.edt_birthday_profile) {
            setTime();
        }
    }

    @Override
    public void showLongToast(String message) {
        super.showLongToast(message);
    }

    @Override
    public void onGetProfileSuccess(Profile profile) {

    }

    @Override
    public void onGetProfileEror() {

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
    public void setProfileSuccess(RESP_Profile profile) {
        Log.e(TAG + "get user", profile.toString());
        userInfo = profile.getUserInfo();
        setDataProfile(userInfo);
    }

    private void setDataProfile(UserInfo userInfo) {
        String f_name = userInfo.getFirst_name();
        String l_name = userInfo.getLast_name();
        String full_name;
        if (f_name != null && l_name != null) {
            full_name = f_name + " " + l_name;
            edt_name.setText(full_name);
            tv_name.setText(full_name);
        } else {
            full_name = " Chưa có tên";
            edt_name.setText(full_name);
            tv_name.setText(full_name);
        }


        String phone_number = userInfo.getPhone();
        if (phone_number != null) {
            edt_phone.setText(phone_number);
        } else {
            phone_number = "Chưa có số điện thoại";
            edt_phone.setText(phone_number);
        }

        String address = userInfo.getAddress();
        if (address != null) {
            edt_address.setText(address);
        } else {
            address = "Chưa có địa chỉ";
            edt_address.setText(address);
        }

        int gender = userInfo.getGender();
        if (gender >= 0) {
            setSpinnerGender(gender);
        } else {
            setSpinnerGender(3);
        }

        long birth_day = userInfo.getBirth_day();
        String date_birth;
        if (birth_day >= 0) {
            date_birth = convertLong2Time(birth_day);
            edt_birth.setText(date_birth);
        } else {
            date_birth = "Chưa có thông tin";
            edt_birth.setText(date_birth);
        }

        String ava = userInfo.getAvatar();
        if (ava != null) {
            setResource(ava, img_avatar);
        }
    }

    private void setTime() {
        Calendar calendar = Calendar.getInstance();
        pickerDialog = new DatePickerDialog(getContext(),
                R.style.TimePicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String time_change = year + "-" + (month + 1) + "-" + dayOfMonth;
                edt_birth.setText(time_change);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        pickerDialog.show();
    }


//    @Override
//    public void setProfileSuccess(Profile profile) {
//        Log.e("Profile model", profile.toString());
//        setData(
//                profile.getRank(),
//                profile.getDate_reg(),
//                profile.getScore(),
//                profile.getFirst_name(),
//                profile.getLast_name(),
//                profile.getGender(),
//                profile.getBirth_day(),
//                profile.getPhone(),
//                profile.getAddress(),
//                profile.getAvatar(),
//                profile.getEmail()
//        );
//    }

//    private void setData(String rank, String date_reg, int score, String f_name, String l_name, int gender, long birDay, String phone, String address, String avatar, String email) {
//
//        Log.e("set profile",
//                rank + ","
//                        + date_reg + ", "
//                        + score + ", "
//                        + f_name + ", "
//                        + l_name + ", "
//                        + gender + ", "
//                        + birDay + ", "
//                        + phone + ", "
//                        + address + ", "
//                        + avatar + ", "
//                        + email);
//        String full_name = f_name + " " + l_name;
//        String time_birth = convertLong2Time(birDay);
//        edt_name.setText(full_name);
//        edt_phone.setText(phone);
//        edt_birth.setText(time_birth);
//        edt_address.setText(address);
//
//        tv_rank.setText("Rank: " + rank);
//        tv_score.setText("Score: " + String.valueOf(score));
//        tv_date_reg.setText("Date register: " + date_reg);
//        tv_name.setText(full_name);
//        setResource(avatar, img_avatar);
//        setSpinnerGender(gender);
//    }

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

