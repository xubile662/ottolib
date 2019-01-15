package com.otto.mart.viewmodule.dialog;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.otto.mart.viewmodule.R;

import java.util.Date;

import glenn.base.viewmodule.dialog.LazyDialog;
import glenn.base.viewmodule.textView.LazyTextview;

public class PayConfirmationDialog extends LazyDialog {
    View contentView, action;
    LazyTextview ltv_name, ltv_amount, ltv_fee;

    private Context mContext;
    private LazyDialog mDialog;
    private payConfirmDialogListener listener;

    public PayConfirmationDialog(@NonNull Context context, Activity parent, Boolean isHideToolbar, Boolean hideCloseBtn) {
        super(context, parent, isHideToolbar, hideCloseBtn);
        mContext = context;
        mDialog = this;
        initComponent();
        initContent();
    }

    public void setListener(payConfirmDialogListener listener) {
        this.listener = listener;
    }

    private void initComponent() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_payconfirm, null);
        this.setContainerView(contentView);
        this.getToolbarView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.ocean_blue));
        this.setToolbarDarkmode();

        ltv_name = contentView.findViewById(R.id.ltv_name);
        ltv_amount = contentView.findViewById(R.id.ltv_val);
        ltv_fee = contentView.findViewById(R.id.ltv_fee);
        action = contentView.findViewById(R.id.confirm);
    }

    private void initContent() {
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.actionPressed();
            }
        });
    }


    public void setData(String name, String amount, String fee){
        ltv_name.setText(name);
        ltv_amount.setText(amount);
        ltv_fee.setText(fee);
    }


    @Override
    public void dismiss() {
        if (listener != null)
            listener.onDialogDismiss();
        super.dismiss();
    }

    public interface payConfirmDialogListener {
        void actionPressed();

        void onDialogDismiss();
    }
}
