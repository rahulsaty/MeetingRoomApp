package com.threepillar.meetingroomfinder.common.util;

import android.content.Context;
import android.os.AsyncTask;

import com.threepillar.meetingroomfinder.fragment.RoomsFragment;
import com.threepillar.meetingroomfinder.model.NetHandler;
import com.threepillar.meetingroomfinder.model.Room;

import java.util.ArrayList;

/**
 * Created by mohd.irfan on 6/16/2017.
 */

/**
 * An asynchronous task that handles the Google Calendar API call.
 * Placing the API calls in their own task ensures the UI stays responsive.
 */
public class MakeRequestTask extends AsyncTask<Void, Void, ArrayList<Room>> {
    private Exception mLastError = null;
    private RoomsFragment baseFragment;
    private Context context;

    public MakeRequestTask(Context context, RoomsFragment baseFragment) {
        this.baseFragment = baseFragment;
        this.context = context;

    }

    @Override
    protected ArrayList<Room> doInBackground(Void... params) {
        try {
            return new NetHandler().fetchAllRooms(context);
        } catch (Exception e) {
            mLastError = e;
            cancel(true);
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        baseFragment.showLoading(false);
    }

    @Override
    protected void onPostExecute(ArrayList<Room> output) {
        baseFragment.hideLoading();
        baseFragment.setAdapter(output);
    }


}
