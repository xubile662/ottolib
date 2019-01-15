package com.otto.mart.viewmodule.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.otto.mart.viewmodule.R;
import com.otto.mart.viewmodule.adapter.RadioishRecycleAdapter;
import com.otto.mart.viewmodule.model.PickerUiModel.ProductIconModel;

import java.util.List;

public class ProductPickerViewGroup extends LinearLayout {
    private View parent;
    private RecyclerView rv;
    private TextView title;
    private int pLayoutID = R.layout.cw_selector_product, cLayoutID = R.layout.item_pulsa;
    private boolean isInitialized;
    private RadioishRecycleAdapter adapter;

    private onProductSelected listener;
    private boolean isPaketData, isAsu;

    public ProductPickerViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void initLayout(@Nullable String title, List<ProductIconModel> models, LinearLayoutManager lm) {
        if (!isInitialized) {
            isInitialized = !isInitialized;
            initComponent(title);
            initContent(models, lm, null);
        }
    }

    public void initLayout(@Nullable String title, List<ProductIconModel> models, LinearLayoutManager lm, RecyclerView.ItemDecoration decorator) {
        if (!isInitialized) {
            isInitialized = !isInitialized;
            initComponent(title);
            initContent(models, lm, decorator);
        }
    }


    public void initLayout(@Nullable String title, List<ProductIconModel> models, int parentLayoutId, int childLayoutId, LinearLayoutManager lm, RecyclerView.ItemDecoration decorator) {
        if (!isInitialized) {
            initLayout(title, models, parentLayoutId, childLayoutId, lm, decorator, false, false);
        }
    }

    public void initLayout(@Nullable String title, List<ProductIconModel> models, int parentLayoutId, int childLayoutId, LinearLayoutManager lm, RecyclerView.ItemDecoration decorator, boolean isPaketData) {
        if (!isInitialized) {
            pLayoutID = parentLayoutId;
            cLayoutID = childLayoutId;
            isInitialized = !isInitialized;
            this.isPaketData = isPaketData;
            initComponent(title);
            initContent(models, lm, decorator);
        }
    }

    public void initLayout(@Nullable String title, List<ProductIconModel> models, int parentLayoutId, int childLayoutId, LinearLayoutManager lm, RecyclerView.ItemDecoration decorator, boolean isPaketData, boolean isAsu) {
        if (!isInitialized) {
            pLayoutID = parentLayoutId;
            cLayoutID = childLayoutId;
            isInitialized = !isInitialized;
            this.isPaketData = isPaketData;
            this.isAsu = isAsu;
            initComponent(title);
            initContent(models, lm, decorator);
        }
    }

    private void initComponent(String titleText) {
        parent = LayoutInflater.from(getContext()).inflate(pLayoutID, this, true);
        title = parent.findViewById(R.id.title);
        if (titleText != null) {
            title.setVisibility(VISIBLE);
            title.setText(titleText);
        }

        rv = parent.findViewById(R.id.rv);
        rv.setNestedScrollingEnabled(false);
    }

    public void setListener(onProductSelected listener) {
        this.listener = listener;
    }


    private void initContent(List<ProductIconModel> models, LinearLayoutManager lm, RecyclerView.ItemDecoration decorator) {
        adapter = new RadioishRecycleAdapter(models, cLayoutID, 0, 0, R.color.ocean_blue, R.color.color_white, isPaketData, isAsu);
        rv.setLayoutManager(lm);
        if (decorator != null) {
            rv.addItemDecoration(decorator);
        }
        rv.setAdapter(adapter);

        adapter.setListener(new RadioishRecycleAdapter.onItemSelectedListener() {
            @Override
            public void onItemSelected(ProductIconModel selectedModel) {
                if (listener != null) {
                    listener.onProductSelected(selectedModel);
                }
            }

            @Override
            public void onListDeselect() {
                if (listener != null) {
                    listener.onListDeselected();
                }
            }
        });
    }

    public Object getSelectedStoreObjectModel() {
        return adapter.getSelectedItem().getStoredModel();
    }

    public void notifyAdapterForDatasetChange() {
        adapter.notifyDataSetChanged();
    }

    public int getSelectedPos() {
        return adapter.getSelectedPos();
    }

    public interface onProductSelected {

        void onProductSelected(ProductIconModel selectedItem);

        void onListDeselected();
    }


}