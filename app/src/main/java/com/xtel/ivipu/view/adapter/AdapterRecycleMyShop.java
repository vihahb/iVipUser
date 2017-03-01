package com.xtel.ivipu.view.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.MyShopCheckin;
import com.xtel.ivipu.view.activity.inf.IMyShopActivity;
import com.xtel.ivipu.view.widget.RoundImage;
import com.xtel.ivipu.view.widget.WidgetHelper;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/7/2017.
 */

public class AdapterRecycleMyShop extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MyShopCheckin> arrayList;
    private IMyShopActivity iMyShopActivity;
    private boolean isLoadMore;
    private int TYPE_VIEW = 1, TYPE_LOAD = 2;

    public AdapterRecycleMyShop(ArrayList<MyShopCheckin> arrayList, IMyShopActivity iMyShopActivity) {
        this.arrayList = arrayList;
        this.iMyShopActivity = iMyShopActivity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_shop_item, parent, false));
        } else if (viewType == TYPE_LOAD) {
            return new ViewProgressBar(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (position == arrayList.size()) {
            iMyShopActivity.onLoadMore();
        }

        if (holder instanceof ViewHolder) {

            ViewHolder viewHolder = (ViewHolder) holder;

            final MyShopCheckin myShopCheckin = arrayList.get(position);
            Log.e("Arr adapter", arrayList.toString());

//            viewHolder.tv_score.setText(myShopCheckin.get());
//            viewHolder.tv_rank.setText(testRecycle.getRank());
            WidgetHelper.getInstance().setTextViewNoResult(viewHolder.tv_content_name, myShopCheckin.getStore_name());
            WidgetHelper.getInstance().setImageURL(viewHolder.shop_brand_img, myShopCheckin.getLogo());
            WidgetHelper.getInstance().setAvatarImageURL(viewHolder.shop_content_img, myShopCheckin.getBanner());
            setTypeIcon(myShopCheckin, viewHolder.icon_type_img);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iMyShopActivity.onItemClick(position, myShopCheckin, v);
                }
            });
        } else {
            ViewProgressBar viewProgressBar = (ViewProgressBar) holder;
            viewProgressBar.progressBar.getIndeterminateDrawable()
                    .setColorFilter(
                            Color.WHITE,
                            android.graphics.PorterDuff.Mode.MULTIPLY
                    );
        }
    }

    @Override
    public int getItemCount() {
        if (isLoadMore && arrayList.size() > 0) {
            return arrayList.size() + 1;
        } else {
            return arrayList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == arrayList.size()) {
            return TYPE_LOAD;
        } else {
            return TYPE_VIEW;
        }
    }

    public void onSetLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    private void setTypeIcon(MyShopCheckin typeIcon, ImageView imageView) {
        int type = typeIcon.getType();
        if (type == 1) {
            WidgetHelper.getInstance().setImageResource(imageView, R.mipmap.ic_news_list);
        } else if (type == 2) {
            WidgetHelper.getInstance().setImageResource(imageView, R.mipmap.ic_fashion);
        } else if (type == 3) {
            WidgetHelper.getInstance().setImageResource(imageView, R.mipmap.ic_food);
        } else if (type == 4) {
            WidgetHelper.getInstance().setImageResource(imageView, R.mipmap.ic_technology);
        } else if (type == 5) {
            WidgetHelper.getInstance().setImageResource(imageView, R.mipmap.ic_health);
        } else if (type == 6) {
            WidgetHelper.getInstance().setImageResource(imageView, R.mipmap.ic_another_service);
        } else if (type == 7) {
            WidgetHelper.getInstance().setImageResource(imageView, R.mipmap.ic_news_for_me);
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView icon_type_img, shop_content_img;
        private RoundImage shop_brand_img;
        private TextView tv_score, tv_rank, tv_content_name;

        ViewHolder(View itemView) {
            super(itemView);
            icon_type_img = (ImageView) itemView.findViewById(R.id.icon_type_img);
            tv_rank = (TextView) itemView.findViewById(R.id.tv_my_rank);
            tv_score = (TextView) itemView.findViewById(R.id.tv_my_score);
            tv_content_name = (TextView) itemView.findViewById(R.id.tv_content_name);
            shop_content_img = (ImageView) itemView.findViewById(R.id.shop_content_img);
            shop_brand_img = (RoundImage) itemView.findViewById(R.id.shop_brand_img);
        }

    }

    private class ViewProgressBar extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        ViewProgressBar(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_progress_bar);
        }
    }
}
