package com.example.bookmangerjava.ui.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookmangerjava.R;
import com.example.bookmangerjava.constant.AppConstant;
import com.example.bookmangerjava.databinding.ItemLibrarianBinding;
import com.example.bookmangerjava.model.User;

import java.util.ArrayList;
import java.util.List;

public class LibrarianAdapter extends RecyclerView.Adapter<LibrarianAdapter.ViewHolder> {
    private List<User> data;
    private Callback callback;
    private List<User> filteredData;
    private String searchQuery = "";

    public LibrarianAdapter(Callback callback) {
        this.callback = callback;
        this.filteredData = new ArrayList<>();
    }

    public void setData(List<User> data) {
        this.data = data;
        filterData();
    }

    @NonNull
    @Override
    public LibrarianAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemLibrarianBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LibrarianAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = filteredData.get(position);
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.book2).error(R.drawable.book2);
        if (user != null) {
            holder.binding.name.setText(user.getFullName());
            holder.binding.username.setText(user.getUsername());
            Glide.with(holder.binding.image.getContext()).load(AppConstant.BASE + "/uploads/" + user.getImage()).apply(options).into(holder.binding.image);
            holder.binding.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onEdit(user, position);
                }
            });

            holder.binding.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onDelete(user, position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return filteredData == null ? 0 : filteredData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemLibrarianBinding binding;

        public ViewHolder(ItemLibrarianBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback {
        void onEdit(User user, int position);

        void onDelete(User user, int position);
    }

    private void filterData() {
        filteredData.clear();
        if (TextUtils.isEmpty(searchQuery)) {
            filteredData.addAll(data);
        } else {
            for (User user : data) {
                if (user.getUsername().toLowerCase().contains(searchQuery.toLowerCase()) || user.getFullName().toLowerCase().contains(searchQuery.toLowerCase())) {
                    filteredData.add(user);
                }
            }
        }
        notifyDataSetChanged();
    }


    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        filterData();
    }

    public void deleteItem(int position) {
        if (position >= 0 && position < filteredData.size()) {
            User userToRemove = filteredData.get(position);
            int indexInData = data.indexOf(userToRemove);

            if (indexInData >= 0) {
                data.remove(indexInData);
                filterData();
            }
        }
    }
}
