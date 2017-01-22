package com.xtel.ivipuser.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtel.ivipuser.R;
import com.xtel.ivipuser.model.entity.TestRecycle;
import com.xtel.ivipuser.view.activity.inf.IFragmentShopView;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/16/2017.
 */

public class AdapterRecycleShop extends RecyclerView.Adapter<AdapterRecycleShop.ViewHolder> {

    private Activity activity;
    private ArrayList<TestRecycle> arrayList;
    private IFragmentShopView fragmentShopView;

    public AdapterRecycleShop(Activity activity, ArrayList<TestRecycle> arrayList, IFragmentShopView fragmentShopView) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.fragmentShopView = fragmentShopView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_layout_shop, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final TestRecycle testRecycle = arrayList.get(position);
        Log.e("Arr adapter", arrayList.toString());

        ViewHolder viewHolder = holder;

        viewHolder.txt_Name.setText(testRecycle.getShopName());
        viewHolder.txt_Member.setText(testRecycle.getShopMenber());
        viewHolder.txt_location.setText(testRecycle.getShopLocation());
        viewHolder.txt_comment.setText(testRecycle.getShopLocation());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentShopView.onItemClick(position, testRecycle, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_view, img_banner_shop;
        private TextView txt_Name, txt_Member, txt_location, txt_comment;

        ViewHolder(View itemView) {
            super(itemView);
            img_view = (ImageView) itemView.findViewById(R.id.img_view);
            img_banner_shop = (ImageView) itemView.findViewById(R.id.img_banner_shop);
            txt_Name = (TextView) itemView.findViewById(R.id.tv_shop_name);
            txt_Member = (TextView) itemView.findViewById(R.id.tv_shop_member);
            txt_location = (TextView) itemView.findViewById(R.id.tv_shop_location);
            txt_comment = (TextView) itemView.findViewById(R.id.tv_shop_comment);
        }
    }

}
