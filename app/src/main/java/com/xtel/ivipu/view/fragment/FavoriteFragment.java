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
import com.xtel.ivipu.model.entity.FavoriteEntity;
import com.xtel.ivipu.model.entity.HistoryEntity;
import com.xtel.ivipu.view.activity.inf.IFragmentFavoriteView;
import com.xtel.ivipu.view.adapter.AdapterRecycleFavorite;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/13/2017.
 */

public class FavoriteFragment extends Fragment implements IFragmentFavoriteView {

    RecyclerView rcl_favorite;
    ArrayList<FavoriteEntity> arrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycle(view);
    }

    private void initRecycle(View view) {
        arrayList = new ArrayList<>();
        rcl_favorite = (RecyclerView) view.findViewById(R.id.rcl_favorite);
        for (int i = 0; i < 100; i++) {
            FavoriteEntity favoriteEntity = new FavoriteEntity();
            favoriteEntity.setFavorite_name("Favorite Name " + i);
            favoriteEntity.setFavorite_address("Address " + i);
            favoriteEntity.setFavorite_time("Time " + i);
            favoriteEntity.setFavorite_date("Date " + i);
            favoriteEntity.setFavorite_view(String.valueOf(i));
            favoriteEntity.setFavorite_like(String.valueOf(i));
            favoriteEntity.setFavorite_comment(String.valueOf(i));
            arrayList.add(i, favoriteEntity);
            Log.e("food object " + i, favoriteEntity.toString());
        }
        Log.e("arr food object ", arrayList.toString());

        rcl_favorite.setHasFixedSize(true);

        rcl_favorite.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.Adapter adapter = new AdapterRecycleFavorite(getActivity(), arrayList, this);

        rcl_favorite.setAdapter(adapter);
    }

    @Override
    public void onGetFavoriteSuccess(ArrayList arrayList) {

    }

    @Override
    public void onGetFavoriteError() {

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
