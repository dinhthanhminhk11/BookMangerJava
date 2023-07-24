package com.example.bookmangerjava.ui.adapter;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmangerjava.R;
import com.example.bookmangerjava.databinding.ItemBookBinding;
import com.example.bookmangerjava.model.Book;
import com.example.bookmangerjava.model.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private List<Book> data;
    private TextView edit;
    private TextView add;
    private TextView delete;
    private TextView cancel;
    private String searchQuery = "";
    private Callback callback;
    private List<Book> filteredData;

    public BookAdapter(Callback callback) {
        this.callback = callback;
        this.filteredData = new ArrayList<>();
    }

    public void setData(List<Book> data) {
        this.data = data;
        filterData();
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        Book book = filteredData.get(position);
        if (book != null) {
            float totalMoney = (book.getPrice()) * (Float.parseFloat(String.valueOf(book.getDiscount())) / 100);
            float totalMoney1 = (book.getPrice()) - totalMoney;
            double totalMoney2 = (double) totalMoney1;
            double roundOff = (double) Math.round(totalMoney2 * 100) / 100;
            holder.binding.idGiaSachGoc.setPaintFlags(holder.binding.idGiaSachGoc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.binding.nameBook.setText(book.getName());
            holder.binding.nameKindofBookItem.setText(book.getNameCategory());
            holder.binding.idGiaSachGoc.setText(book.getPrice() + "$");
            holder.binding.idGiamGia.setText(book.getDiscount() + "$");
            holder.binding.idGiaSachDatinh.setText(roundOff + "$");

            holder.itemView.setOnClickListener(v -> {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(holder.itemView.getContext(), R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.bottomsheet, (LinearLayout) bottomSheetDialog.findViewById(R.id.bottomsheetcontainer));
                edit = (TextView) bottomSheetView.findViewById(R.id.edit);
                add = (TextView) bottomSheetView.findViewById(R.id.add);
                delete = (TextView) bottomSheetView.findViewById(R.id.delete);
                cancel = (TextView) bottomSheetView.findViewById(R.id.cancel);
                edit.setOnClickListener(view1 -> {
                    callback.onEdit(book, position);
                    bottomSheetDialog.cancel();
                });
                add.setOnClickListener(view1 -> {
                    callback.onAdd(book, position);
                    bottomSheetDialog.cancel();
                });
                delete.setOnClickListener(view1 -> {
                    callback.onDelete(book, position);
                    bottomSheetDialog.cancel();
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        bottomSheetDialog.dismiss();
                    }
                });
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
            });
        }
    }

    @Override
    public int getItemCount() {
        return filteredData == null ? 0 : filteredData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemBookBinding binding;

        public ViewHolder(@NonNull ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface Callback {
        void onEdit(Book book, int position);

        void onDelete(Book book, int position);

        void onAdd(Book book, int position);
    }

    private void filterData() {
        filteredData.clear();
        if (TextUtils.isEmpty(searchQuery)) {
            filteredData.addAll(data);
        } else {
            for (Book book : data) {
                if (book.getName().toLowerCase().contains(searchQuery.toLowerCase())) {
                    filteredData.add(book);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        filterData();
    }

    public void addItem(Book book) {
        data.add(book);
        filterData();
    }

    public void updateItem(Book updatedBook, int position) {
        if (position >= 0 && position < data.size()) {
            data.set(position, updatedBook);
            filterData();
        }
    }

    public void deleteItem(int position) {
        if (position >= 0 && position < filteredData.size()) {
            Book userToRemove = filteredData.get(position);
            int indexInData = data.indexOf(userToRemove);

            if (indexInData >= 0) {
                data.remove(indexInData);
                filterData();
            }
        }
    }
}
