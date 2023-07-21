package com.example.bookmangerjava.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bookmangerjava.R;
import com.example.bookmangerjava.databinding.ActivityLoanSlipBinding;

public class LoanSlipActivity extends AppCompatActivity {
    private ActivityLoanSlipBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoanSlipBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();

        binding.btnAddKindofbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoanSlipActivity.this, AddLoanSlipActivity.class));
            }
        });
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
}