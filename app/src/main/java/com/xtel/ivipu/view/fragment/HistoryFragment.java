package com.xtel.ivipu.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.HistoryEntity;
import com.xtel.ivipu.view.activity.inf.IFragmentHistoryView;
import com.xtel.ivipu.view.adapter.AdapterRecycleHistory;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/13/2017.
 */

public class HistoryFragment extends Fragment implements IFragmentHistoryView {

    RecyclerView rcl_history;
    ArrayList<HistoryEntity> arrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycleView(view);
    }

    private void initRecycleView(View view) {
        arrayList = new ArrayList<>();
        rcl_history = (RecyclerView) view.findViewById(R.id.rcl_history);

        for (int i = 0; i < 100; i++) {
            HistoryEntity his = new HistoryEntity();
            his.setHistory_name("History Name " + i);
            his.setHistory_address("Address " + i);
            his.setHistory_time("Time " + i);
            his.setHistory_date("Date " + i);
            his.setHistory_view(String.valueOf(i));
            his.setHistory_like(String.valueOf(i));
            his.setHistory_comment(String.valueOf(i));
            arrayList.add(i, his);
            Log.e("food object " + i, his.toString());
        }
        Log.e("arr food object ", arrayList.toString());

        rcl_history.setHasFixedSize(true);

        rcl_history.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapter = new AdapterRecycleHistory(getActivity(), arrayList, this);

        rcl_history.setAdapter(adapter);
    }


    @Override
    public void onGetHistorySuccess(ArrayList arrayList) {

    }

    @Override
    public void onGetHistoryError() {

    }

    @Override
    public void showShortToast(String mes) {

    }

    @Override
    public void showLongToast(String mes) {

    }

    @Override
    public void onItemClick(int position, HistoryEntity testRecycle, View view) {

    }
}
