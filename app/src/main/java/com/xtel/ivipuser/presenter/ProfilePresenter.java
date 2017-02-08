package com.xtel.ivipuser.presenter;

import android.util.Log;

import com.xtel.ivipuser.model.LoginModel;
import com.xtel.ivipuser.model.entity.Error;
import com.xtel.ivipuser.model.entity.Profile;
import com.xtel.ivipuser.model.entity.RESP_Profile;
import com.xtel.ivipuser.view.activity.inf.IProfileActivityView;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.sdk.callback.ResponseHandle;
import com.xtel.sdk.utils.JsonHelper;

/**
 * Created by vihahb on 1/13/2017.
 */

public class ProfilePresenter {

    public static final String TAG = "Profile_presenter";

    Profile profile;
    private IProfileActivityView view;
    public ProfilePresenter(IProfileActivityView view) {
        this.view = view;
    }


//    public void setProfile() {
//        String rank = "Diamond";
//        String date_reg = "2017/17/01";
//        int score = 100;
//        String f_name = "Vi";
//        String l_name = "Vũ Hà";
//        int gender = 3;
//        long birthday = 838425617000l;
//        String phone = "01673378303";
//        String address = "Hòa Bình - Việt Nam";
//        String ava = "https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/13528706_849870591811691_1547349153482224738_n.jpg?oh=02ba740bab49c1a839995abad5f0802d&oe=59483152";
//        String email = "vihahb@icloud.com";
//        profile = new Profile();
//        profile.setScore(score);
//        profile.setRank(rank);
//        profile.setDate_reg(date_reg);
//        profile.setFirst_name(f_name);
//        profile.setLast_name(l_name);
//        profile.setBirth_day(birthday);
//        profile.setPhone(phone);
//        profile.setGender(gender);
//        profile.setAddress(address);
//        profile.setAvatar(ava);
//        profile.setEmail(email);
//        view.setProfileSuccess(profile);
//    }

    public void getProfileData() {
        String session = LoginManager.getCurrentSessiong();
        Log.e(TAG + "ses", session);
        String url_profile = "http://124.158.5.112:9180/nipum/v1.0/g/user/info/" + session;
        LoginModel.getInstance().getUser(url_profile, null, new ResponseHandle<RESP_Profile>(RESP_Profile.class) {
            @Override
            public void onSuccess(RESP_Profile obj) {
                Log.d(TAG + "succ", JsonHelper.toJson(obj));
                view.setProfileSuccess(obj);
            }

            @Override
            public void onError(Error error) {
                Log.e(TAG + "err", error.getMessage());
            }
        });
    }

}
