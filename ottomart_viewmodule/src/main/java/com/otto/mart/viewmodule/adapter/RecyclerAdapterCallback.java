package com.otto.mart.viewmodule.adapter;

import android.support.v7.widget.RecyclerView;

public interface RecyclerAdapterCallback {

    void onItemClick(Object objectModel, int position, RecyclerView.ViewHolder currHolder);


    void onItemClick(Object objectModel, int position, int paymentTypePos, RecyclerView.ViewHolder currHolder);


}
