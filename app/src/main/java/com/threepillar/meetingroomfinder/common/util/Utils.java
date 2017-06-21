package com.threepillar.meetingroomfinder.common.util;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Utils {
    /***
     *  Display {@link Toast}  by providing  string.
     * @param context
     * @param message
     * @param duration
     */

    public static void displayToastByText(Context context, String message, int duration) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, duration).show();
        }
    }

    /***
     *  Display {@link Toast}  by providing resource Id .
     * @param context
     * @param id
     * @param duration
     */
    public static void displayToastById(Context context, int id, int duration) {
        String message = context.getString(id);
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(context, message, duration).show();
        }
    }

    public static int getStatusBarHeight(Context mContext) {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /*// pop dialog to show alert message to user
    public static void showPopUpDialog(Activity activity, CustomDialogListener customDialogListener) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        CustomDialog customDialog = null;
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        Fragment prev = activity.getFragmentManager().findFragmentByTag(CustomDialog.TAG);
        if (prev != null) {
            customDialog = (CustomDialog) prev;
            customDialog.dismiss();
        }
        ft.addToBackStack(null);
        customDialog = customDialog.newInstance(customDialogListener, activity.getResources().getString(R.string.pin_alert_title),
                activity.getResources().getString(R.string.pin_alert_heading),
                activity.getResources().getString(R.string.pin_alert_message1),
                activity.getResources().getString(R.string.pin_alert_message2),
                activity.getResources().getString(R.string.got_it));
        customDialog.show(ft, CustomDialog.TAG);
        customDialog.setCancelable(false);
    }*/


    /**
     * to check if string is valid or not
     *
     * @param data
     * @return
     */
    public static boolean isNotNull(String data) {
        return !(data == null || data.equalsIgnoreCase("") || data.equalsIgnoreCase(" ") || data.equalsIgnoreCase("NA"));
    }

    /**
     * to set edittext max char limit
     *
     * @param editText
     * @param length
     */
    public static void setEditTextLengthFilter(EditText editText, int length) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }


    /**
     * to request read_sms permission
     *
     * @param fragment
     * @return
     */
    public static boolean requestPermission(Fragment fragment, int requestCOde, String... permissions) {
        if (permissions == null) {
            return true;
        }

        // checking all permissions, if one id ca
        boolean hasPermissions = true;
        for (String permission : permissions) {
            int result = ContextCompat.checkSelfPermission(fragment.getActivity(), permission);
            hasPermissions = hasPermissions && (result == PackageManager.PERMISSION_GRANTED);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (!hasPermissions) {
                fragment.requestPermissions(permissions, requestCOde);
                return false;

            }

        return true;

    }


    /**
     * to read json file from assets
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String readJsonFromAssets(Context context, String fileName) {

        String json = null;
        if (context == null || !isNotNull(fileName))
            return json;
        try {
            InputStream is = context.getAssets().open(fileName + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;

    }

    public static void setVisibilityOnKeyboardOpenClose(View rootView, final View changeVisibilityView) {
        if (rootView == null || changeVisibilityView == null)
            return;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        final int screenHeight = rootView.getRootView().getHeight();
        // r.bottom is the position above soft keypad or device button.
        // if keypad is shown, the r.bottom is smaller than that before.
        final int keypadHeight = screenHeight - r.bottom;

        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
            // keyboard is opened
            changeVisibilityView.setVisibility(View.GONE);
        } else {
            // keyboard is closed
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    changeVisibilityView.setVisibility(View.VISIBLE);
                }
            }, AppConstants.KEYBOARD_VISIBILITY_DELAY);
        }


    }

    public static void makeKeyboardInvisible(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String removeFieldsFromString(String data, String oldChar, String newChar) {
        if (!Utils.isNotNull(data) && !Utils.isNotNull(oldChar) && !Utils.isNotNull(newChar))
            return data;

        if (data.contains(oldChar))
            return data.replace(oldChar, newChar);

        return data;

    }

    /**
     * Hide cut copy paste option
     *
     * @param editText
     */
    public static void hideCCPOption(EditText editText) {
        editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub

            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode,
                                               MenuItem item) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }


    // validating user pin items
    public static boolean validatePinItems(ArrayList<String> pinNumbers) {
        if (pinNumbers != null && pinNumbers.size() != 0) {
            if (getConsecutiveNumbersStatus(pinNumbers) == true || getPINRepetitiveStatus(pinNumbers) == true)
                return false;
        }
        return true;
    }

    // checking whether number is repetitive or not
    public static boolean getPINRepetitiveStatus(ArrayList<String> pinNumbers) {
        boolean pinFormatStatus;
        int repetitiveNumbers = 2;
        int count = 1;
        if (pinNumbers != null && pinNumbers.size() != 0) {
            for (int i = 0; i < pinNumbers.size() - 1; i++) {
                if (i != pinNumbers.size() - 1) {
                    // validation for two repetitive pin number
                    if (pinNumbers.get(i).equals(pinNumbers.get(i + 1))) {
                        count++;
                        if (count == repetitiveNumbers)
                            return true;
                    }
                }
            }
        }
        return false;
    }

    // checking whether number is consecutive or not
    public static boolean getConsecutiveNumbersStatus(ArrayList<String> pinNumbers) {
        int consecutives = 1;
        // i starts at 1 not 0
        for (int i = 1; i < pinNumbers.size(); i++) {
            // Going through each number here
            if ((Integer.parseInt(pinNumbers.get(i)) - Integer.parseInt(pinNumbers.get(i - 1))) == 1)
                consecutives++; // Numbers were consecutive
            if (consecutives == 3)
                return true;
        }
        return false;
    }

    /**
     * Check Internet connection is available or not.
     *
     * @param context is the {@link Context} of the {@link Activity}.
     * @return <b>true</b> is Internet connection is available.
     */
    public static boolean isInternetAvailable(Context context) {
        boolean isInternetAvailable = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && (networkInfo.isConnected())) {
                isInternetAvailable = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return isInternetAvailable;
    }




}

