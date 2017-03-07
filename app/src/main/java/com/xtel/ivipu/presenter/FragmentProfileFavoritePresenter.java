package com.xtel.ivipu.presenter;

import android.os.Handler;

import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.RESP.RESP_ListNews;
import com.xtel.ivipu.view.activity.LoginActivity;
import com.xtel.ivipu.view.fragment.inf.IFragmentFavoriteView;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.commons.NetWorkInfo;

/**
 * Created by vuhavi on 03/03/2017.
 */

public class FragmentProfileFavoritePresenter {

    private IFragmentFavoriteView view;

    public FragmentProfileFavoritePresenter(IFragmentFavoriteView view) {
        this.view = view;
    }

    public void getFavorite(final int page, final int pagesize){

        if (!NetWorkInfo.isOnline(view.getActivity())) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.onNetworkDisable();
                }
            }, 500);
            return;
        } else {
            String session = LoginManager.getCurrentSession();
            String url_favorite = Constants.SERVER_IVIP + "v0.1/user/like? page=" + page + "&pagesize=" + pagesize;

            HomeModel.getInstance().getFavorite(url_favorite, session, new ResponseHandle<RESP_ListNews>(RESP_ListNews.class) {
                @Override
                public void onSuccess(RESP_ListNews obj) {
                    view.onGetFavoriteSuccess(obj.getData());
                }

                @Override
                public void onError(Error error) {
                    if (error != null){
                        int code = error.getCode();
                        if (code == 2){
                            CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                                @Override
                                public void onSuccess(RESP_Login success) {
                                    getFavorite(page, pagesize);
                                }

                                @Override
                                public void onError(Error error) {
                                    view.startActivityAndFinish(LoginActivity.class);
                                    view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                                }
                            });
                        } else {
                            view.showShortToast(JsonParse.getCodeMessage(view.getActivity(), code, null));
                        }
                    }
                }
            });
        }
    }
}
