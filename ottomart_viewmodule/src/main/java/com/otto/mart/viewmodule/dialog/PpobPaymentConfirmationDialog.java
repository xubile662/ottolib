package com.otto.mart.viewmodule.dialog;


import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.otto.mart.viewmodule.R;

import java.util.Timer;
import java.util.TimerTask;

import glenn.base.viewmodule.dialog.LazyDialog;

public class PpobPaymentConfirmationDialog extends LazyDialog {

    View contentView, qrloading, layout_actioncontainer, action, action2;
    TextView tv_payment;
    Timer timer;
    int retries = 0;

    Context mContext;
    LazyDialog mDialog;

    onActionClickListener listener;

    public PpobPaymentConfirmationDialog(@NonNull Context context, Activity parent, Boolean isHideToolbar, Boolean hideCloseBtn) {
        super(context, parent, isHideToolbar, hideCloseBtn);
        mContext = context;
        mDialog = this;
        initComponent();
    }


    public void setListener(onActionClickListener listener) {
        this.listener = listener;
        setupListener();
    }

    public void setTimerListener(onActionClickListener listener) {
        this.listener = listener;
        setupTimerListener();
    }

    private void initComponent() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_cashpayment, null);
        this.setContainerView(contentView);
        this.getToolbarView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.ocean_blue));
        this.setToolbarDarkmode();
        setTitle("Pembayaran Cash");
        layout_actioncontainer = findViewById(R.id.layout_actioncontainer);
        action = findViewById(R.id.action);
        action2 = findViewById(R.id.action2);
        tv_payment = findViewById(R.id.tv_payment);
        qrloading = findViewById(R.id.qrloading);


    }

    public void setupListener() {
        layout_actioncontainer.setVisibility(View.VISIBLE);
        action.setOnClickListener(v -> {
            if (listener != null) {
                listener.onActionClick();
            }
        });
        action2.setOnClickListener(v -> mDialog.dismiss());
    }

    public void setupTimerListener() {
        layout_actioncontainer.setVisibility(View.GONE);
        if (retries > 5) {
            stopTimedListener();
            setupListener();
            return;
        }
        qrloading.setVisibility(View.VISIBLE);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();

        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (listener != null)
                                    listener.onActionClick();
                            }
                        });
                        retries++;

                    }
                },
                6000
        );
    }

    public void stopTimedListener() {
        qrloading.setVisibility(View.GONE);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void setAmount(String cash) {
        tv_payment.setText(cash);
    }

    public interface onActionClickListener {
        void onActionClick();
    }
}
