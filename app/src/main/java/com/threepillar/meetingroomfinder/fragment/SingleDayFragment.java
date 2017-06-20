package com.threepillar.meetingroomfinder.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.threepillar.meetingroomfinder.R;
import com.threepillar.meetingroomfinder.base.BaseFragment;
import com.threepillar.meetingroomfinder.common.util.AppConstants;
import com.threepillar.meetingroomfinder.model.EventInfo;
import com.threepillar.meetingroomfinder.network.SingleDayAdapter;
import com.threepillar.meetingroomfinder.widget.CustomDialogClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleDayFragment extends BaseFragment {
    private SingleDayAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Calendar calendar;
    private int minutes, hour, newRvPosition;
    private static final int SECONDS_IN_A_DAY = 24 * 60 * 60;
    private static final int THIRTY_MINUTES = 30 * 60;
    //    List<Date> dates = null;
    ArrayList<EventInfo> eventList;
    private String todayDateStr;
    private int rvPosition = 0;

    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm a");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    //    @BindView(R.id.dateTv)
//    TextView dateTv;
    @BindView(R.id.unmarked_rooms_rv)
    RecyclerView unmarkedRoomsRv;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private CustomDialogClass customDialog;

    public static SingleDayFragment newInstance(Parcelable fragArgs) {
        SingleDayFragment singleDayFragment = new SingleDayFragment();
        return singleDayFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        Log.d(AppConstants.APP_NAME, "onCreateView() called");
        init();
        customDialog.setOnAddBtnClickListener(new CustomDialogClass.OnAddBtnClickListener() {
            @Override
            public void onAddBtnClick(String title, String email) {
                Toast.makeText(getActivity(), "title=" + title + " ,email= " + email, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void init() {
//        dates = new ArrayList<>();
        eventList = new ArrayList<>();
        customDialog = new CustomDialogClass(getActivity());
        unmarkedRoomsRv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        unmarkedRoomsRv.setLayoutManager(layoutManager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(AppConstants.APP_NAME, "onActivityCreated() called");
        calculateTime();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(todayDateStr);
//        adapter = new SingleDayAdapter(dates, getActivity());
        adapter = new SingleDayAdapter(eventList, getActivity());
        adapter.setOnSingleItemClickListener(new SingleDayAdapter.OnSingleItemClickListener() {
            @Override
            public void onSingleItemClick(int position, Set<Integer> selectedPositions) {
                if (!selectedPositions.isEmpty()) {
                    fab.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.GONE);
                }
            }
        });
        adapter.notifyDataSetChanged();
        unmarkedRoomsRv.setAdapter(adapter);
        calculateRvPosition();
        unmarkedRoomsRv.scrollToPosition(rvPosition);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.show();
            }
        });
    }

    private void calculateTime() {
        for (int i = 0; i < SECONDS_IN_A_DAY; i += THIRTY_MINUTES) {
            minutes = (i / 60) % 60;
            hour = i / (60 * 60);

            calendar = Calendar.getInstance();
            todayDateStr = "Today is " + DATE_FORMAT.format(calendar.getTime());
//            dateTv.setText(todayDateStr);
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minutes);

//            dates.add(calendar.getTime());
            EventInfo eventInfo = new EventInfo();
            eventInfo.setDateEvent(calendar.getTime());
            eventList.add(eventInfo);
        }

    }

    private void calculateRvPosition() {
        Calendar calend = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("hh:mm a");
        String strDate = mdformat.format(calend.getTime());
        String[] delimiter = strDate.split(":");
        String hourOne = delimiter[0];
        String hourTwo = delimiter[1];
        String[] delimiter2 = hourTwo.split(" ");
        String min = delimiter2[0];
        String am_pm = delimiter2[1];
//        Log.d("Nikhil", "hours= " + hourOne);
//        Log.d("Nikhil", "minutes= " + min);
//        Log.d("Nikhil", "am/pm= " + am_pm);
        int totalMinutes = (Integer.parseInt(hourOne) * 60)
                + Integer.parseInt(min);
        if (am_pm.equals("PM")) {
            Log.d("Nikhil", "strDate PM= " + strDate);
            newRvPosition = (totalMinutes / 30) + 24;
        } else {
            Log.d("Nikhil", "strDate AM= " + strDate);
            newRvPosition = totalMinutes / 30;
        }
        rvPosition = newRvPosition;
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
        return R.layout.fragment_single_day;
    }

    @Override
    protected String getFragmentTag() {
        return null;
    }
}
