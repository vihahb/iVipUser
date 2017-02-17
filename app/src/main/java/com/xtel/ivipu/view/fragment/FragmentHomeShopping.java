package com.xtel.ivipu.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.RESP_NewEntity;
import com.xtel.ivipu.presenter.FragmentShopPresenter;
import com.xtel.ivipu.view.activity.ActivityInfoContent;
import com.xtel.ivipu.view.activity.inf.IFragmentShopView;
import com.xtel.ivipu.view.adapter.AdapterRecycleShop;
import com.xtel.ivipu.view.widget.ProgressView;
import com.xtel.sdk.commons.Constants;

import java.util.ArrayList;

/**
 * Created by vihahb on 1/13/2017.
 */

public class FragmentHomeShopping extends BasicFragment implements IFragmentShopView {

    FragmentShopPresenter presenter;
    AdapterRecycleShop adapter;
    int type = 2, page = 1, pagesize = 10;
    private RecyclerView rcl_shop;
    private ArrayList<RESP_NewEntity> arrayListNewsShop;
    private int position = -1;
    private int REQUEST_VIEW_SHOP = 99;
    private ProgressView progressView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_shopping, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentShopPresenter(this);
//        initArrayList();
        initRecylerView(view);
        initProgressView(view);
    }

    private void initProgressView(View view) {
        progressView = new ProgressView(null, view);
        progressView.initData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again), getString(R.string.loading_data), Color.parseColor("#05b589"));
        progressView.setUpWithView(rcl_shop);

        progressView.onLayoutClicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        progressView.onRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });

        progressView.onSwipeLayoutPost(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        });
    }

    private void getData() {
        progressView.hideData();
        progressView.setRefreshing(true);
        inirDataNews();
    }

    private void inirDataNews() {
        presenter.getShop(type, page, pagesize);
//        RESP_NewEntity newEntity = new RESP_NewEntity();
//        newEntity.setStore_name("Clear Shop");
//        newEntity.setComment(7);
//        newEntity.setLike(5);
//        newEntity.setView(3);
//        newEntity.setRate(5);
//        newEntity.setSeen(3);
//        newEntity.setCreate_time(1469577600);
//        newEntity.setBanner("http://i.imgur.com/XvN4AIc.jpg");
//        newEntity.setLogo("http://vignette4.wikia.nocookie.net/logopedia/images/5/59/Coca-Cola_logo_2007.jpg/revision/latest?cb=20150801090518");

    }


    private void initRecylerView(View view) {
        rcl_shop = (RecyclerView) view.findViewById(R.id.rcl_shop);
        rcl_shop.setHasFixedSize(true);
        rcl_shop.setLayoutManager(new LinearLayoutManager(getContext()));

        arrayListNewsShop = new ArrayList<>();
        adapter = new AdapterRecycleShop(getContext(), getActivity(), arrayListNewsShop, this);
        rcl_shop.setAdapter(adapter);
    }

    private void setDataRecyclerView(ArrayList<RESP_NewEntity> newEntities) {
        arrayListNewsShop.addAll(newEntities);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onGetShopSuccess(ArrayList<RESP_NewEntity> arrayList) {
        progressView.setRefreshing(false);
        Log.e("arr news entity", arrayList.toString());
        setDataRecyclerView(arrayList);
        if (arrayList.size() < 10) {
            adapter.onSetLoadMore(false);
        }
        checkListData();
    }

    private void checkListData() {
        progressView.disableSwipe();
        progressView.setRefreshing(false);

        if (arrayListNewsShop.size() == 0) {
            progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_news), getString(R.string.try_again));
            progressView.show();
        } else {
            rcl_shop.getAdapter().notifyDataSetChanged();
            progressView.hide();
        }
    }

    @Override
    public void onGetShopError() {

    }

    @Override
    public void onLoadMore() {

    }


    @Override
    public void showShortToast(String message) {
        super.showShortToast(message);
    }

    @Override
    public void showLongToast(String message) {
        super.showLongToast(message);
    }

    @Override
    public void onItemClick(int position, RESP_NewEntity testRecycle, View view) {
        this.position = position;
//        if (Build.VERSION.SDK_INT >= 20) {
//            int id = R.id.card_view_shop;
//            View viewStart = view.findViewById(R.id.card_view_shop);
//            View view_ava = view.findViewById(R.id.img_view);
//            View view_img = view.findViewById(R.id.img_banner_shop);
//            View view_name = view.findViewById(R.id.tv_shop_name);
//            View view_content = view.findViewById(R.id.shop_content);
//
//            String main = getString(R.string.transition_name_shop);
//            String imga = getString(R.string.transition_shop_img);
//            String icon = getString(R.string.transition_shop_ava);
//            String name = getString(R.string.transition_shop_name);
//            String content = getString(R.string.transition_shop_content);
////            startActivityForResultWithTransition(ActivityInfoContent.class,
////                    Constants.RECYCLER_MODEL,
////                    testRecycle,
////                    R.string.transition_name_shop,
////                    view,
////                    id,
////                    REQUEST_VIEW_SHOP
////            );
//            Intent intent = new Intent(getActivity(), ActivityInfoContent.class);
//            intent.putExtra(Constants.RECYCLER_MODEL, testRecycle);
//            Pair<View, String> p1 = Pair.create(viewStart, main);
//            Pair<View, String> p2 = Pair.create(view_ava, icon);
//            Pair<View, String> p3 = Pair.create(view_img, imga);
//            Pair<View, String> p4 = Pair.create(view_name, name);
//            Pair<View, String> p5 = Pair.create(view_content, content);
//            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), p1, p2, p3, p4);
//            ActivityCompat.startActivityForResult(getActivity(), intent, REQUEST_VIEW_SHOP, options.toBundle());
//        } else {
            startActivityForResult(ActivityInfoContent.class, Constants.RECYCLER_MODEL, testRecycle, REQUEST_VIEW_SHOP);
//        }
    }

    @Override
    public void onNetworkDisable() {
        progressView.setRefreshing(false);
        progressView.updateData(R.mipmap.ic_launcher, getString(R.string.no_internet), getString(R.string.try_again));
        progressView.showData();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getShop(type, page, pagesize);
    }
}
