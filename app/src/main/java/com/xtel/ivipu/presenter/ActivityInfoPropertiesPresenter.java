package com.xtel.ivipu.presenter;

import android.util.Log;

import com.xtel.ivipu.model.HomeModel;
import com.xtel.ivipu.model.entity.NewsActionEntity;
import com.xtel.ivipu.model.entity.RESP_NewsObject;
import com.xtel.ivipu.view.activity.inf.IActivityInfo;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.model.entity.RESP_None;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtel.sdk.commons.Constants;

/**
 * Created by vivhp on 1/24/2017.
 */

public class ActivityInfoPropertiesPresenter {
    private IActivityInfo view;
    private String TAG = "Info presenter";

    public ActivityInfoPropertiesPresenter(IActivityInfo view) {
        this.view = view;
    }

    public void getNewsInfomation(final int id_news) {
        String session = LoginManager.getCurrentSession();
        String url_news_info = Constants.SERVER_IVIP + Constants.NEWS_INFO + id_news;
        HomeModel.getInstance().getNewsInfomation(url_news_info, session, new ResponseHandle<RESP_NewsObject>(RESP_NewsObject.class) {
            @Override
            public void onSuccess(RESP_NewsObject obj) {
                Log.e("News obj", "data " + JsonHelper.toJson(obj));
                view.onGetNewsObjSuccess(obj);
            }

            @Override
            public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                int code = error.getCode();
                Log.e("err json", error.toString());
                if (code == 2) {
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            getNewsInfomation(id_news);
                        }

                        @Override
                        public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                            Log.e("err callback", JsonHelper.toJson(error));
                            view.showSortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                        }
                    });
                } else {
                    view.showSortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                }
            }

        });
    }

    public void onLikeAction(final int id_news) {
        String session = LoginManager.getCurrentSession();
        String url_action_like = Constants.SERVER_IVIP + Constants.NEWS_ACTION;
        NewsActionEntity newsAction = new NewsActionEntity();
        newsAction.setNews_id(id_news);
        newsAction.setAction(1);

        HomeModel.getInstance().postNewsAction(url_action_like, JsonHelper.toJson(newsAction), session, new ResponseHandle<RESP_None>(RESP_None.class) {
            @Override
            public void onSuccess(RESP_None obj) {
                view.onLikeSuccess();
            }

            @Override
            public void onError(Error error) {
                int code = error.getCode();
                if (code == 2) {
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            onLikeAction(id_news);
                        }

                        @Override
                        public void onError(com.xtel.nipservicesdk.model.entity.Error error) {
                            Log.e("err callback", JsonHelper.toJson(error));
                            view.showSortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                        }
                    });
                } else {
                    Log.e(TAG + "err like", JsonHelper.toJson(error));
                    view.showSortToast(JsonParse.getCodeMessage(view.getActivity(), error.getCode(), null));
                }
            }
        });
    }

    public void showQrCode(String url_qr) {
        if (url_qr != null) {
            view.onShowQrCode(url_qr);
        }
    }

    public void showBarCode(String url_qr) {
        if (url_qr != null) {
            view.onShowBarCode(url_qr);
        }
    }
}
