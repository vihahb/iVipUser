package com.xtel.ivipu.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.FavoriteEntity;
import com.xtel.ivipu.view.fragment.inf.IFragmentFavoriteView;
import com.xtel.ivipu.view.widget.WidgetHelper;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/10/2017.
 */

public class AdapterRecycleFavorite extends RecyclerView.Adapter<AdapterRecycleFavorite.ViewHolder> {

    private ArrayList<FavoriteEntity> arrayList;
    private IFragmentFavoriteView fragmentFavoriteView;

    public AdapterRecycleFavorite(ArrayList<FavoriteEntity> arrayList, IFragmentFavoriteView fragmentFavoriteView) {
        this.arrayList = arrayList;
        this.fragmentFavoriteView = fragmentFavoriteView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterRecycleFavorite.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FavoriteEntity favoriteEntity = arrayList.get(position);
        Log.e("Arr adapter", arrayList.toString());

        AdapterRecycleFavorite.ViewHolder viewHolder = holder;

        WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_Name, favoriteEntity.getFavorite_name());
        WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_address, favoriteEntity.getFavorite_address());
        WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_time, favoriteEntity.getFavorite_time());
        WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_date, favoriteEntity.getFavorite_date());
        WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_view, favoriteEntity.getFavorite_view());
        WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_like, favoriteEntity.getFavorite_like());
        WidgetHelper.getInstance().setTextViewNoResult(viewHolder.txt_comment, favoriteEntity.getFavorite_comment());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_favorite_brand, img_favorite_banner;
        private TextView txt_Name, txt_address, txt_time, txt_date, txt_view, txt_like, txt_comment;

        ViewHolder(View itemView) {
            super(itemView);
            img_favorite_brand = (ImageView) itemView.findViewById(R.id.img_brand_favorite);
            img_favorite_banner = (ImageView) itemView.findViewById(R.id.img_favorite_banner);
            txt_Name = (TextView) itemView.findViewById(R.id.tv_favorite_name);
            txt_address = (TextView) itemView.findViewById(R.id.tv_favorite_address);
            txt_time = (TextView) itemView.findViewById(R.id.tv_favorite_time);
            txt_date = (TextView) itemView.findViewById(R.id.tv_favorite_date);
            txt_view = (TextView) itemView.findViewById(R.id.tv_favorite_view);
            txt_like = (TextView) itemView.findViewById(R.id.tv_favorite_like);
            txt_comment = (TextView) itemView.findViewById(R.id.tv_favorite_comment);
        }
    }
}
