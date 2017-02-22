package com.xtel.ivipu.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.MyShopCheckin;
import com.xtel.ivipu.model.entity.TestMyShop;
import com.xtel.ivipu.view.activity.inf.IMyShopActivity;
import com.xtel.ivipu.view.widget.RoundImage;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/7/2017.
 */

public class AdapterRecycleMyShop extends RecyclerView.Adapter<AdapterRecycleMyShop.ViewHolder> {

    private Activity activity;
    private Context context;
    private ArrayList<MyShopCheckin> arrayList;
    private IMyShopActivity iMyShopActivity;
    private boolean isLoadMore;
    private int TYPE_VIEW = 1, TYPE_LOAD = 2;

    public AdapterRecycleMyShop(Activity activity, Context context, ArrayList<MyShopCheckin> arrayList, IMyShopActivity iMyShopActivity) {
        this.activity = activity;
        this.context = context;
        this.arrayList = arrayList;
        this.iMyShopActivity = iMyShopActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_shop_item, parent, false));
        } else if (viewType == TYPE_LOAD) {
            return new ViewProgressBar(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MyShopCheckin myShopCheckin = arrayList.get(position);
        Log.e("Arr adapter", arrayList.toString());

        if (holder != null) {

            AdapterRecycleMyShop.ViewHolder viewHolder = holder;
//            viewHolder.tv_score.setText(myShopCheckin.get());
//            viewHolder.tv_rank.setText(testRecycle.getRank());
            viewHolder.tv_content_name.setText(myShopCheckin.getStore_name());
            setImageContentResource(myShopCheckin, viewHolder.img_content_image);
            setImageBrandResource(myShopCheckin, viewHolder.img_content_brand);
//            setImageShopIconResource(testRecycle, viewHolder.img_shop_icon);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iMyShopActivity.onItemClick(position, myShopCheckin, v);
                }
            });
        } else {
            AdapterRecycleMyShop.ViewProgressBar viewProgressBar = (AdapterRecycleMyShop.ViewProgressBar) holder;
            viewProgressBar.progressBar.getIndeterminateDrawable()
                    .setColorFilter(
                            Color.WHITE,
                            android.graphics.PorterDuff.Mode.MULTIPLY
                    );
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 10) {
            return TYPE_LOAD;
        } else {
            return TYPE_VIEW;
        }
    }

    public void onSetLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    private void setImageContentResource(MyShopCheckin shopCheckin, ImageView viewHolder) {
        Picasso.with(context)
                .load(shopCheckin.getBanner())
                .placeholder(R.drawable.ic_action_circle)
                .error(R.drawable.ic_action_circle)
                .into(viewHolder);
    }

    private void setImageBrandResource(MyShopCheckin shopCheckin, ImageView viewHolder) {
        Picasso.with(context)
                .load(shopCheckin.getLogo())
                .placeholder(R.drawable.ic_action_circle)
                .error(R.drawable.ic_action_circle)
                .into(viewHolder);
    }

    private void setImageShopIconResource(TestMyShop testRecycle, ImageView viewHolder) {
        Picasso.with(context)
                .load(testRecycle.getUrl_img_icon())
                .placeholder(R.drawable.ic_action_circle)
                .error(R.drawable.ic_action_circle)
                .into(viewHolder);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_shop_icon, img_content_image, img_content_brand;
        private TextView tv_score, tv_rank, tv_content_name;

        ViewHolder(View itemView) {
            super(itemView);
            img_shop_icon = (ImageView) itemView.findViewById(R.id.icon_shop_img);
            tv_rank = (TextView) itemView.findViewById(R.id.tv_my_rank);
            tv_score = (TextView) itemView.findViewById(R.id.tv_my_score);
            tv_content_name = (TextView) itemView.findViewById(R.id.tv_content_name);
            img_content_image = (ImageView) itemView.findViewById(R.id.img_content_image);
            img_content_brand = (RoundImage) itemView.findViewById(R.id.img_content_brand);
        }

    }

    class ViewProgressBar extends AdapterRecycleMyShop.ViewHolder {
        private ProgressBar progressBar;

        ViewProgressBar(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_progress_bar);
        }
    }
}
