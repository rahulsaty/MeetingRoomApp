package com.threepillar.meetingroomfinder.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.threepillar.meetingroomfinder.common.utils.LogUtil;
import com.threepillar.meetingroomfinder.enums.FragmentTag;


public abstract class BaseFragment extends Fragment implements BaseView, IActivityCallback, View.OnClickListener {

    private OnFragmentInteractionListener onFragmentInteractionListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof BaseActivity)) {
            LogUtil.LOGE(getClass().getName(), "Activity should extend base activity");
            return;
        }
        onFragmentInteractionListener = (BaseActivity) context;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof BaseActivity)) {
            LogUtil.LOGE(getClass().getName(), "Activity should extend base activity");
            return;
        }
        onFragmentInteractionListener = (BaseActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(getResourceID(), container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    /**
     * initialize view such that it can be reinitialized by calling this method
     */
    protected abstract void initView();

    protected abstract int getResourceID();

    protected abstract String getFragmentTag();

    @Override
    public void showMessage(String msg) {
        if (onFragmentInteractionListener != null)
            onFragmentInteractionListener.showMessage(msg);
    }

    @Override
    public void showError(FailureResponse failureResponse) {

    }

    @Override
    public void showLoading(boolean isCancelable) {
        if (onFragmentInteractionListener != null)
            onFragmentInteractionListener.showLoading(isCancelable);
    }

    @Override
    public void showLoading(String message, boolean isCancelable) {
        if (onFragmentInteractionListener != null)
            onFragmentInteractionListener.showLoading(message, isCancelable);
    }

    @Override
    public void hideLoading() {
        if (onFragmentInteractionListener != null)
            onFragmentInteractionListener.hideLoading();
    }

    @Override
    public void showFragment(FragmentTag fragmentTag, Parcelable fragmentArgs, boolean addToBackStack) {
        if (onFragmentInteractionListener != null)
            onFragmentInteractionListener.showFragment(fragmentTag, fragmentArgs, addToBackStack);
    }

    @Override
    public void clearBackStack() {
        if (onFragmentInteractionListener != null)
            onFragmentInteractionListener.clearBackStack();
    }

    @Override
    public void clearBackStackUptoFrag(FragmentTag fragmentTag) {
        if (onFragmentInteractionListener != null)
            onFragmentInteractionListener.clearBackStackUptoFrag(fragmentTag);
    }

    @Override
    public void showFragmentForContainerId(int containerId, FragmentTag fragmentTag, Parcelable fragmentArgs, boolean addToBackStack) {
        if (onFragmentInteractionListener != null)
            onFragmentInteractionListener.showFragment(containerId, fragmentTag, fragmentArgs, addToBackStack);
    }

    @Override
    public void hideKeyboard() {
        if (onFragmentInteractionListener != null)
            onFragmentInteractionListener.hideKeyboard();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        cancelRequest();
        onFragmentInteractionListener = null;
    }

    @Override
    public void setTextById(int id, String text) {
        if (getView() != null) {
            ((TextView) getView().findViewById(id)).setText(text);
        }
    }

    public void setOnClickListener(int id) {
        if (getView() != null)
            getView().findViewById(id).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onViewClicked(view);
    }


}