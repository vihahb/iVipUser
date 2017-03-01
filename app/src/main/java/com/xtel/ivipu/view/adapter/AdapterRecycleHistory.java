package com.xtel.ivipu.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.HistoryEntity;
import com.xtel.ivipu.view.fragment.inf.IFragmentHistoryView;

import java.util.ArrayList;

/**
 * Created by vivhp on 2/10/2017.
 */

public class AdapterRecycleHistory extends RecyclerView.Adapter<AdapterRecycleHistory.ViewHolder> {

    private Activity activity;
    private ArrayList<HistoryEntity> arrayList;
    private IFragmentHistoryView fragmentHistoryView;

    public AdapterRecycleHistory(Activity activity, ArrayList<HistoryEntity> arrayList, IFragmentHistoryView fragmentHistoryView) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.fragmentHistoryView = fragmentHistoryView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterRecycleHistory.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final HistoryEntity historyRecycle = arrayList.get(position);
        Log.e("Arr adapter", arrayList.toString());

        AdapterRecycleHistory.ViewHolder viewHolder = holder;

        viewHolder.txt_Name.setText(historyRecycle.getHistory_name());
        viewHolder.txt_address.setText(historyRecycle.getHistory_address());
        viewHolder.txt_time.setText(historyRecycle.getHistory_time());
        viewHolder.txt_date.setText(historyRecycle.getHistory_date());
        viewHolder.txt_view.setText(historyRecycle.getHistory_view());
        viewHolder.txt_like.setText(historyRecycle.getHistory_like());
        viewHolder.txt_comment.setText(historyRecycle.getHistory_comment());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_his_brand, img_his_banner;
        private TextView txt_Name, txt_address, txt_time, txt_date, txt_view, txt_like, txt_comment;

        ViewHolder(View itemView) {
            super(itemView);
            img_his_brand = (ImageView) itemView.findViewById(R.id.img_view);
            img_his_banner = (ImageView) itemView.findViewById(R.id.img_his_banner);
            txt_Name = (TextView) itemView.findViewById(R.id.tv_history_name);
            txt_address = (TextView) itemView.findViewById(R.id.tv_history_address);
            txt_time = (TextView) itemView.findViewById(R.id.tv_history_time);
            txt_date = (TextView) itemView.findViewById(R.id.tv_history_date);
            txt_view = (TextView) itemView.findViewById(R.id.tv_history_view);
            txt_like = (TextView) itemView.findViewById(R.id.tv_history_like);
            txt_comment = (TextView) itemView.findViewById(R.id.tv_history_comment);
        }
    }
}
