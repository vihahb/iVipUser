package com.xtel.ivipu.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;
import com.xtel.ivipu.R;
import com.xtel.ivipu.model.RESP.RESP_NewEntity;
import com.xtel.ivipu.model.RESP.RESP_NewsObject;
import com.xtel.ivipu.model.entity.NewsObj;
import com.xtel.ivipu.presenter.ActivityInfoPropertiesPresenter;
import com.xtel.ivipu.view.activity.IFragment;
import com.xtel.ivipu.view.activity.ListCommentActivity;
import com.xtel.ivipu.view.activity.inf.IActivityInfo;
import com.xtel.ivipu.view.widget.AppBarStateChangeListener;
import com.xtel.ivipu.view.widget.RoundImage;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtel.sdk.commons.Constants;
import com.xtel.sdk.commons.NetWorkInfo;
import com.xtel.sdk.utils.SharedPreferencesUtils;

/**
 * Created by vihahb on 1/17/2017.
 */

public class FragmentInfoProperties extends IFragment implements View.OnClickListener, IActivityInfo {
    ActivityInfoPropertiesPresenter presenter;
    private RESP_NewEntity newEntity;
    private TextView txt_info_shop_name, txt_info_shop_view, txt_info_shop_like, txt_info_shop_comment, txt_info_shop_rate, tv_info_shop_title;
    private TextView tv_qr_reward;
    private RoundImage img_brand, img_avatar;
    private ImageView img_qr_code, img_bar_code, img_content_banner;
    private ImageView img_like, img_comment, img_share;
    private Button btn_getGiftCode;
    private LinearLayout inc_get_gift_code, inc_gift_code;
    private AppBarLayout appBarLayout;
    private String qr_code, bar_code;
    private NewsObj newsObject;
    private ExpandableTextView tv_description;
    private String avatar_user;
    private int REQUEST_COMMENT = 101;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info_properties, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ActivityInfoPropertiesPresenter(this);
        initView(view);
    }


    private void initView(View view) {
        appBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar);

        txt_info_shop_name = (TextView) view.findViewById(R.id.tv_info_shop_name);
        txt_info_shop_view = (TextView) view.findViewById(R.id.tv_info_shop_view);
        txt_info_shop_like = (TextView) view.findViewById(R.id.tv_info_shop_like);
//        txt_info_shop_comment = (TextView) view.findViewById(R.id.tv_info_shop_comment);
        txt_info_shop_rate = (TextView) view.findViewById(R.id.tv_info_shop_rate);
        tv_qr_reward = (TextView) view.findViewById(R.id.tv_qr_reward);
        tv_description = (ExpandableTextView) view.findViewById(R.id.expand_text_view);
        tv_info_shop_title = (TextView) view.findViewById(R.id.tv_info_shop_title);

        img_content_banner = (ImageView) view.findViewById(R.id.img_info_banner);
        img_brand = (RoundImage) view.findViewById(R.id.store_info);
        img_qr_code = (ImageView) view.findViewById(R.id.img_qr_code);
        img_qr_code.setOnClickListener(this);
        img_bar_code = (ImageView) view.findViewById(R.id.img_bar_code);
        img_bar_code.setOnClickListener(this);
        img_avatar = (RoundImage) view.findViewById(R.id.user_avatar_rate);
        img_like = (ImageView) view.findViewById(R.id.img_action_like);
        img_like.setOnClickListener(this);
        img_comment = (ImageView) view.findViewById(R.id.img_action_comment);
        img_comment.setOnClickListener(this);
        img_share = (ImageView) view.findViewById(R.id.img_action_share);

        inc_get_gift_code = (LinearLayout) view.findViewById(R.id.inc_get_gift_code);
        inc_gift_code = (LinearLayout) view.findViewById(R.id.inc_gift_code);

        btn_getGiftCode = (Button) view.findViewById(R.id.btn_get_gift_code);
        btn_getGiftCode.setOnClickListener(this);


        initUnderLine();
        initAppBar();
        getDataFromFragmentShop();
    }

    private void initAppBar() {
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateEXPANDED() {
                img_brand.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStateIDLE() {
                img_brand.setVisibility(View.GONE);
            }
        });
    }


    private void initUnderLine() {
        String qr_reward = new String(getActivity().getResources().getString(R.string.qr_reward));
        SpannableString content = new SpannableString(qr_reward);
        content.setSpan(new UnderlineSpan(), 0, qr_reward.length(), 0);
        tv_qr_reward.setText(content);
        tv_qr_reward.setOnClickListener(this);
    }

    private void initShowQrCode() {
        presenter.showQrCode(qr_code);
    }

    private void initBarCode() {
        presenter.showBarCode(bar_code);
    }

    private boolean validData() {
        try {
            newEntity = (RESP_NewEntity) getActivity().getIntent().getSerializableExtra(Constants.RECYCLER_MODEL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newEntity != null;
    }

    private void getDataFromFragmentShop() {
        if (validData()) {
            checkNetWork(1);
        }
    }

    private void setupData2View(NewsObj newsObj) {
        String shopName = newsObj.getStore_name();
        String shopView = String.valueOf(newsObj.getView());
        String shopLike = String.valueOf(newsObj.getLike());
        String shopComment = String.valueOf(newsObj.getComment());
        String shopRate = String.valueOf(newsObj.getRate());
        String shopDescription = newsObj.getDescription();
        String shopTitle = newsObj.getTitle();
        int favorite_check = newsObj.getFavorite();

        txt_info_shop_name.setText(shopName);
        txt_info_shop_view.setText(shopView);
        txt_info_shop_like.setText(shopLike);
//        txt_info_shop_comment.setText(shopComment);
        txt_info_shop_rate.setText(shopRate);
        tv_description.setText(shopDescription);
        tv_info_shop_title.setText(shopTitle);
        String banner = newsObj.getBanner();
        String brand = newsObj.getLogo();
        if (favorite_check != 0) {
            img_like.setImageResource(R.mipmap.ic_action_liked);
        } else {
            img_like.setImageResource(R.mipmap.ic_action_not_like);
        }


        setImg_brand(brand, img_brand);
        setImgContent(banner, img_content_banner);
    }

    private void setImgContent(String brand_url, ImageView img_content_banner) {
        Picasso.with(getContext())
                .load(brand_url)
                .placeholder(R.drawable.ic_action_name)
                .error(R.drawable.ic_action_name)
                .fit()
                .centerCrop()
                .into(img_content_banner);
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
        } else if (id == R.id.img_qr_code) {
            checkNetWork(4);
        } else if (id == R.id.img_bar_code) {
            checkNetWork(5);
        } else if (id == R.id.img_action_like) {
            checkNetWork(3);
        } else if (id == R.id.img_action_comment) {
            commentOnclick();
        } else if (id == R.id.img_action_comment) {

        }
    }

    @Override
    public void onShowQrCode(String url) {
        if (NetWorkInfo.isOnline(getContext())) {
            showQrCode(url);
        } else {
            showShortToast(getString(R.string.no_connection));
        }
    }

    @Override
    public void onShowBarCode(String url_bar_code) {
        if (NetWorkInfo.isOnline(getContext())) {
            showQrCode(url_bar_code);
        } else {
            showShortToast("Không có kết nối internet");
        }
    }

    @Override
    public void showSortToast(String mes) {
        super.showShortToast(mes);
    }

    @Override
    public void onGetNewsObjSuccess(RESP_NewsObject resp_newsObject) {
        newsObject = new NewsObj();
        Log.e("news obj", JsonHelper.toJson(newsObject));
        fillData(resp_newsObject);
        setupData2View(newsObject);
        avatar_user = SharedPreferencesUtils.getInstance().getStringValue(Constants.PROFILE_AVATAR);
        setImg_brand(avatar_user, img_avatar);
    }

    @Override
    public void onLikeSuccess() {
        int id = newEntity.getId();
        presenter.getNewsInfomation(id);
    }

    private void fillData(RESP_NewsObject resp_newsObject) {
        newsObject.setId(resp_newsObject.getId());
        newsObject.setBanner(resp_newsObject.getBanner());
        newsObject.setLogo(resp_newsObject.getLogo());
        newsObject.setStore_name(resp_newsObject.getStore_name());
        newsObject.setLike(resp_newsObject.getLike());
        newsObject.setComment(resp_newsObject.getComment());
        newsObject.setRate(resp_newsObject.getRate());
        newsObject.setView(resp_newsObject.getView());
        newsObject.setCreate_time(resp_newsObject.getCreate_time());
        newsObject.setTitle(resp_newsObject.getTitle());
        newsObject.setDescription(resp_newsObject.getDescription());
        newsObject.setSales(resp_newsObject.getSales());
        newsObject.setVoucher(resp_newsObject.getVoucher());
        newsObject.setStore_id(resp_newsObject.getStore_id());
        newsObject.setChain_store_id(resp_newsObject.getChain_store_id());
        newsObject.setFavorite(resp_newsObject.getFavorite());
    }

    private void checkNetWork(int type) {
        final Context context = getContext();
        if (!NetWorkInfo.isOnline(context)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.TimePicker);
            dialog.setTitle("Kết nối không thành công");
            dialog.setMessage("Rất tiếc, không thể kết nối internet. Vui lòng kiểm tra kết nối Internet.");
            dialog.setPositiveButton("Cài đặt", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                }
            });
            dialog.show();
        } else {
            if (type == 1) {
                int id = newEntity.getId();
                presenter.getNewsInfomation(id);
            } else if (type == 2) {

            } else if (type == 3) {
                onActionLike();
            } else if (type == 4) {
                initShowQrCode();
            } else if (type == 5) {
                initBarCode();
            }
        }
    }

    private void onActionLike() {
        int id = newEntity.getId();
        presenter.onLikeAction(id);
    }

    private void commentOnclick() {
        int id = newEntity.getId();
        startActivityForResultValue(ListCommentActivity.class, Constants.NEWS_ID, String.valueOf(id), REQUEST_COMMENT);
    }

}
