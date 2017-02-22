package com.xtel.ivipu.presenter;

import android.util.Log;

import com.xtel.ivipu.model.LoginModel;
import com.xtel.ivipu.model.RESP.RESP_Profile;
import com.xtel.ivipu.model.RESP.RESP_Short;
import com.xtel.ivipu.view.activity.inf.IHome;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.utils.SharedPreferencesUtils;

/**
 * Created by vivhp on 12/29/2016.
 */

public class HomePresenter {
    private int new_notifycation = 0;
    private IHome view;
    private String TAG = "Home presenter";

    public HomePresenter(IHome iHome) {
        this.view = iHome;
    }

//    public void onGetUserNip() {
//        String session = SharedUtils.getInstance().getStringValue(Constants.SESSION);
//        String url = "http://124.158.5.112:9180/nipum/v1.0/g/user/info/";
//        Log.e(TAG + "url", url);
//        LoginModel.getInstance().getUser(url, session, new ResponseHandle<RESP_Profile>(RESP_Profile.class) {
//            @Override
//            public void onSuccess(RESP_Profile obj) {
//                String name = obj.getFirst_name() + " " + obj.getLast_name();
//                Log.e(TAG + "name", name);
//            }
//
//            @Override
//            public void onError(com.xtel.ivipu.model.entity.Error error) {
//                Log.e(TAG + "err mes", error.getMessage());
//            }
//        });
//    }

    public void onGetUserNip() {
        String session = LoginManager.getCurrentSession();

        String url_profile = Constants.SERVER_IVIP + Constants.GET_USER_IVIP_FULL;
        Log.e(TAG + "url", url_profile);
        LoginModel.getInstance().getUser(url_profile, session, new ResponseHandle<RESP_Profile>(RESP_Profile.class) {
            @Override
            public void onSuccess(RESP_Profile obj) {
                Log.d(TAG + "succ", JsonHelper.toJson(obj));
                saveData2Share(
                        obj.getFullname(),
                        obj.getGender(),
                        obj.getBirthday(),
                        obj.getEmail(),
                        obj.getPhonenumber(),
                        obj.getAddress(),
                        obj.getAvatar(),
                        obj.getQr_code(),
                        obj.getBar_code(),
                        obj.getStatus(),
                        obj.getGeneral_point(),
                        obj.getLevel(),
                        obj.getJoin_date()
                );
            }

            @Override
            public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                Log.e(TAG + "err", error.getMessage());
                view.showShortToast(parseMessage(error.getCode()));
            }
        });
    }

    public void onGetShortUser() {
        String session = LoginManager.getCurrentSession();
        String url_profile = Constants.SERVER_IVIP + Constants.GET_USER_IVIP_SORT;
        Log.e(TAG + "url", url_profile);

        LoginModel.getInstance().getUser(url_profile, session, new ResponseHandle<RESP_Short>(RESP_Short.class) {
            @Override
            public void onSuccess(RESP_Short obj) {
                int notification = obj.getNew_notify();
                Log.e("New notification", String.valueOf(notification));
                view.getShortUser(obj);
            }

            @Override
            public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                if (error != null) {
                    int code_err = error.getCode();
                    if (code_err == 2) {
                        CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                            @Override
                            public void onSuccess(RESP_Login success) {
                                onGetShortUser();
                            }

                            @Override
                            public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                                int code = error.getCode();
                                view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), code, null));
                            }
                        });
                    } else {
                        view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), code_err, null));
                    }
                }
            }
        });
    }

    private String parseMessage(int code) {
        String mess = JsonParse.getCodeMessage(view.getActivity(), code, "");
        return mess;
    }

    private void saveData2Share(
            String full_name,
            int gender,
            long birth_day,
            String email,
            String phone_number,
            String address,
            String avatar,
            String qr_code,
            String bar_code,
            int status,
            int general_point,
            String level,
            long joint_date) {
        SharedPreferencesUtils.getInstance().putStringValue(Constants.PROFILE_FULL_NAME, full_name);
        SharedPreferencesUtils.getInstance().putIntValue(Constants.PROFILE_GENDER, gender);
        SharedPreferencesUtils.getInstance().putLongValue(Constants.PROFILE_BIRTH_DAY, birth_day);
        SharedPreferencesUtils.getInstance().putStringValue(Constants.PROFILE_EMAIL, email);
        SharedPreferencesUtils.getInstance().putStringValue(Constants.PROFILE_PHONE_NUM, phone_number);
        SharedPreferencesUtils.getInstance().putStringValue(Constants.PROFILE_ADDRESS, address);
        SharedPreferencesUtils.getInstance().putStringValue(Constants.PROFILE_AVATAR, avatar);
        SharedPreferencesUtils.getInstance().putStringValue(Constants.PROFILE_QR_CODE, qr_code);
        SharedPreferencesUtils.getInstance().putStringValue(Constants.PROFILE_BAR_CODE, bar_code);
        SharedPreferencesUtils.getInstance().putIntValue(Constants.PROFILE_STATUS, status);
        SharedPreferencesUtils.getInstance().putIntValue(Constants.PROFILE_GENERAL_POINT, general_point);
        SharedPreferencesUtils.getInstance().putStringValue(Constants.PROFILE_LEVEL, level);
        SharedPreferencesUtils.getInstance().putLongValue(Constants.PROFILE_JOINT_DATE, joint_date);
        Log.e(TAG + " share", full_name);
    }
}