package com.xtel.ivipuser.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xtel.ivipuser.R;
import com.xtel.ivipuser.commons.Constants;
import com.xtel.ivipuser.model.entity.Error;
import com.xtel.ivipuser.view.MyApplication;

import org.json.JSONObject;

/**
 * Created by Vũ Hà Vi on 11/9/2016.
 */

public class JsonParse {

    public static Error checkError(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONObject error = jsonObject.getJSONObject(Constants.JSON_ERROR);

            Error errorModel = new Error();
            errorModel.setCode(error.getInt(Constants.JSON_CODE));
            errorModel.setType(error.getString(Constants.JSON_TYPE));
            errorModel.setMessage(error.getString(Constants.JSON_MESSAGE));

            return errorModel;
        } catch (Exception e) {
            Log.e("parse_error", e.toString());
            e.printStackTrace();
        }

        return null;
    }

    public static boolean checkJsonObject(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            return true;
        } catch (Exception e) {
            Log.e("Loi_check_json", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void getCodeError(Context activity, View view, int code, String content) {
        if (code == 3) {
            if (view != null)
                Snackbar.make(view, activity.getString(R.string.error_code_3), Snackbar.LENGTH_SHORT).show();
            else
                Toast.makeText(activity, activity.getString(R.string.error_code_3), Toast.LENGTH_SHORT).show();
        } else if (code == 4) {
            if (view != null)
                Snackbar.make(view, activity.getString(R.string.error_code_4), Snackbar.LENGTH_SHORT).show();
            else
                Toast.makeText(activity, activity.getString(R.string.error_code_4), Toast.LENGTH_SHORT).show();
        }
    }

    public static String getCodeMessage(int code, String content) {
        if (code == 2) {
            return MyApplication.context.getString(R.string.error_code_2);
        } else if (code == 3) {
            return MyApplication.context.getString(R.string.error_code_3);
        } else if (code == 4) {
            return MyApplication.context.getString(R.string.error_code_4);
        } else {
            return content;
        }
    }
}