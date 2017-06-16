package com.threepillar.meetingroomfinder.widget;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.threepillar.meetingroomfinder.common.util.AppConstants;

public class CustomProgressDialog extends DialogFragment {

    private String bodyText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean cancelable = getArguments().getBoolean(AppConstants.Bundle.PROGRESS_DIALOG_ISCANCELABLE);
        bodyText = getArguments().getString(AppConstants.Bundle.PROGRESS_DIALOG_MESSAGE);
        setCancelable(cancelable);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void updateMessage(String message) {
        if (getDialog() != null && getDialog() instanceof ProgressDialog) {
            bodyText = message;
            ((ProgressDialog) getDialog()).setMessage(message);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getActivity(), getTheme());
        dialog.setMessage(bodyText);
        dialog.setIndeterminate(isCancelable());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Override
    public void onDestroyView() {
        Dialog dialog = getDialog();
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
            dialog.setOnDismissListener(null);
        }
        super.onDestroyView();
    }

}
