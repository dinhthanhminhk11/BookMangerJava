package com.example.bookmangerjava.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.bookmangerjava.MainActivity;
import com.example.bookmangerjava.R;
import com.example.bookmangerjava.constant.ApiCallback;
import com.example.bookmangerjava.constant.AppConstant;
import com.example.bookmangerjava.constant.MySharedPreferences;
import com.example.bookmangerjava.controller.ApiController;
import com.example.bookmangerjava.databinding.ActivityLoginBinding;
import com.example.bookmangerjava.model.User;
import com.example.bookmangerjava.model.UserClient;
import com.example.bookmangerjava.model.response.BodyLoginResponse;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private ApiController apiController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        apiController = new ApiController();

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.etUsername.getText().toString().isEmpty() || binding.etUsername.getText().toString() == null) {
                    binding.tvghichu.setText("You Have Not Entered Username");
                    binding.tvghichu.setTextColor(Color.RED);
                } else if (binding.etPassword.getText().toString().isEmpty() || binding.etPassword.getText().toString() == null) {
                    binding.tvghichu.setText("You Have Not Entered Password");
                    binding.tvghichu.setTextColor(Color.RED);
                } else {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    binding.btLogin.setEnabled(false);

                    apiController.login(new User(binding.etUsername.getText().toString(), binding.etPassword.getText().toString()), new ApiCallback<BodyLoginResponse>() {
                        @Override
                        public void onSuccess(BodyLoginResponse data) {
                            binding.progressBar.setVisibility(View.GONE);
                            binding.btLogin.setEnabled(true);
                            Toast.makeText(LoginActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                            if (data.getMessage().isStatus()) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                UserClient userClient = UserClient.getInstance();
                                userClient.setId(data.getData().getId());
                                userClient.setImage(data.getData().getImage() == null ? "" : data.getData().getImage());
                                userClient.setFullName(data.getData().getFullName());
                                userClient.setUsername(data.getData().getUsername());

                                saveDataLogin();
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            binding.progressBar.setVisibility(View.GONE);
                            binding.btLogin.setEnabled(true);
                            Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataLogin();
    }

    private void saveDataLogin() {
        if (binding.cbLuuThongTin.isChecked()) {
            MySharedPreferences.getInstance(this).putString(AppConstant.LOGIN_USERNAME, binding.etUsername.getText().toString());
            MySharedPreferences.getInstance(this).putString(AppConstant.LOGIN_PASSWORD, binding.etPassword.getText().toString());
            MySharedPreferences.getInstance(this).putBoolean(AppConstant.LOGIN_SAVE, binding.cbLuuThongTin.isChecked());
        }
    }

    private void getDataLogin() {
        boolean check = MySharedPreferences.getInstance(this).getBoolean(AppConstant.LOGIN_SAVE, false);
        if (check) {
            binding.etUsername.setText(MySharedPreferences.getInstance(this).getString(AppConstant.LOGIN_USERNAME, ""));
            binding.etPassword.setText(MySharedPreferences.getInstance(this).getString(AppConstant.LOGIN_PASSWORD, ""));
        } else {
            binding.etUsername.setText("");
            binding.etPassword.setText("");
        }
        binding.cbLuuThongTin.setChecked(check);
    }
}