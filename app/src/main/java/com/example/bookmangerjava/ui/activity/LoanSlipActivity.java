package com.example.bookmangerjava.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bookmangerjava.R;
import com.example.bookmangerjava.constant.ApiCallback;
import com.example.bookmangerjava.controller.ApiController;
import com.example.bookmangerjava.databinding.ActivityLoanSlipBinding;
import com.example.bookmangerjava.model.Book;
import com.example.bookmangerjava.model.LoanSlip;
import com.example.bookmangerjava.model.response.BodyListLoanSlip;
import com.example.bookmangerjava.model.response.BodyLoanSlipResponse;
import com.example.bookmangerjava.model.response.BodyResponseAddBook;
import com.example.bookmangerjava.ui.adapter.LoanSlipAdapter;

import java.util.List;

public class LoanSlipActivity extends AppCompatActivity implements LoanSlipAdapter.Callback {
    private ActivityLoanSlipBinding binding;
    private ApiController apiController;
    private LoanSlipAdapter loanSlipAdapter;
    private List<LoanSlip> loanSlipList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoanSlipBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();

        apiController = new ApiController();
        loanSlipAdapter = new LoanSlipAdapter(this);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerview.setAdapter(loanSlipAdapter);
        binding.btnAddKindofbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoanSlipActivity.this, AddLoanSlipActivity.class));
            }
        });

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String searchQuery = editable.toString().trim();
                loanSlipAdapter.setSearchQuery(searchQuery);
            }
        });

        binding.radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.unpaid) {
                    loanSlipAdapter.filterUnpaidItems();
                } else if (checkedId == R.id.paid) {
                    loanSlipAdapter.filterPaidItems();
                } else {
                    loadData();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("Loan Slip Manager");
        }
    }

    private void loadData() {
        apiController.getListLoanSlip(new ApiCallback<BodyListLoanSlip>() {
            @Override
            public void onSuccess(BodyListLoanSlip data) {
                loanSlipList = data.getData();
                loanSlipAdapter.setData(loanSlipList);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    @Override
    public void onEdit(LoanSlip loanSlip, int position) {
        apiController.updateLoanSlip(loanSlip.get_id(), new ApiCallback<BodyLoanSlipResponse>() {
            @Override
            public void onSuccess(BodyLoanSlipResponse data) {
                if (data.getMessage().isStatus()) {
                    Toast.makeText(LoanSlipActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                    loanSlipAdapter.updateItem(data.getData(), position);
                } else {
                    Toast.makeText(LoanSlipActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    @Override
    public void onDelete(LoanSlip loanSlip, int position) {
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("Notification").setMessage("Are you sure you want to remove").setPositiveButton("Ok", null).setNegativeButton("Cancel", null).show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiController.deleteLoanSlip(loanSlip.get_id(), new ApiCallback<BodyLoanSlipResponse>() {
                    @Override
                    public void onSuccess(BodyLoanSlipResponse data) {
                        if (data.getMessage().isStatus()) {
                            Toast.makeText(LoanSlipActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                            loanSlipAdapter.deleteItem(position);
                        } else {
                            Toast.makeText(LoanSlipActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Toast.makeText(LoanSlipActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.cancel();
            }
        });
    }
}