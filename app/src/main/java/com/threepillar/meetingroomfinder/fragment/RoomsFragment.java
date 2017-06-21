package com.threepillar.meetingroomfinder.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.threepillar.meetingroomfinder.R;
import com.threepillar.meetingroomfinder.adapter.RoomDataAdapter;
import com.threepillar.meetingroomfinder.base.BaseFragment;
import com.threepillar.meetingroomfinder.common.util.AppConstants;
import com.threepillar.meetingroomfinder.common.util.AppPrefrence;
import com.threepillar.meetingroomfinder.common.util.MakeRequestTask;
import com.threepillar.meetingroomfinder.common.util.Utils;
import com.threepillar.meetingroomfinder.enums.FragmentTag;
import com.threepillar.meetingroomfinder.model.Room;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RoomsFragment extends BaseFragment {

    @BindView(R.id.rooms_rv)
    RecyclerView roomsRv;
    @BindView(R.id.rl_container)
    RelativeLayout rl_container;
    @BindView(R.id.rl_1)
    RelativeLayout rl_named;
    @BindView(R.id.rl_2)
    RelativeLayout rl_unnamed;
    @BindView(R.id.edt_room_name)
    EditText edt_room_name;
    private boolean IS_NAMED_ROOM = false;

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


    private void init() {
        hideLoading();
        String rooType = new AppPrefrence(getActivity()).getRoomType();
        if (Utils.isNotNull(rooType) && rooType.equalsIgnoreCase(AppConstants.NAMED_ROOM)) {
            IS_NAMED_ROOM = true;
            roomsRv.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            roomsRv.setLayoutManager(layoutManager);
            rl_named.setVisibility(View.VISIBLE);
            rl_unnamed.setVisibility(View.GONE);
            new MakeRequestTask(getActivity(), this).execute();
        } else if (Utils.isNotNull(rooType) && rooType.equalsIgnoreCase(AppConstants.UN_NAMED_ROOM)) {
            hideLoading();
            rl_container.setVisibility(View.VISIBLE);
            rl_named.setVisibility(View.GONE);
            rl_unnamed.setVisibility(View.VISIBLE);
            IS_NAMED_ROOM = false;

        } else
            hideLoading();
    }


    private String ROOM_NAME;

    public void setAdapter(ArrayList<Room> roomsList) {
        dataAdapter = new RoomDataAdapter(roomsList);
        dataAdapter.notifyDataSetChanged();
        roomsRv.setAdapter(dataAdapter);
        rl_container.setVisibility(View.VISIBLE);
        dataAdapter.setOnItemClickListener(new RoomDataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String itemName) {
                ROOM_NAME = itemName;
            }
        });


    }


    @OnClick(R.id.btn_ok)
    public void onClickOK() {
        savePref(ROOM_NAME);
    }

    private void savePref(String roomName) {
        if (!IS_NAMED_ROOM) {
            roomName = edt_room_name.getText().toString();
            showFragment(FragmentTag.SingleDayFragment,null,false);
        }
        if (Utils.isNotNull(roomName)) {
            new AppPrefrence(getActivity()).setRoomName(roomName);
            Toast.makeText(getActivity(), roomName + " saved ", Toast.LENGTH_SHORT).show();
            showFragment(FragmentTag.SingleDayFragment,null,false);
        } else
            Toast.makeText(getActivity(), " Enter room name first ?", Toast.LENGTH_SHORT).show();
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
