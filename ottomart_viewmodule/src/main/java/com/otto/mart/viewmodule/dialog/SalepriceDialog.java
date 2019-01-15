package com.otto.mart.viewmodule.dialog;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.otto.mart.viewmodule.R;
import com.otto.mart.viewmodule.adapter.ItemAdapter;
import com.otto.mart.viewmodule.adapter.RecyclerAdapterCallback;

import java.util.ArrayList;
import java.util.List;

import glenn.base.util.DataUtil;
import glenn.base.viewmodule.dialog.LazyDialog;
import glenn.base.viewmodule.textView.HintTextView;
import glenn.base.viewmodule.textView.LazyTextview;

import android.net.ParseException;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

public class SalepriceDialog extends LazyDialog {

    View contentView, action;
    Context mContext;
    EditText edt_saleprice;
    //    TextView tv_val;
    LazyDialog mDialog;
    HintTextView htv_text;
    RecyclerView rv_saleprice;
    LazyTextview ltv_initval, ltv_profitval;
    ImageView imgv_minus, imgv_plus;
    ItemAdapter adapter;

    salepriceDialogListener listener;
    TextWatcher textWatcher;

    Activity parent;

    int val = 0;
    int defVal = 0;

    int modifier = 100;

    public SalepriceDialog(@NonNull Context context, Activity parent, Boolean hideCloseBtn) {
        super(context, parent, hideCloseBtn);
        mContext = context;
        mDialog = this;
        this.parent = parent;
        initComponent();
        initContent();
    }

    public void setListener(salepriceDialogListener listener) {
        this.listener = listener;
    }

    private void initComponent() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_sp, null);
        this.setContainerView(contentView);
        this.getToolbarView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.ocean_blue));
        this.setToolbarDarkmode();

        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        TextView titleTv = this.getToolbarView().findViewById(R.id.title);
        titleTv.setText("Tentukan Harga Jual");

        edt_saleprice = findViewById(R.id.guk);
        htv_text = findViewById(R.id.htv_text);
        imgv_minus = findViewById(R.id.minus);
        imgv_plus = findViewById(R.id.plus);
        rv_saleprice = findViewById(R.id.rv_saleprice);
        ltv_initval = findViewById(R.id.ltv_initval);
        ltv_profitval = findViewById(R.id.ltv_profitval);
        action = findViewById(R.id.action);
    }

    private void initContent() {
        View.OnClickListener plusMinusListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.minus) {
                    if (val > modifier) {
                        val -= modifier;
                    } else {
                        val = 0;
                    }

                } else if (i == R.id.plus) {
                    val += modifier;

                }

                int profit = val - defVal;
                if (profit >= 0) {
                    ((TextView) ltv_profitval.getComponent()).setTextColor(ContextCompat.getColor(mContext, R.color.charcoal_grey));
                    ltv_profitval.setText(DataUtil.convertCurrency(profit));
                } else {
                    ((TextView) ltv_profitval.getComponent()).setTextColor(ContextCompat.getColor(mContext, R.color.cherry_red));
                    ltv_profitval.setText("-" + DataUtil.convertCurrency(profit));
                }

                edt_saleprice.setText(val + "");
            }
        };

        imgv_plus.setOnClickListener(plusMinusListener);
        imgv_minus.setOnClickListener(plusMinusListener);

        textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                if (s.length() > 0) {
                    int profit = Integer.valueOf(s.toString().replace(".", "")) - defVal;
                    if (profit >= 0) {
                        ((TextView) ltv_profitval.getComponent()).setTextColor(ContextCompat.getColor(mContext, R.color.charcoal_grey));
                        ltv_profitval.setText(DataUtil.convertCurrency(profit));
                    } else {
                        ((TextView) ltv_profitval.getComponent()).setTextColor(ContextCompat.getColor(mContext, R.color.cherry_red));
                        ltv_profitval.setText("-" + DataUtil.convertCurrency(profit));
                    }
                    val = defVal + profit;
                } else {
                    edt_saleprice.setText("0");
                }
            }
        };

        edt_saleprice.addTextChangedListener(textWatcher);

        adapter = new ItemAdapter(new RecyclerAdapterCallback() {

            @Override
            public void onItemClick(Object objectModel, int position, RecyclerView.ViewHolder currHolder) {
                try {
                    val = defVal + Integer.parseInt(((String) objectModel));
                    ltv_profitval.setText(DataUtil.convertCurrency(Integer.parseInt(((String) objectModel))));
                    edt_saleprice.setText(val + "");
                } catch (ParseException e) {
                    Log.e("", "onItemClick: " + e.getMessage());
                }
            }

            @Override
            public void onItemClick(Object objectModel, int position, int paymentTypePos, RecyclerView.ViewHolder currHolder) {

            }
        });
        List<String> jenk = new ArrayList<>();
        jenk.add("500");
        jenk.add("1500");
        jenk.add("2000");
        jenk.add("2500");
        jenk.add("3000");
        adapter.setItems(jenk);
        rv_saleprice.setAdapter(adapter);
        rv_saleprice.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSalepriceDecided(defVal, val);
                }
            }
        });

    }

    public void setInitVal(int initVal) {
        val = initVal;
        defVal = initVal;

        ltv_initval.setText(DataUtil.convertCurrency(val));
        ltv_profitval.setText(DataUtil.convertCurrency(0));
        edt_saleprice.setText(val + "");
    }

    @Override
    public void dismiss() {
        if (listener != null) {
            listener.onDialogDismiss();
        }
        parent.getWindow().
                setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getParentView().getWindowToken(), 0);

        super.dismiss();
    }

    public interface salepriceDialogListener {
        void onSalepriceDecided(int initPrice, int salePrice);

        void onDialogDismiss();
    }


}