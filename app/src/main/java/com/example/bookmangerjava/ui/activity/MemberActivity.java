package com.example.bookmangerjava.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bookmangerjava.R;
import com.example.bookmangerjava.databinding.ActivityMemberBinding;

public class MemberActivity extends AppCompatActivity {

    private ActivityMemberBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
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
            getSupportActionBar().setTitle("Member Manager");
        }
    }
}