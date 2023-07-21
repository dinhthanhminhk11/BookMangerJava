package com.example.bookmangerjava.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.bookmangerjava.R;
import com.example.bookmangerjava.constant.ApiCallback;
import com.example.bookmangerjava.constant.AppConstant;
import com.example.bookmangerjava.controller.ApiController;
import com.example.bookmangerjava.databinding.ActivityMemberBinding;
import com.example.bookmangerjava.model.Book;
import com.example.bookmangerjava.model.User;
import com.example.bookmangerjava.model.request.RequestDeleteAccount;
import com.example.bookmangerjava.model.response.BodyLoginResponse;
import com.example.bookmangerjava.model.response.BodyResponseAddBook;
import com.example.bookmangerjava.model.response.BodyUserListResponse;
import com.example.bookmangerjava.ui.adapter.LibrarianAdapter;

import java.lang.reflect.Member;
import java.util.List;

public class MemberActivity extends AppCompatActivity {

    private ActivityMemberBinding binding;
    private List<User> userList;
    private LibrarianAdapter librarianAdapter;
    private ApiController apiController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMemberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        librarianAdapter = new LibrarianAdapter(new LibrarianAdapter.Callback() {
            @Override
            public void onEdit(User user, int position) {
                Intent intent = new Intent(MemberActivity.this, LibrarianActivity.class);
                intent.putExtra(AppConstant.USER, user);
                startActivity(intent);
            }

            @Override
            public void onDelete(User user, int position) {
                AlertDialog dialog = new AlertDialog.Builder(MemberActivity.this).setTitle("Notification").setMessage("Are you sure you want to remove").setPositiveButton("Ok", null).setNegativeButton("Cancel", null).show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        apiController.deleteAccount(user.getUsername(), new ApiCallback<BodyLoginResponse>() {
                            @Override
                            public void onSuccess(BodyLoginResponse data) {
                                if (data.getMessage().isStatus()) {
                                    Toast.makeText(MemberActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                                    librarianAdapter.deleteItem(position);
                                } else {
                                    Toast.makeText(MemberActivity.this, data.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Throwable t) {
                                Toast.makeText(MemberActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.cancel();
                    }
                });
            }
        });
        apiController = new ApiController();
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerview.setAdapter(librarianAdapter);
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MemberActivity.this, LibrarianActivity.class));
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
                librarianAdapter.setSearchQuery(searchQuery);
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
            getSupportActionBar().setTitle("Member Manager");
        }
    }

    private void loadData() {
        apiController.getListUser(new ApiCallback<BodyUserListResponse>() {
            @Override
            public void onSuccess(BodyUserListResponse data) {
                binding.progressBar.setVisibility(View.GONE);
                userList = data.getData();
                librarianAdapter.setData(userList);
            }

            @Override
            public void onError(Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(MemberActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}