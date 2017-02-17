package com.xtel.ivipu.presenter;

import com.xtel.ivipu.view.activity.inf.IScannerView;
import com.xtel.nipservicesdk.LoginManager;

/**
 * Created by vivhp on 2/15/2017.
 */

public class ScannerQrPresenter {

    private IScannerView view;

    public ScannerQrPresenter(IScannerView view) {
        this.view = view;
    }

    public void checkInStore() {
        String session = LoginManager.getCurrentSession();

    }
}
