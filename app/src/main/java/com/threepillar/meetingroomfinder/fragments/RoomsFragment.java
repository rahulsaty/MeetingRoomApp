package com.threepillar.meetingroomfinder.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.threepillar.meetingroomfinder.R;
import com.threepillar.meetingroomfinder.base.BaseFragment;
import com.threepillar.meetingroomfinder.models.Room;
import com.threepillar.meetingroomfinder.network.JSONResponse;
import com.threepillar.meetingroomfinder.network.RequestInterface;
import com.threepillar.meetingroomfinder.network.RoomDataAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RoomsFragment extends BaseFragment {

    @BindView(R.id.rooms_rv)
    RecyclerView roomsRv;
    @BindView(R.id.titleEt)
    EditText titleEt;
    @BindView(R.id.descriptionEt)
    EditText descriptionEt;
    @BindView(R.id.emailEt)
    EditText emailEt;
    @BindView(R.id.book)
    Button submitBtn;
    @BindView(R.id.reset)
    Button resetBtn;
    @BindView(R.id.roomNameTv)
    TextView roomNameTv;

    private ArrayList<Room> roomsList;
    private RoomDataAdapter dataAdapter;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;
    private Handler handler;
    private Runnable runnable;

    public static RoomsFragment newInstance(Parcelable fragArgs) {
        RoomsFragment roomsFragment = new RoomsFragment();
        return roomsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showLoading(true);
        runHandlerCode();
        instantiateDialog();
        init();
    }

    private void runHandlerCode() {
        // Hide after some seconds
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        };

        handler.postDelayed(runnable, 4000);
    }

    private void instantiateDialog() {
        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialog = alertDialogBuilder.create();
    }

    private void init() {
        roomsRv.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        roomsRv.setLayoutManager(layoutManager);
        emailEt.setText("a@g.com");
        loadJSON();
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.myjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideLoading();
                JSONResponse jsonResponse = response.body();
                roomsList = new ArrayList<>(Arrays.asList(jsonResponse.getRooms()));
                dataAdapter = new RoomDataAdapter(roomsList, getActivity());
                dataAdapter.setOnItemClickListener(new RoomDataAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, String itemName) {
                        roomNameTv.setText(itemName);
                    }
                });
                dataAdapter.notifyDataSetChanged();
                roomsRv.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @OnClick(R.id.reset)
    public void resetFields() {
        titleEt.setText("");
        descriptionEt.setText("");
        emailEt.setText("");
    }

    @OnClick(R.id.book)
    public void bookRoom() {
        String emailText = emailEt.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches() && !emailText.equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.enter_correct_email), Toast.LENGTH_SHORT).show();
        } else if (emailText.equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.enter_email), Toast.LENGTH_SHORT).show();
        } else
            showTheDialog();

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
                getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_layout,
                        new CalenderFragment()).commit();
            }
        });


    }

    private void showTheDialog() {
        alertDialogBuilder.setTitle(getResources().getString(R.string.room_booked_successfully))
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int ids) {
                        dialog.cancel();
                        getActivity().getFragmentManager().beginTransaction().replace(R.id.frame_layout,
                                new CalenderFragment()).commit();
                    }
                });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
