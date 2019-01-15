package com.otto.mart.viewmodule.dialog;


import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.otto.mart.viewmodule.R;
import com.otto.mart.viewmodule.layout.OtpViewGroup;

import java.util.concurrent.TimeUnit;

import glenn.base.viewmodule.dialog.LazyDialog;

public class OtpDialog extends LazyDialog {
    View contentView, action, layout_resend;
    OtpViewGroup ovg_code;
    Context mContext;
    LazyDialog mDialog;
    ProgressBar progressBar;

    OtpDialogListener listener;
    TextView tv_timer, tv_resend, shop_phone;
    CountDownTimer timer;

    public OtpDialog(@NonNull Context context, Activity parent, Boolean hideCloseBtn) {
        super(context, parent, hideCloseBtn);
        mContext = context;
        mDialog = this;
        initComponent();
        initContent();
    }

    public void setOtpListener(OtpDialogListener listener) {
        this.listener = listener;
    }

    private void initComponent() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_inputotp, null);
        this.setContainerView(contentView);
        this.getToolbarView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.ocean_blue));
        this.setToolbarDarkmode();

        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        TextView titleTv = this.getToolbarView().findViewById(R.id.title);
        titleTv.setText("Masukkan Kode OTP");

        ovg_code = findViewById(R.id.otp);
        action = findViewById(R.id.action1);
        progressBar = findViewById(R.id.pBar);
        tv_timer = findViewById(R.id.timer);
        shop_phone = findViewById(R.id.shop_phone);
        tv_resend = findViewById(R.id.tv_resend);
        layout_resend = findViewById(R.id.layout_resend);
    }

    private void initContent() {
        action.setVisibility(View.INVISIBLE);
        ovg_code.setListener(new OtpViewGroup.OtpListener() {
            @Override
            public void onOtpEntered(String otp) {
//                action.setVisibility(View.VISIBLE);
                if (listener != null) {
//                    listener.onOtpEntered(OtpDialog.this);
                    listener.onActionPressed(OtpDialog.this);
                }
            }
        });

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onActionPressed(OtpDialog.this);
            }
        });
        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onResendPressed(OtpDialog.this);
                layout_resend.setVisibility(View.INVISIBLE);
//                action.setVisibility(View.VISIBLE);
                triggerTimer();
            }
        });
    }

    public String getOtpCode() {
        if (ovg_code.hasValidOTP()) {
            return ovg_code.getOTP();
        } else {
            return "otp invalid";
        }
    }

//    public void setPhoneNumber(String phone) {
//        shop_phone.setText(
//                DataUtil.getXXXPhone(phone));
//    }

    public void setLoadingState() {
        progressBar.setVisibility(View.VISIBLE);
        action.setVisibility(View.INVISIBLE);
    }

    public void setNotLoadingState() {
        progressBar.setVisibility(View.INVISIBLE);
//        action.setVisibility(View.VISIBLE);
    }


    @Override
    public void show() {
        super.show();
        triggerTimer();
    }

    @Override
    public void dismiss() {
        timer.cancel();
        super.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (listener != null) {
            listener.onBackPressed(this);
        }
    }

    private void triggerTimer() {
        timer = new CountDownTimer(180000, 1000) {
            public void onTick(long millisUntilFinished) {
                tv_timer.setTextColor(ContextCompat.getColor(mContext, R.color.ocean_blue));
                tv_timer.setText("" + String.format("%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                setNotLoadingState();
                tv_timer.setText("OTP kamu telah kadaluwarsa\n mohon  request ulang");
                tv_timer.setTextColor(ContextCompat.getColor(mContext, R.color.cherry_red));
                action.setVisibility(View.INVISIBLE);
                layout_resend.setVisibility(View.VISIBLE);
            }
        }.start();
    }


    public interface OtpDialogListener {

        void onOtpEntered(OtpDialog dialog);

        void onActionPressed(OtpDialog dialog);

        void onBackPressed(OtpDialog dialog);

        void onResendPressed(OtpDialog dialog);
    }
}

