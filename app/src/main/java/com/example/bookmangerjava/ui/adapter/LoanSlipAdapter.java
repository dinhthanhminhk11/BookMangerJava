package com.example.bookmangerjava.ui.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookmangerjava.constant.TimeUtils;
import com.example.bookmangerjava.databinding.ItemLoanslipBinding;
import com.example.bookmangerjava.model.Book;
import com.example.bookmangerjava.model.LoanSlip;

import java.util.ArrayList;
import java.util.List;

public class LoanSlipAdapter extends RecyclerView.Adapter<LoanSlipAdapter.ViewHolder> {
    private List<LoanSlip> data;
    private String searchQuery = "";
    private Callback callback;
    private List<LoanSlip> filteredData;
    private boolean isUpdatingStatus = false;

    public LoanSlipAdapter(Callback callback) {
        this.callback = callback;
        this.filteredData = new ArrayList<>();
    }

    public void setData(List<LoanSlip> data) {
        this.data = data;
        filterData();
    }

    @NonNull
    @Override
    public LoanSlipAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemLoanslipBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LoanSlipAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LoanSlip item = filteredData.get(position);
        if (item != null) {
            holder.binding.tenNGuoiMuon.setText(item.getNameUser());
            holder.binding.phone.setText(item.getPhoneUser());
            holder.binding.tenSach.setText("Name book: " + item.getNameBook());
            holder.binding.timeStart.setText("Time mượn: " + TimeUtils.getTimeAgo(item.getBorrowedDate()));

            holder.binding.status.setChecked(item.isPay());
            if (item.isPay()) {
                holder.binding.estimatedTimeOfPayment.setText("Time trả thực tế: " + TimeUtils.getTimeAgo(item.getActualPaymentDate()));
                holder.binding.trangThai.setText("Paid");
                holder.binding.trangThai.setTextColor(Color.GREEN);
                holder.binding.status.setEnabled(false);
            } else {
                holder.binding.estimatedTimeOfPayment.setText("Time trả dự kiến: " + TimeUtils.getTimeAgo(item.getPayDay()));
                holder.binding.trangThai.setText("Unpaid");
                holder.binding.trangThai.setTextColor(Color.RED);
            }

            holder.binding.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onDelete(item, position);
                }
            });

            holder.binding.status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(holder.binding.status.getContext()).setTitle("Confirmation").setMessage("Are you sure you want to edit this loan slip " + item.getNameUser() + " to paid").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isUpdatingStatus = true;
                            callback.onEdit(item, position);
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            isUpdatingStatus = true;
                            holder.binding.status.setChecked(false);
                            isUpdatingStatus = false;
                            dialog.dismiss();
                        }
                    }).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return filteredData == null ? 0 : filteredData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemLoanslipBinding binding;

        public ViewHolder(@NonNull ItemLoanslipBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void filterData() {
        filteredData.clear();
        if (TextUtils.isEmpty(searchQuery)) {
            filteredData.addAll(data);
        } else {
            for (LoanSlip loanSlip : data) {
                if (loanSlip.getNameUser().toLowerCase().contains(searchQuery.toLowerCase())) {
                    filteredData.add(loanSlip);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
        filterData();
    }

    public void updateItem(LoanSlip loanSlip, int position) {
        if (position >= 0 && position < data.size()) {
            data.set(position, loanSlip);
            filterData();
        }
    }

    public void deleteItem(int position) {
        if (position >= 0 && position < filteredData.size()) {
            LoanSlip loanSlip = filteredData.get(position);
            int indexInData = data.indexOf(loanSlip);

            if (indexInData >= 0) {
                data.remove(indexInData);
                filterData();
            }
        }
    }

    public void filterPaidItems() {
        filteredData.clear();
        for (LoanSlip loanSlip : data) {
            if (loanSlip.isPay()) {
                filteredData.add(loanSlip);
            }
        }
        notifyDataSetChanged();
    }

    public void filterUnpaidItems() {
        filteredData.clear();
        for (LoanSlip loanSlip : data) {
            if (!loanSlip.isPay()) {
                filteredData.add(loanSlip);
            }
        }
        notifyDataSetChanged();
    }

    public interface Callback {
        void onEdit(LoanSlip loanSlip, int position);

        void onDelete(LoanSlip loanSlip, int position);
    }
}
