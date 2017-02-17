package com.xtel.ivipu.model;

import com.xtel.ivipu.model.entity.RESP_NewsObject;
import com.xtel.nipservicesdk.callback.ResponseHandle;

/**
 * Created by vivhp on 2/14/2017.
 */

public class HomeModel extends Model {

    public static HomeModel instance = new HomeModel();

    public static HomeModel getInstance() {
        return instance;
    }

    public void getShopNews(String url, String session, ResponseHandle responseHandle) {
        requestServer.getApi(url, session, responseHandle);
    }

    public void getNewsInfomation(String url_news_info, String session, ResponseHandle<RESP_NewsObject> responseHandle) {
        requestServer.getApi(url_news_info, session, responseHandle);
    }

    public void postNewsAction(String url_new_action, String action_object, String session, ResponseHandle responseHandle) {
        requestServer.postApi(url_new_action, action_object, session, responseHandle);
    }

    public void getCheckinPresenter(String url_checkin, String session, ResponseHandle responseHandle) {
        requestServer.getApi(url_checkin, session, responseHandle);
    }
}
