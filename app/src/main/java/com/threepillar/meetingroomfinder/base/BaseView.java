package com.threepillar.meetingroomfinder.base;

import android.os.Parcelable;
import android.view.View;

import com.threepillar.meetingroomfinder.enums.FragmentTag;


public interface BaseView {

    /**
     * show message on view
     *
     * @param msg
     */
    void showMessage(String msg);

    /**
     * perform UI action on data failure response
     *
     * @param failureResponse
     */
    void showError(FailureResponse failureResponse);

    /**
     * show progress
     *
     * @param isCancelable
     */
    void showLoading(boolean isCancelable);

    /**
     * show progress with message
     *
     * @param message
     * @param isCancelable
     */
    void showLoading(String message, boolean isCancelable);

    /**
     * hide progress
     */
    void hideLoading();

    /**
     * hide keyboard
     */
    void hideKeyboard();


    /**
     * show fragment
     *
     * @param fragmentTag
     * @param fragmentArgs
     * @param addToBackStack
     */

    void showFragment(FragmentTag fragmentTag, Parcelable fragmentArgs, boolean addToBackStack);


    /**
     * show fragment
     *
     * @param fragmentTag
     * @param fragmentArgs
     * @param addToBackStack
     */

    void showFragmentForContainerId(int containerId, FragmentTag fragmentTag, Parcelable fragmentArgs, boolean addToBackStack);


    /**
     * clearing all back stack.
     */

    void clearBackStack();


    /**
     * clear back stack upto some fragment.
     *
     * @param fragmentTag
     */
    void clearBackStackUptoFrag(FragmentTag fragmentTag);

    /**
     * setting text to view TextView,EditText,Button
     *
     * @param id
     * @param text
     */
    void setTextById(int id, String text);


    /**
     * set on click listener
     *
     * @param view
     */
    void onViewClicked(View view);

}

