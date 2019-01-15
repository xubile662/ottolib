package com.otto.mart.viewmodule.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.otto.mart.viewmodule.R;
import com.otto.mart.viewmodule.dialog.ChainedListDialog;
import com.otto.mart.viewmodule.dialog.IChainedDialog;
import com.otto.mart.viewmodule.viewModels.ChainedListDialogModel;

import java.util.List;

public class ChainedListDialogAdapter extends RecyclerView.Adapter<ChainedListDialogAdapter.KucingHolder> {

    private Context mContext;
    private IChainedDialog parent;
    private List<ChainedListDialogModel> models;
    private int layoutId = R.layout.item_filter_category;
    private int selectedColorId = R.color.colorWhite;
    private int unSelectedColorId = R.color.white_three;
    private int selectedSeparatorColor = R.color.lanc;
    private int unSelectedSepeparatorColor = R.color.charcoal_grey14;
    private boolean isPrimary;

    private static int checkedPos = 0;
    private boolean isFirstTime = true;

    public ChainedListDialogAdapter(IChainedDialog parent, Context context, List<ChainedListDialogModel> models) {
        mContext = context;
        this.models = models;
        isPrimary = true;
        this.parent = parent;
    }

    public ChainedListDialogAdapter(IChainedDialog parent, Context mContext, List<ChainedListDialogModel> models, int layoutId, int selectedColorId, int unSelectedColorId, int selectedSeparatorColor, int unSelectedSepeparatorColor) {
        this.mContext = mContext;
        this.models = models;
        this.layoutId = layoutId;
        this.selectedColorId = selectedColorId;
        this.unSelectedColorId = unSelectedColorId;
        this.selectedSeparatorColor = selectedSeparatorColor;
        this.unSelectedSepeparatorColor = unSelectedSepeparatorColor;
        isPrimary = false;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ChainedListDialogAdapter.KucingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        return new ChainedListDialogAdapter.KucingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KucingHolder holder, final int position) {
        ChainedListDialogModel model = models.get(position);
        if (isFirstTime) {
            isFirstTime = false;
            model.setSelected(true);
        }
        holder.tv.setText(model.getName());
        holder.hitbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectAll();
                model.setSelected(true);
                checkedPos = position;
                notifyDataSetChanged();
                if (isPrimary) {
                    parent.primaryCallback(position);
                } else {
                    parent.secondaryCallback(position);
                }
            }
        });
        if (model.isSelected()) {
            holder.hitbox.setBackgroundColor(ContextCompat.getColor(mContext, selectedColorId));
            holder.separator.setBackgroundColor(ContextCompat.getColor(mContext, selectedSeparatorColor));
        } else {
            holder.hitbox.setBackgroundColor(ContextCompat.getColor(mContext, unSelectedColorId));
            holder.separator.setBackgroundColor(ContextCompat.getColor(mContext, unSelectedSepeparatorColor));
        }
    }

    private void deselectAll() {
        for (ChainedListDialogModel model :
                models) {
            model.setSelected(false);
        }
    }

    public void replaceAllItems(List<ChainedListDialogModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class KucingHolder extends RecyclerView.ViewHolder {
        private View hitbox, separator;
        private TextView tv;

        public KucingHolder(View itemView) {
            super(itemView);
            hitbox = itemView.findViewById(R.id.container);
            separator = itemView.findViewById(R.id.category_separator);
            tv = itemView.findViewById(R.id.category);
        }
    }
}
