package com.xtel.ivipu.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.TestRecycle;
import com.xtel.ivipu.view.activity.inf.IFragmentMovieView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by vivhp on 1/23/2017.
 */

public class AdapterRecycleMovie extends RecyclerView.Adapter<AdapterRecycleMovie.ViewHolder> {

    private Activity activity;
    private ArrayList<TestRecycle> arrayList;
    private IFragmentMovieView fragmentMovieView;
    private ArrayList<Integer> background_alpha_item;
    private int background_position = 0;

    public AdapterRecycleMovie(Activity activity, ArrayList<TestRecycle> arrayList, IFragmentMovieView fragmentMovieView) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.fragmentMovieView = fragmentMovieView;
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
        return new AdapterRecycleMovie.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof AdapterRecycleMovie.ViewHolder) {

            if (background_position == 9) {
                background_position = 0;
            }

            Random random = new Random();

            if (arrayList.get(position).getBg_position() == 0) {
                arrayList.get(position).setBg_position(background_alpha_item.get(random.nextInt(background_alpha_item.size())));
            }

            final TestRecycle testRecycle = arrayList.get(position);
            Log.e("Arr adapter", arrayList.toString());

            AdapterRecycleMovie.ViewHolder viewHolder = holder;

            viewHolder.txt_Name.setText(testRecycle.getShopName());
            viewHolder.txt_Member.setText(testRecycle.getShopMenber());
            viewHolder.txt_location.setText(testRecycle.getShopLocation());
            viewHolder.txt_comment.setText(testRecycle.getShopLocation());
            viewHolder.fr_canvas.setBackgroundResource(testRecycle.getBg_position());


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentMovieView.onItemClick(position, testRecycle, v);
                }
            });
            background_position++;
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
            fr_canvas = (FrameLayout) itemView.findViewById(R.id.color_canvas_movie);
        }
    }
}
