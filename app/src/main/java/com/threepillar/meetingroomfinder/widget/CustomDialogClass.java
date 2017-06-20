package com.threepillar.meetingroomfinder.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.threepillar.meetingroomfinder.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomDialogClass extends Dialog  {

    public Activity c;
    public Dialog d;

    @BindView(R.id.title_meeting)
    EditText titleEt;
    @BindView(R.id.email_et)
    EditText emailEt;
    @BindView(R.id.add_btn)
    Button addBtn;
    @BindView(R.id.cancel_btn)
    Button cancelBtn;

    public CustomDialogClass(Activity a) {
        super(a);
        this.c = a;
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

    }

    @OnClick(R.id.cancel_btn)
    public void cancelBtnClicked() {
        dismiss();
    }
}