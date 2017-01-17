package com.xtel.ivipuser.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xtel.ivipuser.R;
import com.xtel.ivipuser.model.entity.TestRecycle;
import com.xtel.sdk.commons.Constants;

/**
 * Created by vihahb on 1/17/2017.
 */

public class FragmentInfoProperties extends BasicFragment {

    private TestRecycle testRecycle;
    private TextView txt_info_shop_name, txt_info_shop_member, txt_info_shop_location, txt_info_shop_comment;
    private Button btn_getGiftCode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_properties, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }


    private void initView(View view) {
        txt_info_shop_name = (TextView) view.findViewById(R.id.tv_info_shop_name);
        txt_info_shop_member = (TextView) view.findViewById(R.id.tv_info_shop_member);
        txt_info_shop_location = (TextView) view.findViewById(R.id.tv_info_shop_location);
        txt_info_shop_comment = (TextView) view.findViewById(R.id.tv_info_shop_comment);

        btn_getGiftCode = (Button) view.findViewById(R.id.btnGetGiftCode);

        getDataFromFragmentShop();
    }

    private boolean validData() {
        try {
            testRecycle = (TestRecycle) getActivity().getIntent().getSerializableExtra(Constants.RECYCLER_MODEL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testRecycle != null;
    }

    private void getDataFromFragmentShop() {
        if (validData()) {
            String object = testRecycle.toString();
            Log.d("Object Info", object);
            String shopName = testRecycle.getShopName();
            String shopMember = testRecycle.getShopMenber();
            String shopLocation = testRecycle.getShopLocation();
            String shopComment = testRecycle.getShopComment();

            txt_info_shop_name.setText(shopName);
            txt_info_shop_member.setText(shopMember);
            txt_info_shop_location.setText(shopLocation);
            txt_info_shop_comment.setText(shopComment);
        }
    }
}
