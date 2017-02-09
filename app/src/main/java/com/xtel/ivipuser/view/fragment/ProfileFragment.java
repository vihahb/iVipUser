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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xtel.ivipuser.R;
import com.xtel.ivipuser.model.entity.Profile;
import com.xtel.ivipuser.model.entity.UserInfo;
import com.xtel.ivipuser.presenter.ProfilePresenter;
import com.xtel.ivipuser.view.activity.LoginActivity;
import com.xtel.ivipuser.view.activity.inf.IProfileActivityView;
import com.xtel.ivipuser.view.widget.RoundImage;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.SharedUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by vihahb on 1/13/2017.
 */

public class ProfileFragment extends BasicFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, IProfileActivityView {

    public static final String TAG = "Profile frag ";
    public static String[] gender_spinner;
    public UserInfo userInfo;
    ProfilePresenter presenter;
    private TextView tv_name, tv_date_reg, tv_score, tv_rank;
    private EditText edt_name, edt_phone, edt_address, edt_birth;
    private Button btn_Logout;
    private RoundImage img_avatar;
    private ImageView img_cover;
    private LinearLayout cover_avatar;
    private Spinner sp_gender;
    private ArrayAdapter<String> arr_gender;
    private DatePickerDialog pickerDialog;

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

    private void setImageResource(String url, ImageView imageView) {
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

        img_cover = (ImageView) view.findViewById(R.id.profile_cover);
        cover_avatar = (LinearLayout) view.findViewById(R.id.cover_avatar);

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
        gender_spinner = getActivity().getResources().getStringArray(R.array.gender_profile);
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

    private String convertLong2Time(long time) {
//        long time_set = time * 10000;
//        Date date = new Timestamp(time_set);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC+7"));
//        String formatTime = dateFormat.format(date);

        Date date = new Date(time * 1000L);
        SimpleDateFormat formatTime = new SimpleDateFormat("yyyy-MM-dd");
        formatTime.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String formattedDate = formatTime.format(date);
        return formattedDate;
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
    public void setProfileSuccess(UserInfo profile) {
        Log.e("Fragment User info", JsonHelper.toJson(profile));
        setDataProfile(profile);
    }

    private void setDataProfile(UserInfo userInfo) {
        setName(userInfo);

        setPhone(userInfo);

        setAddress(userInfo);

        setGender(userInfo);

        setBirthDay(userInfo);

        setAvatar(userInfo);

        setJointDate(userInfo);

        setGeneralScore(userInfo);

        setLevel(userInfo);

        setCover(userInfo);
    }

    private void setCover(UserInfo userInfo) {
        String avatar = userInfo.getAvatar();
        if (avatar != null) {
            setImageResource(avatar, img_cover);

        }
    }

    private void setLevel(UserInfo userInfo) {
        String rank = userInfo.getLevel();
        if (rank != null) {
            tv_rank.setText("Xếp hạng: " + rank);
        } else {
            rank = "Chưa có cấp độ";
            tv_rank.setText(rank);
        }
    }

    private void setGeneralScore(UserInfo userInfo) {
        String score = String.valueOf(userInfo.getGeneral_point());
        if (score != null) {
            tv_score.setText("Điểm thưởng: " + score);
        } else {
            score = "Chưa có điểm thưởng";
            tv_score.setText(score);
        }
    }

    private void setJointDate(UserInfo userInfo) {
        String date_reg = convertLong2Time(userInfo.getJoin_date());
        Log.e("Time joint: ", date_reg);
        if (date_reg != null) {
            tv_date_reg.setText("Ngày đăng ký: " + date_reg);
        } else {
            date_reg = "Ngày đăng ký: Chưa có thông tin";
            tv_date_reg.setText(date_reg);
        }
    }

    private void setAvatar(UserInfo userInfo) {
        String avatar = userInfo.getAvatar();
        if (avatar != null) {
            setResource(avatar, img_avatar);
        }
    }

    private void setBirthDay(UserInfo userInfo) {
        long date_time = userInfo.getBirthday();
        String date_birth = convertLong2Time(userInfo.getBirthday());
        if (date_time != 0) {

        }
        if (date_birth != null && date_birth != "0") {
            edt_birth.setText(date_birth);
        } else {
            date_birth = getActivity().getResources().getString(R.string.no_birth_day);
            edt_birth.setText(date_birth);
        }
        Log.e("birth day: ", date_birth);
    }

    private void setGender(UserInfo userInfo) {
        int gender = userInfo.getGender();
        if (gender >= 0) {
            setSpinnerGender(gender);
        } else {
            setSpinnerGender(3);
        }
    }

    private void setAddress(UserInfo userInfo) {
        String address = userInfo.getAddress();
        if (address != null) {
            edt_address.setText(address);
        } else {
            address = getActivity().getResources().getString(R.string.no_address);
            edt_address.setText(address);
        }
    }

    private void setPhone(UserInfo userInfo) {
        String phone_number = userInfo.getPhonenumber();
        if (phone_number != null) {
            edt_phone.setText(phone_number);
        } else {
            phone_number = getActivity().getResources().getString(R.string.no_phone);
            edt_phone.setText(phone_number);
        }
    }

    private void setName(UserInfo userInfo) {
        String full_name = userInfo.getFullname();
        if (full_name != null) {
            edt_name.setText(full_name);
            tv_name.setText(full_name);
        } else {
            full_name = getActivity().getResources().getString(R.string.no_name);
            edt_name.setText(full_name);
            tv_name.setText(full_name);
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

