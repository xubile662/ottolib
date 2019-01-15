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
import android.widget.TextView;

import com.otto.mart.viewmodule.R;
import com.otto.mart.viewmodule.adapter.ChainedListDialogAdapter;
import com.otto.mart.viewmodule.viewModels.ChainedListDialogModel;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import glenn.base.viewmodule.dialog.LazyDialog;

public class ChainedListDialog extends LazyDialog implements IChainedDialog {


    View contentView;
    Context mContext;
    LazyDialog mDialog;
    ChainedListDialogAdapter primaryAdapter, secondaryAdapter;

    ChainedListDialogListener listener;
    List<ChainedListDialogModel> filterModel, filterSubModel;
    ExpandableLayout elayout_content, elayout_loading;
    RecyclerView rv_category, rv_subcategory;

    public ChainedListDialog(@NonNull Context context, Activity parent, Boolean hideCloseBtn) {
        super(context, parent, false, hideCloseBtn);
        mContext = context;
        mDialog = this;
        initComponent();
        initContent();
    }

    public ChainedListDialog(@NonNull Context context, Activity parent, List<ChainedListDialogModel> filterModel, Boolean hideCloseBtn) {
        super(context, parent, hideCloseBtn);
        mContext = context;
        mDialog = this;
        this.filterModel = filterModel;
        initComponent();
        initContent();
    }

    public void setListener(ChainedListDialogListener listener) {
        this.listener = listener;
    }

    private void initComponent() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_shopfilter, null);
        this.setContainerView(contentView);
        rv_category = contentView.findViewById(R.id.rv_category);
        rv_subcategory = contentView.findViewById(R.id.rv_subcategory);
        elayout_content = contentView.findViewById(R.id.eLayout_content);
        elayout_loading = contentView.findViewById(R.id.eLayout_loading);
        filterModel = new ArrayList<>();
        filterSubModel = new ArrayList<>();
        this.getToolbarView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.ocean_blue));
        this.setToolbarDarkmode();

        Window window = this.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);

        TextView titleTv = this.getToolbarView().findViewById(R.id.title);
        titleTv.setText("Jenis Usaha");
    }

    private void initContent() {
        if (filterModel.size() == 0) {
            setLoadingState();
        }
        primaryAdapter = new ChainedListDialogAdapter(this, getContext(), filterModel);
        secondaryAdapter = new ChainedListDialogAdapter(this, getContext(), filterSubModel, R.layout.item_filter_subcategory, R.color.colorWhite, R.color.colorWhite, R.color.white_six, R.color.white_six);

        rv_category.setAdapter(primaryAdapter);
        rv_category.setLayoutManager(new LinearLayoutManager(mContext));

        rv_subcategory.setAdapter(secondaryAdapter);
        rv_subcategory.setLayoutManager(new LinearLayoutManager(mContext));
    }

    public void setLoadingState() {
        elayout_loading.setExpanded(true);
        elayout_content.setExpanded(false);
    }

    public void setNotLoadingState() {
        elayout_loading.setExpanded(false);
        elayout_content.setExpanded(true);
        filterSubModel = filterModel.get(0).getBusiness_categories();
        secondaryAdapter.replaceAllItems(filterSubModel);
    }

    @Override
    public void primaryCallback(int val) {
        filterSubModel = filterModel.get(val).getBusiness_categories();
        secondaryAdapter.replaceAllItems(filterSubModel);
    }

    @Override
    public void secondaryCallback(int val) {
        if (listener != null) {
            listener.onSecondaryCallback(filterSubModel.get(val));
        }
        this.dismiss();
    }

    public interface ChainedListDialogListener {
        void onSecondaryCallback(ChainedListDialogModel returnItem);
    }

    @Override
    public void show() {
        super.show();
    }

    public void addCategoryData(List<ChainedListDialogModel> models) {
        filterModel = models;
        primaryAdapter.replaceAllItems(models);
        setNotLoadingState();
    }

}
