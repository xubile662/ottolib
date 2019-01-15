package com.otto.mart.viewmodule.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.otto.mart.viewmodule.R;

import glenn.base.viewmodule.dialog.LazyDialog;

public class FilterDialog extends LazyDialog {

    private FilterDialogInterface listener;
    View contentView;
    EditText edt_filter;
    Context mContext;

    public FilterDialog(@NonNull Context context, Activity parent, Boolean isHideToolbar, Boolean hideCloseBtn) {
        super(context, parent, isHideToolbar, hideCloseBtn);
        mContext = context;
        initComponent();
        initContent();
    }

    public void setListener(FilterDialogInterface listener) {
        this.listener = listener;
    }

    private void initComponent() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_edtfilter, null);
        this.setContainerView(contentView);
        this.getToolbarView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.ocean_blue));
        this.setToolbarDarkmode();

        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.TOP;
        window.setAttributes(wlp);

        TextView titleTv = this.getToolbarView().findViewById(R.id.title);
        titleTv.setText("Filter");
        edt_filter = contentView.findViewById(R.id.edt_filterby);
    }

    private void initContent() {
        edt_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > -1) {
                    if (listener != null) {
                        listener.onTypingStopped(FilterDialog.this, s.toString());
                    }
                }
            }
        });
    }

    @Override
    public void dismiss() {
        if (listener != null) {
            listener.onClose(FilterDialog.this, edt_filter.getText().toString());
        }
        super.dismiss();
    }

    public void setDefaultText(String text) {
        edt_filter.setText(text);
    }

    public interface FilterDialogInterface {
        void onTypingStopped(FilterDialog dialog, String s);

        void onClose(FilterDialog dialog, String s);
    }
}
