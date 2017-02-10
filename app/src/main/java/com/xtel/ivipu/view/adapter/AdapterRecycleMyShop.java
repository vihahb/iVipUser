package com.xtel.ivipu.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xtel.ivipu.R;
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
    private ArrayList<TestMyShop> arrayList;
    private IMyShopActivity iMyShopActivity;

    public AdapterRecycleMyShop(Activity activity, Context context, ArrayList<TestMyShop> arrayList, IMyShopActivity iMyShopActivity) {
        this.activity = activity;
        this.context = context;
        this.arrayList = arrayList;
        this.iMyShopActivity = iMyShopActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterRecycleMyShop.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_shop_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final TestMyShop testRecycle = arrayList.get(position);
        Log.e("Arr adapter", arrayList.toString());

        if (holder instanceof ViewHolder) {

            AdapterRecycleMyShop.ViewHolder viewHolder = holder;
            viewHolder.tv_score.setText(testRecycle.getScore());
            viewHolder.tv_rank.setText(testRecycle.getRank());
            viewHolder.tv_content_name.setText(testRecycle.getContent_name());
            setImageContentResource(testRecycle, viewHolder.img_content_image);
            setImageBrandResource(testRecycle, viewHolder.img_content_brand);
            setImageShopIconResource(testRecycle, viewHolder.img_shop_icon);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iMyShopActivity.onItemClick(position, testRecycle, v);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    private void setImageContentResource(TestMyShop testRecycle, ImageView viewHolder) {
        Picasso.with(context)
                .load(testRecycle.getUrl_img_content())
                .placeholder(R.drawable.ic_action_circle)
                .error(R.drawable.ic_action_circle)
                .into(viewHolder);
    }

    private void setImageBrandResource(TestMyShop testRecycle, ImageView viewHolder) {
        Picasso.with(context)
                .load(testRecycle.getUrl_img_brand())
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
}
