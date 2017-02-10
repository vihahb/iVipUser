package com.xtel.ivipu.view.fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.RESP_Profile;
import com.xtel.ivipu.model.entity.UserInfo;
import com.xtel.ivipu.presenter.ProfilePresenter;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.activity.inf.IProfileActivityView;
import com.xtel.ivipu.view.widget.RoundImage;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.PermissionHelper;
import com.xtel.nipservicesdk.utils.SharedUtils;
import com.xtel.sdk.commons.NetWorkInfo;
import com.xtel.sdk.utils.SharedPreferencesUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.app.Activity.RESULT_OK;

/**
 * Created by vihahb on 1/13/2017.
 */

public class ProfileFragment extends BasicFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener, IProfileActivityView {

    public static final String TAG = "Profile frag ";
    public static String[] gender_spinner;
    private final int CAMERA_REQUEST_CODE = 1002;
    public UserInfo userInfo;
    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    ProfilePresenter presenter;
    String time_set;
    private TextView tv_name, tv_date_reg, tv_score, tv_rank;
    private EditText edt_name, edt_phone, edt_address, edt_birth;
    private Button btn_Logout, btn_Update;
    private RoundImage img_avatar;
    private ImageView img_cover, img_profile_change_avatar;
    private LinearLayout cover_avatar;
    private Spinner sp_gender;
    private ArrayAdapter<String> arr_gender;
    private DatePickerDialog pickerDialog;
    private String time_get;
    private Date date;
    private String gender_set;

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
        img_profile_change_avatar = (ImageView) view.findViewById(R.id.img_profile_change_avatar);
        btn_Logout = (Button) view.findViewById(R.id.btn_Logout);
        btn_Update = (Button) view.findViewById(R.id.btn_update);

        img_cover = (ImageView) view.findViewById(R.id.profile_cover);
        cover_avatar = (LinearLayout) view.findViewById(R.id.cover_avatar);

        sp_gender = (Spinner) view.findViewById(R.id.sp_gender);

        btn_Logout.setOnClickListener(this);
        btn_Update.setOnClickListener(this);
        img_profile_change_avatar.setOnClickListener(this);
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
            SharedPreferencesUtils.getInstance().clearData();
            finishActivityBeforeStartActivity(LoginActivity.class);
        } else if (id == R.id.edt_birthday_profile) {
            setTime();
        } else if (id == R.id.btn_update) {
            checkNetwork(getContext(), 1);
        } else if (id == R.id.img_profile_change_avatar) {
            checkNetwork(getContext(), 2);
            showShortToast("Image Clicked");
            Log.e("Click Image", "Click image");
        }
    }

    private String convertLong2Time(long time) {
//        long time_set = time * 10000;
//        Date date = new Timestamp(time_set);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC+7"));
//        String formatTime = dateFormat.format(date);
        Date date = new Date(time * 1000);
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

    @Override
    public void reloadProfile(RESP_Profile profile) {
        UserInfo userInfo = new UserInfo();

        userInfo.setFullname(profile.getFullname());
        userInfo.setGender(profile.getGender());
        userInfo.setBirthday(profile.getBirthday());
        userInfo.setEmail(profile.getEmail());
        userInfo.setPhonenumber(profile.getPhonenumber());
        userInfo.setAddress(profile.getAddress());
        userInfo.setAvatar(profile.getAvatar());
        userInfo.setQr_code(profile.getQr_code());
        userInfo.setBar_code(profile.getBar_code());
        userInfo.setStatus(profile.getStatus());
        userInfo.setGeneral_point(profile.getGeneral_point());
        userInfo.setLevel(profile.getLevel());
        userInfo.setJoin_date(profile.getJoin_date());

        Log.e("Reload ", userInfo.toString());
        setDataProfile(userInfo);
    }

    @Override
    public void updateProfileSucc() {
        showShortToast(getString(R.string.updated_data));
    }

    @Override
    public void onPostPictureSuccess(String url) {
        setResource(url, img_avatar);
    }

    private void onTakePictureGallary(Uri uri) {
        if (!NetWorkInfo.isOnline(getActivity())) {
            showShortToast(getString(R.string.no_internet));
            return;
        } else if (uri == null) {
            showShortToast("Không thể lấy ảnh");
            return;
        }

        showProgressBar(false, false, null, "Đang tải file...");

        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        presenter.postImage(bitmap);
    }

    private void onTakePictureCamera(Bitmap bitmap) {
        if (!NetWorkInfo.isOnline(getActivity())) {
            showShortToast(getString(R.string.no_internet));
            return;
        } else if (bitmap == null) {
            showShortToast("Không thể lấy ảnh");
            return;
        }

        showProgressBar(false, false, null, "Đang tải file...");

        presenter.postImage(bitmap);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                onTakePictureGallary(uri);
            } else {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                onTakePictureCamera(bitmap);
            }
            Log.e("URI", uri.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initCamera();
            }
        }
    }

    private void initCamera() {
        final Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");

        //Create any other intents you want
        final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Add them to an intent array
        Intent[] intents = new Intent[]{cameraIntent};

        //Create a choose from your first intent then pass in the intent array
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Chọn ảnh");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
        startActivityForResult(chooserIntent, 101);
    }

    @Override
    public void onPostPictureError(String mes) {
        showShortToast(mes);
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
            edt_birth.setText(date_birth);
        } else {
            date_birth = getActivity().getResources().getString(R.string.no_birth_day);
            edt_birth.setText(date_birth);
        }
        Log.e("birth day: ", date_birth);
        time_get = date_birth;
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
        DateFormat dateFormat_time = new SimpleDateFormat("yyyy-MM-dd");
        int Days, months, years;
        if (!time_get.equals(getActivity().getResources().getString(R.string.no_birth_day))) {
            try {
                date = dateFormat_time.parse(time_get);
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Days = calendar.get(Calendar.DAY_OF_MONTH);
            months = calendar.get(Calendar.MONTH);
            years = calendar.get(Calendar.YEAR);
        } else {
            Days = calendar.get(Calendar.DAY_OF_MONTH);
            months = calendar.get(Calendar.MONTH);
            years = calendar.get(Calendar.YEAR);
        }
        pickerDialog = new DatePickerDialog(getContext(),
                R.style.TimePicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String time_change = year + "-" + (month + 1) + "-" + dayOfMonth;
                Log.e("Time set", time_change);
                edt_birth.setText(time_change);
                time_set = time_change;
            }
        }, years, months, Days);
        pickerDialog.show();
    }

    @Override
    public void finishActivityBeforeStartActivity(Class clazz) {
        super.finishActivityBeforeStartActivity(clazz);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int sp_id = parent.getId();
        if (sp_id == R.id.sp_gender) {
            gender_set = arr_gender.getItem(position);
            showShortToast(String.valueOf(gender_set));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_update) {
            showShortToast("Update");
        }
        return super.onOptionsItemSelected(item);
    }

    public void onGetUpdate() {
        time_set = edt_birth.getText().toString();
        String full_name = edt_name.getText().toString();
        String phone_number = edt_phone.getText().toString();
        String address = edt_address.getText().toString();
        long birth_day_set = convertTime2Unix(time_set);
        int gender;
        if (gender_set.equals("Nữ")) {
            gender = 1;
        } else if (gender_set.equals("Nam")) {
            gender = 2;
        } else {
            gender = 3;
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setFullname(full_name);
        userInfo.setPhonenumber(phone_number);
        userInfo.setAddress(address);
        userInfo.setBirthday(birth_day_set);
        userInfo.setGender(gender);
        Log.e("Test object request", userInfo.toString());
        presenter.setData(userInfo);
        setDataProfile(userInfo);
    }

    private long convertTime2Unix(String date_time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = dateFormat.parse(date_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long unixTime = (date.getTime()) / 1000;
        Log.e("Time Unix ", String.valueOf(unixTime));
        return unixTime;
    }

    private void checkNetwork(final Context context, int type) {
        if (!NetWorkInfo.isOnline(context)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.TimePicker);
            dialog.setTitle("Kết nối không thành công");
            dialog.setMessage("Rất tiếc, không thể kết nối internet. Vui lòng kiểm tra kết nối Internet.");
            dialog.setPositiveButton("Cài đặt", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                }
            });
            dialog.show();
        } else {
            if (type == 1) {
                onGetUpdate();
            } else if (type == 2) {
                if (PermissionHelper.checkListPermission(permission, getActivity(), CAMERA_REQUEST_CODE)) {
                    initCamera();
                }
            }
        }
    }
}

