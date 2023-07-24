package com.example.bookmangerjava.ui.adapter;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmangerjava.databinding.ItemTopmostBinding;
import com.example.bookmangerjava.model.TopMost;

import java.util.List;

public class TopMostAdapter extends RecyclerView.Adapter<TopMostAdapter.ViewHolder> {
    private List<TopMost> data;

    public TopMostAdapter(List<TopMost> list) {
        this.data = list;
    }

    @NonNull
    @Override
    public TopMostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemTopmostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopMostAdapter.ViewHolder holder, int position) {
        TopMost item = data.get(position);
        if (item != null) {
            holder.binding.nameBook.setText(item.getNameBook());
            holder.binding.soLuong.setText("Số lượt mượn: "+item.getSize() + " lần");
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemTopmostBinding binding;

        public ViewHolder(@NonNull ItemTopmostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
