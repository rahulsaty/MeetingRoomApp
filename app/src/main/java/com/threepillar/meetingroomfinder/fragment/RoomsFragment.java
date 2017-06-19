package com.threepillar.meetingroomfinder.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.threepillar.meetingroomfinder.R;
import com.threepillar.meetingroomfinder.adapter.RoomDataAdapter;
import com.threepillar.meetingroomfinder.base.BaseFragment;
import com.threepillar.meetingroomfinder.common.util.AppConstants;
import com.threepillar.meetingroomfinder.common.util.AppPrefrence;
import com.threepillar.meetingroomfinder.common.util.MakeRequestTask;
import com.threepillar.meetingroomfinder.common.util.Utils;
import com.threepillar.meetingroomfinder.model.Room;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoomsFragment extends BaseFragment {

    @BindView(R.id.rooms_rv)
    RecyclerView roomsRv;
    private RoomDataAdapter dataAdapter;

    public static RoomsFragment newInstance(Parcelable fragArgs) {
        RoomsFragment roomsFragment = new RoomsFragment();
        return roomsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }


//    ArrayList<Room> roomsList;

    private void init() {
        hideLoading();
        String rooType = new AppPrefrence(getActivity()).getRoomType();
        if (Utils.isNotNull(rooType) && rooType.equalsIgnoreCase(AppConstants.NAMED_ROOM)) {
            roomsRv.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            roomsRv.setLayoutManager(layoutManager);
            new MakeRequestTask(getActivity(), this).execute();
        } else if (Utils.isNotNull(rooType) && rooType.equalsIgnoreCase(AppConstants.UN_NAMED_ROOM)) {
            hideLoading();
        } else
            hideLoading();
    }


    public void setAdapter(ArrayList<Room> roomsList) {
        dataAdapter = new RoomDataAdapter(roomsList, getActivity());
        dataAdapter.notifyDataSetChanged();
        roomsRv.setAdapter(dataAdapter);


    }


    @Override
    public void cancelRequest() {

    }

    @Override
    public void onViewClicked(View view) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getResourceID() {
        return R.layout.fragment_rooms;
    }

    @Override
    protected String getFragmentTag() {
        return null;
    }
}
