package com.threepillar.meetingroomfinder.activity;

import android.Manifest;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.threepillar.meetingroomfinder.R;
import com.threepillar.meetingroomfinder.base.BaseActivity;
import com.threepillar.meetingroomfinder.common.util.AppConstants;
import com.threepillar.meetingroomfinder.common.util.AppPrefrence;
import com.threepillar.meetingroomfinder.common.util.NetworkUtill;
import com.threepillar.meetingroomfinder.common.util.Utils;
import com.threepillar.meetingroomfinder.enums.FragmentTag;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by mohd.irfan on 6/14/2017.
 */

public class SetupActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    GoogleAccountCredential mCredential;


    private FrameLayout container;
    private TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getResourceId() {
        return R.layout.setup_activity;
    }

    @Override
    protected int getMainFragmentContainerId() {
        return R.id.container;
    }


    @Override
    protected void initView() {
        container = (FrameLayout) findViewById(R.id.container);
        tv_title = (TextView) findViewById(R.id.tv_title);
        mCredential = NetworkUtill.getGoogleAccountCredential(SetupActivity.this, null);
        setUpAccount();
    }

    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    private void setUpAccount() {
        if (!NetworkUtill.isGooglePlayServicesAvailable(SetupActivity.this)) {
            NetworkUtill.acquireGooglePlayServices(SetupActivity.this);
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!NetworkUtill.isNetworkAvailable(SetupActivity.this)) {
            Toast.makeText(SetupActivity.this, "No network connection available", Toast.LENGTH_SHORT).show();
        } else {
//            Intent intent = new Intent(SetupActivity.this, HomeActivity.class);
//            startActivity(intent);
//            finish();
            showSelectionDialog(this);
        }
    }


    /**
     * Attempts to set the account used with the API credentials. If an account
     * name was previously saved it will use that one; otherwise an account
     * picker dialog will be shown to the user. Note that the setting the
     * account to use with the credentials object requires the app to have the
     * GET_ACCOUNTS permission, which is requested here if it is not already
     * present. The AfterPermissionGranted annotation indicates that this
     * function will be rerun automatically whenever the GET_ACCOUNTS permission
     * is granted.
     */
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            String accountName = new AppPrefrence(SetupActivity.this).getAccountName();
            if (NetworkUtill.isNotNull(accountName)) {
                mCredential.setSelectedAccountName(accountName);
                setUpAccount();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        AppConstants.GCONSTANTS.REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    AppConstants.GCONSTANTS.REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode  code indicating the result of the incoming
     *                    activity result.
     * @param data        Intent (containing result data) returned by incoming
     *                    activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AppConstants.GCONSTANTS.REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    Toast.makeText(SetupActivity.this, "This app requires Google Play Services. Please install " +
                            "Google Play Services on your device and relaunch this app.", Toast.LENGTH_SHORT).show();
                } else {
                    setUpAccount();
                }
                break;
            case AppConstants.GCONSTANTS.REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        AppPrefrence appPrefrence = new AppPrefrence(SetupActivity.this);
                        appPrefrence.setAccountName(accountName);
                        mCredential.setSelectedAccountName(accountName);
                        setUpAccount();
                    }
                }
                break;
            case AppConstants.GCONSTANTS.REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    setUpAccount();
                }
                break;
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     *
     * @param requestCode  The request code passed in
     *                     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    /**
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
        setUpAccount();
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
        finish();
    }

    private void showSelectionDialog(Context context) {

        AppPrefrence appPrefrence = new AppPrefrence(this);
        if (Utils.isNotNull(appPrefrence.getRoomType())) {
            addRoomsFragement(appPrefrence.getRoomType());
        } else {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.name_unnamed_dialog);
            dialog.setTitle("Title...");

            final RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radiogroup);
            Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int checkedId = radioGroup.getCheckedRadioButtonId();
                    if (checkedId == R.id.rbtn_named) {
                        addRoomsFragement(AppConstants.NAMED_ROOM);
                        dialog.dismiss();
                    } else if (checkedId == R.id.rbtn_unnamedbtn) {
                        addRoomsFragement(AppConstants.UN_NAMED_ROOM);
                        dialog.dismiss();
                    }
                }
            });
            dialog.show();
        }

    }

    private void addRoomsFragement(String roomType) {
        container.setVisibility(View.VISIBLE);
        tv_title.setVisibility(View.GONE);
        new AppPrefrence(this).setRoomType(roomType);
        showFragment(FragmentTag.RoomsFragment, null, true);
    }
}
