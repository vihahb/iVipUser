package com.xtel.ivipu.view.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.TestRecycle;
import com.xtel.ivipu.view.activity.inf.IFragmentShopView;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/16/2017.
 */

public class AdapterRecycleShop extends RecyclerView.Adapter<AdapterRecycleShop.ViewHolder> {

    private Activity activity;
    private ArrayList<TestRecycle> arrayList;
    private IFragmentShopView fragmentShopView;
    private int[] background_item;
    private int background_position = 0;
    private boolean isLoadMore = true;
    private int TYPE_VIEW = 1, TYPE_LOAD = 2;

    public AdapterRecycleShop(Activity activity, ArrayList<TestRecycle> arrayList, IFragmentShopView fragmentShopView) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.fragmentShopView = fragmentShopView;
        background_item = new int[]{R.drawable.item_background_1,
                R.drawable.item_background_1,
                R.drawable.item_background_2,
                R.drawable.item_background_3,
                R.drawable.item_background_4,
                R.drawable.item_background_5,
                R.drawable.item_background_6,
                R.drawable.item_background_7,
                R.drawable.item_background_8};
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_shop, parent, false));
        } else if (viewType == 2) {
            return new ViewProgressBar(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (position == arrayList.size()) {
            fragmentShopView.onLoadMore();
        }

        if (holder instanceof AdapterRecycleShop.ViewHolder) {

            if (background_position == 8) {
                background_position = 0;
            }

            if (arrayList.get(position).getBg_position() == 0) {
                arrayList.get(position).setBg_position(background_item[background_position]);
            }

            final TestRecycle testRecycle = arrayList.get(position);
            Log.e("Arr adapter", arrayList.toString());

            ViewHolder viewHolder = holder;

            viewHolder.txt_Name.setText(testRecycle.getShopName());
            viewHolder.txt_Member.setText(testRecycle.getShopMenber());
            viewHolder.txt_location.setText(testRecycle.getShopLocation());
            viewHolder.txt_comment.setText(testRecycle.getShopLocation());
            viewHolder.fr_canvas.setBackgroundResource(testRecycle.getBg_position());


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentShopView.onItemClick(position, testRecycle, v);
                }
            });
            background_position++;
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
    public int getItemViewType(int position) {
        if (position == 10) {
            return TYPE_LOAD;
        } else {
            return TYPE_VIEW;
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

    public void onSetLoadMore(boolean isLoadMore) {
        background_position = 0;
        this.isLoadMore = isLoadMore;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_view, img_banner_shop;
        private TextView txt_Name, txt_Member, txt_location, txt_comment;
        private FrameLayout fr_canvas;

        ViewHolder(View itemView) {
            super(itemView);
            img_view = (ImageView) itemView.findViewById(R.id.img_view);
            img_banner_shop = (ImageView) itemView.findViewById(R.id.img_banner_shop);
            txt_Name = (TextView) itemView.findViewById(R.id.tv_shop_name);
            txt_Member = (TextView) itemView.findViewById(R.id.tv_shop_member);
            txt_location = (TextView) itemView.findViewById(R.id.tv_shop_location);
            txt_comment = (TextView) itemView.findViewById(R.id.tv_shop_comment);
            fr_canvas = (FrameLayout) itemView.findViewById(R.id.color_canvas);
        }
    }

    private class ViewProgressBar extends AdapterRecycleShop.ViewHolder {
        private ProgressBar progressBar;

        ViewProgressBar(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_progress_bar);
        }
    }

}
