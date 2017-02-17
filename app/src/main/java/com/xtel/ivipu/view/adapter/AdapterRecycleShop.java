package com.xtel.ivipu.view.adapter;

import android.app.Activity;
import android.content.Context;
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

import com.squareup.picasso.Picasso;
import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.RESP_NewEntity;
import com.xtel.ivipu.view.activity.inf.IFragmentShopView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by vihahb on 1/16/2017.
 */

public class AdapterRecycleShop extends RecyclerView.Adapter<AdapterRecycleShop.ViewHolder> {

    Context context;
    private Activity activity;
    private ArrayList<RESP_NewEntity> arrayList;
    private IFragmentShopView fragmentShopView;
    private int[] background_item;
    private ArrayList<Integer> background_alpha_item;
    private int background_position = 0;
    private boolean isLoadMore = true;
    private int TYPE_VIEW = 1, TYPE_LOAD = 2;
//    background_item = new int[]{
//        R.drawable.item_background_1,
//            R.drawable.item_background_1,
//            R.drawable.item_background_2,
//            R.drawable.item_background_3,
//            R.drawable.item_background_4,
//            R.drawable.item_background_5,
//            R.drawable.item_background_6,
//            R.drawable.item_background_7,
//            R.drawable.item_background_8};

    public AdapterRecycleShop(Context context, Activity activity, ArrayList<RESP_NewEntity> arrayList, IFragmentShopView fragmentShopView) {
        this.context = context;
        this.activity = activity;
        this.arrayList = arrayList;
        this.fragmentShopView = fragmentShopView;
        background_alpha_item = new ArrayList<>();
        background_alpha_item.add(R.drawable.item_background_1);
        background_alpha_item.add(R.drawable.item_background_2);
        background_alpha_item.add(R.drawable.item_background_3);
        background_alpha_item.add(R.drawable.item_background_4);
        background_alpha_item.add(R.drawable.item_background_5);
        background_alpha_item.add(R.drawable.item_background_6);
        background_alpha_item.add(R.drawable.item_background_7);
        background_alpha_item.add(R.drawable.item_background_8);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEW) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_shop, parent, false));
        } else if (viewType == TYPE_LOAD) {
            return new ViewProgressBar(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_progress_bar, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (position == arrayList.size()) {
            fragmentShopView.onLoadMore();
        }

        Random random = new Random();

        if (holder instanceof AdapterRecycleShop.ViewHolder) {


            if (arrayList.get(position).getBg_position() == 0) {
                arrayList.get(position).setBg_position(background_alpha_item.get(random.nextInt(background_alpha_item.size())));
            }

            final RESP_NewEntity newsEntity = arrayList.get(position);
            Log.e("Arr adapter", arrayList.toString());

            ViewHolder viewHolder = holder;

            viewHolder.txt_Name.setText(newsEntity.getTitle());
            viewHolder.txt_View.setText(String.valueOf(newsEntity.getView()));
            viewHolder.txt_Like.setText(String.valueOf(newsEntity.getLike()));
            viewHolder.txt_comment.setText(String.valueOf(newsEntity.getComment()));
            setImageContentResource(newsEntity, viewHolder.img_banner_shop);
            setImageContentResource(newsEntity, viewHolder.img_view);
            viewHolder.fr_canvas.setBackgroundResource(newsEntity.getBg_position());


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentShopView.onItemClick(position, newsEntity, v);
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

    private void setImageContentResource(RESP_NewEntity respNewEntity, ImageView viewHolder) {
        Picasso.with(context)
                .load(respNewEntity.getBanner())
                .placeholder(R.drawable.ic_action_circle)
                .error(R.drawable.ic_action_circle)
                .into(viewHolder);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_view, img_banner_shop;
        private TextView txt_Name, txt_View, txt_Like, txt_comment;
        private FrameLayout fr_canvas;

        ViewHolder(View itemView) {
            super(itemView);
            img_view = (ImageView) itemView.findViewById(R.id.img_view);
            img_banner_shop = (ImageView) itemView.findViewById(R.id.img_banner_shop);
            txt_Name = (TextView) itemView.findViewById(R.id.tv_shop_name);
            txt_View = (TextView) itemView.findViewById(R.id.tv_shop_view);
            txt_Like = (TextView) itemView.findViewById(R.id.tv_shop_like);
            txt_comment = (TextView) itemView.findViewById(R.id.tv_shop_comment);
            fr_canvas = (FrameLayout) itemView.findViewById(R.id.color_canvas);
        }
    }

    class ViewProgressBar extends AdapterRecycleShop.ViewHolder {
        private ProgressBar progressBar;

        ViewProgressBar(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.item_progress_bar);
        }
    }

}
