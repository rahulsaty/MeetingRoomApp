package com.threepillar.meetingroomfinder.base;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.threepillar.meetingroomfinder.R;
import com.threepillar.meetingroomfinder.common.util.AppConstants;
import com.threepillar.meetingroomfinder.common.util.Utils;
import com.threepillar.meetingroomfinder.enums.FragmentTag;
import com.threepillar.meetingroomfinder.fragmentManager.AppFragmentManager;
import com.threepillar.meetingroomfinder.widget.CustomProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private static final String PROGRESS_TAG = "progress_dialog";
    @BindView(R.id.base_container_view)
    CoordinatorLayout baseContainerView;
    private CustomProgressDialog mProgressDialog;
    ActionBar actionBar;
    private AppFragmentManager appFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        ButterKnife.bind(this);
        if (getResourceId() != 0) {
            CoordinatorLayout.LayoutParams layoutParams = new
                    CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT,
                    CoordinatorLayout.LayoutParams.MATCH_PARENT);
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View vi = inflater.inflate(getResourceId(), null);
            baseContainerView.addView(vi, layoutParams);
        }
        actionBar = getSupportActionBar();
        appFragmentManager = new AppFragmentManager(this);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setToolbar(String title) {
        actionBar.setTitle(title);
        supportInvalidateOptionsMenu();
    }


    protected abstract int getResourceId();

    /**
     * get main framelayout id to show fragments
     *
     * @return
     */
    protected abstract int getMainFragmentContainerId();

    /**
     * initialize view such that it can be reinitialized by calling this method
     */
    protected abstract void initView();

    @Override
    public void showMessage(String msg) {
        Snackbar snackBar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        snackBar.show();
    }

    @Override
    public void showFragment(FragmentTag fragmentTag, Parcelable fragmentArgs, boolean addToBackStack) {
        if (appFragmentManager != null)
            appFragmentManager.showFragment(getMainFragmentContainerId(), fragmentTag, fragmentArgs, addToBackStack);

    }

    @Override
    public void showFragment(int containerId, FragmentTag fragmentTag, Parcelable fragmentArgs, boolean addToBackStack) {
        if (appFragmentManager != null)
            appFragmentManager.showFragment(containerId, fragmentTag, fragmentArgs, addToBackStack);
    }

    @Override
    public void clearBackStack() {
        if (appFragmentManager != null)
            appFragmentManager.clearBackStack();
    }

    @Override
    public void clearBackStackUptoFrag(FragmentTag fragmentTag) {
        if (appFragmentManager != null)
            appFragmentManager.clearBackStackUptoFrag(fragmentTag);

    }

    @Override
    public void showError(FailureResponse failureResponse) {

    }

    @Override
    public void showLoading(boolean isCancelable) {
        hideLoading();
        mProgressDialog = new CustomProgressDialog();
        Bundle args = new Bundle();
        args.putBoolean(AppConstants.Bundle.PROGRESS_DIALOG_ISCANCELABLE, isCancelable);
        args.putString(AppConstants.Bundle.PROGRESS_DIALOG_MESSAGE, getString(R.string.loading));
        mProgressDialog.setArguments(args);
        mProgressDialog.setRetainInstance(true);
        mProgressDialog.show(getSupportFragmentManager(), PROGRESS_TAG);
    }

    @Override
    public void showLoading(String message, boolean isCancelable) {
        hideLoading();
        mProgressDialog = new CustomProgressDialog();
        Bundle args = new Bundle();
        args.putBoolean(AppConstants.Bundle.PROGRESS_DIALOG_ISCANCELABLE, isCancelable);
        args.putString(AppConstants.Bundle.PROGRESS_DIALOG_MESSAGE, message);
        mProgressDialog.setArguments(args);
        mProgressDialog.setRetainInstance(true);
        mProgressDialog.show(getSupportFragmentManager(), PROGRESS_TAG);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isAdded()) {
            mProgressDialog.dismissAllowingStateLoss();
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (appFragmentManager != null)
            appFragmentManager.cancelAction(getMainFragmentContainerId());
    }


    @Override
    public void hideKeyboard() {
        Utils.makeKeyboardInvisible(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        appFragmentManager.cancelAction(getMainFragmentContainerId());
    }

}
