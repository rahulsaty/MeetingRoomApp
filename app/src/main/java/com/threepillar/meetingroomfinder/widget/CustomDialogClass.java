package com.threepillar.meetingroomfinder.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.threepillar.meetingroomfinder.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomDialogClass extends Dialog {

    public Activity activity;
    //    public Dialog d;
    public OnAddBtnClickListener mOnAddBtnClickListener;

    @BindView(R.id.title_meeting)
    EditText titleEt;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.add_btn)
    Button addBtn;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;

    public CustomDialogClass(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_btn)
    public void addBtnClicked() {
        if (mOnAddBtnClickListener != null) {
            mOnAddBtnClickListener.onAddBtnClick(titleEt.getText().toString(), emailEt.getText().toString());
            dismiss();
        }
    }

    @OnClick(R.id.cancel_btn)
    public void cancelBtnClicked() {
        dismiss();
    }

    public interface OnAddBtnClickListener {
        void onAddBtnClick(String title, String email);
    }

    public void setOnAddBtnClickListener(OnAddBtnClickListener mOnAddBtnClickListener) {
        this.mOnAddBtnClickListener = mOnAddBtnClickListener;
    }
}