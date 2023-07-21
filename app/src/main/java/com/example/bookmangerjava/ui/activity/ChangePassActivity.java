package com.example.bookmangerjava.ui.activity;

import static android.text.TextUtils.isEmpty;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookmangerjava.constant.ApiCallback;
import com.example.bookmangerjava.controller.ApiController;
import com.example.bookmangerjava.databinding.ActivityChangePassBinding;
import com.example.bookmangerjava.model.User;
import com.example.bookmangerjava.model.UserClient;
import com.example.bookmangerjava.model.response.BodyLoginResponse;

public class ChangePassActivity extends AppCompatActivity {
    private ActivityChangePassBinding binding;
    private ApiController apiController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiController = new ApiController();
        initToolbar();

        binding.btDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.etmatkhaucu.getText().toString().equals(UserClient.getInstance().getPassword())) {
                    binding.tvghichu.setText("Old Password Incorrect");
                    binding.tvghichu.setTextColor(Color.RED);
                } else if (isEmpty(binding.etmatkhaucu.getText().toString()) || isEmpty(binding.etmatkhaumoi.getText().toString()) || isEmpty(binding.nhaplaimatkhaumoi.getText().toString())) {
                    binding.tvghichu.setText("Fields cannot be left blank");
                    binding.tvghichu.setTextColor(Color.RED);
                } else if (!binding.etmatkhaumoi.getText().toString().equals(binding.nhaplaimatkhaumoi.getText().toString())) {
                    binding.tvghichu.setText("Re-enter Unmatched Password");
                    binding.tvghichu.setTextColor(Color.RED);
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.btDangKi.setEnabled(false);
                    apiController.changePass(new User(UserClient.getInstance().getUsername(), binding.nhaplaimatkhaumoi.getText().toString()), new ApiCallback<BodyLoginResponse>() {
                        @Override
                        public void onSuccess(BodyLoginResponse data) {
                            binding.progressBar.setVisibility(View.GONE);
                            binding.btDangKi.setEnabled(true);
                            Toast.makeText(ChangePassActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                            if (data.getMessage().isStatus()) {
                                UserClient.getInstance().setPassword(binding.nhaplaimatkhaumoi.getText().toString());
                                finish();
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            binding.progressBar.setVisibility(View.GONE);
                            binding.btDangKi.setEnabled(true);
                            Toast.makeText(ChangePassActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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
            getSupportActionBar().setTitle("");
        }
    }
}