package com.example.bookmangerjava.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmangerjava.databinding.ItemKindOfBookBinding;
import com.example.bookmangerjava.model.KindOfBook;

import java.util.List;

public class KindOfBookAdapter extends RecyclerView.Adapter<KindOfBookAdapter.ViewHolder> {

    private List<KindOfBook> data;

    private Callback callback;

    public void setData(List<KindOfBook> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public KindOfBookAdapter( Callback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public KindOfBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemKindOfBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KindOfBookAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        KindOfBook item = data.get(position);
        if (item != null) {
            holder.binding.name.setText(item.getName());
            holder.binding.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onEdit(item , position);
                }
            });

            holder.binding.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onDelete(item, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemKindOfBookBinding binding;

        public ViewHolder(ItemKindOfBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback {
        void onEdit(KindOfBook kindOfBook, int position);

        void onDelete(KindOfBook kindOfBook, int position);
    }
}
