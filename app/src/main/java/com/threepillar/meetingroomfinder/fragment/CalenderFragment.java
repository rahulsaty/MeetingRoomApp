package com.threepillar.meetingroomfinder.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;

import com.threepillar.meetingroomfinder.R;
import com.threepillar.meetingroomfinder.base.BaseFragment;
import com.threepillar.meetingroomfinder.enums.FragmentTag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalenderFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.calender_view)
    CalendarView calendarView;
    @BindView(R.id.fromTimeSpinner)
    Spinner fromTimeSpinner;
    @BindView(R.id.toTimeSpinner)
    Spinner toTimeSpinner;
    @BindView(R.id.find_room)
    Button findRoomBtn;

    private String monthSelected;
    private String yearSelected;
    private String daySelected;
    private final int SECONDS_IN_A_DAY = 24 * 60 * 60;

    private ArrayList<String> toTimeList;
    private int minutes, hour;
    private String timeSpinner;
    private Calendar calendar;
    private ArrayAdapter<String> fromTimeAdapter;
    private ArrayAdapter<String> toTimeAdapter;

    public static CalenderFragment newInstance(Parcelable fragArgs) {
        CalenderFragment calenderFragment = new CalenderFragment();
        return calenderFragment;
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
        fromTimeSpinner.setOnItemSelectedListener(this);
        toTimeSpinner.setOnItemSelectedListener(this);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                yearSelected = String.valueOf(year);
                monthSelected = String.valueOf(month + 1);
                daySelected = String.valueOf(dayOfMonth);
                Log.d("Nikhil", "Selected date: " + daySelected + "/" + monthSelected + "/" + yearSelected);
            }
        });
       // ((ViewPager)((ViewGroup) calendarView.getChildAt(0)).getChildAt(0)).;

        toTimeList = new ArrayList<>();

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");
        for (int i = 0; i < SECONDS_IN_A_DAY; i += 1800) {
            minutes = (i / 60) % 60;
            hour = i / 3600;

            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minutes);

            timeSpinner = simpleDateFormat.format(calendar.getTime());
            toTimeList.add(timeSpinner);
        }
//        ArrayAdapter<String> fromTimeAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, toTimeList);
        fromTimeAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_custom_row,
                R.id.custom_row, toTimeList);
        toTimeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_custom_row,
                R.id.custom_row, new ArrayList<String>(toTimeList));

//        fromTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromTimeSpinner.setAdapter(fromTimeAdapter);
        toTimeSpinner.setAdapter(toTimeAdapter);

        fromTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position <= 45) {
                    ArrayList<String> subArrayList = new ArrayList<>(toTimeList.subList(position, toTimeList.size()));
                    toTimeAdapter.clear();
                    toTimeAdapter.addAll(subArrayList);
                    toTimeAdapter.notifyDataSetChanged();
                    toTimeSpinner.setSelection(2);
                } else if (position == 46) {
                    toTimeAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_custom_row,
                            R.id.custom_row, new ArrayList<>(toTimeList));
                    toTimeSpinner.setAdapter(toTimeAdapter);
                    toTimeSpinner.setSelection(0);
                } else if (position == 47) {
                    toTimeAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_custom_row,
                            R.id.custom_row, new ArrayList<>(toTimeList));
                    toTimeSpinner.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        return R.layout.fragment_calender;
    }

    @Override
    protected String getFragmentTag() {
        return null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnClick(R.id.find_room)
    public void findTheRoom(){
        showFragment(FragmentTag.RoomsFragment, null, true);
    }
}
