package com.otto.mart.viewmodule.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.otto.mart.viewmodule.R;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    List<String> items = new ArrayList<>();
    private RecyclerAdapterCallback callback;

    public ItemAdapter(RecyclerAdapterCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_omzet, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.item.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<String> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView item;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.text);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        callback.onItemClick(items.get(getAdapterPosition()),getAdapterPosition(),ItemViewHolder.this);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onItemClick(items.get(getAdapterPosition()), getAdapterPosition(), ItemViewHolder.this);
                }
            });
        }
    }

    public interface onValueClickListener {
    }

}
