package com.xtel.ivipu.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtel.ivipu.R;
import com.xtel.ivipu.model.entity.MemberObj;
import com.xtel.ivipu.presenter.FragmentNavMemberCardPresenter;
import com.xtel.ivipu.view.fragment.inf.IFragmentMemberCard;

import java.util.ArrayList;

/**
 * Created by vuhavi on 07/03/2017.
 */

public class FragmentMemberCard extends BasicFragment implements IFragmentMemberCard {

    private int page = 1, pagesize = 5;
    private FragmentNavMemberCardPresenter presenter;
    private RecyclerView rcl_member_card;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nav_member_card, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FragmentNavMemberCardPresenter(this);
    }


    @Override
    public void onGetMemberCardSuccess(ArrayList<MemberObj> arrayList) {

    }

    @Override
    public void onGetMemberCardError() {

    }

    @Override
    public void showShortToast(String mes) {
        super.showShortToast(mes);
    }

    @Override
    public void startActivityAndFinish(Class clazz) {
        super.startActivityAndFinish(clazz);
    }

    @Override
    public void onLoadMore() {
        page++;
    }
}
