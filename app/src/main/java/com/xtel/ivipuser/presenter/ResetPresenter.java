package com.xtel.ivipuser.presenter;

import android.util.Log;

import com.xtel.ivipuser.view.activity.inf.IResetView;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.callback.CallbackListenerReset;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Reset;
import com.xtel.nipservicesdk.utils.JsonParse;

/**
 * Created by vihahb on 1/11/2017.
 */

public class ResetPresenter {
    public static final String TAG = "Reset";
    CallbackManager callbackManager;
    private IResetView view;

    public ResetPresenter(IResetView view) {
        this.view = view;
    }

    public void createCallbackManager() {
        callbackManager = CallbackManager.create(view.getActivity());
    }

    public void requestPermission(int requestCode, String[] pesmission, int[] grantResults) {
        callbackManager.onRequestPermissionsResult(requestCode, pesmission, grantResults);
    }


    public void resetAccountNIP(String password, String authorization_code) {
        callbackManager.AdapterReset(null, password, authorization_code, new CallbackListenerReset() {
            @Override
            public void onSuccess(RESP_Reset reset) {
                view.showShortToast("Thay đổi mật khẩu thành công");
                Log.e(TAG, "New password: " + reset.getPassword());
                view.finishActivity();
            }

            @Override
            public void onError(Error error) {
                Log.e(TAG, "Error code: " + error.getCode());
                String code_err = String.valueOf(error.getCode());
                int code = Integer.parseInt(code_err);
                if (!(code_err == null)) {
                    view.showShortToast(parseMessage(code));
                }
            }
        });
    }

    private String parseMessage(int code) {
        String mess = JsonParse.getCodeMessage(view.getActivity(), code, "");
        return mess;
    }

}
