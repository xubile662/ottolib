package com.otto.mart.viewmodule.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.otto.mart.viewmodule.R;
import com.otto.mart.viewmodule.adapter.FavoriteDialogAdapter;
import com.otto.mart.viewmodule.viewModels.FavoriteItemModel;

import java.util.List;

import glenn.base.viewmodule.dialog.LazyDialog;

public class FavoriteDialog extends LazyDialog {

    FavoriteDialogInterface listener;
    FavoriteDialogAdapter adapter;
    View contentView, labelEmpty;
    RecyclerView rv_items;
    Context mContext;

    public FavoriteDialog(@NonNull Context context, Activity parent, Boolean isHideToolbar, Boolean hideCloseBtn) {
        super(context, parent, isHideToolbar, hideCloseBtn);
        mContext = context;
        initComponent();
        initContent();
    }

    public void setListener(FavoriteDialogInterface listener) {
        this.listener = listener;
    }

    private void initComponent() {
        contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_ppob_favorite, null);
        this.setContainerView(contentView);
        this.getToolbarView().setBackgroundColor(ContextCompat.getColor(mContext, R.color.ocean_blue));
        this.setToolbarDarkmode();
        rv_items = contentView.findViewById(R.id.favoriteList);
        labelEmpty = contentView.findViewById(R.id.labelEmpty);
        adapter = new FavoriteDialogAdapter();
    }

    private void initContent() {
        rv_items.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv_items.setAdapter(adapter);
        adapter.setListener(new FavoriteDialogAdapter.favoriteAdapterListener() {
            @Override
            public void onItemSelected(FavoriteItemModel model) {
                if (listener != null)
                    listener.OnItemSelected(FavoriteDialog.this, model);
            }

            @Override
            public void onDelete(FavoriteItemModel model) {
                if (listener != null)
                    listener.OnDeleteItemSelected(FavoriteDialog.this, model);
            }
        });
        this.setTitle("Favorite");
    }

    public void addItem(List<FavoriteItemModel> odel) {
        if (adapter != null) {
            adapter.replaceModel(odel);
            labelEmpty.setVisibility(View.GONE);
        } else {
            labelEmpty.setVisibility(View.VISIBLE);
        }
    }

    public interface FavoriteDialogInterface {
        void OnItemSelected(FavoriteDialog dialog, FavoriteItemModel model);

        void OnDeleteItemSelected(FavoriteDialog dialog, FavoriteItemModel model);
    }

    @Override
    public void dismiss() {
        adapter.setDeleteModel(false);
        super.dismiss();
    }
}


