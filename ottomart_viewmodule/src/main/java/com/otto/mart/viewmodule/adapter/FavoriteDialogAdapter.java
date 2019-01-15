package com.otto.mart.viewmodule.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.otto.mart.viewmodule.R;
import com.otto.mart.viewmodule.viewModels.FavoriteItemModel;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDialogAdapter extends RecyclerView.Adapter<FavoriteDialogAdapter.FavoriteViewHolder> {
    private boolean isDeleteModel = false;
    List<FavoriteItemModel> models = new ArrayList<>();

    private favoriteAdapterListener listener;

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false));
    }

    public void setListener(favoriteAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        if (models.size() > 0) {
            FavoriteItemModel model = models.get(position);
            holder.tv_item.setText(model.getCustomer_reference());
            holder.container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    isDeleteModel = !isDeleteModel;
                    if (isDeleteModel) {
                        holder.tv_delete.setVisibility(View.VISIBLE);
                    }
                    return false;
                }
            });

            holder.tv_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemSelected(model);

                }
            });

            holder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onDelete(model);
                }
            });
        }
    }

    public void setDeleteModel(boolean deleteModel) {
        isDeleteModel = deleteModel;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void replaceModel(List<FavoriteItemModel> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        TextView tv_item, tv_delete;
        View container;

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            tv_item = itemView.findViewById(R.id.tv_item);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            container = itemView.findViewById(R.id.container_favorite);
        }
    }

    public interface favoriteAdapterListener {
        void onItemSelected(FavoriteItemModel model);

        void onDelete(FavoriteItemModel model);
    }
}