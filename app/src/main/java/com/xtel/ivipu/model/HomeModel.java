package com.xtel.ivipu.model;

import com.xtel.ivipu.model.RESP.RESP_NewsObject;
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

    public void postChekinAction(String url_checkin, String checkInObj, String session, ResponseHandle responseHandle) {
        requestServer.postApi(url_checkin, checkInObj, session, responseHandle);
    }

    public void getMyShopCheckin(String url, String session, ResponseHandle responseHandle) {
        requestServer.getApi(url, session, responseHandle);
    }

    public void getNewsComment(String url, String session, ResponseHandle responseHandle) {
        requestServer.getApi(url, session, responseHandle);
    }

    public void postNewsComment(String url, String jsonObject, String session, ResponseHandle responseHandle) {
        requestServer.postApi(url, jsonObject, session, responseHandle);
    }

    public void postNewsRates(String url, String jsonObject, String session, ResponseHandle responseHandle) {
        requestServer.postApi(url, jsonObject, session, responseHandle);
    }

    public void getNewsVoucher(String url, String session, ResponseHandle responseHandle) {
        requestServer.getApi(url, session, responseHandle);
    }

    public void getNewsSuggestion(String url, String session, ResponseHandle responseHandle) {
        requestServer.getApi(url, session, responseHandle);
    }

    public void getGalleryArray(String url, String session, ResponseHandle responseHandle) {
        requestServer.getApi(url, session, responseHandle);
    }

    public void getAddress(String url, String session, ResponseHandle responseHandle) {
        requestServer.getApi(url, session, responseHandle);
    }

    public void getHistory(String url, String session, ResponseHandle responseHandle){
        requestServer.getApi(url, session, responseHandle);
    }

    public void getFavorite(String url, String session, ResponseHandle responseHandle){
        requestServer.getApi(url, session, responseHandle);
    }

    public void getMemberCard(String url, String session, ResponseHandle responseHandle){
        requestServer.getApi(url, session, responseHandle);
    }
}
