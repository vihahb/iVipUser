package com.xtel.ivipuser.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xtel.ivipuser.R;
import com.xtel.ivipuser.model.entity.TestRecycle;
import com.xtel.ivipuser.view.widget.RoundImage;
import com.xtel.sdk.commons.Constants;

/**
 * Created by vihahb on 1/17/2017.
 */

public class FragmentInfoProperties extends BasicFragment implements View.OnClickListener {

    private TestRecycle testRecycle;
    private TextView txt_info_shop_name, txt_info_shop_member, txt_info_shop_location, txt_info_shop_comment;
    private TextView tv_qr_reward;
    private RoundImage img_brand;
    private Button btn_getGiftCode;
    private LinearLayout inc_get_gift_code, inc_gift_code;

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

        tv_qr_reward = (TextView) view.findViewById(R.id.tv_qr_reward);

        img_brand = (RoundImage) view.findViewById(R.id.store_info);

        inc_get_gift_code = (LinearLayout) view.findViewById(R.id.inc_get_gift_code);
        inc_gift_code = (LinearLayout) view.findViewById(R.id.inc_gift_code);


        btn_getGiftCode = (Button) view.findViewById(R.id.btn_get_gift_code);
        btn_getGiftCode.setOnClickListener(this);


        initUnderLine();
        getDataFromFragmentShop();
    }

    private void initUnderLine() {
        String qr_reward = new String(getActivity().getResources().getString(R.string.qr_reward));
        SpannableString content = new SpannableString(qr_reward);
        content.setSpan(new UnderlineSpan(), 0, qr_reward.length(), 0);
        tv_qr_reward.setText(content);
        tv_qr_reward.setOnClickListener(this);
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

            String url = "https://unige.ch/mcr/application/files/5614/7220/3533/avatar_square_512.png";
//            setImg_brand(url, img_brand);
        }
    }

    private void setImg_brand(String url, RoundImage img_avatar) {
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.drawable.ic_action_name)
                .error(R.drawable.ic_action_name)
                .fit()
                .centerCrop()
                .into(img_avatar);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_get_gift_code) {
            inc_get_gift_code.setVisibility(View.GONE);
            inc_gift_code.setVisibility(View.VISIBLE);
        } else if (id == R.id.tv_qr_reward) {
            inc_get_gift_code.setVisibility(View.VISIBLE);
            inc_gift_code.setVisibility(View.GONE);
        }
    }
}
