package com.threepillar.meetingroomfinder.common.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.threepillar.meetingroomfinder.model.Room;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mohd.irfan on 6/14/2017.
 */

public class NetworkUtill {


    public static GoogleAccountCredential getGoogleAccountCredential(Context context, String accountName) {
        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(context,
                Collections.singleton(CalendarScopes.CALENDAR));
        if (NetworkUtill.isNotNull(accountName) && credential.getSelectedAccountName() == null)
            credential.setSelectedAccountName(accountName);
        return credential;
    }

    public static Calendar getGoogleCalenderInstance(Context context) {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return new Calendar.Builder(transport, jsonFactory, getGoogleAccountCredential(context, new AppPrefrence(context).getAccountName()))
                .setApplicationName("Meeting Room App")
                .build();
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     *
     * @return true if Google Play Services is available and up to
     * date on this device; false otherwise.
     */
    public static boolean isGooglePlayServicesAvailable(Context context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }


    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    public static void acquireGooglePlayServices(Activity activity) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        final int connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(activity,
                    AppConstants.GCONSTANTS.REQUEST_GOOGLE_PLAY_SERVICES, connectionStatusCode);
        }
    }


    /**
     * Display an error dialog showing that Google Play Services is missing
     * or out of date.
     *
     * @param connectionStatusCode code describing the presence (or lack of)
     *                             Google Play Services on this device.
     */
    private static void showGooglePlayServicesAvailabilityErrorDialog(
            Activity activity, int REQUEST_GOOGLE_PLAY_SERVICES, final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                activity,
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();
    }


    /**
     * Checks whether the device currently has a network connection.
     *
     * @return true if the device has a network connection, false otherwise.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    public static boolean isNotNull(String data) {
        if (data == null || data.equalsIgnoreCase("NA") || data.equalsIgnoreCase("") || data.equalsIgnoreCase(" "))
            return false;
        return true;
    }


    public static void serializeList(Context context, ArrayList<Room> returnlist) {
        try {

            File file = new File(context.getFilesDir() + "/" + AppConstants.GCONSTANTS.SAVED_FILE_NAME);
            if (file.exists())
                file.delete();
            FileOutputStream fos = context.openFileOutput(AppConstants.GCONSTANTS.SAVED_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(returnlist);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static ArrayList<Room> dSerializeList(Context context) {
        FileInputStream fis;
        try {
            fis = context.openFileInput(AppConstants.GCONSTANTS.SAVED_FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Room> list = (ArrayList<Room>) ois.readObject();
            ois.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
