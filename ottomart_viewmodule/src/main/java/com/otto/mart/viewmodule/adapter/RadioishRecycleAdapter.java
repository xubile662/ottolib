package com.otto.mart.viewmodule.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.otto.mart.viewmodule.R;
import com.otto.mart.viewmodule.model.PickerUiModel.ProductIconModel;

import java.util.List;

import glenn.base.util.DataUtil;

public class RadioishRecycleAdapter extends RecyclerView.Adapter<RadioishRecycleAdapter.AyamHolder> {
    private List<ProductIconModel> models;
    private int layoutID;
    private int selectedPos = -1;
    private int unselectedItemColor = R.color.colorTransparent;
    private int unselectedItemTextColor = R.color.charcoal_grey;
    private int unselectedItemText2Color = R.color.ocean_blue;
    private int selectedItemLayout = R.drawable.border_blue_line;
    private int unselectedItemLayout = R.drawable.border_gey_line;
    private int selectedItemColor;
    private int selectedItemTextColor;
    private boolean isPaketData, isAsu;

    private onItemSelectedListener listener;


    public RadioishRecycleAdapter(List<ProductIconModel> models, int layoutID, int unselectedItemColor, int unselectedItemTextColor, int selectedItemColor, int selectedItemTextColor) {
        this.models = models;
        this.layoutID = layoutID;
        this.unselectedItemColor = unselectedItemColor == 0 ? this.unselectedItemColor : unselectedItemColor;
        this.unselectedItemTextColor = unselectedItemTextColor == 0 ? this.unselectedItemTextColor : unselectedItemTextColor;
        this.selectedItemColor = selectedItemColor;
        this.selectedItemTextColor = selectedItemTextColor;
    }

    public RadioishRecycleAdapter(List<ProductIconModel> models, int layoutID, int unselectedItemColor, int unselectedItemTextColor, int selectedItemColor, int selectedItemTextColor, boolean isPaketData, boolean isAsu) {
        this.models = models;
        this.layoutID = layoutID;
        this.unselectedItemColor = unselectedItemColor == 0 ? this.unselectedItemColor : unselectedItemColor;
        this.unselectedItemTextColor = unselectedItemTextColor == 0 ? this.unselectedItemTextColor : unselectedItemTextColor;
        this.selectedItemColor = selectedItemColor;
        this.selectedItemTextColor = selectedItemTextColor;
        this.isPaketData = isPaketData;
        this.isAsu = isAsu;
    }

    public RadioishRecycleAdapter(List<ProductIconModel> models, int layoutID, int unselectedItemColor, int unselectedItemTextColor, int unselectedItemText2Color, int selectedItemColor, int selectedItemTextColor) {
        this.models = models;
        this.layoutID = layoutID;
        this.unselectedItemColor = unselectedItemColor == 0 ? this.unselectedItemColor : unselectedItemColor;
        this.unselectedItemTextColor = unselectedItemTextColor == 0 ? this.unselectedItemTextColor : unselectedItemTextColor;
        this.unselectedItemText2Color = unselectedItemText2Color == 0 ? this.unselectedItemText2Color : unselectedItemText2Color;
        this.selectedItemColor = selectedItemColor;
        this.selectedItemTextColor = selectedItemTextColor;
    }

    public void setListener(onItemSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public AyamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutID, parent, false);
        return new AyamHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull AyamHolder holder, int position) {
        final ProductIconModel model = models.get(position);
        if (isPaketData) {
            holder.title.setText(model.getProductPrice() + "");
            holder.productPrice.setText(model.getProductDiscountPrice() + "");
        } else if (isAsu) {
            holder.title.setText(model.getTitle());
            holder.productPrice.setText("");
        } else {
            holder.title.setText(DataUtil.cleanDigit(model.getTitle()) + " " + DataUtil.convertCurrency(DataUtil.convertLongFromCurrency(model.getTitle())).replace("Rp. ", ""));
            holder.productPrice.setText(DataUtil.cleanDigit(model.getProductPrice()) + " " + DataUtil.convertCurrency(DataUtil.convertLongFromCurrency(model.getProductPrice())).replace("Rp. ", ""));
        }
        holder.productDiscountPrice.setText(DataUtil.cleanDigit(model.getProductDiscountPrice()) + " " + DataUtil.convertCurrency(DataUtil.convertLongFromCurrency(model.getProductDiscountPrice())));
        try {
            if (model.getIconID() != 0) {
                holder.icon.setImageResource(model.getIconID());
                holder.icon.setVisibility(View.VISIBLE);
                holder.productPrice.setVisibility(View.GONE);
                holder.productDiscountPrice.setVisibility(View.GONE);
                if (!isAsu)
                    holder.title.setVisibility(View.GONE);
            } else if (model.getIconID() == 0 && model.getIconUri() != null) {
                Glide.with(holder.icon).load(model.getIconUri()).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)).into(holder.icon);
                holder.icon.setVisibility(View.VISIBLE);
                holder.productPrice.setVisibility(View.GONE);
                holder.productDiscountPrice.setVisibility(View.GONE);
                if (!isAsu)
                    holder.title.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            Log.e("ICADPT: ", model.getTitle() + " no icon!");
        }

        final int tempPos = position;
        holder.hitbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPos != tempPos) {
                    deselectAll();
                    model.setSelected(true);
                    selectedPos = tempPos;
                    if (listener != null) {
                        listener.onItemSelected(model);
                    }
                } else {
                    model.setSelected(false);
                    selectedPos = -1;
                    if (listener != null) {
                        listener.onListDeselect();
                    }
                }
                notifyDataSetChanged();
            }

        });

        if (model.isSelected()) {
            holder.select();
        } else if (!model.isSelected()) {
            holder.deselect();
        }
    }

    public void deselectAll() {
        for (ProductIconModel model :
                models) {
            model.setSelected(false);
        }
        if (listener != null) {
            listener.onListDeselect();
        }
    }

    public ProductIconModel getSelectedItem() {
        return models.get(selectedPos);
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    public ProductIconModel getItemAt(int pos) {
        return models.get(pos);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class AyamHolder extends RecyclerView.ViewHolder {
        private ViewGroup hitbox;
        private ImageView icon;
        private TextView title, productPrice, productDiscountPrice;

        public AyamHolder(View itemView) {
            super(itemView);
            hitbox = itemView.findViewById(R.id.hitbox);
            icon = itemView.findViewById(R.id.product_imgv);
            title = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productDiscountPrice = itemView.findViewById(R.id.product_discount_price);
        }

        public void select() {
            try {
                ((CardView) hitbox).setCardBackgroundColor(ContextCompat.getColor(hitbox.getContext(), selectedItemColor));
                title.setTextColor(ContextCompat.getColor(title.getContext(), selectedItemTextColor));
                productPrice.setTextColor(ContextCompat.getColor(productPrice.getContext(), selectedItemTextColor));
                productDiscountPrice.setTextColor(ContextCompat.getColor(productDiscountPrice.getContext(), selectedItemTextColor));
            } catch (Exception e) {
                ((LinearLayout) hitbox).setBackgroundResource(selectedItemLayout);
            }
        }

        public void deselect() {
            try {
                ((CardView) hitbox).setCardBackgroundColor(ContextCompat.getColor(hitbox.getContext(), unselectedItemColor));
                title.setTextColor(ContextCompat.getColor(title.getContext(), unselectedItemTextColor));
                productPrice.setTextColor(ContextCompat.getColor(productPrice.getContext(), unselectedItemTextColor));
                productDiscountPrice.setTextColor(ContextCompat.getColor(productDiscountPrice.getContext(), unselectedItemText2Color));
            } catch (Exception e) {
                ((LinearLayout) hitbox).setBackgroundResource(unselectedItemLayout);
            }
        }
    }

    public interface onItemSelectedListener {
        void onItemSelected(ProductIconModel selectedModel);

        void onListDeselect();
    }

}