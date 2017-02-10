package com.xtel.ivipu.presenter;

import com.xtel.ivipu.view.activity.inf.IActivityInfo;

/**
 * Created by vivhp on 1/24/2017.
 */

public class ActivityInfoPropertiesPresenter {
    private IActivityInfo view;
    private String TAG = "Info presenter";

    public ActivityInfoPropertiesPresenter(IActivityInfo view) {
        this.view = view;
    }

    public void showQrCode() {
        String qr_code = "http://cdnqrcgde.s3-eu-west-1.amazonaws.com/wp-content/uploads/2013/11/jpeg.jpg";
        if (qr_code != null) {
            view.onShowQrCode(qr_code);
        }
    }

    public void showBarCode() {
        String bar_code = "http://www.ean-int.org/sites/default/files/docs/barcodes/EAN-13.png";
        if (bar_code != null) {
            view.onShowBarCode(bar_code);
        }
    }
}
