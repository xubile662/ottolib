package com.otto.mart.viewmodule.model.PickerUiModel;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

public class ProductIconModel extends DashboardIconModel {
    private String productPrice;
    private String productDiscountPrice;
    private boolean isSelected;
    private View.OnClickListener listener;
    private Object storedModel;
    private String iconUri;



    public ProductIconModel(String title, Intent target, int iconID, @Nullable String extradataString, @Nullable int extradataInt, String productPrice, @Nullable String productDiscountPrice) {
        super(title, target, iconID, extradataString, extradataInt);
        this.productPrice = productPrice;
        this.productDiscountPrice = productDiscountPrice;
    }

    public ProductIconModel(String title, Intent target, int iconID, @Nullable String extradataString, @Nullable int extradataInt, String productPrice, String productDiscountPrice, View.OnClickListener listener) {
        super(title, target, iconID, extradataString, extradataInt);
        this.productPrice = productPrice;
        this.productDiscountPrice = productDiscountPrice;
        this.listener = listener;
    }

    public ProductIconModel(String title, Intent target, int iconID, @Nullable String extradataString, @Nullable int extradataInt, String productPrice, String productDiscountPrice, View.OnClickListener listener, Object storedModel) {
        super(title, target, iconID, extradataString, extradataInt);
        this.productPrice = productPrice;
        this.productDiscountPrice = productDiscountPrice;
        this.listener = listener;
        this.storedModel = storedModel;
    }

    public ProductIconModel(String title, Intent target, int iconID, @Nullable String extradataString, @Nullable int extradataInt, String productPrice, String productDiscountPrice, View.OnClickListener listener, String iconUri, Object storedModel) {
        super(title, target, iconID, extradataString, extradataInt);
        this.productPrice = productPrice;
        this.productDiscountPrice = productDiscountPrice;
        this.listener = listener;
        this.storedModel = storedModel;
        this.iconUri = iconUri;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDiscountPrice() {
        return productDiscountPrice;
    }

    public void setProductDiscountPrice(String productDiscountPrice) {
        this.productDiscountPrice = productDiscountPrice;
    }

    public View.OnClickListener getListener() {
        return listener;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Object getStoredModel() {
        return storedModel;
    }

    public void setStoredModel(Object storedModel) {
        this.storedModel = storedModel;
    }

    public String getIconUri() {
        return iconUri;
    }

    public void setIconUri(String iconUri) {
        this.iconUri = iconUri;
    }
}
